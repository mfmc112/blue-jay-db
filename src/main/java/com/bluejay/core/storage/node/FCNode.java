package com.bluejay.core.storage.node;

import com.bluejay.core.metadata.FCData;


public interface FCNode {

	void addNode(Long newRoot, FCData data);
	
	FCNode getFirstNode();
	
	Long getRoot();
	void setRoot(long root);
	
	FCNode getHead();
	void setHead(FCNode head);
	
	FCNode getTail();
	void setTail(FCNode tail);
	
	FCData getData();
	void setData(FCData data);
	
}
