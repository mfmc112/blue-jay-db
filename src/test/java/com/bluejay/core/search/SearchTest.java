package com.bluejay.core.search;

import java.util.Set;

import org.junit.Test;

import com.bluejay.core.exception.FCException;

public class SearchTest {

	
	@Test
	public void searchTest() throws FCException {
		Search s = new Search("cars");
		Set<Long> ids = s.search("cars.make", "Infiniti");
		System.out.println(ids);
	}
}
