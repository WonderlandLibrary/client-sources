package none.module.modules.render;

import org.lwjgl.input.Keyboard;

import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.Event2D;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;

public class Brightness extends Module{

	public Brightness() {
		super("Brightness", "Brightness", Category.RENDER, Keyboard.KEY_NONE);
	}
	
	public float bright;
	
	@Override
	protected void onEnable() {
		super.onEnable();
		bright = mc.gameSettings.gammaSetting;
		mc.gameSettings.gammaSetting = 100F;
	}
	
	@Override
	protected void onDisable() {
		super.onDisable();
		mc.gameSettings.gammaSetting = bright;
	}
	
	@Override
	@RegisterEvent(events = Event2D.class)
	public void onEvent(Event event) {
		if (!isEnabled()) return;
	}

}
