// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.impl.modules.movement.speed;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.triton.impl.modules.movement.Speed;
import net.minecraft.client.triton.management.event.Event;
import net.minecraft.client.triton.management.event.events.MoveEvent;
import net.minecraft.client.triton.management.event.events.UpdateEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.option.Option.Op;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.client.triton.utils.MathUtils;
import net.minecraft.client.triton.utils.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;

public class Jump extends SpeedMode {
	private int stage;
	private double moveSpeed;
	private double lastDist;
	private int ticks;

	public Jump(String name, boolean value, Module module) {
		super(name, value, module);
	}

	public boolean enable() {
		if (super.enable()) {
			if (ClientUtils.player() != null) {
				this.moveSpeed = Speed.getBaseMoveSpeed();
			}
			this.lastDist = 0.0D;
			stage = 1;
			this.ticks = 0;
		}
		return true;
	}

	public boolean onMove(MoveEvent event) {
		if (super.onMove(event)) {
			ticks++;
			if (!ClientUtils.player().onGround && (ClientUtils.player().moveForward != 0.0F)
					|| (ClientUtils.player().moveStrafing != 0.0F)) {
				this.ticks = 0;
			}
			if (MathUtils.roundToPlace(ClientUtils.player().posY - (int) ClientUtils.player().posY, 3) == MathUtils
					.roundToPlace(0.481D, 3)) {
				Block pos = ClientUtils.world().getBlock((int) ClientUtils.x(), (int) ClientUtils.y() - 1,
						(int) ClientUtils.z());
				if (!(pos instanceof BlockAir)) {
					ClientUtils.player().motionY -= 0.075D;
					event.setY(-0.075D);
				}
			} else if (MathUtils.roundToPlace(ClientUtils.player().posY - (int) ClientUtils.player().posY,
					3) == MathUtils.roundToPlace(0.40599999999999997D, 3)) {
				Block pos = ClientUtils.world().getBlock((int) ClientUtils.x(), (int) ClientUtils.y() - 1,
						(int) ClientUtils.z());
				if (!(pos instanceof BlockAir)) {
					ClientUtils.player().motionY = -0.1D;
					event.setY(-0.1D);
				}
			} else if ((MathUtils.roundToPlace(ClientUtils.player().posY - (int) ClientUtils.player().posY,
					3) == MathUtils.roundToPlace(0.306D, 3)) && (!ClientUtils.player().isCollidedHorizontally)
					&& (this.stage != 0)) {
				Block pos = ClientUtils.world().getBlock((int) ClientUtils.x(), (int) ClientUtils.y() - 1,
						(int) ClientUtils.z());
				if ((!(pos instanceof BlockAir)) && ((ClientUtils.player().moveForward != 0.0F)
						|| (ClientUtils.player().moveStrafing != 0.0F))) {
					ClientUtils.player().motionY = -0.000008;
					event.setY(-0.000008);
				}
			} else if ((MathUtils.roundToPlace(ClientUtils.player().posY - (int) ClientUtils.player().posY,
					3) == MathUtils.roundToPlace(0.305D, 3))
					|| ((ClientUtils.player().moveForward == 0.0F) && (ClientUtils.player().moveStrafing == 0.0F))
					|| (ClientUtils.player().isCollidedHorizontally)) {
				this.stage = 0;
			}
			if ((this.stage == 1)
					&& ((ClientUtils.player().moveForward != 0.0F) || (ClientUtils.player().moveStrafing != 0.0F))) {
				this.stage = 2;
				this.moveSpeed = (!ClientUtils.player().isPotionActive(Potion.moveSpeed)
						? 4.3 * Speed.getBaseMoveSpeed() : 3.25D * Speed.getBaseMoveSpeed());
			} else if (this.stage == 2) {
				this.stage = 3;
				ClientUtils.setMoveSpeed(event, 0.15D);
				ClientUtils.player().motionY = 0.42D;
				event.setY(0.42D);
				this.moveSpeed *= 2.147D;
			} else if (this.stage == 3) {
				this.stage = 4;
				double difference = 0.66D * (this.lastDist - Speed.getBaseMoveSpeed());
				this.moveSpeed = (this.lastDist - difference);
			} else {
				if ((ClientUtils.world()
						.getCollidingBoundingBoxes(ClientUtils.player(),
								ClientUtils.player().boundingBox.offset(0.0D, ClientUtils.player().motionY, 0.0D))
						.size() > 0) || (ClientUtils.player().isCollidedVertically)) {
					if (ticks >= 6) {
						this.stage = 1;
					} else {
						this.stage = 0;
					}
				}
				this.moveSpeed = (this.lastDist - this.lastDist / 159.0D);
			}
			ClientUtils.setMoveSpeed(event,
					this.stage != 0 ? (this.moveSpeed = Math.max(this.moveSpeed, Speed.getBaseMoveSpeed())) : 0.15D);
		}
		return true;
	}

	public boolean onUpdate(UpdateEvent event) {
		if ((super.onUpdate(event)) && (event.getState() == Event.State.PRE)) {
			double xDist = ClientUtils.x() - ClientUtils.player().prevPosX;
			double zDist = ClientUtils.z() - ClientUtils.player().prevPosZ;
			this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
		}
		return true;
	}
}
