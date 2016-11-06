package com.bluejay.core.storage.stack;

import junit.framework.Assert;

import org.junit.Test;

import com.bluejay.core.exception.FCException;
import com.bluejay.core.metadata.FCData;
import com.bluejay.core.storage.node.FCNode;
import com.bluejay.core.storage.node.LinkedNode;
import com.bluejay.core.storage.stub.StubData;

public class MemoryStackTest extends StubData {

	@Test
	public void pushGetCluster0Test() throws FCException{
		stub.deleteAllFiles("memory");
		
		// create memory stack
		FCStack stack = new MemoryStack("memory");
		
		// create node
		stack.push( new LinkedNode(22L, new FCData(stub.singleJsonString)) );
		stack.push( new LinkedNode(11L, new FCData(stub.singleJsonString)) );
		FCNode node = stack.getNode(0L).getFirstNode();
		
		Assert.assertEquals(2L, stack.clusterSize(0L));
		Assert.assertEquals(node.getRoot().longValue(), 11L);
		Assert.assertEquals(node.getTail().getRoot().longValue(), 22L);
	}
	
	@Test
	public void pushGetCluster1Test() throws FCException{
		stub.deleteAllFiles("memory");
		
		// create memory stack
		FCStack stack = new MemoryStack("memory");
		
		// create node
		stack.push( new LinkedNode(1002L, new FCData(stub.singleJsonString)) );
		stack.push( new LinkedNode(1001L, new FCData(stub.singleJsonString)) );
		FCNode node = stack.getNode(1L).getFirstNode();
		
		Assert.assertEquals(2L, stack.clusterSize(1L));
		Assert.assertEquals(node.getRoot().longValue(), 1001L);
		Assert.assertEquals(node.getTail().getRoot().longValue(), 1002L);
	}
	
	@Test
	public void pushGetCluster0And1Test() throws FCException{
		// create memory stack
		FCStack stack = new MemoryStack("memory");
		// create node
		stack.push( new LinkedNode(2L, new FCData(stub.singleJsonString)) );
		stack.push( new LinkedNode(1L, new FCData(stub.singleJsonString)) );
		stack.push( new LinkedNode(1002L, new FCData(stub.singleJsonString)) );
		stack.push( new LinkedNode(1001L, new FCData(stub.singleJsonString)) );
		FCNode node0 = stack.getNode(0L).getFirstNode();
		FCNode node1 = stack.getNode(1L).getFirstNode();
		
		Assert.assertEquals(2L, stack.clusterSize(0L));
		Assert.assertEquals(2L, stack.clusterSize(1L));
		Assert.assertEquals(node0.getRoot().longValue(), 1L);
		Assert.assertEquals(node0.getTail().getRoot().longValue(), 2L);
		Assert.assertEquals(node1.getRoot().longValue(), 1001L);
		Assert.assertEquals(node1.getTail().getRoot().longValue(), 1002L);
	}
}
