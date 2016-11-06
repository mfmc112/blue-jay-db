package com.bluejay.core.utils;

import com.bluejay.core.FCStorageFileType;
import com.bluejay.core.PropertiesParser;
import com.bluejay.core.exception.FCException;

public class PropertiesUtil {

	public static String getfileName(String dataType, String sufix) throws FCException {
		return getfileName(dataType, FCStorageFileType.LOCATOR.getName(), sufix);
	}
	
	public static String getfileName(String dataType, String type, String sufix) throws FCException {
		StringBuilder fileName = new StringBuilder();
		fileName.append(PropertiesParser.INSTANCE.get("serialization.file.path"));
		fileName.append("\\");
		fileName.append(dataType);
		fileName.append("\\");
		fileName.append(PropertiesParser.INSTANCE.get("serialization.file.prefix"));
		fileName.append(type);
		fileName.append(sufix);
		return fileName.toString();
	}
	
	public static String getFilePath(String dataType) throws FCException {
		StringBuilder fileName = new StringBuilder(getFilePath());
		fileName.append("\\");
		fileName.append(dataType);
		return fileName.toString();
	}
	
	public static String getFilePath() throws FCException {
		StringBuilder fileName = new StringBuilder();
		fileName.append(PropertiesParser.INSTANCE.get("serialization.file.path"));
		return fileName.toString();
	}
	
	public static String getMetadataPath() throws FCException {
		StringBuilder fileName = new StringBuilder();
		fileName.append(PropertiesParser.INSTANCE.get("serialization.file.path"));
		fileName.append(PropertiesParser.INSTANCE.get("serialization.metadata.path"));
		return fileName.toString();
	}
	
	public static String getMetadataFileName() throws FCException {
		StringBuilder fileName = new StringBuilder();
		fileName.append(PropertiesParser.INSTANCE.get("serialization.file.path"));
		fileName.append(PropertiesParser.INSTANCE.get("serialization.metadata.path"));
		fileName.append("\\");
		fileName.append(PropertiesParser.INSTANCE.get("serialization.metadata.filename"));
		return fileName.toString();
	}
	
}
