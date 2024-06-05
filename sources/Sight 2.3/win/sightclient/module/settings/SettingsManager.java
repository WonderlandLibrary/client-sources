package win.sightclient.module.settings;

import java.util.ArrayList;

import win.sightclient.module.Module;

public class SettingsManager {

	private ArrayList<Setting> settings;
	
	public SettingsManager(){
		this.settings = new ArrayList<>();
	}
	
	public void rSetting(Setting in) {
		boolean contains = false;
		for (Setting s : this.settings) {
			if (s.getModule().getName().equalsIgnoreCase(in.getModule().getName()) && s.getName().equalsIgnoreCase(in.getName())) {
				contains = true;
			}
		}
		if (!contains) {
			this.settings.add(in);
		}
	}
	
	public ArrayList<Setting> getSettings() {
		return this.settings;
	}
	
	public ArrayList<Setting> getSettingsByMod(Module mod){
		ArrayList<Setting> out = new ArrayList<>();
		for(Setting s : getSettings()){
			if(s.getModule().equals(mod)){
				out.add(s);
			}
		}
		return out;
	}
	
	public ArrayList<String> getSettingsNameByMod(Module mod){
		ArrayList<String> out = new ArrayList<>();
		for(Setting s : getSettings()){
			if(s.getModule().equals(mod)){
				out.add(s.getName());
			}
		}
		if(out.isEmpty()){
			return null;
		}
		return out;
	}
	
	public Setting getSettingByName(Module mod, String name){
		for(Setting set : getSettings()){
			if(set.getName().equalsIgnoreCase(name) && set.getModule().equals(mod)){
				return set;
			}
		}
		return null;
	}
}
