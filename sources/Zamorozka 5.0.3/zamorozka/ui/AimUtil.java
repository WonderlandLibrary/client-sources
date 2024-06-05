package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class AimUtil implements MCUtil {
	public static float getYawToEntity(Entity entity) {
		double pX = Minecraft.player.posX;
		double pZ = Minecraft.player.posZ;
		double eX = entity.posX;
		double eZ = entity.posZ;
		double dX = pX - eX;
		double dZ = pZ - eZ;
		double yaw = Math.toDegrees(Math.atan2(dZ, dX)) + 90.0;
		return (float) yaw;
	}

	public static float getYawToEntity(Entity mainEntity, Entity targetEntity) {
		double pX = mainEntity.posX;
		double pZ = mainEntity.posZ;
		double eX = targetEntity.posX;
		double eZ = targetEntity.posZ;
		double dX = pX - eX;
		double dZ = pZ - eZ;
		double yaw = Math.toDegrees(Math.atan2(dZ, dX)) + 90.0;
		return (float) yaw;
	}

	public static float getNormalizedYaw(float yaw) {
		float yawStageFirst = yaw % 360;
		if (yawStageFirst > 180) {
			yawStageFirst -= 360;
			return yawStageFirst;
		}
		if (yawStageFirst < -180) {
			yawStageFirst += 360;
			return yawStageFirst;
		}
		return yawStageFirst;
	}

	public static boolean isAimAtMe(Entity entity) {
		float entityYaw = getNormalizedYaw(entity.rotationYaw);
		float entityPitch = entity.rotationPitch;
		double pMinX = Minecraft.player.getEntityBoundingBox().minX;
		double pMaxX = Minecraft.player.getEntityBoundingBox().maxX;
		double pMaxY = Minecraft.player.posY + (double) Minecraft.player.height;
		double pMinY = Minecraft.player.getEntityBoundingBox().minY;
		double pMaxZ = Minecraft.player.getEntityBoundingBox().maxZ;
		double pMinZ = Minecraft.player.getEntityBoundingBox().minZ;
		double eX = entity.posX;
		double eY = entity.posY + (double) (entity.height / 2.0f);
		double eZ = entity.posZ;
		double dMaxX = pMaxX - eX;
		double dMaxY = pMaxY - eY;
		double dMaxZ = pMaxZ - eZ;
		double dMinX = pMinX - eX;
		double dMinY = pMinY - eY;
		double dMinZ = pMinZ - eZ;
		double dMinH = Math.sqrt(Math.pow(dMinX, 2.0) + Math.pow(dMinZ, 2.0));
		double dMaxH = Math.sqrt(Math.pow(dMaxX, 2.0) + Math.pow(dMaxZ, 2.0));
		double maxPitch = 90 - Math.toDegrees(Math.atan2(dMaxH, dMaxY));
		double minPitch = 90 - Math.toDegrees(Math.atan2(dMinH, dMinY));
		boolean yawAt = Math.abs(getNormalizedYaw(getYawToEntity(entity, Minecraft.player)) - entityYaw) <= (16
				- Minecraft.player.getDistanceToEntity(entity) / 2);
		boolean pitchAt = (maxPitch >= entityPitch && entityPitch >= minPitch)
				|| (minPitch >= entityPitch && entityPitch >= maxPitch);
		return yawAt && pitchAt;
	}

	public static float getYawToPos(BlockPos blockPos) {
		int pX = Minecraft.player.getPosition().getX(), pZ = Minecraft.player.getPosition().getZ();
		double dX = pX - blockPos.getX();
		double dZ = pZ - blockPos.getZ();
		double yaw = Math.toDegrees(Math.atan2(dZ, dX)) + 90.0;
		return (float) yaw;
	}

	public static float[] getRotationByBoundingBox(Entity ent, boolean random) {
		if (ent == null) {
			return new float[] { 0, 0 };
		}
		AxisAlignedBB boundingBox = ent.getEntityBoundingBox();
		double boundingX = (boundingBox.maxX - boundingBox.minX) / 8;
		double boundingY = (boundingBox.maxY - boundingBox.minY) / 8;
		double boundingZ = (boundingBox.maxZ - boundingBox.minZ) / 8;
		double orPosX = (boundingBox.maxX - boundingBox.minX) / 2 + boundingBox.minX, orPosY = boundingBox.minY,
				orPosZ = (boundingBox.maxZ - boundingBox.minZ) / 2 + boundingBox.minZ;
		if (random) {
			orPosX += RandomizeUtil.randomDouble(boundingX, -boundingX);
			orPosY += RandomizeUtil.randomDouble(boundingY, -boundingY);
			orPosZ += RandomizeUtil.randomDouble(boundingZ, -boundingZ);
		}
		double pX = Minecraft.player.posX;
		double pZ = Minecraft.player.posZ;
		double dX = pX - orPosX;
		double dZ = pZ - orPosZ;
		double yaw = Math.toDegrees(Math.atan2(dZ, dX)) + 90.0;
		AxisAlignedBB entityBoundingBox = ent.getEntityBoundingBox();
		LocationUtil BestPos = new LocationUtil(entityBoundingBox.minX, orPosY, entityBoundingBox.minZ);
		LocationUtil myEyePos = new LocationUtil(Minecraft.player.getEntityBoundingBox().minX,
				Minecraft.player.getEntityBoundingBox().minY + (double) Minecraft.player.getEyeHeight(),
				Minecraft.player.getEntityBoundingBox().minZ);
		double diffY;
		for (diffY = ent.getEntityBoundingBox().minY + 0.7D; diffY < ent.getEntityBoundingBox().maxY
				- 0.1D; diffY += 0.1D) {
			if (myEyePos.distanceTo(new LocationUtil(entityBoundingBox.minX, diffY, entityBoundingBox.minZ)) < myEyePos
					.distanceTo(BestPos)) {
				BestPos = new LocationUtil(entityBoundingBox.minX, diffY, entityBoundingBox.minZ);
			}
		}
		diffY = BestPos.getY()
				- (Minecraft.player.getEntityBoundingBox().minY + (double) Minecraft.player.getEyeHeight());
		double dist = MathHelper.sqrt_double(dX * dX + dZ * dZ);
		float pitch = (float) (-(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D));
		float random360 = (int) (Math.random() * 10 - 5) * 360f;
		return new float[] { (float) yaw + random360, pitch };
	}
}