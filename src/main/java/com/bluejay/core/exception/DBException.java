package com.bluejay.core.exception;

public class DBException extends FCException {

	private static final long serialVersionUID = 1L;

	public DBException(String string, Exception e) {
		super(string, e);
	}

}
