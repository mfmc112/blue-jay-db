package com.bluejay.core.index;

import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import com.bluejay.core.exception.FCException;
import com.bluejay.core.metadata.FCData;
import com.bluejay.core.storage.node.LinkedNode;
import com.bluejay.core.storage.stack.FCStack;
import com.bluejay.core.storage.stack.PersistentStack;
import com.bluejay.core.storage.stub.StubData;

public class IndexStartsWithTest extends StubData {

	@Test
	public void idnexTest() throws FCException {
		// delete existing files
		stub.deleteAllFiles("index-equals"); 
		
		// create persistence stack
		FCStack stack = new PersistentStack("index-equals");
		
		// create node
		stack.push( new LinkedNode(10L, new FCData(stub.singleJsonString.replaceAll("Marcos Costa", "Ana Goulet").replaceAll("mfmc112", "ana300"))) );
		stack.push( new LinkedNode(5L, new FCData(stub.singleJsonString)));
		stack.commit();
		
		long s1 = System.currentTimeMillis();
		FCIndex indexLogin = new IndexStartsWith("index-equals", "login");
		Set<Long> idsLogin = indexLogin.search("ana300");
		long s2 = System.currentTimeMillis();
		
		FCIndex indexName = new IndexStartsWith("index-equals", "name");
		Set<Long> idsName = indexName.search("Marcos Costa");
		
		Assert.assertTrue(idsLogin.contains(10L));
		Assert.assertTrue(idsName.contains(5L));
		Assert.assertTrue((s2-s1)<1000);
		
		
	}
}
