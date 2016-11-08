package com.bluejay.core.queue;

import com.bluejay.core.exception.FCException;
import com.bluejay.logger.Logger;

/**
 * <p>
 * Pipeline executes task. For each pipeline created a single task can be injected.
 * The Pipeline class is nothing more than a Factory to creates threads and starts it.
 * </p>
 * <p>
 * The FCTask.execute() gets triggered and once it is done the thread ends.
 * </p>
 * @author Marcos Costa
 *
 */
public class Pipeline implements Runnable {

	Logger log = new Logger(Pipeline.class);
	
	Thread runner;
	private FCTask task = null;
	
	public Pipeline(FCTask task) {
		this.task = task;
	}
	
	/**
	 * Starts the thread created by this Pipeline
	 * 
	 */
	public void start() {
		runner = new Thread(this);
		runner.start();
	}

	/**
	 * Returns the name of the task being executed
	 * @return
	 */
	public String getTaskName() {
		return task.getName();
	}
	
	@Override
	public void run() {
		try {
			log.startTimer();
			task.execute();
			log.info("Async task (" + getTaskName() + ") executed in " + log.endTimer() + " ms");
		} catch (FCException e) {
			log.error("Error executing async task (" + getTaskName() + ")", e);
		}
	}

}
