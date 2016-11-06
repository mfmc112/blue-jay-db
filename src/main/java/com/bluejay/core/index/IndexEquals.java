package com.bluejay.core.index;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.bluejay.core.exception.FCException;

/**
 * This index implements leverage HashCode and HashMap to retrieve the related ids quickly.
 * it stores any string as hashcode and the ids containing that string;
 * 
 * @author Marcos Costa
 *
 */
public class IndexEquals extends AbstractIndex<HashMap<Long, Set<Long>>> implements Serializable {

	private static final long serialVersionUID = -1032116992559082346L;
	private String dataType;

	@Override
	public FCType getIndexType() {
		return FCIndexType.EQUALS;
	}
	
	public IndexEquals(String dataType, String fieldName) throws FCException {
		if (fieldName == null || fieldName.equals("")) throw new FCException("Cannot index without a fieldName");
		if (dataType == null || dataType.equals("")) throw new FCException("Cannot use index without a dataType");
		
		this.dataType = dataType;
		setIndexField(fieldName);
		loadIndexObject();
	}

	/**
	 * This implementation is equality of the whole String. The implementation leverages 
	 * HashMap and hashCode to generate the index file as opposed of using a prefix tree. 
	 * This reduced the index size in 75%
	 * 
	 * @param value
	 * @param id
	 */
	@Override
	public void insert(String value, Long id) {
		if (value == null || id == null) return;
		Long key = generateKey(value);
		Set<Long> ids = getIndexObject().get(key);
		if (ids == null) ids = new HashSet<Long>(1);
		ids.add(id);
		getIndexObject().put(key, ids);
	}

	@Override
	public Set<Long> search(String q) {
		if (q == null) return null;
		return getIndexObject().get(generateKey(q));
	}

	@Override
	public HashMap<Long, Set<Long>> createEmptyObject() {
		return new HashMap<Long, Set<Long>>();
	}

	private Long generateKey(String value) {
		return new Long(value.hashCode());
	}
	
	@Override
	public String getDataType() {
		return dataType;
	}	
	
}
