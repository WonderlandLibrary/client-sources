package xyz.vergoclient.modules.impl.visual;

import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventTick;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.settings.NumberSetting;

public class SmallHand extends Module implements OnEventInterface {

	public SmallHand() {
		super("SmallItems", Category.VISUAL);
	}
	
	public NumberSetting scale = new NumberSetting("Scale", 0, 0, 100, 1);
	
	@Override
	public void loadSettings() {
		addSettings(scale);
	}

	@Override
	public void onEvent(Event e) {
		if (e instanceof EventTick && e.isPre()) {

		}
	}
	
}
