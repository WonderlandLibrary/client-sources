package me.swezedcode.client.manager.managers.more;

public class Friend {

	private String user, alias;

	public Friend(String user, String alias) {
		this.user = user;
		this.alias = alias;
	}

	public String getAlias() {
		return alias;
	}

	public String getUser() {
		return user;
	}

	public void setAl(String alias) {
		this.alias = alias;
	}

	public void setUs(String username) {
		this.user = username;
	}

}