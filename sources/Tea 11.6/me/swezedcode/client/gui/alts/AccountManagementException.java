package me.swezedcode.client.gui.alts;

public class AccountManagementException extends Exception {
	public AccountManagementException() {
		super("AccountManagementException");
	}
	
	public AccountManagementException(String message) {
		super(message);
	}
	
	public AccountManagementException(String message, Throwable tObj) {
		super(message, tObj);
	}
}
