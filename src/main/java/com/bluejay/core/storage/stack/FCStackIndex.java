package com.bluejay.core.storage.stack;

import com.bluejay.core.index.FCIndex;

public interface FCStackIndex extends FCStack {
	
	FCIndex getIndexForField(String field);
}
