package xyz.vergoclient.settings;

import com.google.gson.annotations.SerializedName;

public abstract class Setting {
	
	@SerializedName(value = "name")
	public String name;
	
	public Setting getSetting() {
		return this;
	}
}
