package com.kilo.mod.all;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class RainbowEnchant extends Module {
	
	public RainbowEnchant(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
		
		addOption("Color", "Color of the enchantment glint|Set to 255 for rainbow", Interactable.TYPE.SLIDER, 0, new float[] {0, 255}, false);
	}
}
