package me.protocol_client.modules;

import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.thanks_slicky.properties.ClampedValue;

import org.lwjgl.input.Keyboard;

import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventPreMotionUpdates;

public class Timer extends Module{

	public Timer() {
		super("Timer", "timer", Keyboard.KEY_NONE, Category.WORLD, new String[]{"timer"});
	}
	public final ClampedValue<Float> setting = new ClampedValue<>("timer_speed", 2f, 1f, 5f);
	@EventTarget
	public void onEvent(EventPreMotionUpdates event) {
		if(isToggled())
		{
			setDisplayName("Timer [" + this.setting.getValue() + "]");
			mc.timer.timerSpeed = setting.getValue();
		}
	}
	@Override
	public void onDisable() {
		EventManager.unregister(this);
		mc.timer.timerSpeed = 1F;
		super.onDisable();
	}
	public void onEnable(){
		EventManager.register(this);
	}
}
