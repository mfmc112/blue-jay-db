package com.bluejay.core.search;

import java.util.Map;

import com.bluejay.core.exception.FCException;

public interface FCQueryParser {

	String MATCH = "match";
	String MATCH_OR = "matchOr";
	String MATCH_AND = "matchAnd";
	
	Map<String, Object> parseQuery(String jsonQuery) throws FCException;

}
