package me.gishreload.yukon.hacks;

import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.*;
import me.gishreload.yukon.module.Module;

public class UpdateListAura extends Module{
	
	public UpdateListAura() {
		super("UpListAura", Keyboard.KEY_V, Category.OTHER);
	}
	
	public void onEnable(){
		Meanings.aacauraarrow = true;
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a7aPlayers list for killaura(AAC) Updated.");
	}

	public void onDisable(){
		Meanings.aacauraarrow = false;
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74Players list disabled");
		super.onDisable();
	}
}