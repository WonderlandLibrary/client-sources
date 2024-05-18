package com.kilo.mod.all;

import net.minecraft.potion.Potion;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.mod.toolbar.dropdown.SettingsRel;

public class History extends Module {
	
	private final Potion[] blacklist = new Potion[] {Potion.blindness, Potion.confusion, Potion.digSlowdown, Potion.hunger, Potion.moveSlowdown, Potion.poison, Potion.weakness, Potion.wither};
	
	public History(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		addOption("Check", "Check players' previous usernames", Interactable.TYPE.SETTINGS, SettingsRel.HISTORY, null, false);
	}
	
	public void onEnable() {
		super.onEnable();
		onDisable();
	}
}
