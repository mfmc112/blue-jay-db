package com.bluejay.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.bluejay.core.exception.FCException;

public class PropertiesParser {
	
	private Properties properties; 
	public final String PROPERTY_FILE_NAME = "config.properties";
	public static final PropertiesParser INSTANCE;
	
	static {
        try {
            INSTANCE = new PropertiesParser();
        } catch (FCException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
	
	private PropertiesParser() throws FCException { 
		parseProperties();
	}
	
	private void parseProperties() throws FCException {
		try {
			properties = new Properties();
			String propFileName = PROPERTY_FILE_NAME;
			InputStream propertyStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			if (propertyStream != null){
				properties.load(propertyStream);
			}
			else throw new FCException("Property file not found");
		} catch (IOException e) {
			throw new FCException("Error opening property file");
		}
	}
	
	public String get(String key) throws FCException {
		String value = properties.getProperty(key);
		if (value == null) throw new FCException("Property not found");
		return value;
	}
}
