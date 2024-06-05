package win.sightclient.module.movement;

import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventUpdate;
import win.sightclient.event.events.player.EventFlag;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.module.settings.NumberSetting;
import win.sightclient.utils.minecraft.MoveUtils;

public class AntiVoid extends Module {

	private NumberSetting fallDistance = new NumberSetting("FallDistance", this, 4, 1, 10, false);
	
	private boolean fall;
	
	public AntiVoid() {
		super("AntiVoid", Category.MOVEMENT);
	}

	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate && mc.thePlayer != null && mc.theWorld != null) {
			EventUpdate eu = (EventUpdate)e;
			if (isFalling() && eu.isPre()) {
				eu.setY(eu.getY() + 8);
			}
			if (this.fall) {
				this.fall = false;
				mc.timer.timerSpeed = 1F;
			}
		} else if (e instanceof EventFlag && isFalling()) {
			this.fall = true;
			mc.timer.timerSpeed = 0.2F;
		}
	}
	
	private boolean isFalling() {
		return mc.theWorld != null && mc.thePlayer.fallDistance > fallDistance.getValueFloat() && !MoveUtils.isBlockUnderneath(mc.thePlayer.getPosition());
	}
}
