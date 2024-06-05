package win.sightclient.ui.altmanager;

import java.util.ArrayList;

public class AltManager {

	private static ArrayList<Alt> alts = new ArrayList<Alt>();
	
	public static void addAlt(Alt alt) {
		alts.add(alt);
	}
	
	public static void removeAlt(Alt alt) {
		if (alts.contains(alt)) {
			alts.remove(alt);
		}
	}
	
	public static void removeAlt(int alt) {
		if (alts.size() > alt) {
			alts.remove(alt);
		}
	}
	
	public static ArrayList<Alt> getAlts() {
		return alts;
	}
}
