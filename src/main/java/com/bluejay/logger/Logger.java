package com.bluejay.logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.bluejay.core.PropertiesParser;
import com.bluejay.core.exception.FCException;

public class Logger extends java.util.logging.Logger {

	public static String loggerLevel = "ALL";
	private Long startTime = null;
	
	public Logger(String name, String resourceBundleName) {
		super(name, resourceBundleName);
		configureHandler();
	}

	public Logger (Class<?> elementClass){
		super(elementClass.getName(), null);
		configureHandler();
	}

	public void error(String message) {
		error(message, null);
	}

	public void error(String message, Exception e) {
		StringBuilder msg = new StringBuilder(message);
		msg.append(getStackTraceAsString(e));
		severe(msg.toString());
	}
	
	
	/*
	 * Return a StackTrace as String
	 */
	private String getStackTraceAsString(Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		return exceptionAsString;
	}

	private void configureHandler() {
		Handler handler = new FCConsoleHandler();
		try {
			String level = PropertiesParser.INSTANCE.get("logger.level");
			if (level != null) loggerLevel = level;
			handler.setLevel(Level.parse(loggerLevel));
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FCException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		handler.setFormatter(new FCFormatter());
		this.addHandler(handler);
	}


	private class FCFormatter extends Formatter {
		@Override
		public String format(LogRecord record) {
			StringBuilder builder = new StringBuilder(1000);
	        builder.append(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS").format(new Date(record.getMillis()))).append(" - ");
	        builder.append("[").append(record.getLevel()).append("] - ");
	        builder.append(record.getSourceClassName()).append(".");
	        builder.append(record.getSourceMethodName()).append(" - ");
	        builder.append(formatMessage(record));
	        builder.append("\n");
	        return builder.toString();
		}
	}
	
	public void startTimer() {
		startTime  = System.currentTimeMillis();
	}
	
	public long endTimer() throws FCException {
		if (startTime == null) throw new FCException("Timer was not started");
		long endTime = System.currentTimeMillis() - startTime.longValue();
		startTime = null;
		return endTime;
	}
}
