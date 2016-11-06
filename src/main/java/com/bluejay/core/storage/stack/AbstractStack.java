package com.bluejay.core.storage.stack;

import com.bluejay.core.exception.FCException;
import com.bluejay.core.storage.node.FCNode;

/**
 * Abstract Class that implements basic functionalities for Stacks
 * 
 * @author Marcos Costa
 */
public abstract class AbstractStack implements FCStack {
	
	
	public abstract int getClusterSize();
	
	protected Cluster<Long, FCNode> cluster;
	
	@Override
	public long getClusterId(FCNode node) {
		return getClusterId(node.getRoot());
	}
	
	@Override
	public long getClusterId(Long partitionId) {
		long partitionid = new Double(Math.floor(partitionId/getClusterSize())).longValue();
		return partitionid;
	}
	
	@Override
	public void clear() throws FCException {
		cluster.clear();
		cluster.clearClusterSize();
	}

}
