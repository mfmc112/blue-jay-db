package com.bluejay.core.search;

import java.util.Map;

import com.bluejay.core.exception.FCException;
import com.bluejay.core.utils.JsonUtils;

/**
 * This parses a simple query with no condition (and/or) 
 * 
 * @author Marcos Costa
 *
 */
public class SimpleQueryParser implements FCQueryParser {

	@Override
	public Map<String, Object> parseQuery(String jsonQuery) throws FCException {
		Map<String, Object> parsedQuery = JsonUtils.convertJsonToMap(jsonQuery);
		return parsedQuery;
	}
}
