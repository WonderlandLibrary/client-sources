package com.kilo.mod.all;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class LSD extends Module {
	
	public LSD(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
		
		addOption("Time", "", Interactable.TYPE.SLIDER, 1, new float[] {0, 24}, false);
	}
	
	public void onEnable() {
		super.onEnable();
		
		mc.entityRenderer.shaderIndex = 18;
		mc.entityRenderer.activateNextShader();
	}
	
	public void onDisable() {
		super.onDisable();
		
		mc.entityRenderer.shaderIndex = 0;
		mc.entityRenderer.activateNextShader();
	}
	
	public void update() {
		if (!active) { return; }
		
		mc.entityRenderer.shaderIndex = 18;
	}
	
	public void refresh() {
		if (mc.entityRenderer.getShaderGroup() != null){
			mc.entityRenderer.getShaderGroup().loadShaderGroup(mc.entityRenderer.shaderIndex);
		} else {
			mc.entityRenderer.activateNextShader();
			mc.entityRenderer.shaderIndex = 18;
		}
	}
}
