package me.swezedcode.client.utils.location;

import java.util.Random;

import me.swezedcode.client.utils.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class EntityHelper {

	public static float[] getRotations(final Entity ent) {
		final double x = ent.posX;
		final double z = ent.posZ;
		final double y = ent.boundingBox.maxY - 4.5;
		return getRotationFromPosition(x, z, y);
	}

	public static float[] getRotationsforBow(final Entity ent) {
		final double x = ent.posX;
		final double z = ent.posZ;
		final double y = ent.boundingBox.maxY;
		return getRotationFromPosition(x, z, y + 2.0);
	}

	public static float[] getRotationFromPosition(final double x, final double z, final double y) {
		Minecraft.getMinecraft();
		final double xDiff = x - Minecraft.thePlayer.posX;
		Minecraft.getMinecraft();
		final double zDiff = z - Minecraft.thePlayer.posZ;
		Minecraft.getMinecraft();
		Minecraft.getMinecraft();
		final double yDiff = y - Minecraft.thePlayer.posY + Minecraft.thePlayer.getEyeHeight() - -1;
		final double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
		final float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
		final float pitch = (float) (-(Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793));
		return new float[] { yaw, pitch };
	}

	public static float getYawChangeToEntity(final Entity entity) {
		final double deltaX = entity.posX - Minecraft.thePlayer.posX;
		final double deltaZ = entity.posZ - Minecraft.thePlayer.posZ;
		double yawToEntity1 = 0.0;
		if (deltaZ < 0.0 && deltaX < 0.0) {
			yawToEntity1 = 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
		} else if (deltaZ < 0.0 && deltaX > 0.0) {
			final double yawToEntity2 = -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
		} else {
			Math.toDegrees(-Math.atan(deltaX / deltaZ));
		}
		return MathHelper.wrapAngleTo180_float(-(Minecraft.thePlayer.rotationYaw - (float) yawToEntity1));
	}

	public static float getPitchChangeToEntity(final Entity entity) {
		final double deltaX = entity.posX - Minecraft.thePlayer.posX;
		final double deltaZ = entity.posZ - Minecraft.thePlayer.posZ;
		final double deltaY = entity.posY - 1.6 + entity.getEyeHeight() - Minecraft.thePlayer.posY;
		final double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
		final double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
		return -MathHelper.wrapAngleTo180_float(Minecraft.thePlayer.rotationPitch - (float) pitchToEntity);
	}

	public static float[] getAngles(final Entity e) {
		return new float[] { getYawChangeToEntity(e) + Minecraft.thePlayer.rotationYaw,
				getPitchChangeToEntity(e) + Minecraft.thePlayer.rotationPitch };
	}

	public static double getDirectionCheckVal(final Entity e, final Vec3 lookVec) {
		return directionCheck(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + Minecraft.thePlayer.getEyeHeight(),
				Minecraft.thePlayer.posZ, lookVec.xCoord, lookVec.yCoord, lookVec.zCoord, e.posX,
				e.posY + e.height / 2.0, e.posZ, e.width, e.height, 5.0);
	}

	public static double[] getRotationToEntity(final Entity entity) {
		Minecraft.getMinecraft();
		final double pX = Minecraft.thePlayer.posX;
		Minecraft.getMinecraft();
		final double posY = Minecraft.thePlayer.posY;
		Minecraft.getMinecraft();
		final double pY = posY + Minecraft.thePlayer.getEyeHeight();
		Minecraft.getMinecraft();
		final double pZ = Minecraft.thePlayer.posZ;
		final double eX = entity.posX;
		final double eY = entity.posY + entity.height / 2.0f;
		final double eZ = entity.posZ;
		final double dX = pX - eX;
		final double dY = pY - eY;
		final double dZ = pZ - eZ;
		final double dH = Math.sqrt(Math.pow(dX, 2.0) + Math.pow(dZ, 2.0));
		final double yaw = Math.toDegrees(Math.atan2(dZ, dX)) + 90.0;
		final double pitch = Math.toDegrees(Math.atan2(dH, dY));
		return new double[] { yaw, 90.0 - pitch };
	}

	public static boolean isWithingFOV(final Entity en, float angle) {
		angle *= 0.5;
		Minecraft.getMinecraft();
		final double a = Minecraft.thePlayer.rotationYaw;
		final double angleDifference = angleDifference(a, getRotationToEntity(en)[0]);
		return (angleDifference > 0.0 && angleDifference < angle)
				|| (-angle < angleDifference && angleDifference < 0.0);
	}

	private static double directionCheck(final double sourceX, final double sourceY, final double sourceZ,
			final double dirX, final double dirY, final double dirZ, final double targetX, final double targetY,
			final double targetZ, final double targetWidth, final double targetHeight, final double precision) {
		double dirLength = Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
		if (dirLength == 0.0) {
			dirLength = 1.0;
		}
		final double dX = targetX - sourceX;
		final double dY = targetY - sourceY;
		final double dZ = targetZ - sourceZ;
		final double targetDist = Math.sqrt(dX * dX + dY * dY + dZ * dZ);
		final double xPrediction = targetDist * dirX / dirLength;
		final double yPrediction = targetDist * dirY / dirLength;
		final double zPrediction = targetDist * dirZ / dirLength;
		double off = 0.0;
		off += Math.max(Math.abs(dX - xPrediction) - (targetWidth / 2.0 + precision), 0.0);
		off += Math.max(Math.abs(dZ - zPrediction) - (targetWidth / 2.0 + precision), 0.0);
		off += Math.max(Math.abs(dY - yPrediction) - (targetHeight / 2.0 + precision), 0.0);
		if (off > 1.0) {
			off = Math.sqrt(off);
		}
		return off;
	}

	public static double angleDifference(final double a, final double b) {
		return ((a - b) % 360.0 + 540.0) % 360.0 - 180.0;
	}

	public static float[] faceTarget(final Entity target, final float p_70625_2_, final float p_70625_3_,
			final boolean miss) {
		double var4 = target.posX - Wrapper.getPlayer().posX;
		final double var5 = target.posZ - Wrapper.getPlayer().posZ;
		if (target instanceof EntityLivingBase) {
			final EntityLivingBase var6 = (EntityLivingBase) target;
			var4 = var6.posY + var6.getEyeHeight() - (Wrapper.getPlayer().posY + Wrapper.getPlayer().getEyeHeight());
		} else {
			var4 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0
					- (Wrapper.getPlayer().posY + Wrapper.getPlayer().getEyeHeight());
		}
		final Random rnd = new Random();
		final float offset = miss ? (rnd.nextInt(15) * 0.25f + 5.0f) : 0.0f;
		final double var7 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
		final float var8 = (float) (Math.atan2(var5 + offset, var4) * 180.0 / 3.141592653589793) - 90.0f;
		final float var9 = (float) (-(Math.atan2(var4 - ((target instanceof EntityPlayer) ? 0.5f : 0.0f) + offset, var7)
				* 180.0 / 3.141592653589793));
		final float pitch = changeRotation(Wrapper.getPlayer().rotationPitch, var9, p_70625_3_);
		final float yaw = changeRotation(Wrapper.getPlayer().rotationYaw, var8, p_70625_2_);
		return new float[] { yaw, pitch };
	}

	public static float changeRotation(final float p_70663_1_, final float p_70663_2_, final float p_70663_3_) {
		float var4 = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);
		if (var4 > p_70663_3_) {
			var4 = p_70663_3_;
		}
		if (var4 < -p_70663_3_) {
			var4 = -p_70663_3_;
		}
		return p_70663_1_ + var4;
	}

}
