package ooo.cpacket.ruby.api;

public class ClientInfo {
	
	protected String name; 
	protected String build;
	
	public ClientInfo(String name, String build) {
		this.name = name;
		this.build = build;
	}

	public static ClientInfo get() {
		return new ClientInfo("Meme", "stable-build8");
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getNameUpperCase() {
		return this.name.toUpperCase();
	}
	
	public String getBuild() {
		return this.build;
	}

	public String getNameF() {
		return "\u00A7aM";
	}

	public String getNameG() {
		return "\u00A79eme";
	}
	
}
