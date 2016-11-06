package com.bluejay.core.storage.node;

import java.io.Serializable;

import com.bluejay.core.metadata.FCData;

/**
 * This is the implementation of a linkedList
 * 
 * @author Marcos Costa
 *
 */
public class LinkedNode implements FCNode, Serializable {

	private static final long serialVersionUID = 2029621746256437538L;

	private Long root;
	private FCNode head;
	private FCNode tail;
	private FCData data;
	
	public LinkedNode(){
		this.root = null;
		this.head = null;
		this.tail = null;
		this.data = null;
	}
	
	public LinkedNode(Long root, FCData data) {
		this.root = null;
		addNode(root, data);
	}

	/**
	 * Add a node into the LinkedList ordered by id
	 * 
	 * @param Long newRoot
	 * @param FCData data
	 */
	@Override
	public void addNode(Long newRoot, FCData data) {
		if (this.root == null) {
			this.root = newRoot;
			this.data = data;
		}else{
			if (newRoot.longValue() < this.root.longValue()) {
				if (this.head != null && this.head.getRoot().longValue() > newRoot.longValue()){
					this.head.addNode(newRoot, data);
				}else{
					FCNode node = new LinkedNode(newRoot, data);
					FCNode currentHead = this.head;	
					if (currentHead != null) currentHead.setTail(node);
					node.setTail(this);
					node.setHead(currentHead);
					this.head = node;
				}
			}else{
				if (this.tail == null){
					FCNode node = new LinkedNode(newRoot, data);
					node.setHead(this);
					this.tail = node;
				} else {
					this.tail.addNode(newRoot, data);
				}
			}
		}
	}
	
	/**
	 * traverse to the firstNode and return it. This method does not create extra objects
	 */
	@Override
	public FCNode getFirstNode() {
		if (this.head != null) return this.head.getFirstNode();
		return this;
	}
	
	@Override
	public Long getRoot() {
		return root;
	}

	@Override
	public void setRoot(long root) {
		this.root = root;
	}

	@Override
	public FCNode getHead() {
		return this.head;
	}

	@Override
	public void setHead(FCNode head) {
		this.head = head;
	}

	@Override
	public FCNode getTail() {
		return this.tail;
	}

	@Override
	public void setTail(FCNode tail) {
		this.tail = tail;
	}

	@Override
	public FCData getData() {
		return data;
	}

	@Override
	public void setData(FCData data) {
		this.data = data;
	}


}
