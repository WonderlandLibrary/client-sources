package win.sightclient.module.movement;

import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventUpdate;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.utils.minecraft.MoveUtils;

public class Sprint extends Module {

	public Sprint() {
		super("Sprint", Category.MOVEMENT);
	}
	
	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate) {
			mc.thePlayer.setSprinting(MoveUtils.isMoving());
		}
	}
	
	@Override
	public void onDisable() {
		mc.thePlayer.setSprinting(false);
	}
}
