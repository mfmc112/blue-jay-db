package com.bluejay.core.queue;

import com.bluejay.core.exception.FCException;

/**
 * This class implements Singleton. It is responsible to administer open threads for 
 * the Messenger.
 * 
 * @author Marcos Costa
 */
public class Messenger {

	public static final Messenger INSTANCE = new Messenger();

	/**
	 * Create the Pipeline object and add start the process asynchronously.
	 * The Task is passed as a parameter. Any task can be implemented as long 
	 * as it implements FCTask.
	 * 
	 * @param task
	 * @return
	 * @throws FCException
	 */
	public synchronized boolean asyncProcess(FCTask task) throws FCException {
		Pipeline pipeline = new Pipeline(task);
		pipeline.start();
		return true;
	}


}
