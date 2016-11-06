package com.bluejay.core.index;

import java.io.Serializable;
import java.util.Set;

import com.bluejay.core.exception.FCException;
import com.bluejay.core.serializer.FCDeserializer;
import com.bluejay.core.serializer.FCSerializer;

public abstract class AbstractIndex<T extends Serializable> implements FCIndex {

	private String indexField = "id";
	private T indexObject;
	private boolean dirty = false;
	
	/**
	 * Retrieve the dataType for the index. DataTypes are "object name" e.g. users
	 * @return
	 */
	public abstract String getDataType();
	
	/**
	 * Return the type of the index being implemented
	 * @return FCType
	 */
	public abstract FCType getIndexType();
	
	/**
	 * Implementation of the method that inserts records into the index
	 * @param value
	 * @param id
	 */
	public abstract void insert(String value, Long id);

	
	/**
	 * Implementation of the search algorithm into the index implementation
	 * @param q
	 * @return
	 */
	public abstract Set<Long> search(String q);
	
	/**
	 * Returns an empty object with is the head of the index
	 * @return
	 */
	public abstract T createEmptyObject();
	
	/**
	 * Load the index for the given Index type and field name
	 * Those values are defined by the constructor from the child class
	 * 
	 * @return
	 * @throws FCException
	 */
	public void loadIndexObject() throws FCException {
		T obj = FCDeserializer.readNoFail(getDataType(), getIndexType(), indexField);
		if (obj == null) obj = createEmptyObject();
		indexObject = obj;
	}
	

	/**
	 * Persists all the changes made into the file
	 * @throws FCException
	 */
	@Override
	public void persist() throws FCException {
		FCSerializer.write(getDataType(), getIndexType(), indexObject, indexField);
	}
	
	protected void setIndexField(String indexField) {
		this.indexField = indexField;
	}

	protected T getIndexObject() {
		return indexObject;
	}

	protected boolean isDirty() {
		return dirty;
	}

	protected void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
	
	
}
