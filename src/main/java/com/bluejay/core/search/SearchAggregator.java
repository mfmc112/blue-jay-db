package com.bluejay.core.search;

import java.util.Set;

/**
 * Aggregates the result of the search (IDs) into a single Set of IDs.
 * 
 * @author Marcos Costa
 *
 */
public class SearchAggregator {

	/**
	 * Intersect received sets of IDs. In other words it will remove the IDs that
	 * are not present in all of the Sets received. 
	 * 
	 * @param ids multiple Sets of IDs
	 * @return Set of IDs present in all the results
	 */
	public Set<Long> aggregateAnd(Set<Long> ...ids) {
		if (ids == null || ids.length <= 1 ) return ids[0];
		Set<Long> mainSet = ids[0];
		
		for(int i=1;i<ids.length;i++) {
			mainSet.retainAll(ids[i]);
		}
		return mainSet;
	}
	
	/**
	 * Concatenate all the Sets of IDs. In other words it will combine all the Sets of IDs
	 * received into a single one. 
	 * 
	 * @param ids multiple Sets of ids
	 * @return Set combination of all the IDs
	 */
	public Set<Long> aggregateOr(Set<Long> ...ids) {
		if (ids == null || ids.length <= 1 ) return ids[0];
		Set<Long> mainSet = ids[0];
		
		for(int i=1;i<ids.length;i++) {
			mainSet.addAll(ids[i]);
		}
		return mainSet;
	}
}
