package com.bluejay.logger;

import java.io.OutputStream;
import java.util.logging.ConsoleHandler;

public class FCConsoleHandler extends ConsoleHandler {
	
	protected void setOutputStream(OutputStream out) throws SecurityException {
		super.setOutputStream(System.out);
	}
	
}
