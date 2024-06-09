package com.kilo.mod.all;

import net.minecraft.util.MathHelper;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class AutoMine extends Module {
	
	public AutoMine(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
	}
	
	public void onPlayerPreUpdate() {
		if (!mc.gameSettings.keyBindAttack.isKeyDown()) {
			mc.sendClickBlockToController(true);
		}
	}
}
