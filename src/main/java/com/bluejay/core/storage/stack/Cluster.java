package com.bluejay.core.storage.stack;

import java.util.HashMap;
import java.util.Map;

import com.bluejay.core.FCStorageFileType;
import com.bluejay.core.exception.FCException;
import com.bluejay.core.index.FCType;
import com.bluejay.core.serializer.FCDeserializer;
import com.bluejay.core.storage.node.FCNode;
import com.bluejay.logger.Logger;

/**
 * Cluster is the actual data. A Cluster is a multi-storage map which stores ordered LinkedNode.
 * The implementation of the Stack is the one that defines how many records will be stored for 
 * Cluster in the map.
 *  
 * @author Marcos Costa
 *
 * @param <K extends Number>
 * @param <V extends FCNode>
 */
public class Cluster<K extends Number, V extends FCNode> extends HashMap<K, V> {

	private static final long serialVersionUID = -1388665378638117548L;
	private Logger log = new Logger(Cluster.class);
	
	public static final FCType DEFAULT_STORAGE_TYPE = FCStorageFileType.LOCATOR;
	protected Map<Long, Long> clusterSizeMap = new HashMap<Long, Long>();
	protected String dataType;
	
	public Cluster(String dataType){
		super();
		this.dataType = dataType;
	}
	
	@Override
	public V put(K clusterId, V node) {
		V linkedNode = this.get(clusterId); // retrieve if exists into map
		
		if (linkedNode != null) { // if it is already loaded
			linkedNode.addNode(node.getRoot(), node.getData()); 
	    }else { // if it is not loaded into memory
	    	linkedNode = loadCluesterFromDisk(clusterId); // load cluster (id) from disk
	    	if (linkedNode == null){
	    		linkedNode = node;
	    	}else{
	    		linkedNode.addNode(node.getRoot(), node.getData());
	    	}
	    }
		super.put(clusterId, linkedNode); // add to the map

		Long clusterSize = clusterSizeMap.get(clusterId) != null ? clusterSizeMap.get(clusterId) : 0L;
		clusterSizeMap.put((Long) clusterId, ++clusterSize);
		
		return linkedNode;
	}
	
	@Override
	public V get(Object clusterId) {
		V linkedNode = super.get(clusterId);
		if (linkedNode == null){
			try {
				linkedNode = FCDeserializer.readNoFail(dataType, DEFAULT_STORAGE_TYPE, clusterId + "");
			} catch (FCException e) {
				log.error("Error retrieving data from the PersistedStack", e);
			}
		}
		return linkedNode;
	}
	
	protected V loadCluesterFromDisk(K clusterId) {
		try {
			return FCDeserializer.readNoFail(dataType, FCStorageFileType.LOCATOR, clusterId+"");
		} catch (FCException e) {
			log.error("Error while loading cluster from disk", e);
		}
		return null;
	}
	
	public void clearClusterSize(){
		clusterSizeMap.clear();
	}
	
	public long clusterSize(K clusterId) {
		return clusterSizeMap.get(clusterId);
	}
	
}
