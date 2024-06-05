package xyz.vergoclient.settings;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import xyz.vergoclient.modules.ModuleManager;

public class ModeSetting extends Setting {
	
	@SerializedName(value = "index")
	public int index;
	
	@SerializedName(value = "modes")
	public List<String> modes;
	
	public ModeSetting(String name, String defaultMode, String... modes) {
		
		this.name = name;
		this.modes = new LinkedList<String>(Arrays.asList(modes));
		index = this.modes.indexOf(defaultMode);
		
	}
	
	public String getMode() {
		
		if (index < 0)
			index = 0;
		
		try {
			return this.modes.get(index);
		} catch (Exception e) {
			e.printStackTrace();
			e.fillInStackTrace();
		}
		return "";
		
	}
	
	public boolean is(String mode) {
		
		if (getMode().equals(mode) || index == modes.indexOf(mode)) {
			return true;
		}
		return false;
		
	}
	
	public void cycle(boolean backwards) {
		
		if (backwards) {
			
			if (index > 0) {
				index--;
			}else {
				index = modes.size() - 1;
			}
			
		}else {
			
			if (index < modes.size() - 1) {
				index++;
			}else {
				index = 0;
			}
			
		}
		
		SettingChangeEvent settingMode = new SettingChangeEvent(getSetting());
		ModuleManager.onSettingChange(settingMode);
		
	}
	
	public void setMode(String mode) {
		
		for (String string : modes) {
			if (string.equals(mode)) {
				index = modes.indexOf(string);
			}
		}
		
		SettingChangeEvent settingMode = new SettingChangeEvent(getSetting());
		ModuleManager.onSettingChange(settingMode);
		
	}
	
}
