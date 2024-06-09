package com.kilo.mod.all;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;

public class Jetpack extends Module {

	public Jetpack(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
	}
	
	public void update() {
		if (!active) { return; }
		
		if (mc.gameSettings.keyBindJump.isKeyDown()) {
			mc.thePlayer.motionY+= 0.07;
		}
	}
}
