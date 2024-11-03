package net.silentclient.client.mods;

import java.util.ArrayList;
import java.util.Collections;

import net.silentclient.client.Client;

public class SettingsManager {
	private ArrayList<Setting> settings;

	public SettingsManager(){
		this.settings = new ArrayList<>();
	}

	public Setting addSetting(Setting in){
		this.settings.add(in);
		return in;
	}

	public ArrayList<Setting> getSettingByMod(Mod mod){
		ArrayList<Setting> out = new ArrayList<>();
		for(Setting s : getSettings()){
			if(s.getParentMod().equals(mod)){
				out.add(s);
			}
		}

		Collections.sort(out);
		return out;
	}

	public Setting getSettingByNameForClickGUI(String name){
		for(Setting set : getSettings()){
			if(set.getName().equalsIgnoreCase(name)){
				return set;
			}
		}
		return null;
	}

	public Setting getSettingByName(Mod mod, String name){
		for(Setting set : getSettings()){
			if(set.getName().equalsIgnoreCase(name) && set.getParentMod() == mod){
				return set;
			}
		}
		return null;
	}

	public Setting getSettingByClass(Class<?> modClass, String name){
		for(Setting set : getSettings()){
			if(set.getName().equalsIgnoreCase(name) && set.getParentMod().equals(Client.getInstance().getModInstances().getModByClass(modClass))){
				return set;
			}
		}
		return null;
	}

	public ArrayList<Setting> getSettings() {
		return settings;
	}
}
