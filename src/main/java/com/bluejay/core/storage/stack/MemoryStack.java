package com.bluejay.core.storage.stack;

/**
 * Memory implementation for the Stack. This class extends the BasicStatus and does not implement 
 * the methods to commit are not implemented so it will not persist.
 * 
 * @author Marcos Costa
 */
public class MemoryStack extends BasicStack {

	public MemoryStack(){
		super("basic");
	}
	
	public MemoryStack(String dataType){
		super(dataType);
	}

}
