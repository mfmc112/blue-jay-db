package com.bluejay.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;

import com.bluejay.core.exception.FCException;

public class FileUtils {

	public static void visitFolder(String path) {
		File dir = new File(path);
		if (!dir.exists()){
			dir.mkdirs();
		}
	}
	
	
	public synchronized static String readFile(File file) throws FCException {
		if (file == null) return null;
		try{
			Reader fileReader = new FileReader(file);
			BufferedReader buff = new BufferedReader(fileReader);
			StringBuilder data = new StringBuilder();
			String line;
			while((line = buff.readLine()) != null) {
				data.append(line);
			}
			buff.close();
			fileReader.close();
			return data.toString();
		} catch(Exception e) {
			throw new FCException("Error reading file " + file.getName(), e);
		}
	}
}
