package com.bluejay.core.exception;

public class FCException extends Exception {

	private static final long serialVersionUID = 459282174537648327L;

	public FCException(Exception e) {
		super(e.getMessage(), e);
	}
	
	public FCException(String string, Exception e) {
		super(string, e);
	}

	public FCException(String string) {
		super(string);
	}

}
