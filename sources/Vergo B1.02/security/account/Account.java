package xyz.vergoclient.security.account;

import com.google.gson.annotations.SerializedName;

public class Account {
	
	public Account(int uid, String username, String hwid, int banned) {
		this.uid = uid;
		this.username = username;
		this.hwid = hwid;
		this.banned = banned;
	}
	
	@SerializedName(value = "uid")
	public int uid;

	@SerializedName(value = "username")
	public String username;

	@SerializedName(value = "hwid")
	public String hwid;
	
	@SerializedName(value = "banned")
	public int banned;
	
}
