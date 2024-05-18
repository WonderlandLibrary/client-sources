package com.kilo.users;

public class User {

	public boolean sizeEnabled, earsEnabled, flipEnabled, ircEnabled;
	public float size;
	public String minecraftName, ircTag, earSize;
	
	public User(String minecraftName, boolean sizeEnabled, boolean earsEnabled, boolean flipEnabled, boolean ircEnabled, float size, String earSize, String ircTag) {
		this.minecraftName = minecraftName;
		this.sizeEnabled = sizeEnabled;
		this.earsEnabled = earsEnabled;
		this.flipEnabled = flipEnabled;
		this.ircEnabled = ircEnabled;
		this.size = size;
		this.earSize = earSize;
		this.ircTag = ircTag;
	}
}
