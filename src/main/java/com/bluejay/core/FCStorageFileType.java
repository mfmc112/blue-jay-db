package com.bluejay.core;

import com.bluejay.core.index.FCType;

public enum FCStorageFileType implements FCType {

	LOCATOR("-loc-", "locator"), INDEX("-i", "index");
	
	private String name;
	private String type;
	
	private FCStorageFileType(String name, String type){
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

}
