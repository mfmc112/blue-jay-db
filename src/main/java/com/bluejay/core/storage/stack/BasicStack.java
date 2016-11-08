package com.bluejay.core.storage.stack;

import java.util.Set;

import com.bluejay.core.exception.FCException;
import com.bluejay.core.storage.node.FCNode;

/**
 * Basic implementation for the Stack. This class saves the data into the Cluster in memory. 
 * The methods to commit are not implemented so it will not persist.
 * 
 * @author Marcos Costa
 */
public class BasicStack extends AbstractStack {

	private String dataType;
	protected final int CLUSTER_SIZE = 1000;
	
	@Override
	public String getDataType() {
		return dataType;
	}

	@Override
	public int getClusterSize(){
		return CLUSTER_SIZE;
	}
	
	public BasicStack(){
		this.dataType = "basic";
		cluster = new Cluster<Long, FCNode>(dataType);
	}
	
	public BasicStack(String dataType){
		this.dataType = dataType;
		cluster = new Cluster<Long, FCNode>(dataType);
	}
	
	@Override
	public FCNode push(FCNode node) throws FCException {
		long clusterId = getClusterId(node);
		return cluster.put(clusterId, node);
	}
	
	@Override
	public FCNode getNode(Long clusterId) {
		return cluster.get(clusterId);
	}
	
	@Override
	public void commit() throws FCException {
		// This stack do not commit
	}
	
	@Override
	public void commit(boolean refreshIndex) throws FCException {
		// This stack do not commit
	}

	@Override
	public void rollback() throws FCException {
		clear();
	}

	@Override
	public long size() {
		return cluster.size();
	}

	@Override
	public long clusterSize(Long clusterId) {
		return cluster.clusterSize(clusterId);
	}

	@Override
	public void refreshIndexForClusters(Set<Long> clusterIds) throws FCException {
		// Basic stack doesn't update any index
	}

	@Override
	public void refreshIndexForSingleCluster(Long cluesterId) throws FCException {
		// TODO Auto-generated method stub
		
	}

}
