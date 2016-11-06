package com.bluejay.core.index;

public enum FCIndexType implements FCType {

	EQUALS("e-", "exactMatch"), 
	STARTS_WITH("li-", "startsWith"), 
	ID("id-", "id");
	
	private String name;
	private String type;
	
	private FCIndexType(String name, String type) {
		this.name = name;
		this.type = type;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String getType() {
		return type;
	}
	
	public static FCIndexType getByType(String type) {
		for (FCIndexType indexType : FCIndexType.values()) {
			if (indexType.getType().equalsIgnoreCase(type)) return indexType;
		}
		return null;
	}
}
