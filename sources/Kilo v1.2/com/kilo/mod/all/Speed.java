package com.kilo.mod.all;

import net.minecraft.util.MathHelper;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class Speed extends Module {
	
	public Speed(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		addOption("Speed", "Speed to move at", Interactable.TYPE.SLIDER, 1, new float[] {1, 10}, true);
	}
}
