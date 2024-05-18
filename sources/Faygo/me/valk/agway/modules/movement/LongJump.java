package me.valk.agway.modules.movement;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.lwjgl.input.Keyboard;

import me.valk.event.EventListener;
import me.valk.event.EventType;
import me.valk.event.events.entity.EventMoveRaw;
import me.valk.event.events.player.EventPlayerUpdate;
import me.valk.module.ModData;
import me.valk.module.ModType;
import me.valk.module.Module;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;

public class LongJump extends Module {


	public double yOffset;
	private int stage;
	private double moveSpeed;
	private double lastDist;

	public LongJump() {
		super(new ModData("LongJump", Keyboard.KEY_NONE, new Color(40, 255, 10)), ModType.MOVEMENT);
		this.moveSpeed = 0.2873;
	}

	@Override
	public void onEnable() {
		if (p != null) {
			this.moveSpeed = this.getBaseMoveSpeed();
		}
		this.lastDist = 0.0;
		this.stage = 1;
	}

	public double getBaseMoveSpeed() {
		double baseSpeed = 0.2873;
		if (p.isPotionActive(Potion.moveSpeed)) {
			int amplifier = p.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
			baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
		}
		return baseSpeed;
	}

	@EventListener
	private void onMove(EventMoveRaw event) {
		if (!(p.moveStrafing != 0) && !(p.moveForward != 0)) {
			this.stage = 1;
		}
		if (round(p.posY - (int) p.posY, 3) == round(0.93, 3)) {
			p.motionY -= 0.01;
			event.y -= 0.01;
		}
		if (this.stage == 1 && (p.moveForward != 0.0f || p.moveStrafing != 0.0f) && p.onGround && p.isCollidedVertically
				&& p.motionY < 0) {
			this.stage = 2;
            moveSpeed = 3.4 * getBaseMoveSpeed() - 0.01;
		} else if (this.stage == 2) {
			this.stage = 3;
			p.motionY = 0.44;
			event.y = 0.44;
			this.moveSpeed *= (2.149802);
		} else if (this.stage == 3) {
			this.stage = 4;
			mc.timer.timerSpeed = 1.095f;
			double difference = 0.66 * (this.lastDist - this.getBaseMoveSpeed());
			this.moveSpeed = this.lastDist - difference;
		} else {
			if (mc.theWorld.getCollidingBoundingBoxes(p, p.boundingBox.offset(0.0, p.motionY, 0.0)).size() > 0
					|| p.isCollidedVertically) {
				this.stage = 1;
			}
			this.moveSpeed = this.lastDist - this.lastDist / 159.0;
		}
		this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
		MovementInput movementInput = p.movementInput;
		float forward = movementInput.moveForward;
		float strafe = movementInput.moveStrafe;
		float yaw = p.rotationYaw;
		if (forward == 0.0f && strafe == 0.0f) {
			event.x = 0.0;
			event.z = 0.0;
		} else if (forward != 0.0f) {
			if (strafe >= 1.0f) {
				yaw += ((forward > 0.0f) ? -45 : 45);
				strafe = 0.0f;
			} else if (strafe <= -1.0f) {
				yaw += ((forward > 0.0f) ? 45 : -45);
				strafe = 0.0f;
			}
			if (forward > 0.0f) {
				forward = 1.0f;
			} else if (forward < 0.0f) {
				forward = -1.0f;
			}
		}
		double mx = Math.cos(Math.toRadians(yaw + 90.0f));
		double mz = Math.sin(Math.toRadians(yaw + 90.0f));
		event.x = forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz;
		event.z = forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx;
	}

	@EventListener
	private void onUpdate(EventPlayerUpdate event) {
		if (event.getType() == EventType.PRE) {
			boolean speedy = p.isPotionActive(Potion.moveSpeed);
			double xDist = p.posX - p.prevPosX;
			double zDist = p.posZ - p.prevPosZ;
			this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
		}
	}

	public double round(double value, int places) {
		if (places < 0) {
			throw new IllegalArgumentException();
		}
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

}
