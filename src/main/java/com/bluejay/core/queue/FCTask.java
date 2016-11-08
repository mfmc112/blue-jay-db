package com.bluejay.core.queue;

import com.bluejay.core.exception.FCException;

public interface FCTask {

	/**
	 * This is executed into the run() method from the Pipeline
	 * 
	 * @throws FCException
	 */
	public void execute() throws FCException;
	
	/**
	 * Sets the name of the Task.
	 * 
	 * @return String
	 */
	public String getName();
}
