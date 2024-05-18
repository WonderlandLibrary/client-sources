// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.modules.movement;

import net.minecraft.client.triton.management.event.Event;
import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.MoveEvent;
import net.minecraft.client.triton.management.event.events.StepEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.management.option.Option;
import net.minecraft.client.triton.management.option.Option.Op;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.client.triton.utils.Timer;
import net.minecraft.network.play.client.C03PacketPlayer;

@Mod(displayName = "Step")
public class Step extends Module {
	@Op(min = 1.0, max = 10.0, increment = 1.0, name = "Height")
	private double height;
	@Op(name = "NCP")
	public boolean packet;
	@Op(name = "Reverse")
	private boolean reverse;
	private Timer timer = new Timer();

	public Step() {
		this.height = 1.0;
	}

	@EventTarget
	private void onStep(final StepEvent event) {
		if (event.getState() == Event.State.PRE) {
			if (this.height > 1.0) {
				ClientUtils.mc().timer.timerSpeed = 1F;
				event.setStepHeight(this.height);
			} else if (!ClientUtils.movementInput().jump && ClientUtils.player().isCollidedVertically) {
				event.setStepHeight(1.0);
				event.setActive(true);
			}
		} else if (event.getState() == Event.State.POST && this.packet) {
			ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y() + 0.42,
					ClientUtils.z(), ClientUtils.player().onGround));
			ClientUtils.packet(new C03PacketPlayer.C04PacketPlayerPosition(ClientUtils.x(), ClientUtils.y() + 0.75,
					ClientUtils.z(), ClientUtils.player().onGround));
			ClientUtils.mc().timer.timerSpeed = 0.3f;
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(100L);
					} catch (InterruptedException ex) {

					}
					ClientUtils.mc().timer.timerSpeed = 1.0f;
				}
			}).start();
		}
	}

	@EventTarget
	private void onMove(final MoveEvent event) {
		if (reverse && !ClientUtils.player().onGround && !ClientUtils.player().isCollidedHorizontally) {
			ClientUtils.player().motionY = -5.0;
			event.setY(-5.0);
		}
	}
}
