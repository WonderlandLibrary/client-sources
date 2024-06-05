package win.sightclient.module.other;

import win.sightclient.Sight;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventUpdate;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.module.settings.NumberSetting;
import win.sightclient.module.settings.Setting;

public class Timer extends Module {

	private NumberSetting speed = new NumberSetting("Speed", this, 1.25, 0.05, 6, false);
	
	public Timer() {
		super("Timer", Category.OTHER);
	}

	@Override
	public void onDisable() {
		mc.timer.timerSpeed = 1F;
	}
	
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			mc.timer.timerSpeed = speed.getValueFloat();
		}
	}
}
