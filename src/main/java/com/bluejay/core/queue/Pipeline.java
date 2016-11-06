package com.bluejay.core.queue;

import com.bluejay.core.exception.FCException;
import com.bluejay.logger.Logger;

public class Pipeline implements Runnable {

	Logger log = new Logger(Pipeline.class);
	
	Thread runner;
	private FCTask task = null;
	
	public Pipeline(FCTask task) {
		this.task = task;
	}
	
	public void start() {
		runner = new Thread(this);
		runner.start();
	}

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
