package me.protocol_client.modules;

import java.util.ArrayList;

import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.utils.MathUtils;
import net.minecraft.potion.Potion;
import darkmagician6.EventManager;
import darkmagician6.EventTarget;
import events.EventMove;
import events.EventPrePlayerUpdate;

public class BunnyHop extends Module {

	public BunnyHop() {
		super("Bunny Hop", "bunnyhop", 0, Category.MOVEMENT, new String[] { "bunnyhop", "bhop" });
	}

	private double	moveSpeed;
	private double	lastDist;
	private double	stage;

	public void onEnable() {
		EventManager.register(this);
		net.minecraft.util.Timer.timerSpeed = 1.0F;
		this.lastDist = 0.0D;
		this.stage = 4.0D;
		this.moveSpeed = (Wrapper.getPlayer() == null ? 0.2873D : getBaseMoveSpeed());
	}

	public void onDisable() {
		EventManager.unregister(this);
	}

	//Aris told me to skid this, so I did
	@EventTarget
	public void onMove(EventMove event) {
		if (MathUtils.roundToPlace(Wrapper.getPlayer().posY - (int) Wrapper.getPlayer().posY, 3) == MathUtils.roundToPlace(0.138D, 3)) {
			Wrapper.getPlayer().motionY -= 0.08D;
			event.y = event.y - 0.0931D;
			Wrapper.getPlayer().posY -= 0.0931D;
		}
		if ((this.stage == 2.0D) && ((Wrapper.getPlayer().moveForward != 0.0F) || (Wrapper.getPlayer().moveStrafing != 0.0F))) {
			Wrapper.getPlayer().motionY = 0.4D;
			event.y = 0.4D;
			this.moveSpeed *= 2.149D;
		} else if (this.stage == 3.0D) {
			double difference = 0.66D * (this.lastDist - getBaseMoveSpeed());
			this.moveSpeed = (this.lastDist - difference);
		} else {
			ArrayList collidingList = Wrapper.getWorld().getCollidingBlockBoundingBoxes(Wrapper.getPlayer(), Wrapper.getPlayer().boundingBox.offset(0.0D, Wrapper.getPlayer().motionY, 0.0D));
			if ((collidingList.size() > 0) || (Wrapper.getPlayer().isCollidedVertically)) {
				this.stage = 1.0D;
			}
			this.moveSpeed = (this.lastDist - this.lastDist / 159.0D);
		}
		this.moveSpeed = Math.max(this.moveSpeed, getBaseMoveSpeed());
		setMoveSpeed(event, this.moveSpeed);
		this.stage += 1.0D;
	}

	@EventTarget
	public void onUpdate(EventPrePlayerUpdate event) {
		double xDist = Wrapper.getPlayer().posX - Wrapper.getPlayer().prevPosX;
		double zDist = Wrapper.getPlayer().posZ - Wrapper.getPlayer().prevPosZ;
		this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
	}

	private double getBaseMoveSpeed() {
		double baseSpeed = 0.2873D;
		if (Wrapper.getPlayer().isPotionActive(Potion.moveSpeed)) {
			int amplifier = Wrapper.getPlayer().getActivePotionEffect(Potion.moveSpeed).getAmplifier();
			baseSpeed *= (1.0D + 0.2D * (amplifier + 1));
		}
		return baseSpeed;
	}

	public static void setMoveSpeed(EventMove event, double speed) {
		double forward = Wrapper.getPlayer().movementInput.moveForward;
		double strafe = Wrapper.getPlayer().movementInput.moveStrafe;
		float yaw = Wrapper.getPlayer().rotationYaw;
		if ((forward == 0.0D) && (strafe == 0.0D)) {
			event.x = 0.0D;
			event.z = 0.0D;
		} else {
			if (forward != 0.0D) {
				if (strafe > 0.0D) {
					yaw += (forward > 0.0D ? -45 : 45);
				} else if (strafe < 0.0D) {
					yaw += (forward > 0.0D ? 45 : -45);
				}
				strafe = 0.0D;
				if (forward > 0.0D) {
					forward = 1.0D;
				} else if (forward < 0.0D) {
					forward = -1.0D;
				}
			}
			event.x = forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F));
			event.z = forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F));
		}
	}
}
