package com.bluejay.core.search;

import java.util.Set;

import com.bluejay.core.exception.FCException;
import com.bluejay.core.index.FCIndex;
import com.bluejay.core.storage.stack.FCStackIndex;
import com.bluejay.core.storage.stack.PersistentStack;

public class Search {

	FCStackIndex stack;
	String dataType;
	
	public Search(String dataType) throws FCException {
		this.dataType = dataType;
		stack = new PersistentStack(dataType);
	}
	
	public Set<Long> search(String field, String value) {
		FCIndex index = stack.getIndexForField(field);
		return index.search(value);
	}
}
