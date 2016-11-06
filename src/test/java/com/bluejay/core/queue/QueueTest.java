package com.bluejay.core.queue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.bluejay.core.exception.FCException;
import com.bluejay.core.metadata.FCData;
import com.bluejay.core.storage.node.LinkedNode;
import com.bluejay.core.storage.stack.FCStack;
import com.bluejay.core.storage.stack.PersistentStack;
import com.bluejay.core.storage.stub.StorageTestStub;
import com.bluejay.core.storage.stub.StubData;
import com.bluejay.logger.Logger;

public class QueueTest extends StubData {

	private static Logger log = new Logger(QueueTest.class);
	
	public QueueTest(){
		super.stub = new StorageTestStub();
	}
	
	public static void main(String args[]) throws FCException {
		QueueTest q = new QueueTest();
		q.pipelineCreateTest();
		Set<Long> ids = new HashSet<Long>();
		ids.add(0L);
		FCTask task = new IndexTask("pipe-index", ids);
		
		Messenger.INSTANCE.asyncProcess(task);
		log.info("Returned to the code. Async being processed");

	}
	
	@Test
	public void pipelineCreateTest() throws FCException {
		int numOfRecords = 10000;
		String dataType = "pipe-index";
		stub.deleteAllFiles(dataType);
		QueueTest.log.startTimer();		
		FCStack stack = new PersistentStack(dataType);
		for (int i=0;i<numOfRecords;i++){
			stack.push( new LinkedNode(new Long(i), new FCData(stub.singleJsonString)) );
		}
		QueueTest.log.info("added (" + numOfRecords + ") records into the stack in " + log.endTimer() + " ms");
		stack.commitNoIndex();
	}
}
