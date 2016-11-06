package com.bluejay.core.queue;

import java.util.Set;

import com.bluejay.core.exception.FCException;
import com.bluejay.core.storage.stack.FCStack;
import com.bluejay.core.storage.stack.PersistentStack;
import com.bluejay.logger.Logger;

public class IndexTask implements FCTask {

	Logger log = new Logger(IndexTask.class);
	
	private String dataType;
	Set<Long> ids;
	
	public IndexTask(String dataType, Set<Long> ids) {
		this.dataType = dataType;
		this.ids = ids;
	}

	@Override
	public String getName() {
		return "index";
	}
	
	@Override
	public void execute() throws FCException {
		FCStack stack = new PersistentStack(dataType);
		stack.refreshIndex(ids);
		log.info("Task " + getName() + " executed from the pipeline successfully");
	}
	
}
