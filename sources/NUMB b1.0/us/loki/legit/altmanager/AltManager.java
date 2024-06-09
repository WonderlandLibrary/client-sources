package us.loki.legit.altmanager;

import java.util.ArrayList;
import java.util.List;

public class AltManager {
	public static Alt lastAlt;
	public static ArrayList<Alt> registry;
	private List<Alt> alts;

	static {
		registry = new ArrayList();
	}

	public ArrayList<Alt> getRegistry() {
		return registry;
	}

	public void setLastAlt(Alt alt2) {
		lastAlt = alt2;
	}

	public List<Alt> getAlts() {
		return this.alts;
	}

	public Alt getLastAlt() {
		return this.lastAlt;
	}
}
