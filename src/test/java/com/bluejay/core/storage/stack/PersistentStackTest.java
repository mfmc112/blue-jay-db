package com.bluejay.core.storage.stack;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

import com.bluejay.core.exception.FCException;
import com.bluejay.core.metadata.FCData;
import com.bluejay.core.storage.node.FCNode;
import com.bluejay.core.storage.node.LinkedNode;
import com.bluejay.core.storage.stub.StubData;
import com.bluejay.core.utils.PropertiesUtil;

public class PersistentStackTest extends StubData {

	@Test
	public void pushGetTest() throws FCException{
		// delete existing files
		stub.deleteAllFiles("persistence");
		
		// create persistence stack
		FCStack stack = new PersistentStack("persistence");

		// create node
		stack.push( new LinkedNode(10L, new FCData(stub.singleJsonString)) );
		stack.push( new LinkedNode(5L, new FCData(stub.singleJsonString)) );
		FCNode node = stack.getNode(0L).getFirstNode();
		
		Assert.assertEquals(2L, stack.clusterSize(0L));
		Assert.assertEquals(5L, node.getRoot().longValue());
		Assert.assertEquals(10L, node.getTail().getRoot().longValue());
	}
	
	@Test
	public void pushCommitTest() throws FCException{
		// delete existing files
		stub.deleteAllFiles("persistence"); 
		
		// create persistence stack
		FCStack stack = new PersistentStack("persistence");

		// create node
		stack.push( new LinkedNode(10L, new FCData(stub.singleJsonString)) );
		stack.push( new LinkedNode(5L, new FCData(stub.singleJsonString)) );
		stack.commitNoIndex();
		File f = new File(PropertiesUtil.getfileName("persistence", "0"));

		Assert.assertEquals(2L, stack.clusterSize(0L));
		Assert.assertTrue(f.exists());
	}
	
	public static void main(String[] args) {
		PersistentStackTest test = new PersistentStackTest();
		test.init();
		try {
			test.addNodeToIndexTest();
		} catch (FCException e) {
			System.out.println("Error testing addNodeToIndex");
		}
	}
	
	public void addNodeToIndexTest() throws FCException {
		stub.deleteAllFiles("cars");
		
		String json = "{\"id\":123, \"name\":\"Marcos Costa\", \"cars\":[{\"make\":\"Infiniti\", \"model\":\"G37\", \"accessories\":[{\"type\":\"GPS\", \"installed\":\"true\"},{\"type\":\"Auto Drive\", \"installed\":\"false\"}]},{\"make\":\"Toyota\", \"model\":\"Supra\"}]}";
		
		FCStack stack = new PersistentStack("cars");
		stack.push( new LinkedNode(1L, new FCData(json)) );
		stack.commit();
	}
}
