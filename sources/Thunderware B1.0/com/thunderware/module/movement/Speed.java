package com.thunderware.module.movement;

import org.lwjgl.input.Keyboard;

import com.thunderware.events.Event;
import com.thunderware.events.listeners.EventMotion;
import com.thunderware.module.ModuleBase;
import com.thunderware.settings.settings.NumberSetting;

public class Speed extends ModuleBase {

	public NumberSetting speed = new NumberSetting("Speed", 0.5, 0.05, 0.15, 1);
	
	public Speed() {
		super("Speed", Keyboard.KEY_R, Category.MOVEMENT);
		addSettings(speed);
	}
	
	public void onEnable() {
		
	}
	
	public void onDisable() {
		mc.gameSettings.keyBindJump.pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode());
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventMotion) {
			if(mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0) {
				mc.gameSettings.keyBindJump.pressed = false;
				if(mc.thePlayer.onGround)
					mc.gameSettings.keyBindJump.pressed = true;
				mc.thePlayer.setSpeed(speed.getValue());
			}else {
				mc.thePlayer.motionX = 0;
				mc.thePlayer.motionZ = 0;
			}
		}
	}

}
