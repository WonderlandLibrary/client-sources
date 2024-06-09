package de.Hero.settings;

import me.Emir.Karaguc.Karaguc;
import me.Emir.Karaguc.module.Module;

import java.util.ArrayList;

/**
 *  Made by KaragucCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author KaragucCode
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
	
	public Setting getSettingByName(String name){
		for(Setting set : getSettings()){
			if(set.getName().equalsIgnoreCase(name)){
				return set;
			}
		}
		System.err.println("["+ Karaguc.instance.name + "] Error Setting NOT found: '" + name +"'!");
		return null;
	}

}