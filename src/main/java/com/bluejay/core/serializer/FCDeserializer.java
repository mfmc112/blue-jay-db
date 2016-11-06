package com.bluejay.core.serializer;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.bluejay.core.exception.DBException;
import com.bluejay.core.exception.FCException;
import com.bluejay.core.index.FCType;
import com.bluejay.core.utils.PropertiesUtil;

import de.ruedigermoeller.serialization.FSTObjectInput;

public class FCDeserializer {

	public static synchronized <T> T readNoFail(String dataType, FCType type, String suffix) throws FCException {
		T obj = null;
		try{
			obj = read(dataType, type, suffix);
		}catch(FCException e){
			// Ignore error and return null;
		}
		return obj;
	}
	
	/**
	 * Deserialize Object. It receives the suffix for the file name to be deserialized
	 * as a generics <T> object  
	 * 
	 * @param InputStream stream
	 * @return
	 * @throws DBException
	 */
	public static synchronized <T> T read(String dataType, FCType type, String suffix) throws FCException {
		T obj = null;
		try{
			InputStream is = new FileInputStream(PropertiesUtil.getfileName(dataType, type.getName(), suffix));
			BufferedInputStream buf = new BufferedInputStream(is);
			obj = read(buf);
		}catch(FileNotFoundException e){
			throw new FCException("Could not find the file to deserialize", e);
		}
		return obj;
	}
	
	/**
	 * Deserialize Object. It receives the InputStream with the file to be deserialized
	 * as a generics <T> object  
	 * 
	 * @param InputStream stream
	 * @return
	 * @throws DBException
	 */
	@SuppressWarnings("unchecked")
	public static synchronized <T> T read(InputStream stream) throws FCException {
	    T result;
	    FSTObjectInput in = null;
		try {
			in = new FSTObjectInput(stream);
			result = (T) in.readObject();
			in.close();
		} catch (Exception e) {
			throw new DBException("Error deserializing object", e);
		}
	    return result;
	}
	
}
