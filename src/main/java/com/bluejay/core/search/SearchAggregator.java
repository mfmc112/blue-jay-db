package com.bluejay.core.search;

import java.util.Set;

public class SearchAggregator {

	/**
	 * gets the intersection of common ids. In other words it will remove the ids that
	 * are not present in all the Sets received. 
	 * 
	 * @param ids multiple Sets of ids
	 * @return Set of ids present in all the results
	 */
	public Set<Long> aggregateAnd(Set<Long> ...ids) {
		if (ids == null || ids.length <= 1 ) return ids[0];
		Set<Long> mainSet = ids[0];
		
		for(int i=1;i<ids.length;i++) {
			mainSet.retainAll(ids[i]);
		}
		return mainSet;
	}
}
