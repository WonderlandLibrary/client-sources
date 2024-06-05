package xyz.vergoclient.settings;

import com.google.gson.annotations.SerializedName;

import xyz.vergoclient.modules.ModuleManager;

public class BooleanSetting extends Setting {
	
	@SerializedName(value = "enabled")
	public boolean enabled;

	public BooleanSetting(String name, boolean enabled) {
		
		this.name = name;
		this.enabled = enabled;
		
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	public boolean isDisabled() {
		return !enabled;
	}

	public void setEnabled(boolean enabled) {
		
		this.enabled = enabled;
		
		SettingChangeEvent settingBoolean = new SettingChangeEvent(getSetting());
		ModuleManager.onSettingChange(settingBoolean);
		
	}
	
	public void toggle() {
		
		enabled = !enabled;
		
		SettingChangeEvent settingBoolean = new SettingChangeEvent(getSetting());
		ModuleManager.onSettingChange(settingBoolean);
		
	}
	
}
