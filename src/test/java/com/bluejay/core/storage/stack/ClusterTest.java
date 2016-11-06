package com.bluejay.core.storage.stack;

import junit.framework.Assert;

import org.junit.Test;

import com.bluejay.core.metadata.FCData;
import com.bluejay.core.storage.node.LinkedNode;
import com.bluejay.core.storage.stub.StubData;

public class ClusterTest extends StubData {

	@Test
	public void putGetTest() {
		Cluster<Long, LinkedNode> p = new Cluster<Long, LinkedNode>("cluster");
		p.put(0L, new LinkedNode(1L, new FCData(stub.singleJsonString)));
		
		LinkedNode node = p.get(0L);
		Assert.assertEquals(1L, node.getRoot().longValue());
	}
	
}
