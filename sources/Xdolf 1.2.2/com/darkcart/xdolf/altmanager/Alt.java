package com.darkcart.xdolf.altmanager;

public class Alt {
	private String aUserName;
	private String aPassword;
	private boolean premium;
	
	public Alt(String username, String password) {
		this.premium = true;
		this.aUserName = username;
		this.aPassword = password;
	}
	
	public Alt(String username) {
		this.premium = false;
		this.aUserName = username;
		this.aPassword = "N/A";
	}
	
	public String getFileLine() {
		if(this.premium) {
			return this.aUserName.concat(":").concat(this.aPassword);
		} else {
			return this.aUserName;
		}
	}
	
	public String getUsername() {
		return this.aUserName;
	}
	
	public String getPassword() throws AccountManagementException {
		if(this.premium) {
			return this.aPassword;
		} else {
			throw new AccountManagementException("Non-Premium accounts do not have passwords!");
		}
	}
	
	public boolean isPremium() {
		return this.premium;
	}
}
