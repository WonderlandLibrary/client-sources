package Snipeware.module.impl.combat;

import Snipeware.api.annotations.Handler;
import Snipeware.events.player.EventMotionUpdate;
import Snipeware.module.Module;
import Snipeware.value.impl.NumberValue;

public class Hitboxes extends Module {
	
	private NumberValue<Float> size = new NumberValue<>("Hitbox Size", 0.4f, 0.1f, 1.0f);
	
	public Hitboxes() {
		super("Hitboxes", 0, ModuleCategory.COMBAT);
		addValues(size);
	}
	
	@Handler
	public void onMotionUpdate(final EventMotionUpdate event) {
		setSuffix(size.getValueAsString());
	}
	
	public void onEnable() {
		super.onEnable();
	}
	
	public void onDisable() {
		super.onDisable();
	}
	
	public float getSize() {
		return size.getValue();
	}
}
