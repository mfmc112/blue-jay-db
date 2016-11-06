package com.bluejay.core.index;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.bluejay.core.exception.FCException;

public class IndexParserTest {
	
	@Test
	public void retrieveMappingTest() throws FCException {
		IndexParser parser = new IndexParser();
		Map<String, Object> map = parser.openMappingFileAsJSON("pipe-index");
		Assert.assertEquals(map.get("login"), "startsWith");
		Assert.assertEquals(map.get("id"), "id");
		Assert.assertEquals(map.get("avatar_url"), "exactMatch");
		Assert.assertEquals(map.get("site_admin"), "boolean");
	}
	
	@Test
	public void getIndexesTest() throws FCException {
		IndexParser parser = new IndexParser();
		Map<String, FCIndex> indexMap = parser.getIndexes("pipe-index", null);
		Assert.assertTrue(FCIndexType.EQUALS == indexMap.get("avatar_url").getIndexType());
		Assert.assertTrue(FCIndexType.STARTS_WITH == indexMap.get("login").getIndexType());
	}
	
	@Test
	public void getIndexesInnerArrayTest() throws FCException {
		IndexParser parser = new IndexParser();
		Map<String, FCIndex> indexMap = parser.getIndexes("cars", null);
		Assert.assertTrue(FCIndexType.EQUALS == indexMap.get("avatar_url").getIndexType());
		Assert.assertTrue(FCIndexType.STARTS_WITH == indexMap.get("login").getIndexType());
	}
}
