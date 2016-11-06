package com.bluejay.core.index;

import java.util.Set;

import com.bluejay.core.exception.FCException;

public interface FCIndex {

	FCType getIndexType();
	/**
	 * <p>
	 * Insert the value of the field and the id to the index.
	 * </p>
	 * <p>
	 * This inserts in Memory and persist needs to be called to serialize the 
	 * object into the disk</p>
	 * 
	 * @param value
	 * @param id
	 */
	void insert(String value, Long id);
	
	/**
	 * Implementation of the search using the appropriate implemented algorithm
	 * 
	 * @param q
	 * @return
	 */
	Set<Long> search(String q);
	
	/**
	 * Persist the index object from memory into disk
	 * 
	 * @throws FCException
	 */
	void persist() throws FCException;
	
}
