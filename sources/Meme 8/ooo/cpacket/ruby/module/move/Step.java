package ooo.cpacket.ruby.module.move;

import ooo.cpacket.ruby.api.event.EventImpl;
import ooo.cpacket.ruby.api.event.events.player.EventMotionUpdate;
import ooo.cpacket.ruby.module.Module;

public class Step extends Module {

	public Step(String name, int key, Category category) {
		super(name, key, category);
	}

	@Override
	public void onEnable() {
		
	}

	@Override
	public void onDisable() {
		mc.thePlayer.stepHeight = 0.5f;
	}
	
	@EventImpl
	public void onUpdate(EventMotionUpdate e) {
		mc.thePlayer.stepHeight = 1.0f;
		if (mc.thePlayer.isCollidedHorizontally && mc.thePlayer.isMoving()) {
			e.setPosY(e.getPosY() + 0.42);
		}
	}
	
	
}
