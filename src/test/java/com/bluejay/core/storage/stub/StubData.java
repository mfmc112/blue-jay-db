package com.bluejay.core.storage.stub;

import org.junit.Before;

public class StubData {
	
	protected StorageTestStub stub; 
	
	@Before
	public void init() {
		stub = new StorageTestStub();
	}
}
