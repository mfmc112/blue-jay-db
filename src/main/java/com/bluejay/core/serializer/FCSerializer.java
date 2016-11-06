package com.bluejay.core.serializer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.bluejay.core.exception.DBException;
import com.bluejay.core.exception.FCException;
import com.bluejay.core.index.FCType;
import com.bluejay.core.utils.FileUtils;
import com.bluejay.core.utils.PropertiesUtil;

import de.ruedigermoeller.serialization.FSTObjectOutput;

public class FCSerializer {

	/**
	 * Serialize the object. It receives the object to be serialized.
	 * 
	 * @param FCStorageFileType type
	 * @param T toWrite
	 * @param String suffix
	 * @throws FCException 
	 */
	public static synchronized <T> void write(String dataType, FCType type, T toWrite, String suffix ) throws FCException {
		write(dataType, type.getName(), toWrite, suffix);
	}
	
	/**
	 * Serialize the object. It receives the object to be serialized.
	 * 
	 * @param String type
	 * @param T toWrite
	 * @param String suffix
	 * @throws FCException 
	 */
	public static synchronized <T> void write(String dataType, String type, T toWrite, String suffix ) throws FCException {
		try{
			FileUtils.visitFolder(PropertiesUtil.getFilePath(dataType));
			String fileName = PropertiesUtil.getfileName(dataType, type, suffix);
			FileOutputStream fout = new FileOutputStream(fileName);
			write(fout, toWrite);
		}catch(FileNotFoundException e){
			throw new FCException("Could not find the file to write serialization of the locator", e);
		}
	}
	
	
	/**
	 * Serialize the object. It receives the inputStream of the file where it 
	 * will be persisted and the object to be serialized.
	 * 
	 * @param OutputStream stream
	 * @param LinkedListStorageNode toWrite
	 * @throws DBException
	 */
	public static synchronized <T> void write(OutputStream stream, T toWrite) throws FCException {
	    FSTObjectOutput out = null;
	    try {
	    	out = new FSTObjectOutput(stream);
			out.writeObject(toWrite);
			out.close();
		} catch (IOException e) {
			throw new FCException("Error serializing object", e);
		}
	}
}
