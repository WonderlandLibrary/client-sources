package me.valk.agway.modes.speed;

import java.math.BigDecimal;
import java.math.RoundingMode;

import me.valk.Vital;
import me.valk.agway.modules.movement.NoSlowMod;
import me.valk.agway.modules.movement.SpeedMod;
import me.valk.event.EventListener;
import me.valk.event.EventType;
import me.valk.event.events.entity.EventMoveRaw;
import me.valk.event.events.player.EventPlayerUpdate;
import me.valk.help.entity.Player;
import me.valk.module.ModMode;
import me.valk.utils.TimerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;

public class SlowHop extends ModMode<SpeedMod> {
	private int timeState;
	public int state;
	public double moveSpeed;
	private double lastDist;
	private int cooldownHops;
	private boolean wasOnWater = false;
	private boolean doTime = true;
	private TimerUtils time = new TimerUtils();

	public SlowHop(SpeedMod parent) {
		super(parent, "SlowHop");
	}

	public void onEnable() {
		this.cooldownHops = 2;
		this.state = 0;
		this.moveSpeed = (getBaseMoveSpeed() * 0.800000011920929D);
	}

	private double getBaseMoveSpeed() {
		double baseSpeed = 0.2873D;
		if (this.p.isPotionActive(Potion.moveSpeed)) {
			int amplifier = this.p.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
			baseSpeed *= (1.0D + 0.2D * (amplifier + 1));
		}
		return baseSpeed;
	}

	public void onDisable() {
		this.state = 0;
	}

	@EventListener
	public void onUpdate(EventPlayerUpdate event) {
		if ((Player.isOnLiquid()) || (Player.isInLiquid())) {
			return;
		}
		if (event.getType() == EventType.PRE) {
			if ((this.p.moveForward == 0.0F) && (this.p.moveStrafing == 0.0F) && (this.p.onGround)) {
				this.cooldownHops = 2;
				this.moveSpeed *= 1.1800000429153442D;
				this.state = 2;
			}
			double xDist = this.p.posX - this.p.prevPosX;
			double zDist = this.p.posZ - this.p.prevPosZ;
			this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
		}
	}

	@EventListener
	public void onMove(EventMoveRaw event) {
		if ((Player.isOnLiquid()) || (Player.isInLiquid())) {
			this.cooldownHops = 2;
			return;
		}
		if ((this.p.isInsideBlock()) || (this.p.isOnLadder()) || (this.p.isEntityInsideOpaqueBlock())) {
			this.moveSpeed = 0.0D;
			this.wasOnWater = true;
			return;
		}
		if (this.wasOnWater) {
			this.moveSpeed = 0.0D;
			this.wasOnWater = false;
			return;
		}
		if ((this.p.moveForward == 0.0F) && (this.p.moveStrafing == 0.0F)) {
			if (!this.p.isMoving()) {
				this.moveSpeed = (getBaseMoveSpeed() * 0.9D);
			} else {
				this.moveSpeed = (getBaseMoveSpeed() * 0.8D);
			}
			return;
		}
		if (this.p.onGround) {
			this.state = 2;
			mc.timer.timerSpeed = 1.1F;
			this.timeState += 1;
			if (this.timeState > 4) {
				this.timeState = 0;
			}
			if (this.time.hasReached(3000L)) {
				this.doTime = (!this.doTime);
				this.time.reset();
			}
			this.p.motionY *= 1.199999523162842D;
		}
		if (round(this.p.posY - (int) this.p.posY, 3) == round(0.138D, 3)) {
			event.y -= 0.4316090325960147D;
			this.p.posY -= 0.4316090325960147D;
		}
		if ((this.state == 1) && ((this.p.moveForward != 0.0F) || (this.p.moveStrafing != 0.0F))) {
			this.state = 2;
			this.moveSpeed = (1.8D * getBaseMoveSpeed() - 0.01D);
		} else if (this.state == 2) {
			this.state = 3;
			if ((this.p.moveForward != 0.0F) || (this.p.moveStrafing != 0.0F)) {
				this.mc.timer.timerSpeed = 1.0f;
				this.p.motionY = 0.39936D;
				event.y = 0.39936D;
				if (this.cooldownHops > 0) {
					this.cooldownHops -= 1;
				}
				this.moveSpeed *= 2.145D;
			}
		} else if (this.state == 3) {
			this.state = 4;
			double difference = 0.66D * (this.lastDist - getBaseMoveSpeed());
			this.moveSpeed = (this.lastDist - difference);
		} else {
			if ((Minecraft.theWorld
					.getCollidingBoundingBoxes(this.p, this.p.boundingBox.offset(0.0D, this.p.motionY, 0.0D))
					.size() > 0) || (this.p.isCollidedVertically)) {
				this.state = 1;
			}
			this.mc.timer.timerSpeed = (this.p.ticksExisted % 2 == 0 ? 1.025F : 1.003F);
			this.moveSpeed = (this.lastDist - this.lastDist / 27.0D);
		}
		this.moveSpeed = Math.max(this.moveSpeed, getBaseMoveSpeed());
		MovementInput movementInput = this.p.movementInput;
		float forward = movementInput.moveForward;
		float strafe = movementInput.moveStrafe;
		float yaw = this.p.rotationYaw;
		if ((forward == 0.0F) && (strafe == 0.0F)) {
			event.x = 0.0D;
			event.z = 0.0D;
		} else if (forward != 0.0F) {
			if (strafe >= 1.0F) {
				yaw += (forward > 0.0F ? -45 : 45);
				strafe = 0.0F;
			} else if (strafe <= -1.0F) {
				yaw += (forward > 0.0F ? 45 : -45);
				strafe = 0.0F;
			}
			if (forward > 0.0F) {
				forward = 1.0F;
			} else if (forward < 0.0F) {
				forward = -1.0F;
			}
		}
		double mx = Math.cos(Math.toRadians(yaw + 90.0F));
		double mz = Math.sin(Math.toRadians(yaw + 90.0F));
		double motionX = forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz;
		double motionZ = forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx;
		if (((this.p.isUsingItem()) || (this.p.isBlocking()))
				&& (!Vital.getManagers().getModuleManager().getModuleFromClass(NoSlowMod.class).getState())) {
			mc.timer.timerSpeed = 1.0f;
			motionX *= 0.4000000059604645D;
			motionZ *= 0.4000000059604645D;
		}
		event.x = (forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz);
		event.z = (forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx);

		this.p.stepHeight = 0.6F;
		if ((forward == 0.0F) && (strafe == 0.0F)) {
			event.x = 0.0D;
			event.z = 0.0D;
		} else {
			boolean collideCheck = false;
			if (Minecraft.theWorld.getCollidingBoundingBoxes(this.p, this.p.boundingBox.expand(0.5D, 0.0D, 0.5D))
					.size() > 0) {
				collideCheck = true;
			}
			if (forward != 0.0F) {
				if (strafe >= 1.0F) {
					yaw += (forward > 0.0F ? -45 : 45);
					strafe = 0.0F;
				} else if (strafe <= -1.0F) {
					yaw += (forward > 0.0F ? 45 : -45);
					strafe = 0.0F;
				}
				if (forward > 0.0F) {
					forward = 1.0F;
				} else if (forward < 0.0F) {
					forward = -1.0F;
				}
			}
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