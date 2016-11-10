package com.bluejay.core.search;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.bluejay.core.exception.FCException;

public class SimpleQueryParserTest {

	@Test
	public void testSimpleQueryOneConditionParser() throws FCException {
		String jsonQuery = "{\"matchOr\":{\"cars.make\":\"Infiniti\"}}";
		SimpleQueryParser parser = new SimpleQueryParser();
		Map<String, Object> parsedQuery = parser.parseQuery(jsonQuery);
		String or = (String) parsedQuery.get("matchOr").toString();
		Assert.assertEquals("{\"cars.make\":\"Infiniti\"}", or);
	}
	
	@Test
	public void testSimpleQueryTwoConditionsParser() throws FCException {
		String jsonQuery = "{\"matchOr\":{\"age\":\"40\"}, \"matchAnd\":{\"name\":\"Marcos\",\"language\":\"Java\"} }";
		SimpleQueryParser parser = new SimpleQueryParser();
		Map<String, Object> parsedQuery = parser.parseQuery(jsonQuery);
		String orClause = (String) parsedQuery.get("matchOr").toString();
		String andClause = (String) parsedQuery.get("matchAnd").toString();
		Assert.assertEquals("{\"age\":\"40\"}", orClause);
		Assert.assertEquals("{\"name\":\"Marcos\",\"language\":\"Java\"}", andClause);
	}
	
	@Test
	public void parseQueryNoMatchingTest() throws FCException {
		String jsonQuery = "{\"age\":\"40\"}";
		SimpleQueryParser parser = new SimpleQueryParser();
		Map<String, Object> queryParsed = parser.parseQuery(jsonQuery);
		Assert.assertEquals("40", queryParsed.get("age").toString());
	}
	
	@Test(expected=FCException.class)
	public void parseQueryEmptyTest() throws FCException {
		String jsonQuery = "{}";
		SimpleQueryParser parser = new SimpleQueryParser();
		Map<String, Object> queryParsed = parser.parseQuery(jsonQuery);
		Assert.assertEquals("40", queryParsed.get("age").toString());
	}
}
