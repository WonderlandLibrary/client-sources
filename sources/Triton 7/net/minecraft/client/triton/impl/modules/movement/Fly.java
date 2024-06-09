// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.modules.movement;

import net.minecraft.client.triton.management.event.Event;
import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.MoveEvent;
import net.minecraft.client.triton.management.event.events.UpdateEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.management.option.Option.Op;
import net.minecraft.client.triton.utils.ClientUtils;

@Mod(displayName = "Fly")
public class Fly extends Module {
	@Op(name = "Glide")
	private boolean glide;
	@Op(min = 0, max = 1, increment = 0.05, name = "Fall Speed")
	private double glideSpeed;
	@Op(min = 0.0, max = 9.0, increment = 0.01, name = "Speed")
	private double speed;

	public Fly() {
		this.speed = 0.8;
	}

	@EventTarget
	private void onUpdate(final UpdateEvent event) {
		if (event.getState() == Event.State.PRE) {
			if (!glide) {
				if (ClientUtils.movementInput().jump) {
					ClientUtils.player().motionY = this.speed;
				} else if (ClientUtils.movementInput().sneak) {
					ClientUtils.player().motionY = -this.speed;
				} else {
					ClientUtils.player().motionY = 0.0;
				}
			} else {
				ClientUtils.player().motionY = -glideSpeed;
			}
		}
	}

	@EventTarget
	private void onMove(final MoveEvent event) {
		ClientUtils.setMoveSpeed(event, this.speed);
	}
}
