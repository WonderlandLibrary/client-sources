package com.kilo.mod.all;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class Timer extends Module {
	
	public Timer(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
		
		addOption("Timer Speed", "Speed to set timer to", Interactable.TYPE.SLIDER, 5, new float[] {0.1f, 10}, true);
	}
	
	public void onDisable() {
		super.onDisable();
		mc.timer.timerSpeed = 1f;
	}
	
	public void update() {
		if (!active) { return; }
		
		mc.timer.timerSpeed = Util.makeFloat(getOptionValue("timer speed"));
	}
}
