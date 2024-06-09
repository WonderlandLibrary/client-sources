package com.kilo.mod.all;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class Glide extends Module {
	
	public Glide(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		addOption("Speed", "Maximum fall speed", Interactable.TYPE.SLIDER, 0.05f, new float[] {0, 1}, true);
	}
	
	public void onPlayerPreUpdate() {
		mc.thePlayer.motionY = Math.max(-Util.makeFloat(getOptionValue("speed")), mc.thePlayer.motionY);
	}
}
