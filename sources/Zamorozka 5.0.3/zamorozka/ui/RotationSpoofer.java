package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import zamorozka.main.Zamorozka;

public class RotationSpoofer implements MCUtil {
	
	public static Float[] getLookAngles(net.minecraft.util.math.Vec3d vec) {
		Float[] angles = new Float[2];
		Minecraft mc = Minecraft.getMinecraft();
		angles[0] = Float.valueOf((float) (Math.atan2(mc.player.posZ - vec.zCoord, mc.player.posX - vec.xCoord) / Math.PI * 180.0D) + 90.0F);
		float heightdiff = (float) (mc.player.posY + mc.player.getEyeHeight() - vec.yCoord);
		float distance = (float) Math.sqrt((mc.player.posZ - vec.zCoord) * (mc.player.posZ - vec.zCoord) + (mc.player.posX - vec.xCoord) * (mc.player.posX - vec.xCoord));
		angles[1] = Float.valueOf((float) (Math.atan2(heightdiff, distance) / Math.PI * 180.0D));
		return angles;
	}

	public static boolean isFacingEntity(EntityLivingBase entityLivingBase) {
		float yaw = getNeededRotations(entityLivingBase)[0];
		float pitch = getNeededRotations(entityLivingBase)[1];
		float playerYaw = mc.player.rotationYaw;
		float playerPitch = mc.player.rotationPitch;
		float boudingBoxSize = 21.0F + entityLivingBase.getCollisionBorderSize();
		if (playerYaw < 0)
			playerYaw += 360;
		if (playerPitch < 0)
			playerPitch += 360;
		if (yaw < 0)
			yaw += 360;
		if (pitch < 0)
			pitch += 360;
		if (playerYaw >= (yaw - boudingBoxSize) && playerYaw <= (yaw + boudingBoxSize))
			return playerPitch >= (pitch - boudingBoxSize) && playerPitch <= (pitch + boudingBoxSize);
		return false;
	}

	/**
	 * @param entityIn The entity to get the yaw change to.
	 * @return The total angle change between the center of your and the center of
	 *         entityIn's bounding box.
	 */
	public static float getAngleChange(EntityLivingBase entityIn) {
		float yaw = getNeededRotations(entityIn)[0];
		float pitch = getNeededRotations(entityIn)[1];
		float playerYaw = mc.player.rotationYaw;
		float playerPitch = mc.player.rotationPitch;
		if (playerYaw < 0)
			playerYaw += 360;
		if (playerPitch < 0)
			playerPitch += 360;
		if (yaw < 0)
			yaw += 360;
		if (pitch < 0)
			pitch += 360;
		float yawChange = Math.max(playerYaw, yaw) - Math.min(playerYaw, yaw);
		float pitchChange = Math.max(playerPitch, pitch) - Math.min(playerPitch, pitch);
		return yawChange + pitchChange;
	}

	/**
	 * @param entityIn The entity to get rotations to.
	 * @return The needed rotations to entityIn.
	 */
	public static float[] getNeededRotations(EntityLivingBase entityIn) {
		double d0 = entityIn.posX - mc.player.posX;
		double d1 = entityIn.posZ - mc.player.posZ;
		double d2 = entityIn.posY + entityIn.getEyeHeight() - (mc.player.getEntityBoundingBox().minY + mc.player.getEyeHeight());

		double d3 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
		float f = (float) (MathHelper.atan2(d1, d0) * 180.0D / Math.PI) - 90.0F;
		float f1 = (float) (-(MathHelper.atan2(d2, d3) * 180.0D / Math.PI));
		return new float[] { f, f1 };
	}

	/**
	 * @param entityIn The entity to get rotations to.
	 * @param speed    How fast the rotations are.
	 * @return Rotations to entityIn with speed.
	 */
	public static float[] getRotations(EntityLivingBase entityIn, float speed) {
		float yaw = updateRotation(mc.player.rotationYaw, getNeededRotations(entityIn)[0], speed);
		float pitch = updateRotation(mc.player.rotationPitch, getNeededRotations(entityIn)[1], speed);
		return new float[] { yaw, pitch };
	}

	public static float updateRotation(float currentRotation, float intendedRotation, float increment) {
		float f = MathHelper.wrapAngleTo180_float(intendedRotation - currentRotation);

		if (f > increment)
			f = increment;

		if (f < -increment)
			f = -increment;

		return currentRotation + f;
	}

	public static boolean isLookingAtEntity(Entity e) {
		return isLookingAt(e.getPositionEyes((Minecraft.getMinecraft()).timer.renderPartialTicks));
	}
	
	public static void lookAtEntityPacketinstant(Entity e) {
		lookAtPosPacket(e.getPositionEyes((Minecraft.getMinecraft()).timer.renderPartialTicks));
	}

	public static void lookAtPosPacket(net.minecraft.util.math.Vec3d vec) {
		Float[] angles = getLookAngles(vec);
		Zamorozka.setFakeYaw(angles[0].floatValue());
		Zamorozka.setFakePitch(angles[1].floatValue());
	}

	public static boolean lookAtPacketSmooth(net.minecraft.util.math.Vec3d vec, float maxyawchange, float maxpitchchange, boolean smartsmooth) {
		if (smartsmooth) {
			Float[] arrayOfFloat = getLookAngles(vec);
			float f = Math.abs(MathHelper.wrapAngleTo180_float(arrayOfFloat[0].floatValue() - Zamorozka.getFakeYaw())) / 6.0F;
			Zamorozka.setFakeYaw(clampF(arrayOfFloat[0].floatValue(), Zamorozka.getFakeYaw(), f * maxyawchange / 15.0F));
			Zamorozka.setFakePitch(clampF(arrayOfFloat[1].floatValue(), Zamorozka.getFakePitch(), maxpitchchange));
			arrayOfFloat = getLookAngles(vec);
			f = Math.abs(MathHelper.wrapAngleTo180_float(arrayOfFloat[0].floatValue() - Zamorozka.getFakeYaw())) / 2.0F;
			return (f < 20.0F);
		}

		Float[] targetangles = getLookAngles(vec);
		Zamorozka.setFakeYaw(clampF(targetangles[0].floatValue(), Zamorozka.getFakeYaw(), maxyawchange));
		Zamorozka.setFakePitch(clampF(targetangles[1].floatValue(), Zamorozka.getFakePitch(), maxpitchchange));
		targetangles = getLookAngles(vec);
		float change = Math.abs(MathHelper.wrapAngleTo180_float(targetangles[0].floatValue() - Zamorozka.getFakeYaw())) / 2.0F;
		return (change < 20.0F);
	}

	public static boolean isLookingAt(net.minecraft.util.math.Vec3d vec) {
		Float[] targetangles = getLookAngles(vec);
		targetangles = getLookAngles(vec);
		float change = Math.abs(MathHelper.wrapAngleTo180_float(targetangles[0].floatValue() - Zamorozka.getFakeYaw())) / (float) Zamorozka.settingsManager.getSettingByName("RayTraceBoxReduce").getValDouble();
		return (change < 20.0F);
	}
	
	public static boolean isLookingAt2(net.minecraft.util.math.Vec3d vec) {
		Float[] targetangles = getLookAngles(vec);
		targetangles = getLookAngles(vec);
		float change = Math.abs(MathHelper.wrapAngleTo180_float(targetangles[0].floatValue() - Zamorozka.getFakeYaw())) / 0.6f;
		return (change < 20.0F);
	}

	public static boolean isLookingAt1(net.minecraft.util.math.Vec3d vec) {
		Float[] targetangles = getLookAngles(vec);
		targetangles = getLookAngles(vec);
		float change = Math.abs(MathHelper.wrapAngleTo180_float(targetangles[0].floatValue() - Zamorozka.getFakeYaw())) / (float) Zamorozka.settingsManager.getSettingByName("BlockTraceReduce").getValDouble();
		return (change < 20.0F);
	}

	public static void lookAtPosSmooth(net.minecraft.util.math.Vec3d vec, float maxyawchange, float maxpitchchange) {
		Minecraft mc = Minecraft.getMinecraft();
		Float[] targetangles = getLookAngles(vec);
		mc.player.rotationYaw = clampF(targetangles[0].floatValue(), mc.player.rotationYaw, maxyawchange);
		mc.player.rotationPitch = clampF(targetangles[1].floatValue(), mc.player.rotationPitch, maxpitchchange);
	}

	public static void lookAtPos(net.minecraft.util.math.Vec3d vec) {
		Minecraft mc = Minecraft.getMinecraft();
		Float[] targetangles = getLookAngles(vec);
		mc.player.rotationYaw = targetangles[0].floatValue();
		mc.player.rotationPitch = targetangles[1].floatValue();
	}

	public static double getLookDiff(net.minecraft.util.math.Vec3d vec) {
		Minecraft mc = Minecraft.getMinecraft();
		Float[] targetangles = getLookAngles(vec);
		return Math.sqrt(
				((mc.player.rotationYaw - targetangles[0].floatValue()) * (mc.player.rotationYaw - targetangles[0].floatValue()) + (mc.player.rotationPitch - targetangles[1].floatValue()) * (mc.player.rotationPitch - targetangles[1].floatValue())));
	}

	public static float clampF(float intended, float current, float maxchange) {
		float change = MathHelper.wrapAngleTo180_float(intended - current);
		if (change > maxchange) {
			change = maxchange;
		} else if (change < -maxchange) {
			change = -maxchange;
		}
		return MathHelper.wrapAngleTo180_float(current + change);
	}

	public static float clampFPercentage(float intended, float current, float percentage) {
		float change = MathHelper.wrapAngleTo180_float(intended - current);
		return MathHelper.wrapAngleTo180_float(current + change * percentage);
	}

	public static void lookAtBlockPacket(BlockPos bpos, EnumFacing face) {
		Float[] angles = getLookAngles((new net.minecraft.util.math.Vec3d((net.minecraft.util.math.Vec3i) bpos)).addVector(0.5D, 0.5D, 0.5D).addVector(face.getFrontOffsetX() * 0.5D, face.getFrontOffsetY() * 0.5D, face.getFrontOffsetZ() * 0.5D));
		Zamorozka.setFakeYaw(angles[0].floatValue());
		Zamorozka.setFakePitch(angles[1].floatValue());
	}

	public static void lookAtBlockPacketSmooth(BlockPos bpos, EnumFacing face, float maxyaw, float maxpitch) {
		Float[] targetangles = getLookAngles(
				(new net.minecraft.util.math.Vec3d((net.minecraft.util.math.Vec3i) bpos)).addVector(0.5D, 0.5D, 0.5D).addVector(face.getFrontOffsetX() * 0.5D, face.getFrontOffsetY() * 0.5D, face.getFrontOffsetZ() * 0.5D));
		Zamorozka.setFakeYaw(clampF(targetangles[0].floatValue(), Zamorozka.getFakeYaw(), maxyaw));
		Zamorozka.setFakePitch(clampF(targetangles[1].floatValue(), Zamorozka.getFakePitch(), maxpitch));
	}

	public static void lookAtBlockPacketSmooth(BlockPos bpos, EnumFacing face, float percentage) {
		Float[] targetangles = getLookAngles(
				(new net.minecraft.util.math.Vec3d((net.minecraft.util.math.Vec3i) bpos)).addVector(0.5D, 0.5D, 0.5D).addVector(face.getFrontOffsetX() * 0.5D, face.getFrontOffsetY() * 0.5D, face.getFrontOffsetZ() * 0.5D));
		Zamorozka.setFakeYaw(clampFPercentage(targetangles[0].floatValue(), Zamorozka.getFakeYaw(), percentage));
		Zamorozka.setFakePitch(targetangles[1].floatValue());
	}

	public static void lookAtAngleSmooth(float pitch, float yaw, float maxyaw, float maxpitch) {
		Zamorozka.setFakeYaw(clampF(yaw, Zamorozka.getFakeYaw(), maxyaw));
		Zamorozka.setFakePitch(clampF(pitch, Zamorozka.getFakePitch(), maxpitch));
	}

	public static boolean lookAtEntityPacketSmooth(Entity e, float horspeed, float verspeed, boolean smartSmooth) {
		return lookAtPacketSmooth(e.getPositionEyes((Minecraft.getMinecraft()).timer.renderPartialTicks), horspeed, verspeed, smartSmooth);
	}
}