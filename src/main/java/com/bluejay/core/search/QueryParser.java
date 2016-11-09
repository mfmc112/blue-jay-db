package com.bluejay.core.search;

import java.util.Map;

import com.bluejay.core.exception.FCException;

public interface QueryParser {

	Map<String, Object> parseQuery(String jsonQuery) throws FCException;

}
