package win.sightclient.server;

public enum Capes {

	NONE(0, null, "Nothing"),
	MIDNIGHT(1, "https://sighthost.netlify.app/capes/Cape1.png", "Midnight"),
	COLORSIGHT(2, "https://sighthost.netlify.app/capes/Cape2.png", "ColorSight"),
	FREE(3, "https://sighthost.netlify.app/capes/Cape3.png", "Free"),
	FREE2(4, "https://sighthost.netlify.app/capes/Cape4.png", "Free2"),
	CORSAIR(5, "https://sighthost.netlify.app/capes/Corsair.gif", "Corsair"),
	FINALMOM(6, "https://sighthost.netlify.app/finalmom/finalmom2.png", "FinalMom");
	
	private final int id;
	private final String url;
	private final String name;
	
	Capes(int id, String url, String name) {
		this.id = id;
		this.url = url;
		this.name = name;
	}
	
	public int getID() {
		return id;
	}
	
	public String getURL() {
		return url;
	}
	
	public String getName() {
		return this.name;
	}
}
