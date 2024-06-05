package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import zamorozka.modules.WORLD.Scaffold;

public class RotUtils implements MCUtil {
	public static float[] getIntaveRots(BlockPos bp, EnumFacing enumface) {
		double x = bp.getX() + 0.5D, y = bp.getY() + 0.5D, z = bp.getZ() + 0.5D;
		if (enumface != null)
			if (EnumFacing.UP != null) {
				y += 0.5D;
			} else if (EnumFacing.DOWN != null) {
				y -= 0.5D;
			} else if (EnumFacing.WEST != null) {
				x += 0.5D;
			} else if (EnumFacing.EAST != null) {
				x -= 0.5D;
			} else if (EnumFacing.NORTH != null) {
				z += 0.5D;
			} else if (EnumFacing.SOUTH != null) {
				z -= 0.5D;
			}
		double dX = x - mc.player.posX, dY = y - mc.player.posY + mc.player.getEyeHeight();
		double dZ = z - mc.player.posZ;
		float yaw = (float) (Math.atan2(dZ, dX) * 180.0D / Math.PI) - 90.0F;
		float pitch = (float) -(Math.atan2(dY, Math.sqrt(dX * dX + dZ * dZ)) * 180.0D / Math.PI);
		yaw = MathHelper.wrapDegrees(yaw);
		pitch = MathHelper.wrapDegrees(pitch);
		return new float[] { yaw, pitch };
	}

	public static float[] getRotations(BlockPos block, EnumFacing face) {
		double x = block.getX() + 0.5 - mc.player.posX + (double) face.getFrontOffsetX() / 2;
		double z = block.getZ() + 0.5 - mc.player.posZ + (double) face.getFrontOffsetZ() / 2;
		double y = (block.getY() + 0.5);
		double dist = mc.player.getDistance(block.getX() + 0.5 + (double) face.getFrontOffsetX() / 2, block.getY(), block.getZ() + 0.5 + (double) face.getFrontOffsetZ() / 2);
		if (dist > 1.5) {
			y += 0.5;
			x += (double) face.getFrontOffsetX() / 8;
			z += (double) face.getFrontOffsetZ() / 8;
		}

		double d1 = mc.player.posY + mc.player.getEyeHeight() - y;
		double d3 = MathHelper.sqrt_double(x * x + z * z);
		float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
		float pitch = (float) (Math.atan2(d1, d3) * 180.0D / Math.PI);
		if (yaw < 0.0F) {
			yaw += 360f;
		}
		return new float[] { yaw, pitch };
	}

	public static float getSensitivityMultiplier2() {
		float f = mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
		return (f * f * f * 8.0F) * 0.15F;
	}

	public static void applyStrafeToPlayer() {
		EntityPlayerSP player = Minecraft.player;
		float yaw = Minecraft.player.rotationYaw;
		float dif = ((MathHelper.wrapAngleTo180_float(player.rotationYaw - yaw - 23.5f - 135 + 180) / 45));
		float calcForward = 0f;
		float calcStrafe = 0f;
	}

	public static float[] getBlockRotations(final double x, final double y, final double z) {
		final double var4 = x - mc.player.posX + 0.5;
		final double var5 = z - mc.player.posZ + 0.5;
		final double var6 = y - (mc.player.posY + mc.player.getEyeHeight() - 1.0);
		final double var7 = MathHelper.sqrt(var4 * var4 + var5 * var5);
		final float var8 = (float) (Math.atan2(var5, var4) * 180.0 / 3.141592653589793) - 90.0f;
		return new float[] { var8, (float) (-(Math.atan2(var6, var7) * 180.0 / 3.141592653589793)) };
	}

	public static float[] getRotationsNeededBlock(double x, double y, double z) {
		double diffX = x + 0.4D - Minecraft.getMinecraft().player.posX;
		double diffZ = z + 0.4D - Minecraft.getMinecraft().player.posZ;
		double diffY = y - (Minecraft.getMinecraft().player.posY + (double) Minecraft.getMinecraft().player.getEyeHeight() - 1.0);
		double dist = (double) MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
		float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
		float pitch = (float) (-(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D));
		return new float[] { Minecraft.getMinecraft().player.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().player.rotationYaw),
				Minecraft.getMinecraft().player.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().player.rotationPitch) };
	}

	public static void setPlayerRotations(float yaw, float pitch) {
		Minecraft.player.rotationYaw = yaw;
		Minecraft.player.rotationYawHead = yaw;
		Minecraft.player.rotationPitch = pitch;
	}

	public static void lookAtVec3d(Vec3d vec3d) {
		final float[] angle = MathUtil.calcAngle(Minecraft.player.getPositionEyes(mc.getRenderPartialTicks()), new Vec3d(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord));
		setPlayerRotations(angle[0], angle[1]);
	}

	public static void lookAtVec3d(double x, double y, double z) {
		Vec3d vec3d = new Vec3d(x, y, z);
		lookAtVec3d(vec3d);
	}

	public static float[] getRotations(double posX, double posY, double posZ) {
		EntityPlayerSP player = Minecraft.player;
		double x = posX - player.posX;
		double y = posY - player.posY + player.getEyeHeight();
		double z = posZ - player.posZ;
		double dist = MathHelper.sqrt(x * x + z * z);
		float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
		float pitch = (float) -(Math.atan2(y, dist) * 180.0D / Math.PI);
		return new float[] { yaw, pitch };
	}

	public static float[] getRotationsEntity(EntityLivingBase entity) {
		return getRotations(entity.posX + MathUtils.randomNumber(0.03D, -0.03D), entity.posY + entity.getEyeHeight() - 0.4D + MathUtils.randomNumber(0.07D, -0.07D), entity.posZ + MathUtils.randomNumber(0.03D, -0.03D));
	}

	public static float[] getRotations(EntityLivingBase ent) {
		double x = ent.posX;
		double z = ent.posZ;
		double y = ent.posY + ent.getEyeHeight() / 2.0F;
		return getRotationFromPosition(x, z, y);
	}

	public static float[] getAngles(Entity e) {
		return new float[] { getYawChangeToEntity(e) + Minecraft.player.rotationYaw, getPitchChangeToEntity(e) + Minecraft.player.rotationPitch };
	}

	public static float getPitchChangeToEntity(Entity entity) {
		double deltaX = entity.posX - Minecraft.player.posX;
		double deltaZ = entity.posZ - Minecraft.player.posZ;
		double deltaY = entity.posY - 1.6D + entity.getEyeHeight() - 0.4D - Minecraft.player.posY;
		double distanceXZ = MathHelper.sqrt(deltaX * deltaX + deltaZ * deltaZ);
		double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
		return -MathHelper.wrapDegrees(Minecraft.player.rotationPitch - (float) pitchToEntity);
	}

	public static float getYawChangeToEntity(Entity entity) {
		double deltaX = entity.posX - Minecraft.player.posX;
		double deltaZ = entity.posZ - Minecraft.player.posZ;
		double yawToEntity;
		if ((deltaZ < 0.0D) && (deltaX < 0.0D)) {
			yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
		} else {
			if ((deltaZ < 0.0D) && (deltaX > 0.0D)) {
				yawToEntity = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
			} else
				yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
		}
		return MathHelper.wrapDegrees(-(Minecraft.player.rotationYaw - (float) yawToEntity));
	}

	public static float normalizeAngle(float angle) {
		return MathHelper.wrapDegrees((angle + 180.0F) % 360.0F - 180.0F);
	}

	public static boolean canEntityBeSeen(Entity e) {
		Vec3d vec1 = new Vec3d(Minecraft.player.posX, Minecraft.player.posY + Minecraft.player.getEyeHeight(), Minecraft.player.posZ);

		AxisAlignedBB box = e.getEntityBoundingBox();
		Vec3d vec2 = new Vec3d(e.posX, e.posY + (e.getEyeHeight() / 1.32F), e.posZ);
		double minx = e.posX - 0.25;
		double maxx = e.posX + 0.25;
		double miny = e.posY;
		double maxy = e.posY + Math.abs(e.posY - box.maxY);
		double minz = e.posZ - 0.25;
		double maxz = e.posZ + 0.25;
		boolean see = mc.world.rayTraceBlocks(vec1, vec2) == null;
		if (see)
			return true;
		vec2 = new Vec3d(maxx, miny, minz);
		see = mc.world.rayTraceBlocks(vec1, vec2) == null;
		if (see)
			return true;
		vec2 = new Vec3d(minx, miny, minz);
		see = mc.world.rayTraceBlocks(vec1, vec2) == null;

		if (see)
			return true;
		vec2 = new Vec3d(minx, miny, maxz);
		see = mc.world.rayTraceBlocks(vec1, vec2) == null;
		if (see)
			return true;
		vec2 = new Vec3d(maxx, miny, maxz);
		see = mc.world.rayTraceBlocks(vec1, vec2) == null;
		if (see)
			return true;

		vec2 = new Vec3d(maxx, maxy, minz);
		see = mc.world.rayTraceBlocks(vec1, vec2) == null;

		if (see)
			return true;
		vec2 = new Vec3d(minx, maxy, minz);

		see = mc.world.rayTraceBlocks(vec1, vec2) == null;
		if (see)
			return true;
		vec2 = new Vec3d(minx, maxy, maxz - 0.1);
		see = mc.world.rayTraceBlocks(vec1, vec2) == null;
		if (see)
			return true;
		vec2 = new Vec3d(maxx, maxy, maxz);
		see = mc.world.rayTraceBlocks(vec1, vec2) == null;
		return see;
	}

	public static float[] getNeededFacing(final Vec3d target, final Vec3d from) {
		final double diffX = target.xCoord - from.xCoord;
		final double diffY = target.yCoord - from.yCoord;
		final double diffZ = target.zCoord - from.zCoord;
		final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
		final float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
		final float pitch = (float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)));
		return new float[] { MathHelper.wrapDegrees(yaw), MathHelper.wrapDegrees(pitch) };
	}

	public static float[] getRotationFromPosition(double x, double z, double y) {
		double xDiff = x - Minecraft.player.posX;
		double zDiff = z - Minecraft.player.posZ;
		double yDiff = y - Minecraft.player.posY - 1.2;

		double dist = MathHelper.sqrt(xDiff * xDiff + zDiff * zDiff);
		float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
		float pitch = (float) -(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D);
		return new float[] { yaw, pitch };
	}

	public static Vec3d getEyesPos() {
		return new Vec3d(Minecraft.player.posX, Minecraft.player.posY + Minecraft.player.getEyeHeight(), Minecraft.player.posZ);
	}

	public final Vec3d getVectorForRotation(float pitch, float yaw) {
		float f = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
		float f1 = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
		float f2 = -MathHelper.cos(-pitch * 0.017453292F);
		float f3 = MathHelper.sin(-pitch * 0.017453292F);
		return new Vec3d((f1 * f2), f3, (f * f2));
	}
}