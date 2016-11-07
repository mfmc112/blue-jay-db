package com.bluejay.core.storage.stack;

import java.util.HashMap;
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
 * Implementation of the Stack which persists the data into disk.
 * 
 * @author Marcos Costa
 */
public class PersistentStack extends BasicStack {

	Logger log = new Logger(PersistentStack.class);
	Map<String, FCIndex> indexesMap;	

	public PersistentStack(String dataType){
		super(dataType);
	}
	
	/**
	 * Commits all the changes made to the stackNodes. 
	 * This method update the indexes asynchronously.
	 *  
	 */
	@Override
	public void commit() throws FCException {
		for (Long id : cluster.keySet()) {
			searializeCluster(id);
			log.info("Committed cluster " + id);
		}
		FCTask task = new IndexTask(getDataType(), cluster.keySet());
		Messenger.INSTANCE.asyncProcess(task);
	}
	
	/**
	 * Commits all the changes made to the stackNodes. 
	 * This method does not update the indexes.
	 *  
	 */
	@Override
	public void commitNoIndex() throws FCException {
		log.startTimer();
		for (Long id : cluster.keySet()) {
			searializeCluster(id);
		}
		log.info("Commited stack without indexes in " + log.endTimer() + " ms");
	}

	/**
	 * Rollback all the changes made to the stackNodes
	 *  
	 */
	@Override
	public void rollback() throws FCException {
		super.rollback();
	}
	
	private void searializeCluster(Long id) throws FCException {
		FCSerializer.write(getDataType(), Cluster.DEFAULT_STORAGE_TYPE, cluster.get(id).getFirstNode(), id+"");
	}
	
	@Override
	public void refreshIndex(Set<Long> clusterIds) throws FCException {
		if (clusterIds == null) return;
		buildIndexMap(getDataType());
		for (Long clusterId : clusterIds) {
			refreshIndex(clusterId);
		}
	}
	
	// TODO retrieve the fields and configuration from config.json (implement it)
	private void refreshIndex(Long clusterId) throws FCException {
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
				log.info("index (" + indexesMap.get(key) + ") persisted in " + log.endTimer() + " ms");
			}
		}catch(FCException e){
			log.error("Error refreshing indexes", e);
			throw new FCException(e.getMessage(), e);
		}
	}

	/*
	 * 
	 * It uses JsonUtils to convert from JSON to Map<String, Object>. If there is an array
	 * inside the object it will return the object and a List of Map<String, Object> 
	 * representing each element of the array which will be another JSON object
	 * 
	 */
	void addNodeToIndex(FCNode node) throws FCException {
		boolean useDefaults = false;
		if (indexesMap == null) {
			useDefaults = true;
			indexesMap = new HashMap<String, FCIndex>();
		}
		
		Map<String, Object> map = JsonUtils.convertJsonToMap(node.getData().getJSONData());
		Set<String> fields = map.keySet();
		for (String fieldKey : fields) {
			FCIndex index = null;
			if (useDefaults){
				index = new IndexEquals(getDataType(), fieldKey);
				indexesMap.put(fieldKey, index);
			} else {
				index = indexesMap.get(fieldKey);
			}
			if (index != null && map.get(fieldKey) != null){
				Long id = Long.parseLong((String) map.get("id"));
				Object fieldValue = map.get(fieldKey);
				if (JsonUtils.isArray(fieldValue)){
					//TODO: deal with inner arrays
				}else{
					index.insert((String) fieldValue, id);	
				}
				
				
			}
		}
	}

	private void buildIndexMap(String dataType) throws FCException {
		if (indexesMap != null) return;
		IndexParser parser = new IndexParser();
		indexesMap = parser.getIndexes(dataType, null);
	}

}
