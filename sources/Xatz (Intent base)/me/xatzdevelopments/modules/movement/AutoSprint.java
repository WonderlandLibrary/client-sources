package me.xatzdevelopments.modules.movement;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.modules.Module;

public class AutoSprint extends Module {
	
	public AutoSprint() {
		super("AutoSprint", Keyboard.KEY_N, Category.MOVEMENT, "Sprint Automatically");
	}
	
	public void onEnable() {
		
	}
	
	public void onDisable() {
		mc.thePlayer.setSprinting(mc.gameSettings.keyBindSprint.getIsKeyPressed());
	}


	public void onEvent(Event e) {
		if(e instanceof EventUpdate) {
			if(e.isPre()) {
				
				if(mc.thePlayer.moveForward > 0 && !mc.thePlayer.isSneaking() && !mc.thePlayer.isCollidedHorizontally) {
					if(mc.thePlayer.isUsingItem() && !Xatz.getModuleByName("NoSlowDown").isEnabled()) {
				mc.thePlayer.setSprinting(false);
				} else {
					mc.thePlayer.setSprinting(true);
				}
			}
		}
	}
}
}