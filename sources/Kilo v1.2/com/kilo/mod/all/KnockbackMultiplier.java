package com.kilo.mod.all;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.mod.toolbar.dropdown.Interactable.TYPE;

public class KnockbackMultiplier extends Module {
	
	public KnockbackMultiplier(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
		
		addOption("Multiplier", "Knockback force multiplier", Interactable.TYPE.SLIDER, 1, new float[] {0, 10}, true);
	}
}
