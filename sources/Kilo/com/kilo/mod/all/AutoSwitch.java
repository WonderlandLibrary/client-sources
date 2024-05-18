package com.kilo.mod.all;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBow;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.mod.util.CombatUtil;
import com.kilo.util.Util;

public class AutoSwitch extends Module {

	public AutoSwitch(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
	}
	
	public void update() {
		if (!active) { return; }
		
		mc.thePlayer.inventory.currentItem++;
		mc.thePlayer.inventory.currentItem = mc.thePlayer.inventory.currentItem%9;
	}
}
