package me.swezedcode.client.module.modules.Motion;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;

public class Strafe extends Module {

	public Strafe() {
		super("AirControl", Keyboard.KEY_NONE, 0xFFDFFC4E, ModCategory.Motion);
	}

	@EventListener
	public void onMove(EventPreMotionUpdates e) {
		if(mc.thePlayer.isMoving()) {
			mc.thePlayer.setSpeed(mc.thePlayer.getSpeed());
		}
	}
	
}
