package com.kilo.mod.all;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class FastFall extends Module {
	
	public FastFall(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
	}
	
	public void onPlayerPreUpdate() {
		if (mc.thePlayer.motionY < 0) {
			if (!mc.thePlayer.onGround) {
				mc.thePlayer.motionY = -1;
			}
		}
	}
}
