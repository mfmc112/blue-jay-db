package com.bluejay.core.storage.stack;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import com.bluejay.core.exception.FCException;
import com.bluejay.core.index.FCIndex;
import com.bluejay.core.index.IndexEquals;
import com.bluejay.core.metadata.FCData;
import com.bluejay.core.storage.node.FCNode;
import com.bluejay.core.storage.node.LinkedNode;
import com.bluejay.core.storage.stub.StubData;
import com.bluejay.core.utils.PropertiesUtil;

public class PersistentStackTest extends StubData {

	/**
	 * Main method used here so we can debug the thread. JUnit finished before the thread starts
	 * @param args
	 */
	public static void main(String[] args) {
		PersistentStackTest test = new PersistentStackTest();
		test.init();
		try {
			test.addNodeToIndexTest();
		} catch (FCException e) {
			System.out.println("Error testing addNodeToIndex");
		}
	}
	
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
		stack.commit(false);
		File f = new File(PropertiesUtil.getfileName("persistence", "0"));

		Assert.assertEquals(2L, stack.clusterSize(0L));
		Assert.assertTrue(f.exists());
	}
	
	public void addNodeToIndexTest() throws FCException {
		stub.deleteAllFiles("cars");
		
		
		FCStack stack = new PersistentStack("cars");
		stack.push( new LinkedNode(1L, new FCData(stub.carsJson)) );
		stack.commit();
	}
	
	@Test
	public void refreshIndexForSingleClusterTest() throws FCException {
		// delete existing files
		stub.deleteAllFiles("cars"); 
		
		// create persistence stack
		FCStack stack = new PersistentStack("cars");
		
		// create node and add to the stack
		stack.push( new LinkedNode(10L, new FCData(stub.carsJson)) );
		stack.commit(false);
		
		Set<Long> clusterIds = new HashSet<Long>();
		clusterIds.add(0L);
		stack.refreshIndexForSingleCluster(0L);
//		
//		long s1 = System.currentTimeMillis();
//		FCIndex indexName = new IndexEquals("index-equals", "avatar_url");
//		Set<Long> idsFound = indexName.search("https://avatars.githubusercontent.com/u/1028680?v=3");
//		long s2 = System.currentTimeMillis();
//		
//		Assert.assertTrue(idsFound.contains(1028680L));
//		Assert.assertTrue((s2-s1)<1000);
	}
}
