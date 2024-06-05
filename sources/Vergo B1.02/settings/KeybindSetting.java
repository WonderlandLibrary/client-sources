package xyz.vergoclient.settings;

import com.google.gson.annotations.SerializedName;

import xyz.vergoclient.modules.ModuleManager;

public class KeybindSetting extends Setting {
	
	@SerializedName(value = "code")
	public int code;
	
	public KeybindSetting(String name, int code) {
		this.name = name;
		this.code = code;
	}
	
	public int getKeycode() {
		return code;
	}

	public void setKeycode(int code) {
		
		this.code = code;
		
		SettingChangeEvent settingKeybind = new SettingChangeEvent(getSetting());
		ModuleManager.onSettingChange(settingKeybind);
		
	}
	
}
