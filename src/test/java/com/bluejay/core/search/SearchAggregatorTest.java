package com.bluejay.core.search;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class SearchAggregatorTest {

	@Test
	public void aggregateAndTest() {
		Set<Long> set1 = new HashSet<Long>();
		set1.add(2L);
		set1.add(4L);
		set1.add(6L);
		set1.add(8L);
		Set<Long> set2 = new HashSet<Long>();
		set2.add(1L);
		set2.add(3L);
		set2.add(4L);
		set2.add(5L);
		set2.add(8L);
		Set<Long> set3 = new HashSet<Long>();
		set3.add(1L);
		set3.add(3L);
		set3.add(4L);
		set3.add(5L);
		SearchAggregator s = new SearchAggregator();
		Set<Long> aggregatedSet = s.aggregateAnd(set1, set2, set3);
		Assert.assertEquals("[4]", aggregatedSet.toString());
	}
	
	@Test
	public void aggregateAndSingleSetTest() {
		Set<Long> set1 = new HashSet<Long>();
		set1.add(2L);
		set1.add(4L);
		set1.add(6L);
		set1.add(8L);
		SearchAggregator s = new SearchAggregator();
		Set<Long> aggregatedSet = s.aggregateAnd(set1);
		Assert.assertEquals("[2,4,6,8]", aggregatedSet.toString());
	}
}
