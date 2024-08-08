package me.xatzdevelopments.modules.render;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.modules.Module;

public class TargetHud2 extends Module {
	
	public TargetHud2() {
		super("TargetHUD2", Keyboard.KEY_K, Category.RENDER, "Show info about the entity you are attacking");
	}
	
	public void onEnable() {
		
	}
	
	public void onDisable() {
		
	}


	
	
}
