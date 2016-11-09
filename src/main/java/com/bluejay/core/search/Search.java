package com.bluejay.core.search;

import java.util.Map;
import java.util.Set;

import com.bluejay.core.exception.FCException;
import com.bluejay.core.storage.stack.FCStackIndex;
import com.bluejay.core.storage.stack.PersistentStack;

public class Search {

	FCStackIndex stack;
	String dataType;
	
	public Search(String dataType) throws FCException {
		this.dataType = dataType;
		stack = new PersistentStack(dataType);
	}

	public Set<Long> search(String json) {
		
		// TODO I need to parse the query an SearchAggregator to filter out the ids
		// The query format needs to be defined :) FUN PART 
		return null;
	}

	
	
	
}
