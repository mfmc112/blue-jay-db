package com.bluejay.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.bluejay.core.exception.FCException;
import com.bluejay.core.index.IndexParser;
import com.bluejay.logger.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.smartasset.asset.exception.JsonException;
import com.smartasset.asset.json.JsonMapper;
import com.smartasset.asset.json.JsonMapperFactory;
import com.smartasset.asset.json.MapperType;

public class JsonUtils {

	private static Logger log = new Logger(IndexParser.class);
	
	public static Map<String, Object> convertJsonToMap(String content) throws FCException {
		try {
			Map<String, Object> mapToReturn = new HashMap<String, Object>();
			JsonElement mapping = fromStringToJsonElement(content);
			if (mapping == null) throw new FCException("Requested mapping not found");
			
			JsonObject obj = mapping.getAsJsonObject();
			Set<Entry<String, JsonElement>> mappingObjects = obj.entrySet();
			if (mappingObjects == null || mappingObjects.size() <= 0) throw new FCException("Requested Mapping is empty");

			parseEntrySet(mappingObjects, mapToReturn, null);
			
			return mapToReturn;
		} catch (JsonException e) {
			log.error("Error converting JSON to Map ", e);
			throw new FCException("Error converting JSON to Map ", e);
		}
	}
	
	private static void parseEntrySet(Set<Entry<String, JsonElement>> mappingObjects, Map<String, Object> mapToReturn, String superKey) {
		for (Entry<String, JsonElement> mappingObject : mappingObjects) {
			String key = mappingObject.getKey();
			JsonElement value = mappingObject.getValue();
			
			if (isNull(mappingObject.getValue())) continue;
			if (isArray(value)) {
				List<Map<String, Object>> list = parseArrayAsList(key, value, key);
				if (list != null) mapToReturn.put(buildKey(superKey, key), list);
			}else{
				mapToReturn.put(buildKey(superKey, key), value.getAsString());	
			}
		}		
	}

	public static boolean isArray(Object value) {
		return (value != null && value instanceof JsonArray);
	}

	public static boolean isNull(JsonElement value) {
		return (value == null || value instanceof JsonNull);
	}

	private static List<Map<String, Object>> parseArrayAsList(String key, JsonElement value, String superKey) {
		if (key == null || value == null) return null;

		List<Map<String, Object>> listToReturn = new ArrayList<Map<String,Object>>();
		JsonArray array = value.getAsJsonArray();
		for (JsonElement arrayElement : array) {
			Set<Entry<String, JsonElement>> set = arrayElement.getAsJsonObject().entrySet();
			if (set == null || set.size() <= 0) continue;
			Map<String, Object> elementMap = parseEntryIntoMap(key, set, superKey);
			if (elementMap != null) listToReturn.add(elementMap);
		}
		return listToReturn;
	}

	/*
	 * Iterate over all the elements of an Entry and return Map<String, Object>
	 */
	private static Map<String, Object> parseEntryIntoMap(String entryKey, Set<Entry<String, JsonElement>> set, String superKey) {
		Map<String, Object> mapToReturn = new HashMap<String, Object>();
		parseEntrySet(set, mapToReturn, superKey);
		return mapToReturn;
	}

	private static String buildKey(String superKey, String elementKey) {
		if (superKey == null) return elementKey;
		return superKey + "." + elementKey;
	}

	public static JsonElement fromStringToJsonElement(String content) throws JsonException {
		JsonMapper<JsonElement> mapper = JsonMapperFactory.createFactory(MapperType.GSON, JsonElement.class);
		return mapper.readSingleValue(content);
	}
	
}
