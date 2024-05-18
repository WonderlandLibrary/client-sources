package com.kilo.mod.all;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.ModuleManager;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class Panic extends Module {
	
	public Panic(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
	}
	
	public void onEnable() {
		for(Module m : ModuleManager.enabledList()) {
			m.onDisable();
		}
		onDisable();
	}
}
