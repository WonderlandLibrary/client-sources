package me.swezedcode.client.module.modules.Motion;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.module.modules.Options.MemeNames;
import me.swezedcode.client.utils.events.EventMove;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import me.swezedcode.client.utils.math.MathUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;

public class LongHop extends Module {

	public LongHop() {
		super("LongHop", Keyboard.KEY_NONE, 0xFFD796FF, ModCategory.Motion);
		if (!MemeNames.enabled) {
			setDisplayName("LongHop");
		} else {
			setDisplayName("LongJum");
		}
		this.moveSpeed = 0.2873D;
		this.boost = 3.5D;
	}

	public static double boost = 2.8D;
	private int speed;
	private double moveSpeed;
	private double lastDist;

	public void onEnable() {
		if (mc.thePlayer != null) {
			this.moveSpeed = a();
		}
		this.lastDist = 0.0D;
		this.speed = 1;
		super.onEnable();
	}

	public double a() {
		double boostSpeed = 0.2873D;
		if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
			int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
			boostSpeed *= (1.0D + 0.2D * (amplifier + 1));
		}
		return boostSpeed;
	}

	@EventListener
	private void b(EventMove event) {
		if ((mc.thePlayer.moveStrafing <= 0.0F) && (mc.thePlayer.moveForward <= 0.0F)) {
			this.speed = 1;
		}
		if (MathUtils.round(mc.thePlayer.posY - (int) mc.thePlayer.posY, 3) == MathUtils.round(0.943D, 3)) {
			mc.thePlayer.motionY -= 0.03D;
			event.y -= 0.03D;
		}
		if ((this.speed == 1) && ((mc.thePlayer.moveForward != 0.0F) || (mc.thePlayer.moveStrafing != 0.0F))
				&& mc.thePlayer.onGround) {
			this.speed = 2;
			this.mc.thePlayer.motionY = -0.42D;
			this.moveSpeed = (this.boost * a() - 0.01D);
		} else if (this.speed == 2) {
			this.speed = 3;
			mc.thePlayer.motionY = 0.424D;
			event.y = 0.424D;
			this.moveSpeed *= 2.149802D;
		} else if (this.speed == 3) {
			this.speed = 4;
			double difference = 0.66D * (this.lastDist - a());
			this.moveSpeed = (this.lastDist - difference);
		} else {
			if ((mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer,
					mc.thePlayer.boundingBox.offset(0.0D, mc.thePlayer.motionY, 0.0D)).size() > 0)
					|| (mc.thePlayer.isCollidedVertically)) {
				this.speed = 1;
			}
			this.moveSpeed = (this.lastDist - this.lastDist / 159.0D);
		}
		this.moveSpeed = Math.max(this.moveSpeed, a());
		float f = MovementInput.moveForward;
		float s = MovementInput.moveStrafe;
		float y = mc.thePlayer.rotationYaw;
		if ((f == 0.0F) && (s == 0.0F)) {
			event.x = 0.0D;
			event.z = 0.0D;
		} else if (f != 0.0F) {
			if (s >= 1.0F) {
				y += (f > 0.0F ? -45 : 45);
				s = 0.0F;
			} else if (s <= -1.0F) {
				y += (f > 0.0F ? 45 : -45);
				s = 0.0F;
			}
			if (f > 0.0F) {
				f = 1.0F;
			} else if (f < 0.0F) {
				f = -1.0F;
			}
		}
		double mx = Math.cos(Math.toRadians(y + 90.0F));
		double mz = Math.sin(Math.toRadians(y + 90.0F));
		event.x = (f * this.moveSpeed * mx + s * this.moveSpeed * mz);
		event.z = (f * this.moveSpeed * mz - s * this.moveSpeed * mx);
	}

	@EventListener
	private void c(EventPreMotionUpdates event) {
		boolean speedy = mc.thePlayer.isPotionActive(Potion.moveSpeed);
		double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
		double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
		this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
	}

	@Override
	public void onDisable() {
		mc.timer.timerSpeed = 1.0F;
	}

}
