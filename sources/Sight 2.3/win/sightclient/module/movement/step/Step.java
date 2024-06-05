package win.sightclient.module.movement.step;

import java.util.ArrayList;

import win.sightclient.Sight;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventUpdate;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.module.settings.ModeSetting;
import win.sightclient.module.settings.NumberSetting;
import win.sightclient.module.settings.Setting;
import win.sightclient.utils.TimerUtils;

public class Step extends Module {

	private ModeSetting mode = new ModeSetting("Mode", this, new String[] {"Hypixel", "Vanilla"});
	private NumberSetting height = new NumberSetting("StepHeight", this, 1, 0.5, 5, false);
	private NumberSetting delay = new NumberSetting("Delay", this, 150, 10, 750, true);
	
	private NCPStep ncp;
	private VanillaStep vanilla;
	private FakeJumpStep fakejump;
	
	private TimerUtils timer = new TimerUtils();
	
	public Step() {
		super("Step", Category.MOVEMENT);
		ncp = new NCPStep(this, timer);
		fakejump = new FakeJumpStep(this, timer);
		vanilla = new VanillaStep(this, height, timer);
	}
	
	@Override
	public void updateSettings() {
		this.height.setVisible(this.mode.getValue().equalsIgnoreCase("Vanilla"));
	}

	@Override
	public void onEvent(Event e) {
		this.setSuffix(this.mode.getValue());
		if (!timer.hasReached(this.delay.getValue())) {
			return;
		}
		
		if (e instanceof EventUpdate && mc.thePlayer.fallDistance > 0) {
			timer.reset();
			return;
		}
		
		if (this.mode.getValue().equalsIgnoreCase("NCP")) {
			this.ncp.onEvent(e);
		}
		if (this.mode.getValue().equalsIgnoreCase("Hypixel")) {
			this.fakejump.onEvent(e);
		}
		if (this.mode.getValue().equalsIgnoreCase("Vanilla")) {
			this.vanilla.onEvent(e);
		}
	}
}
