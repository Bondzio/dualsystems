package com.dual.bean;

public class MNBFaultException extends Exception {

	private static final long serialVersionUID = 3536827391570631854L;

	public MNBFaultException(String message) {
		super(message);
	}
	public MNBFaultException(Exception e) {
		super(e);
	}

}
