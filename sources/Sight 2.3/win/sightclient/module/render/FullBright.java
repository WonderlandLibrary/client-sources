package win.sightclient.module.render;

import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventUpdate;
import win.sightclient.module.Category;
import win.sightclient.module.Module;

public class FullBright extends Module {

	private float lastGamma = 0;
	
	public FullBright() {
		super("FullBright", Category.RENDER);
	}

	@Override
	public void onEnable() {
		this.lastGamma = mc.gameSettings.gammaSetting;
	}
	
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			mc.gameSettings.gammaSetting = 10F;
		}
	}
	
	@Override
	public void onDisable() {
		mc.gameSettings.gammaSetting = this.lastGamma;
	}
}
