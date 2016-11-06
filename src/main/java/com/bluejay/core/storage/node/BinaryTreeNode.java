package com.bluejay.core.storage.node;

public class BinaryTreeNode implements FCBinaryNode {

	private Long root;
	private FCBinaryNode left;
	private FCBinaryNode right;
	
	public BinaryTreeNode(Long root){
		this.root = root;
	}
	
	@Override
	public void addNode(Long newRoot) {
		if (newRoot.longValue() <= root.longValue()){
			if (left == null) left = new BinaryTreeNode(newRoot);
			else left.addNode(newRoot);
		}else{
			if (right == null) right = new BinaryTreeNode(newRoot);
			else right.addNode(newRoot);
		}
	}

	public Long getRoot() {
		return root;
	}

	public FCBinaryNode getLeft() {
		return left;
	}

	public FCBinaryNode getRight() {
		return right;
	}

	
}
