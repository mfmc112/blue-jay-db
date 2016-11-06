package com.bluejay.core.queue;

import com.bluejay.core.exception.FCException;

public interface FCTask {

	public void execute() throws FCException;
	
	public String getName();
}
