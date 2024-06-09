package de.Hero.settings;

import java.util.ArrayList;

import me.finz0.osiris.module.Module;

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class SettingsManager {
	
	private ArrayList<Setting> settings;
	
	public SettingsManager(){
		this.settings = new ArrayList<>();
	}
	
	public void rSetting(Setting in){
		this.settings.add(in);
	}
	
	public ArrayList<Setting> getSettings(){
		return this.settings;
	}
	
	public ArrayList<Setting> getSettingsByMod(Module mod){
		ArrayList<Setting> out = new ArrayList<>();
		for(Setting s : getSettings()){
			if(s.getParentMod().equals(mod)){
				out.add(s);
			}
		}
		if(out.isEmpty()){
			return null;
		}
		return out;
	}
	
	public Setting getSettingByDisplayName(String name){
		for(Setting set : getSettings()){
			if(set.getDisplayName().equalsIgnoreCase(name)){
				return set;
			}
		}
		System.err.println("[Aurora] Error Setting NOT found: '" + name +"'!");
		return null;
	}

	public Setting getSettingByID(String id){
		for(Setting s : getSettings()){
			if(s.getId().equalsIgnoreCase(id)){
				return s;
			}
		}
		System.err.println("[Aurora] Error Setting NOT found: '" + id +"'!");
		return null;
	}

}