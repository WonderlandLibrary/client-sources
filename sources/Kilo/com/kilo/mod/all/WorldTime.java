package com.kilo.mod.all;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class WorldTime extends Module {
	
	public WorldTime(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
		
		addOption("Time", "", Interactable.TYPE.SLIDER, 1, new float[] {0, 24}, false);
	}
}
