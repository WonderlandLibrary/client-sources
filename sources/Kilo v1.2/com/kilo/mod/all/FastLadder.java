package com.kilo.mod.all;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class FastLadder extends Module {
	
	public FastLadder(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		addOption("Climb Speed", "Speed to climb at", Interactable.TYPE.SLIDER, 0.3f, new float[] {0, 1}, true);
	}
}
