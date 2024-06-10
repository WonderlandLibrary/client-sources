package Snipeware.module.impl.movement;

import Snipeware.api.annotations.Handler;
import Snipeware.events.player.EventMotionUpdate;
import Snipeware.events.player.EventMove;
import Snipeware.events.player.EventMoveOnLadder;
import Snipeware.module.Module;
import Snipeware.value.impl.EnumValue;
import Snipeware.value.impl.NumberValue;
import net.minecraft.client.entity.EntityPlayerSP;

public class FastLadder extends Module {
	
	private EnumValue<Mode> mode = new EnumValue<>("FastLadder Mode", Mode.Motion);

	private NumberValue<Double> ladderSpeed = new NumberValue<>("Ladder Speed", 0.35, 0.15, 1.0);

	public FastLadder() {
		super("FastLadder", 0, ModuleCategory.MOVEMENT);
		addValues(mode, ladderSpeed);
	}

	@Handler
	public void onMotionUpdate(final EventMove event) {
		final EntityPlayerSP player = mc.thePlayer;
		if (player.isOnLadder()) {
			switch (mode.getValue()) {
				case Motion: {
					if (player.isCollidedHorizontally) {
						event.setY(player.motionY = ladderSpeed.getValue());
					} else {
						event.setY(player.motionY = -0.151);
					}
					break;
				}
				case Timer: {
					mc.timer.timerSpeed = 3f;
					break;
				}
			}
		} else {
			mc.timer.timerSpeed = 1.0f;
		}
	}

	@Handler
	public void onLadder(final EventMoveOnLadder eventMoveOnLadder) {
		eventMoveOnLadder.setCancelled(true);
	}

	public void onEnable() {
		super.onEnable();
	}

	public void onDisable() {
		super.onDisable();
	}
	
	private enum Mode {
		Motion, Timer;
	}
}
