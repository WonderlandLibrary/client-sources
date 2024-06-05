package win.sightclient.module.movement.step;

import win.sightclient.event.Event;
import win.sightclient.event.events.player.EventStep;
import win.sightclient.module.Module;
import win.sightclient.module.ModuleMode;
import win.sightclient.module.settings.NumberSetting;
import win.sightclient.utils.TimerUtils;

public class VanillaStep extends ModuleMode {

	private NumberSetting height;
	private TimerUtils timer;
	
	public VanillaStep(Module parent, NumberSetting height, TimerUtils timer) {
		super(parent);
		this.height = height;
		this.timer = timer;
	}

	@Override
	public void onEvent(Event e) {
		if (e instanceof EventStep) {
			EventStep es = (EventStep)e;
			es.stepHeight = this.height.getValue();
			if (!es.isPre()) {
				this.timer.reset();
			}
		}
	}
}
