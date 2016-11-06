package com.bluejay.core.index;

import com.bluejay.core.exception.FCException;

public class IndexFactory {

	public static FCIndex createIndex(FCIndexType indexType, String dataType, String fieldName) throws FCException {
		FCIndex index = null;
		switch(indexType) {
		case EQUALS:
			index = new IndexEquals(dataType, fieldName);
			break;
		case STARTS_WITH:
			index = new IndexStartsWith(dataType, fieldName);
			break;
		default:
			break;
		}
		return index;
	}
	
}
