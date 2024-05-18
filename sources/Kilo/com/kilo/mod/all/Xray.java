package com.kilo.mod.all;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.ModuleOption;
import com.kilo.mod.ModuleSubOption;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.mod.toolbar.dropdown.SettingsRel;
import com.kilo.mod.util.XrayHandler;
import com.kilo.util.Util;

public class Xray extends Module {

	public Xray(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		addOption("Edit", "Toggle blocks to be seen", Interactable.TYPE.SETTINGS, SettingsRel.XRAY, null, false);
	}
	
	public void onEnable() {
		super.onEnable();
		mc.renderGlobal.loadRenderers();
	}
	
	public void onDisable() {
		super.onDisable();
		mc.renderGlobal.loadRenderers();
	}
}
