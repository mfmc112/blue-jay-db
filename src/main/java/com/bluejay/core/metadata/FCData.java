package com.bluejay.core.metadata;

import java.io.Serializable;

import com.smartasset.asset.exception.JsonException;
import com.smartasset.asset.json.JsonMapper;
import com.smartasset.asset.json.JsonMapperFactory;
import com.smartasset.asset.json.MapperType;

public class FCData implements Serializable {

	private static final long serialVersionUID = 1612323369462177272L;
	String JSONData;

	public FCData(String JSONData){
		this.JSONData = JSONData;
	}
	
	public String getJSONData() {
		return JSONData;
	}
	
	public <T> T getJSONDataAsJSON(@SuppressWarnings("rawtypes") Class elementClass) throws JsonException {
		@SuppressWarnings("unchecked")
		JsonMapper<T> mapper = JsonMapperFactory.createFactory(MapperType.GSON, elementClass);
		return mapper.readSingleValue(JSONData);
	}

}
