package com.kilo.mod.all;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraft.util.Vec3;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.render.Render;
import com.kilo.util.Util;

public class SelectionBox extends Module {
	
	public SelectionBox(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		addOption("Red", "", Interactable.TYPE.SLIDER, 0, new float[] {0, 255}, false);
		addOption("Green", "", Interactable.TYPE.SLIDER, 0, new float[] {0, 255}, false);
		addOption("Blue", "", Interactable.TYPE.SLIDER, 0, new float[] {0, 255}, false);
		addOption("Opacity", "", Interactable.TYPE.SLIDER, 96, new float[] {0, 255}, false);
	}
}
