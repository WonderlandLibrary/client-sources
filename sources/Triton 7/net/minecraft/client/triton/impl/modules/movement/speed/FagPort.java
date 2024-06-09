package net.minecraft.client.triton.impl.modules.movement.speed;

import java.util.List;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.triton.impl.modules.movement.Speed;
import net.minecraft.client.triton.management.event.Event;
import net.minecraft.client.triton.management.event.events.MoveEvent;
import net.minecraft.client.triton.management.event.events.UpdateEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.client.triton.utils.MathUtils;
import net.minecraft.potion.Potion;

public class FagPort extends SpeedMode {

	private double moveSpeed;
	private double lastDist;
	public static double stage;

	public FagPort(String name, boolean value, Module module) {
		super(name, value, module);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean enable() {
		if (super.enable()) {
			if (ClientUtils.player() != null) {
				this.moveSpeed = Speed.getBaseMoveSpeed();
			}
			this.lastDist = 0.0;
			this.stage = 2.0;
		}
		return true;
	}

	@Override
	public boolean onMove(final MoveEvent event) {
		if ((super.onMove(event)) && ((ClientUtils.player().onGround) || (this.stage == 3.0D))) {
			if (MathUtils.roundToPlace(ClientUtils.player().posY - (int) ClientUtils.player().posY, 3) == MathUtils
					.roundToPlace(0.138, 3)) {
				event.setY(-0.0931);
			}
			if (((!ClientUtils.player().isCollidedHorizontally) && (ClientUtils.player().moveForward != 0.0F))
					|| (ClientUtils.player().moveStrafing != 0.0F)) {
				if (this.stage == 2) {
					this.moveSpeed *= 2.149D;
					this.stage = 3;
				} else if (this.stage == 3) {
					this.stage = 2;
					double difference = 0.66D * (this.lastDist - Speed.getBaseMoveSpeed());
					this.moveSpeed = (this.lastDist - difference);
				} else {
					if (ClientUtils.world()
							.getCollidingBoundingBoxes(ClientUtils.player(),
									ClientUtils.player().boundingBox.offset(0.0, ClientUtils.player().motionY, 0.0))
							.size() > 0 || ClientUtils.player().isCollidedVertically) {
						this.stage = 1;
					}
				}
			} else {
				ClientUtils.mc().timer.timerSpeed = 1.0F;
			}
			this.moveSpeed = Math.max(this.moveSpeed, Speed.getBaseMoveSpeed());
			ClientUtils.setMoveSpeed(event, this.moveSpeed);
		}
		return true;
	}

	@Override
	public boolean onUpdate(final UpdateEvent event) {
		if (super.onUpdate(event) && event.getState() == Event.State.PRE) {
			if (this.stage == 3) {
				event.setY(event.getY() + 0.4D);
			}
			final double xDist = ClientUtils.x() - ClientUtils.player().prevPosX;
			final double zDist = ClientUtils.z() - ClientUtils.player().prevPosZ;
			this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
			ClientUtils.player().motionY = -0.41;
		}
		return true;
	}
}
