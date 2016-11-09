package com.bluejay.core.search;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.bluejay.core.exception.FCException;
import com.bluejay.logger.Logger;

@SuppressWarnings("unchecked")
public class SearchAggregatorTest {

	Logger log = new Logger(SearchAggregatorTest.class);
	
	@Test
	public void aggregateAndTest() {
		Long[] l1 = new Long[]{2L,4L,6L,8L};
		Set<Long> set1 = new HashSet<Long>(Arrays.asList(l1));
		
		Long[] l2 = new Long[]{1L,3L,4L,5L,8L};
		Set<Long> set2 = new HashSet<Long>(Arrays.asList(l2));
		
		Long[] l3 = new Long[]{1L,3L,4L,5L};
		Set<Long> set3 = new HashSet<Long>(Arrays.asList(l3));

		SearchAggregator s = new SearchAggregator();
		Set<Long> aggregatedSet = s.aggregateAnd(set1, set2, set3);

		Assert.assertEquals(1, aggregatedSet.size());
		Assert.assertTrue(aggregatedSet.contains(4L));
	}
	
	@Test
	public void aggregateAndSingleSetTest() {
		Long[] l1 = new Long[]{2L,4L,6L,8L};
		Set<Long> set1 = new HashSet<Long>(Arrays.asList(l1));
		SearchAggregator s = new SearchAggregator();
		Set<Long> aggregatedSet = s.aggregateAnd(set1);
		
		Assert.assertEquals(4, aggregatedSet.size());
		Assert.assertTrue(aggregatedSet.contains(2L));
		Assert.assertTrue(aggregatedSet.contains(4L));
		Assert.assertTrue(aggregatedSet.contains(6L));
		Assert.assertTrue(aggregatedSet.contains(8L));
	}
	
	@Test
	public void aggregateAnd100kTime() throws FCException {
		Set<Long> set1 = randomSet(0, 100000);
		Set<Long> set2 = randomSet(7000, 100000);
		log.startTimer();
		SearchAggregator s = new SearchAggregator();
		Set<Long> aggregatedSet = s.aggregateAnd(set1);
		
		// It needs to run in less then 10 milliseconds. Yes 1/100 of a millisecond
		Assert.assertTrue(log.endTimer() <=10);
	}
	
	private Set<Long> randomSet(int startAt, int size) {
		Set<Long> set = new HashSet<Long>();
		for(int i = startAt; i < (startAt + size); i++) {
			set.add(new Long(i));
		}
		return set;
	}
}
