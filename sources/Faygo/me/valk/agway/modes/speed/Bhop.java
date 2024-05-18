package me.valk.agway.modes.speed;

import java.math.BigDecimal;
import java.math.RoundingMode;

import me.valk.agway.modules.movement.SpeedMod;
import me.valk.event.EventListener;
import me.valk.event.EventType;
import me.valk.event.events.entity.EventMoveRaw;
import me.valk.event.events.player.EventPlayerUpdate;
import me.valk.help.entity.Player;
import me.valk.module.ModMode;
import me.valk.utils.TimerUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;

public class Bhop extends ModMode<SpeedMod> {
	private int timeState;
	public int state;
	public double moveSpeed;
	private double lastDist;
	private int cooldownHops;
	private boolean wasOnWater = false;

	private boolean doTime = true;
	private TimerUtils time = new TimerUtils();

	public Bhop(SpeedMod parent) {
		super(parent, "Bhop");
	}

	@Override
	public void onEnable() {
		this.getParent().setDisplayName(this.getParent().getName() + " §7Bhop");
		cooldownHops = 2;
		state = 0;
		moveSpeed = getBaseMoveSpeed() * 0.8f;
	}

	private double getBaseMoveSpeed() {
		double baseSpeed = 0.2873;
		if (p.isPotionActive(Potion.moveSpeed)) {
			int amplifier = p.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
			baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
		}

		return baseSpeed;
	}

	@Override
	public void onDisable() {
		state = 0;
	}

	@EventListener
	public void onUpdate(EventPlayerUpdate event) {
		if (Player.isOnLiquid() || Player.isInLiquid())
			return;

		if (event.getType() == EventType.PRE) {
			if ((p.moveForward == 0 && p.moveStrafing == 0)) {
				if (p.onGround) {
					cooldownHops = 2;
					moveSpeed *= 1.02f;
					state = 2;
				}
			}

			double xDist = p.posX - p.prevPosX;
			double zDist = p.posZ - p.prevPosZ;
			this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
		}
	}

	@EventListener
	public void onMove(EventMoveRaw event) {
		if (p.isInLiquid()) {
			return;
		}
		mc.timer.timerSpeed = 1.0f;
		mc.thePlayer.isAirBorne = true;
		if (!mc.gameSettings.keyBindForward.pressed && !mc.gameSettings.keyBindLeft.pressed
				&& !mc.gameSettings.keyBindRight.pressed && !mc.gameSettings.keyBindBack.pressed) {
			this.state = 1;
		}
		double round = this.round(p.posY - (int) p.posY, 3);
		if (round == this.round(0.138, 3)) {
			EntityPlayerSP thePlayer = p;
			thePlayer.motionY -= 0.08;
			event.y -= 0.09316090325960147;
			EntityPlayerSP thePlayer2 = p;
			thePlayer2.posY -= 0.09316090325960147;
		}
		if (this.state == 1 && (p.moveForward != 0.0f || p.moveStrafing != 0.0f)) {
			this.state = 2;
			this.moveSpeed = 1.5 * this.getBaseMoveSpeed() - 0.1;
		} else if (this.state == 2) {
			this.state = 3;
			p.motionY = 0.4;
			event.y = 0.4;
			this.moveSpeed *= 2.4;
		} else if (this.state == 3) {
			this.state = 4;
			double difference = 0.66 * (this.lastDist - this.getBaseMoveSpeed());
			this.moveSpeed = this.lastDist - difference;
		} else {
			if (mc.theWorld.getCollidingBoundingBoxes(p, p.boundingBox.offset(0.0, p.motionY, 0.0)).size() > 0
					|| p.isCollidedVertically) {
				this.state = 1;
			}
			this.moveSpeed = this.lastDist - this.lastDist / 159.0;
		}
		this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
		MovementInput movementInput = p.movementInput;
		float forward = movementInput.moveForward;
		float strafe = movementInput.moveStrafe;
		float yaw = p.rotationYaw;
		if (forward == 0.0f && strafe == 0.0f) {
			event.x = 1.0;
			event.z = 1.0;
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
		double motionX = forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz;
		double motionZ = forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx;
		event.x = motionX;
		event.z = motionZ;
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
