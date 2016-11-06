package com.bluejay.core.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.bluejay.core.exception.FCException;

public class MetadataUtil {

	private static final String LAST_UPDATE_KEY = "last.update";
	private static Map<String, Long> metadata = new HashMap<String, Long>(1);
	public static final MetadataUtil INSTANCE;

	// Initialize the INSTANCE and catch the exception if any
	static {
        try {
        	INSTANCE = new MetadataUtil();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
		}
    }
	
	private MetadataUtil() throws FCException, IOException{
		readMetadata();
	}
	
	public static Long getLastUpdated() throws FCException, IOException {
		readFile();
		return metadata.get(LAST_UPDATE_KEY);
	}
	
	private void readMetadata() throws FCException, IOException{
		createFolderIfDoesNotExist();
		readFile();
	}

	private static void readFile() throws FCException, IOException {
		File file = new File(PropertiesUtil.getMetadataFileName());
		if (!file.exists()){
			metadata.put(LAST_UPDATE_KEY,0L);
		}
		else{
			FileReader fileReader = new FileReader(file);
			@SuppressWarnings("resource")
			BufferedReader buff = new BufferedReader(fileReader);
			String line;
			while((line = buff.readLine()) != null) {
				String[] split = line.split("=");
				if (split.length == 2){
	                metadata.put(split[0], new Long(split[1]));
	            }
			}
		}
	}
	
	public static void updateMetadata(long millis) throws FCException, IOException {
		File file = new File(PropertiesUtil.getMetadataFileName());
		file.createNewFile();
		String data = LAST_UPDATE_KEY+"="+millis;
		FileWriter fileWriter = new FileWriter(file);
		BufferedWriter writer = new BufferedWriter(fileWriter);
		writer.write(data);
		writer.flush();
		writer.close();
	}

	private void createFolderIfDoesNotExist() throws FCException {
		File dir = new File(PropertiesUtil.getMetadataPath());
		if (!dir.exists()){
			dir.mkdirs();
		}
		
	}
	
	
}
