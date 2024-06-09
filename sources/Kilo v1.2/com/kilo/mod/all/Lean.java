package com.kilo.mod.all;

import net.minecraft.client.renderer.GlStateManager;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class Lean extends Module {
	
	private float lean, leanTo;
	
	public Lean(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		addOption("Distance", "Distance to lean", Interactable.TYPE.SLIDER, 15, new float[] {0, 30}, true);
	}
	
	public void update() {
		if (!active) { return; }
		
		leanTo = 0;
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			leanTo-= Util.makeFloat(getOptionValue("distance"));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			leanTo+= Util.makeFloat(getOptionValue("distance"));
		}
		
		lean+= (leanTo-lean)/10f;
	}
	
	public void onCameraTransform() {
		GlStateManager.rotate(lean, 0, 0, 1);
		GlStateManager.translate((-lean/10), 0, 0);
	}
}
