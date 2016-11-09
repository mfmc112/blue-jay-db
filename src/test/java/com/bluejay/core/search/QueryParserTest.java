package com.bluejay.core.search;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.bluejay.core.exception.FCException;

public class QueryParserTest {

	@Test
	public void testSimpleQueryParser() throws FCException {
		String jsonQuery = "{\"cars.make\":\"Infiniti\"}";
		SimpleQueryParser parser = new SimpleQueryParser();
		Map<String, Object> parsedQuery = parser.parseQuery(jsonQuery);
		Assert.assertEquals("Infiniti", (String)parsedQuery.get("cars.make"));
	}
}
