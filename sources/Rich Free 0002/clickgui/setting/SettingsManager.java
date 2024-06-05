package clickgui.setting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import me.rich.module.Feature;

//Deine Imports

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class SettingsManager {
	
	private static ArrayList<Setting> settings;
	
	public SettingsManager(){
		this.settings = new ArrayList<>();
	}
	
	public void rSetting(Setting in){
		this.settings.add(in);
	}
	
	public static ArrayList<Setting> getSettings(){
		return settings;
	}
	
	public ArrayList<Setting> getSettingsByMod(Feature mod){
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
	
	public Setting getSettingByName(Feature mod, String name){
        for(Setting set : getSettings()){
            if(set.getName().equalsIgnoreCase(name) && set.getParentMod() == mod){
                return set;
            }
        }
        System.err.println("[] Error Setting NOT found: '" + name +"'!");
        return null;
    }
	public boolean hasSettings(Feature mod) {
        ArrayList<Setting> out = new ArrayList<>();
        for(Setting s : getSettings()){
            if(s.getParentMod().equals(mod)){
                out.add(s);
            }
        }
        if(out.isEmpty()){
            return false;
        }
        return true;
    }
	
	
	public Setting getSettingByName(String name){
		for(Setting set : getSettings()){
			if(set.getName().equalsIgnoreCase(name)){
				return set;
			}
		}
		return null;
	}

}