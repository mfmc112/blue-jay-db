package com.bluejay.core.index;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bluejay.core.exception.FCException;
import com.bluejay.core.utils.FileUtils;
import com.bluejay.core.utils.JsonUtils;
import com.bluejay.core.utils.PropertiesUtil;
import com.bluejay.logger.Logger;

/**
 * Parse the mapping from JSON format into a Map of Indexes using 
 * the field name as a key. The index instance added to the map 
 * implements the appropriate algorithm to make the index fast and 
 * persistent in a small in size file as possible.
 * 
 * @author Marcos Costa
 *
 */
public class IndexParser {

	private Logger log = new Logger(IndexParser.class);
	private final String MAPPING_SUFFIX = "_mapping";
	
	/**
	 * Retrieve the configuration mapping for the dataType and get the instances of 
	 * the necessary indexes accordingly to what was specified into the configuration
	 *  
	 * @param dataType
	 * @param mapping
	 * @return Map<String, FCIndex>
	 * @throws FCException
	 */
	public Map<String, FCIndex> getIndexes(String dataType, Map<String, Object> mapping) throws FCException {
		if (dataType == null || dataType.equals("")) throw new FCException("dataType cannot be null or empty");
		
		if (mapping == null){
			try{
				mapping = openMappingFileAsJSON(dataType);
			} catch(FCException e) {
				log.warning("Mapping file not found. The default (IndexEquals) will be used to every field.");
				return null;
			}
		}
		
		Map<String, FCIndex> indexMap = new HashMap<String, FCIndex>();
		for (String key : mapping.keySet()) {
			FCIndexType indexType = null;
			Object value = mapping.get(key);
			if (value instanceof List) {
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> l = (List<Map<String, Object>>) value;
				for (Map<String, Object> arrayObj : l) {
					//TODO: find better name for this variable
					Map<String, FCIndex> iMap = getIndexes(dataType, arrayObj);
					indexMap.putAll(iMap);
				}
			} else {
				indexType = FCIndexType.getByType((String) mapping.get(key));
			}
			
			if (indexType != null ) {
				indexMap.put(key, IndexFactory.createIndex(indexType, dataType, key));
			}
		}
		return indexMap;
	}
	
	public Map<String, Object> openMappingFileAsJSON(String dataType) throws FCException {
		File mapping = new File(PropertiesUtil.getFilePath(dataType + MAPPING_SUFFIX)+".json");
		String stringJson = FileUtils.readFile(mapping);
		return JsonUtils.convertJsonToMap(stringJson);
		
	}
}
