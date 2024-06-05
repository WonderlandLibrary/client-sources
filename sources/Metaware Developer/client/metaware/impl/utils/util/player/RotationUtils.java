package client.metaware.impl.utils.util.player;

import java.util.concurrent.ThreadLocalRandom;

import client.metaware.Metaware;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import client.metaware.impl.module.combat.KillAura;
import client.metaware.impl.utils.util.other.MathUtils;
import client.metaware.impl.utils.util.other.PlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RotationUtils {

	private static Minecraft mc = Minecraft.getMinecraft();


	public static Rotation getRotationsRandom(EntityLivingBase entity) {
		ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
		double randomXZ = threadLocalRandom.nextDouble(-0.05, 0.1);
		double randomY = threadLocalRandom.nextDouble(-0.05, 0.1);
		double x = entity.posX + randomXZ;
		double y = entity.posY + (entity.getEyeHeight() / 2.05) + randomY;
		double z = entity.posZ + randomXZ;
		return attemptFacePosition(x, y, z);
	}

	public static float getYawBetween(final float yaw,
									  final double srcX,
									  final double srcZ,
									  final double destX,
									  final double destZ) {
		final double xDist = destX - srcX;
		final double zDist = destZ - srcZ;
		final float var1 = (float) (StrictMath.atan2(zDist, xDist) * 180.0D / Math.PI) - 90.0F;
		return yaw + MathHelper.wrapAngleTo180_float(var1 - yaw);
	}


	public static Rotation attemptFacePosition(double x, double y, double z) {
		double xDiff = x - mc.thePlayer.posX;
		double yDiff = y - mc.thePlayer.posY - 1.2;
		double zDiff = z - mc.thePlayer.posZ;

		double dist = Math.hypot(xDiff, zDiff);
		float yaw = (float) (Math.atan2(zDiff, xDiff) * 180 / Math.PI) - 90;
		float pitch = (float) -(Math.atan2(yDiff, dist) * 180 / Math.PI);
		return new Rotation(yaw, pitch);
	}



	public static float[] getNeededRotations(Entity entity) {
		double d0 = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
		double d1 = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
		double d2 = entity.posY + entity.getEyeHeight()
				- (Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().minY
						+ (Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().maxY
								- Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().minY));
		double d3 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
		float f = (float) (MathHelper.func_181159_b(d1, d0) * 180.0D / Math.PI) - 90.0F;
		float f1 = (float) (-(MathHelper.func_181159_b(d2, d3) * 180.0D / Math.PI));
		return new float[] { f, f1 };
	}

	public static Vec3 getVectorForRotation(final float pitch, final float yaw) {
		final float f = MathHelper.cos(-yaw * 0.017453292f - 3.1415927f);
		final float f2 = MathHelper.sin(-yaw * 0.017453292f - 3.1415927f);
		final float f3 = -MathHelper.cos(-pitch * 0.017453292f);
		final float f4 = MathHelper.sin(-pitch * 0.017453292f);
		return new Vec3(f2 * f3, f4, f * f3);
	}

	public static float[] getRotations(final float[] lastRotations,
									   final float changeMax,
									   final float[] dstRotation) {
		dstRotation[0] = maxAngleChange(lastRotations[0], dstRotation[0], changeMax);
		dstRotation[1] = maxAngleChange(lastRotations[1], dstRotation[1], changeMax);

		return applyGCD(dstRotation, lastRotations);
	}

	public static double getMouseGCD() {
		final float sens = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6F + 0.2F;
		final float pow = sens * sens * sens * 8.0F;
		return pow * 0.15;
	}

	public static float[] applyGCD(final float[] rotations,
								   final float[] prevRots) {
		final float yawDif = rotations[0] - prevRots[0];
		final float pitchDif = rotations[1] - prevRots[1];
		final double gcd = getMouseGCD();

		rotations[0] -= yawDif % gcd;
		rotations[1] -= pitchDif % gcd;
		return rotations;
	}

	public static float[] getPredictedRotations(EntityLivingBase ent) {
		double x = ent.posX + (ent.posX - ent.lastTickPosX) * Math.random();
		double z = ent.posZ + (ent.posZ - ent.lastTickPosZ) * Math.random();
		double y = ent.posY + ent.getEyeHeight() / 2.0F * Math.random();
		return getRotationFromPosition1(x, z, y);
	}



	public static float[] getRotationFromPosition1(double x, double z, double y) {
		double xDiff = x - mc.thePlayer.posX;
		double zDiff = z - mc.thePlayer.posZ;
		double yDiff = y - mc.thePlayer.posY - 0.8;
		double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
		float yaw = (float) Math.toDegrees(Math.atan2(zDiff, xDiff)) - 90.0F;
		float pitch = (float) -Math.toDegrees(Math.atan2(yDiff, dist));
		return new float[]{yaw, pitch};
	}


	public static Rotation getRotationsToEntity(final Entity entity) {
		if (entity == null) return null;
		final EntityPlayerSP player = mc.thePlayer;

		final double diffX = entity.posX - player.posX,
				diffY = entity.posY + entity.getEyeHeight() * 0.9 - (player.posY + player.getEyeHeight()),
				diffZ = entity.posZ - player.posZ, dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ); // @on

		final float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F,
				pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);

		return new Rotation(player.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - player.rotationYaw), player.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - player.rotationPitch));
	}

	public static float getDistanceBetweenAngles(float angle1, float angle2) {
		float angle = Math.abs(angle1 - angle2) % 360.0f;
		if (angle > 180.0f) {
			angle = 360.0f - angle;
		}
		return angle;
	}

	public static void rotate(final UpdatePlayerEvent event, final float[] rotations, final float aimSpeed, final boolean lockView) {
		final float[] prevRotations = {event.getPrevYaw(), event.getPrevPitch()};

		final float[] cappedRotations = {
				maxAngleChange(prevRotations[0], rotations[0], aimSpeed),
				maxAngleChange(prevRotations[1], rotations[1], aimSpeed)
		};

		event.setYaw(cappedRotations[0]);
		event.setPitch(cappedRotations[1]);

		if (lockView) {
			PlayerUtil.getLocalPlayer().rotationYaw = cappedRotations[0];
			PlayerUtil.getLocalPlayer().rotationPitch = cappedRotations[1];
		}
	}

	public static void rotate(final UpdatePlayerEvent event, Rotation rotation, final float aimSpeed, final boolean lockView) {
		final float[] prevRotations = {event.getPrevYaw(), event.getPrevPitch()};

		final float[] cappedRotations = {
				maxAngleChange(prevRotations[0], rotation.getRotationYaw(), aimSpeed),
				maxAngleChange(prevRotations[1], rotation.getRotationPitch(), aimSpeed)
		};

		event.setYaw(cappedRotations[0]);
		event.setPitch(cappedRotations[1]);

		if (lockView) {
			PlayerUtil.getLocalPlayer().rotationYaw = cappedRotations[0];
			PlayerUtil.getLocalPlayer().rotationPitch = cappedRotations[1];
		}
	}

	public static void rotate(final UpdatePlayerEvent event, final float[] rotations) {
		KillAura killAura = (KillAura) Metaware.INSTANCE.getModuleManager().getModuleByClass(KillAura.class);
		final float[] prevRotations = {event.getPrevYaw(), event.getPrevPitch()};

		final float[] cappedRotations = {
				maxAngleChange(prevRotations[0], rotations[0], killAura.maxAngleChangeProperty.getValue().floatValue()),
				maxAngleChange(prevRotations[1], rotations[1], killAura.maxAngleChangeProperty.getValue().floatValue())
		};

		event.setYaw(cappedRotations[0]);
		event.setPitch(cappedRotations[1]);

		if (killAura.lockview.getValue()) {
			PlayerUtil.getLocalPlayer().rotationYaw = cappedRotations[0];
			PlayerUtil.getLocalPlayer().rotationPitch = cappedRotations[1];
		}
	}

	public static void rotate(final UpdatePlayerEvent event, Rotation rotation) {
		KillAura killAura = Metaware.INSTANCE.getModuleManager().getModuleByClass(KillAura.class);
		final float[] prevRotations = {event.getPrevYaw(), event.getPrevPitch()};

		final float[] cappedRotations = {
				maxAngleChange(prevRotations[0], rotation.getRotationYaw(), killAura.maxAngleChangeProperty.getValue().floatValue()),
				maxAngleChange(prevRotations[1], rotation.getRotationPitch(), killAura.maxAngleChangeProperty.getValue().floatValue())
		};

		float[] rots1 = applyGCD(cappedRotations, prevRotations);

		event.setYaw(rots1[0]);
		event.setPitch(rots1[1]);

		if (killAura.lockview.getValue()) {
			PlayerUtil.getLocalPlayer().rotationYaw = cappedRotations[0];
			PlayerUtil.getLocalPlayer().rotationPitch = cappedRotations[1];
		}
	}

	public static float maxAngleChange(final float prev, final float now, final float maxTurn) {
		float dif = MathHelper.wrapAngleTo180_float(now - prev);
		if (dif > maxTurn) dif = maxTurn;
		if (dif < -maxTurn) dif = -maxTurn;
		return prev + dif;
	}

	public static float getPitchChange(float pitch, Entity entity, double posY) {
		double deltaX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
		double deltaZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
		double deltaY = posY - 2.2D + entity.getEyeHeight() - Minecraft.getMinecraft().thePlayer.posY;
		double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
		double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
		return -MathHelper.wrapAngleTo180_float(pitch - (float) pitchToEntity) - 2.5F;
	}

	public static float getYawChange(float yaw, double posX, double posZ) {
		double deltaX = posX - Minecraft.getMinecraft().thePlayer.posX;
		double deltaZ = posZ - Minecraft.getMinecraft().thePlayer.posZ;
		double yawToEntity = 0;
		if ((deltaZ < 0.0D) && (deltaX < 0.0D)) {
			if (deltaX != 0)
				yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
		} else if ((deltaZ < 0.0D) && (deltaX > 0.0D)) {
			if (deltaX != 0)
				yawToEntity = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
		} else {
			if (deltaZ != 0)
				yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
		}

		return MathHelper.wrapAngleTo180_float(-(yaw - (float) yawToEntity));
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

	public static float[] getBowAngles(final Entity entity) {
		double xDelta = entity.posX - entity.lastTickPosX;
		double zDelta = entity.posZ - entity.lastTickPosZ;
		double distance = mc.thePlayer.getDistanceToEntity(entity) % .8;
		boolean sprint = entity.isSprinting();
		double xMulti = distance / .8 * xDelta * (sprint ? 1.45 : 1.3);
		double zMulti = distance / .8 * zDelta * (sprint ? 1.45 : 1.3);
		double x = entity.posX + xMulti - mc.thePlayer.posX;
		double y = mc.thePlayer.posY + mc.thePlayer.getEyeHeight()
				- (entity.posY + entity.getEyeHeight());
		double z = entity.posZ + zMulti - mc.thePlayer.posZ;
		double distanceToEntity = mc.thePlayer.getDistanceToEntity(entity);
		float yaw = (float) Math.toDegrees(Math.atan2(z, x)) - 90;
		float pitch = (float) Math.toDegrees(Math.atan2(y, distanceToEntity));
		return new float[]{yaw, pitch};
	}

	public static float getYawToEntity(Entity entity, boolean useOldPos) {
		final EntityPlayerSP player = mc.thePlayer;
		double xDist = (useOldPos ? entity.prevPosX : entity.posX) - (useOldPos ? player.prevPosX : player.posX);
		double zDist = (useOldPos ? entity.prevPosZ : entity.posZ) - (useOldPos ? player.prevPosZ : player.posZ);
		float rotationYaw = useOldPos ? player.prevRotationYaw : player.rotationYaw;
		float var1 = (float) (Math.atan2(zDist, xDist) * 180.0D / Math.PI) - 90.0F;
		return rotationYaw + MathHelper.wrapAngleTo180_float(var1 - rotationYaw);
	}

	public static Rotation attempKarhu(double x, double y, double z) {
		double xDiff = x - mc.thePlayer.posX;
		double yDiff = y - mc.thePlayer.posY - MathUtils.getRandomInRange(0.89 + MathUtils.randomFloatValue(), 1.2 + MathUtils.randomFloatValue());
		double zDiff = z - mc.thePlayer.posZ;

		double dist = Math.hypot(xDiff, zDiff);
		float yaw = (float) (Math.atan2(zDiff, xDiff) * 180 / Math.PI) - 90;
		float pitch = (float) -(Math.atan2(yDiff, dist) * 180 / Math.PI);
		return new Rotation(yaw, pitch);
	}

	public static Rotation getRotationsKarhu(EntityLivingBase entity) {
		ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
		double randomXZ = MathUtils.getRandomInRange(-0.025 + MathUtils.randomFloatValue(), 0.135 + MathUtils.randomFloatValue());
		double randomY = MathUtils.getRandomInRange(-0.025 + MathUtils.randomFloatValue(), 0.135 + MathUtils.randomFloatValue());
		double x = entity.posX;
		double y = entity.posY + (entity.getEyeHeight() / 2.05) - randomY;
		double z = entity.posZ;
		return attempKarhu(x - randomXZ - MathUtils.randomFloatValue(), y, z - randomXZ - MathUtils.randomFloatValue());
	}

	public static Vec3 getRotationVector(float pitch, float yaw) {
		float f = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
		float f1 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
		float f2 = -MathHelper.cos(-pitch * 0.017453292F);
		float f3 = MathHelper.sin(-pitch * 0.017453292F);
		return new Vec3((double) (f1 * f2), (double) f3, (double) (f * f2));
	}
}
