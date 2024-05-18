package me.valk.agway.modes.speed;

import me.valk.agway.modules.movement.SpeedMod;
import me.valk.event.EventListener;
import me.valk.event.events.player.EventMotion;
import me.valk.module.ModMode;

public class Gcheat extends ModMode<SpeedMod> {

	public Gcheat(SpeedMod parent) {
		super(parent, "GCheat");

	}

	@EventListener
	public void onMotion(EventMotion event) {
		if (p.moveForward != 0 || p.moveStrafing != 0)
			if (p.onGround) {
				p.setSpeed(0.469315221f);
				p.motionY = 0.39936D;
			} else {
				p.setSpeed(0.3894613f);
			}
	}
}
