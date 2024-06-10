package Snipeware.module.impl.world;

import Snipeware.api.annotations.Handler;
import Snipeware.events.player.EventMotionUpdate;
import Snipeware.module.Module;
import Snipeware.util.other.Logger;
import Snipeware.util.other.PlayerUtil;
import Snipeware.value.impl.NumberValue;

public class Timer extends Module {
	
	private NumberValue<Float> timerValue = new NumberValue<>("Timer Speed", 1.0f, 0.1f, 10.0f);
	
	public void onEnable() {
		super.onEnable();
	}
	
	public void onDisable() {
		super.onDisable();
		mc.timer.timerSpeed = 1.0f;
	}
	
	public Timer() {
		super("Timer",0, ModuleCategory.WORLD);
		addValues(timerValue);
	}
	
	@Handler
	public void onMotionUpdate(final EventMotionUpdate event) {
		if(PlayerUtil.isOnServer("Hypixel")) {
			if(mc.timer.timerSpeed >= 1.6f) {
				Logger.print("Timer is maxed on 1.6 for Hypixel this limit may get removed in the future!");
				toggle();
			}
		}
			mc.timer.timerSpeed = timerValue.getValue();
	}
}
