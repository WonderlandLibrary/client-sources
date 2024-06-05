package xyz.vergoclient.keybinds;

import com.google.gson.annotations.SerializedName;

public class Keybind {
	
	public Keybind(int keybind, String modName) {
		this.keybind = keybind;
		this.modName = modName;
	}
	
	@SerializedName(value = "keybind")
	public int keybind;
	@SerializedName(value = "modName")
	public String modName;
	
}
