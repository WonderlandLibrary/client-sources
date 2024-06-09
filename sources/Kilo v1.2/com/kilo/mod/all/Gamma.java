package com.kilo.mod.all;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;

public class Gamma extends Module {
	
	private float oldGamma;
	
	public Gamma(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
	}
	
	public void onEnable() {
		super.onEnable();
		oldGamma = mc.gameSettings.gammaSetting;
	}
	
	public void onDisable() {
		super.onDisable();
		mc.gameSettings.gammaSetting = oldGamma;
	}
	
	public void update() {
		if (!active) { return; }
		
		mc.gameSettings.gammaSetting = 1000f;
	}
}
