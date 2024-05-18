package me.protocol_client.friendsList;

public class Protect {
	private String name;
	private String protect;
	
	public Protect(String name, String protect) {
		this.name = name;
		this.protect = protect;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAlias() {
		return protect;
	}
}
