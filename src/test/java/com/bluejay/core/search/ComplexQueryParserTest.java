package com.bluejay.core.search;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.bluejay.core.exception.FCException;

public class ComplexQueryParserTest {

	@SuppressWarnings("unchecked")
	@Test
	public void parseQueryTest() throws FCException {
		String jsonQuery = "{\"matchOr\":{\"age\":\"40\"}, \"matchAnd\":{\"name\":\"Marcos\",\"language\":\"Java\"} }";
		ComplexQueryParser parser = new ComplexQueryParser();
		Map<String, Object> queryParsed = parser.parseQuery(jsonQuery);
		Assert.assertEquals("40", ((Map<String, Object>)queryParsed.get("matchOr")).get("age").toString());
		Assert.assertEquals("Marcos", ((Map<String, Object>)queryParsed.get("matchAnd")).get("name").toString());
		Assert.assertEquals("Java", ((Map<String, Object>)queryParsed.get("matchAnd")).get("language").toString());
	}
	
	@Test
	public void parseQueryNoMatchingTest() throws FCException {
		String jsonQuery = "{\"age\":\"40\"}";
		ComplexQueryParser parser = new ComplexQueryParser();
		Map<String, Object> queryParsed = parser.parseQuery(jsonQuery);
		Assert.assertEquals("40", queryParsed.get("age").toString());
	}
	
	@Test(expected=FCException.class)
	public void parseQueryEmptyTest() throws FCException {
		String jsonQuery = "{}";
		ComplexQueryParser parser = new ComplexQueryParser();
		Map<String, Object> queryParsed = parser.parseQuery(jsonQuery);
		Assert.assertEquals("40", queryParsed.get("age").toString());
	}
	
}
