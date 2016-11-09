package com.bluejay.core.search;

import java.util.Set;

import com.bluejay.core.exception.FCException;
import com.bluejay.core.index.FCIndex;
import com.bluejay.core.storage.stack.FCStackIndex;

/**
 * <p>Search a value into an index. The stack received already contains the DB and
 * indexes defined.</p>
 * <p>TieldName defines which index will be used to search.</p>
 *  
 * @author Marcos Costa
 *
 */
public class SearchIndex {

	FCStackIndex stack;
	String dataType;
	
	public SearchIndex(FCStackIndex stack) throws FCException {
		this.stack = stack;
	}
	
	/**
	 * Retrieves the appropriate index and search for all the ids that matches the 
	 * received value.
	 * 
	 * @param field
	 * @param value
	 * @return Set<Long>
	 */
	public Set<Long> search(String field, String value) {
		FCIndex index = stack.getIndexForField(field);
		return index.search(value);
	}
}
