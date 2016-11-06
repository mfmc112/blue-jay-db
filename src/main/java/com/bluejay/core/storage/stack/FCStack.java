package com.bluejay.core.storage.stack;

import java.util.Set;

import com.bluejay.core.exception.FCException;
import com.bluejay.core.storage.node.FCNode;

public interface FCStack {

	FCNode push(FCNode node) throws FCException;
	
	FCNode getNode(Long clusterId);
	
	void commit() throws FCException;
	
	void commitNoIndex() throws FCException;
	
	void rollback() throws FCException;
	
	String getDataType();

	long getClusterId(Long clusterId);
	
	long getClusterId(FCNode node);

	long size();
	
	long clusterSize(Long locatorId);
	
	public void clear() throws FCException;

	void refreshIndex(Set<Long> ids) throws FCException;
	
}
