package me.xatzdevelopments.modules.render;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.settings.BooleanSetting;

public class Notifications extends Module {
	
	public BooleanSetting onEnable = new BooleanSetting("OnEnable", true);
	public BooleanSetting onDisable = new BooleanSetting("OnDisable", true);
	
	public Notifications() {
		super("Notifications", Keyboard.KEY_P, Category.RENDER, "Render Notifications");
		this.addSettings(onEnable, onDisable);
	}
	
	public void onEnable() {
		
	}
	
	public void onDisable() {
		
	}


	
	
}
