package xyz.vergoclient.modules.impl.player;

import xyz.vergoclient.Vergo;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventTick;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.settings.NumberSetting;
import xyz.vergoclient.util.main.ChatUtils;
import xyz.vergoclient.util.main.ServerUtils;
import xyz.vergoclient.util.main.TimerUtil;

public class ModTimer extends Module implements OnEventInterface {

	public ModTimer() {
		super("Timer", Category.PLAYER);
	}
	
	public static TimerUtil blinkTimer = new TimerUtil();
	
	public NumberSetting timerSpeed = new NumberSetting("Timer speed", 1, 1, 2, 0.05);
	
	@Override
	public void loadSettings() {
		addSettings(timerSpeed);
	}
	
	@Override
	public void onEnable() {
		if (Vergo.config.modBlink.isEnabled()) {
			Vergo.config.modBlink.toggle();
		}
	}
	
	@Override
	public void onDisable() {
		if (Vergo.config.modBlink.isEnabled()) {
			Vergo.config.modBlink.toggle();
		}
		mc.timer.timerSpeed = 1;
		mc.timer.ticksPerSecond = 20;
	}
	
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventTick && e.isPre()) {
			mc.timer.timerSpeed = (float) timerSpeed.getValueAsDouble();
		}
	}

}
