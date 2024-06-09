package com.kilo.mod.all;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EnumPlayerModelParts;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.ModuleSubOption;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Timer;
import com.kilo.util.Util;

public class PlayerSettings extends Module {
	
	private Timer timer = new Timer();
	
	public PlayerSettings(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
		
		addOption("Lean", "Lean when running", Interactable.TYPE.CHECKBOX, false, null, false);
		
		List<ModuleSubOption> ls = new ArrayList<ModuleSubOption>();
		ls.add(new ModuleSubOption("Speed", "Speed to switch layers", Interactable.TYPE.SLIDER, 5, new float[] {0, 2}, true));
		ls.add(new ModuleSubOption("Random", "Randomize the layer switching", Interactable.TYPE.CHECKBOX, false, null, false));
		ls.add(new ModuleSubOption("Full Skin", "Toggle all the layers at the same time", Interactable.TYPE.CHECKBOX, false, null, false));
		
		addOption("Layer Swap", "Switch skin layers on and off", Interactable.TYPE.CHECKBOX, false, null, false, ls);
	}
	
	public void update() {
		if (!active || !Util.makeBoolean(getOptionValue("layer swap"))) { return; }
		
		if (timer.isTime(Util.makeFloat(getSubOptionValue("layer swap", "speed")))) {
			if (Util.makeBoolean(getSubOptionValue("layer swap", "random"))) {
				Random rand = new Random();
				
				EnumPlayerModelParts var2 = EnumPlayerModelParts.values()[rand.nextInt(EnumPlayerModelParts.values().length)];
		        mc.gameSettings.setModelPartEnabled(var2, !mc.gameSettings.getModelParts().contains(var2));
			}
			if (Util.makeBoolean(getSubOptionValue("layer swap", "full skin"))) {
				for(int i = 0; i < EnumPlayerModelParts.values().length; i++) {
					EnumPlayerModelParts var2 = EnumPlayerModelParts.values()[i];
					mc.gameSettings.setModelPartEnabled(var2, !mc.gameSettings.getModelParts().contains(var2));
				}
			}
	        timer.reset();
		}
	}
}
