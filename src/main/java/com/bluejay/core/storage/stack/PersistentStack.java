package com.bluejay.core.storage.stack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bluejay.core.exception.FCException;
import com.bluejay.core.index.FCIndex;
import com.bluejay.core.index.IndexEquals;
import com.bluejay.core.index.IndexParser;
import com.bluejay.core.queue.FCTask;
import com.bluejay.core.queue.IndexTask;
import com.bluejay.core.queue.Messenger;
import com.bluejay.core.serializer.FCSerializer;
import com.bluejay.core.storage.node.FCNode;
import com.bluejay.core.utils.JsonUtils;
import com.bluejay.logger.Logger;

/**
 * <p>
 * Implementation of the Stack that persists data into the DB.
 * </p>
 * <p>
 * PersistentSatck allows the data to be saved and indexed accordingly to the mapping file 
 * used. The _mapping files needs to be created and placed into the storage main folder.
 * <br>
 * All the changes made are done into the clusters in memory. The clusters are loaded as needed
 * and are just saved when the commit method is called. 
 * </p>
 * <p>
 * The commit() triggers the refresh of the indexes enabling search into the added/updated  data
 * <br>
 * The commit(false) or commitNoIndex() just persist the data and do not refresh of the indexes.
 * Data added to the cluster and persisted cannot be retrieved using the filters.
 * </p>
 * @author Marcos Costa
 */
public class PersistentStack extends BasicStack {

	Logger log = new Logger(PersistentStack.class);
	Map<String, FCIndex> indexesMap;	

	public PersistentStack(String dataType){
		super(dataType);
	}
	
	/**
	 * <p>Commits all the changes made to the stackNodes. The data gets persisted into disk.</p> 
	 * <p>This method update the indexes asynchronously.</p>
	 * 
	 */
	@Override
	public void commit() throws FCException {
		commit(true);
	}
	
	/**
	 * <p>Commits all the changes made to the stackNodes. The data gets persisted into disk.</p> 
	 * <p>If refreshIndex id true then it will call refreshIndex asynchronously otherwise indexes 
	 * will not be updated</p>
	 * 
	 * @param boolean refreshIndex
	 */
	@Override
	public void commit(boolean refreshIndex) throws FCException {
		log.startTimer();
		for (Long id : cluster.keySet()) {
			searializeCluster(id);
			log.info("Committed cluster " + id);
		}
		if (refreshIndex) {
			FCTask task = new IndexTask(getDataType(), cluster.keySet());
			Messenger.INSTANCE.asyncProcess(task);
		} else {
			log.info("Commited stack without indexes in " + log.endTimer() + " ms");
		}
	}

	/**
	 * Roll-back all the changes made to the stackNodes. Remove the changes made in memory.
	 *  
	 */
	@Override
	public void rollback() throws FCException {
		super.rollback();
	}
	
	/*
	 * Persist cluster into the storage folder
	 * 
	 * @param id
	 * @throws FCException
	 */
	private void searializeCluster(Long id) throws FCException {
		FCSerializer.write(getDataType(), Cluster.DEFAULT_STORAGE_TYPE, cluster.get(id).getFirstNode(), id+"");
	}
	
	@Override
	public void refreshIndexForClusters(Set<Long> clusterIds) throws FCException {
		if (clusterIds == null) return;
		buildIndexMap(getDataType());
		for (Long clusterId : clusterIds) {
			refreshIndexForSingleCluster(clusterId);
		}
	}
	
	public void refreshIndexForSingleCluster(Long clusterId) throws FCException {
		if (clusterId == null) return;
		buildIndexMap(getDataType());
		FCNode node = getNode(clusterId).getFirstNode();
		log.info("refreshing nodes from cluster (" + clusterId + ")");

		try{
			log.startTimer();
			while(node != null){
				addNodeToIndex(node);
				node = node.getTail();
			}
			log.info("nodes added to the indexes in " + log.endTimer() + " ms");

			for(String key: indexesMap.keySet()) {
				if (indexesMap.get(key) == null) continue;
				log.startTimer();
				indexesMap.get(key).persist();
				log.info("index (" + indexesMap.get(key).getIndexType().getType() + ") for " + key + " persisted in " + log.endTimer() + " ms");
			}
		}catch(FCException e){
			log.error("Error refreshing indexes", e);
			throw new FCException(e.getMessage(), e);
		}
	}

	/**
	 * It uses JsonUtils to convert from JSON to Map<String, Object>.
	 * <p>
	 * If there is an array inside the object it will return the object and a List 
	 * of Map<String, Object> representing each element of the array which will be 
	 * another JSON object.</p>
	 * <p>
	 * Using the configuration mapped it will match field by field with the map to know
	 * what is the chosen type of index that it should be added to.
	 * </p>
	 * @param FCNode node
	 * @throws FCException
	 */
	void addNodeToIndex(FCNode node) throws FCException {
		Map<String, Object> dataMap = readDataFromNode(node);
		parseMapIntoIndex(dataMap, null);
		
	}

	private void parseMapIntoIndex(Map<String, Object> dataMap, Long id) throws FCException {
		Set<String> jsonAttributes = dataMap.keySet();
		for (String jsonAttributeName : jsonAttributes) {
			FCIndex index = getIndexForJsonAttributeName(jsonAttributeName);
			if (!JsonUtils.isNull(dataMap.get(jsonAttributeName))) {
				if (id == null) id = Long.parseLong((String) dataMap.get("id"));
				Object fieldValue = dataMap.get(jsonAttributeName);
				if (fieldValue instanceof List){
					@SuppressWarnings("unchecked")
					List<Map<String, Object>> l = (List<Map<String, Object>>) fieldValue;
					for (Map<String, Object> arrayObj : l) {
						parseMapIntoIndex(arrayObj, id);
					}
				}else{
					index.insert((String) fieldValue, id);	
				}
			}
		}
	}

	/**
	 * <p>Returns the appropriate index accordingly to the JSON attribute name received.</p>
	 * <p> If the attribute found into the JSON is not configured it will create the default 
	 * index for that field</p>
	 * 
	 * @param fieldKey
	 * @return FCIndex
	 * @throws FCException
	 */
	private FCIndex getIndexForJsonAttributeName(String jsonAttributeName) throws FCException {
		FCIndex index = null;
		if (!hasIndexMapPresent()) createDefaultIndex(jsonAttributeName);
		index = indexesMap.get(jsonAttributeName);

		if (index == null){
			createDefaultIndex(jsonAttributeName);
			index = indexesMap.get(jsonAttributeName);
		}
		return index;
	}

	/**
	 * Create a default Index in case it is not configured into the *_mapping json file.
	 * @param jsonAttributeName
	 * @throws FCException
	 */
	private void createDefaultIndex(String jsonAttributeName) throws FCException {
		FCIndex index = new IndexEquals(getDataType(), jsonAttributeName);
		indexesMap.put(jsonAttributeName, index);
	}

	/**
	 * Often if indexMap is null it means that there is no configuration *_mapping defined 
	 * for the current dataType
	 *
	 * @return boolean
	 */
	private boolean hasIndexMapPresent() {
		if (indexesMap == null) {
			indexesMap = new HashMap<String, FCIndex>();
			return false;
		}
		return true;
	}

	/**
	 * <p>
	 * This method is responsible for parsing the *_mapping file and add the indexes 
	 * related to the JSON keys into a map.
	 * <br>
	 * If a *_mapping is not found then it will set indexesMap as null
	 * </p>
	 * @param dataType
	 * @throws FCException
	 */
	private void buildIndexMap(String dataType) throws FCException {
		if (indexesMap != null) return;
		IndexParser parser = new IndexParser();
		indexesMap = parser.getIndexes(dataType, null);
	}

	/**
	 * Reads data from node and parse into a Map which will be easily used to find the appropriate index
	 * 
	 * @param node
	 * @return Map<String, Object>
	 * @throws FCException
	 */
	private Map<String, Object> readDataFromNode(FCNode node) throws FCException {
		return JsonUtils.convertJsonToMap(node.getData().getJSONData());
	}
}
