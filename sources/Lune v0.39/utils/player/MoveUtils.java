package me.superskidder.lune.utils.player;

import me.superskidder.lune.events.EventMove;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class MoveUtils {
	private static Minecraft mc = Minecraft.getMinecraft();

	public static boolean isOnGround(double height) {
		if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer,mc.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public static double defaultSpeed() {
		double baseSpeed = 0.2873D;
		if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
			int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
			baseSpeed *= (1.0D + 0.2D * (amplifier + 1));
		}
		return baseSpeed;
	}

	public static int getSpeedEffect() {
		if (mc.thePlayer.isPotionActive(Potion.moveSpeed))
			return mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
		else
			return 0;
	}

	public static int getJumpEffect() {
		if (mc.thePlayer.isPotionActive(Potion.jump))
			return mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1;
		else
			return 0;
	}

	public static void setMotion(double speed) {
		double forward = mc.thePlayer.movementInput.moveForward;
		double strafe = mc.thePlayer.movementInput.moveStrafe;
		float yaw = mc.thePlayer.rotationYaw;
		if ((forward == 0.0D) && (strafe == 0.0D)) {
			mc.thePlayer.motionX = 0;
			mc.thePlayer.motionZ = 0;
		} else {
			if (forward != 0.0D) {
				if (strafe > 0.0D) {
					yaw += (forward > 0.0D ? -45 : 45);
				} else if (strafe < 0.0D) {
					yaw += (forward > 0.0D ? 45 : -45);
				}
				strafe = 0.0D;
				if (forward > 0.0D) {
					forward = 1;
				} else if (forward < 0.0D) {
					forward = -1;
				}
			}
			mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0F))
					+ strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F));
			mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0F))
					- strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F));
		}
	}

	public static float[] getRotationsBlock(BlockPos block, EnumFacing face) {
		double x = block.getX() + 0.5 - mc.thePlayer.posX + (double) face.getFrontOffsetX() / 2;
		double z = block.getZ() + 0.5 - mc.thePlayer.posZ + (double) face.getFrontOffsetZ() / 2;
		double y = (block.getY() + 0.5);
		double d1 = mc.thePlayer.posY + mc.thePlayer.getEyeHeight() - y;
		double d3 = MathHelper.sqrt_double(x * x + z * z);
		float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
		float pitch = (float) (Math.atan2(d1, d3) * 180.0D / Math.PI);
		if (yaw < 0.0F) {
			yaw += 360f;
		}
		return new float[] { yaw, pitch };
	}

	public static boolean isCollidedH(double dist) {
		AxisAlignedBB bb = new AxisAlignedBB(mc.thePlayer.posX - 0.3, mc.thePlayer.posY + 2.0,
				mc.thePlayer.posZ + 0.3, mc.thePlayer.posX + 0.3, mc.thePlayer.posY + 3.0,
				mc.thePlayer.posZ - 0.3);
		if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(0.3 + dist, 0.0, 0.0))
				.isEmpty()) {
			return true;
		}
		if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(-0.3 - dist, 0.0, 0.0))
				.isEmpty()) {
			return true;
		}
		if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(0.0, 0.0, 0.3 + dist))
				.isEmpty()) {
			return true;
		}
		if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb.offset(0.0, 0.0, -0.3 - dist))
				.isEmpty()) {
			return true;
		}
		return false;
	}

	public static double getDirection() {
		float rotationYaw = mc.thePlayer.rotationYaw;

		if (mc.thePlayer.moveForward < 0F)
			rotationYaw += 180F;

		float forward = 1F;
		if (mc.thePlayer.moveForward < 0F)
			forward = -0.5F;
		else if (mc.thePlayer.moveForward > 0F)
			forward = 0.5F;

		if (mc.thePlayer.moveStrafing > 0F)
			rotationYaw -= 90F * forward;

		if (mc.thePlayer.moveStrafing < 0F)
			rotationYaw += 90F * forward;

		return Math.toRadians(rotationYaw);
	}

	public static void setSpeed(EventMove e, double moveSpeed) {
		MoveUtils.setSpeed(e, moveSpeed, MoveUtils.mc.thePlayer.rotationYaw,
				MoveUtils.mc.thePlayer.movementInput.moveStrafe, MoveUtils.mc.thePlayer.movementInput.moveForward);
	}

	public static void setSpeed(EventMove moveEvent, double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
		double forward = pseudoForward;
		double strafe = pseudoStrafe;
		float yaw = pseudoYaw;
		if (forward != 0.0) {
			if (strafe > 0.0) {
				yaw += (float)(forward > 0.0 ? -45 : 45);
			} else if (strafe < 0.0) {
				yaw += (float)(forward > 0.0 ? 45 : -45);
			}
			strafe = 0.0;
			if (forward > 0.0) {
				forward = 1.0;
			} else if (forward < 0.0) {
				forward = -1.0;
			}
		}
		if (strafe > 0.0) {
			strafe = 1.0;
		} else if (strafe < 0.0) {
			strafe = -1.0;
		}
		double mx = Math.cos(Math.toRadians(yaw + 90.0f));
		double mz = Math.sin(Math.toRadians(yaw + 90.0f));
		moveEvent.x = forward * moveSpeed * mx + strafe * moveSpeed * mz;
		moveEvent.z = forward * moveSpeed * mz - strafe * moveSpeed * mx;
	}

	public static double getJumpBoostModifier(double baseJumpHeight) {
		if (mc.thePlayer.isPotionActive(Potion.jump)) {
			int amplifier = mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier();
			baseJumpHeight += (double) ((float) (amplifier + 1) * 0.1f);
		}
		return baseJumpHeight;
	}

	public static double getBaseMoveSpeed() {
		double baseSpeed = 0.2875;
		if (MoveUtils.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
			baseSpeed *= 1.0 + 0.2
					* (double) (MoveUtils.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
		}
		return baseSpeed;
	}

	public static void setSpeedInAir(float speedInAir) {
		mc.thePlayer.speedInAir = speedInAir;
	}
}
