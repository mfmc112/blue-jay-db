package com.bluejay.core.search;

import java.util.Map;

import com.bluejay.core.exception.FCException;

/**
 * <p>This class parses the match, matchOr and matchAnd. It will parse match and a map of 
 * everything that matches. MatchOr and a Map of everything to be matched using or condition 
 * and finally matchAnd and everything that should be matched using and condition.</p> 
 * <p><b>This will query for records that have name=Marcos and age=40</b></p>
 * <pre>
 * <tt>
 * {
 *  "matchAnd":{
 *    "name":"Marcos",
 *    "age":40
 *  }
 * }
 * </tt>
 * </pre>
 * <br><br>
 * <p><b>This will query for records that have name=Marcos or age=40</b></p>
 * <pre>
 * <tt>
 * {
 *  "matchOr":{
 *    "name":"Marcos",
 *    "age":40
 *  }
 * }
 * </tt>
 * </pre>
 * @author Marcos Costa
 *
 */
public class ComplexQueryParser implements FCQueryParser {

	private SimpleQueryParser parser = new SimpleQueryParser();
	
	@Override
	public Map<String, Object> parseQuery(String jsonQuery) throws FCException {
		Map<String, Object> conditionQuery = parser.parseQuery(jsonQuery);
		if (hasMatchOr(conditionQuery) || hasMatchAnd(conditionQuery)){
			return parseConditions(conditionQuery);
		} else {
			return conditionQuery;
		}
	}

	private Map<String, Object> parseConditions(Map<String, Object> conditionQuery) throws FCException {
		if (conditionQuery == null) return null;
		
		for (String key : conditionQuery.keySet()) {
			String conditions = (String) conditionQuery.get(key).toString();
			Map<String, Object> parsedConditions  = parser.parseQuery(conditions);
			conditionQuery.put(key, parsedConditions);
		}
		return conditionQuery;
	}

	private boolean hasMatchOr(Map<String, Object> conditionQuery) {
		return matches(conditionQuery, MATCH_OR);
	}

	private boolean hasMatchAnd(Map<String, Object> conditionQuery) {
		return matches(conditionQuery, MATCH_AND);
	}
	
	private boolean matches(Map<String, Object> conditionQuery, String toMatch){
		if (conditionQuery == null) return false;
		return conditionQuery.get(toMatch) != null;
	}
	
}
