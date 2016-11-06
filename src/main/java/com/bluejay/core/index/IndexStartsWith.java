package com.bluejay.core.index;

import java.io.Serializable;
import java.util.Set;

import com.bluejay.core.exception.FCException;
import com.bluejay.core.storage.node.PrefixNode;

/**
 * This index implements Prefix Tree algorithm which is Ties algorithm. This allow the search to
 * be performed quickly. 
 * 
 * It will return a Set of the ids found for all the records that match the field with the value 
 * starting with.
 * 
 * @author Marcos Costa
 *
 */
public class IndexStartsWith extends AbstractIndex<PrefixNode> implements Serializable {

	private static final long serialVersionUID = -1032116992559082346L;
	
	private String dataType;

	@Override
	public FCType getIndexType() {
		return FCIndexType.STARTS_WITH;
	}
	
	public IndexStartsWith(String dataType, String fieldName) throws FCException {
		if (fieldName == null || fieldName.equals("")) throw new FCException("Cannot use index without a fieldName");
		if (dataType == null || dataType.equals("")) throw new FCException("Cannot use index without a dataType");

		this.dataType = dataType;
		setIndexField(fieldName);
		loadIndexObject();
	}

	/**
	 * This implementation is equality of the whole String.
	 * 
	 * @param value
	 * @param id
	 */
	@Override
	public void insert(String value, Long id) {
		if (value == null || id == null) return;
		getIndexObject().insert(value, id);
	}

	@Override
	public Set<Long> search(String q) {
		if (q == null) return null;
		return getIndexObject().searchStartWith(q);
	}

	@Override
	public PrefixNode createEmptyObject() {
		return new PrefixNode(' ');
	}

	@Override
	public String getDataType() {
		return dataType;
	}	

	
}
