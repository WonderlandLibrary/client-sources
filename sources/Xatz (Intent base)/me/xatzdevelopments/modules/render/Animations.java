package me.xatzdevelopments.modules.render;

import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.settings.ModeSetting;
import me.xatzdevelopments.settings.NumberSetting;

public class Animations extends Module{

	public ModeSetting hitanimation = new ModeSetting("Hit Animation", "Vanilla", "Vanilla", "Smooth");
	
	public ModeSetting blockanimation = new ModeSetting("Block Animation", "Vanilla", "Vanilla", "Xatz", "Cool", "Slide", "Tap", "Tap2", "Avatar", "Sigma", "Helium", "Derp", "Derp2", "HaxxerClient", "Spinny", "Exhi", "Spin", "Whack", "UpDown", "Penis");
	
	public Animations() {
		super("Animations", 0, Category.RENDER, "Change different animations");
		this.addSettings(hitanimation, blockanimation);
		toggled = true;
	}
	
	public void onEnable() {
		
	}
	
	public void onDisable() {
		this.toggle();
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			
		}
	}
}
