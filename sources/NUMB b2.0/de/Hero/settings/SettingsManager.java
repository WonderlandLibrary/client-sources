package de.Hero.settings;

import java.util.ArrayList;

//Your Imports
import us.loki.legit.modules.*;
import us.loki.legit.*;

/**
 * Made by HeroCode it's free to use but you have to credit him
 *
 * @author HeroCode
 */
public class SettingsManager {

	private ArrayList<Setting> settings;

	public SettingsManager() {
		this.settings = new ArrayList<Setting>();
	}

	public void rSetting(Setting in) {
		this.settings.add(in);
	}

	public ArrayList<Setting> getSettings() {
		return this.settings;
	}

	public ArrayList<Setting> getSettingsByMod(Module mod) {
		ArrayList<Setting> out = new ArrayList<Setting>();
		for (Setting s : getSettings()) {
			if (s.getParentMod().equals(mod)) {
				out.add(s);
			}
		}
		if (out.isEmpty()) {
			return null;
		}
		return out;
	}

	public Setting getSettingByName(String name) {
		for (Setting set : getSettings()) {
			if (set.getName().equalsIgnoreCase(name)) {
				return set;
			}
		}
		System.err.println("[" + Client.Client_Name + "] Error Setting NOT found: '" + name + "'!");
		return null;
	}

}