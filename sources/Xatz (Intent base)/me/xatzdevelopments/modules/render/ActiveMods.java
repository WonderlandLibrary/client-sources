package me.xatzdevelopments.modules.render;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.modules.Module;

public class ActiveMods extends Module {
	
	public ActiveMods() {
		super("ActiveMods", Keyboard.KEY_NONE, Category.RENDER, "Renders Active Modules");
		toggled = true;
	}
	
	public void onEnable() {
		
	}
	
	public void onDisable() {
		
	}


	
	
}
