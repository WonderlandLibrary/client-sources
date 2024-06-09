package com.kilo.mod.all;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class Spider extends Module {
	
	public Spider(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
	}
	
	public void onPlayerPreUpdate() {
		if (mc.thePlayer.isCollidedHorizontally) {
			mc.thePlayer.motionY = 0.2f;
		}
	}
}
