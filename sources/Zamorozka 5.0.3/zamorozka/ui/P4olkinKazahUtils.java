package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;

public class P4olkinKazahUtils {
	
	public static float[] getRotationsToward(final Entity closestEntity) {
		double xDist = closestEntity.posX - Minecraft.player.posX;
		double yDist = closestEntity.posY + closestEntity.getEyeHeight()- (Minecraft.player.posY + Minecraft.player.getEyeHeight() + 0.5);
		double zDist = closestEntity.posZ - Minecraft.player.posZ;
		double fDist = MathHelper.sqrt(xDist * xDist + zDist * zDist);
		float yaw = fixRotation(Minecraft.player.rotationYaw,(float) (MathHelper.atan2(zDist, xDist) * 180.0D / Math.PI) - 90.0F);
		float pitch = fixRotation(Minecraft.player.rotationPitch,(float) (-(MathHelper.atan2(yDist, fDist) * 180.0D / Math.PI)));
		return new float[] { yaw, pitch };
	}

	private static float fixRotation(final float p_70663_1_, final float p_70663_2_) {
		float var4 = MathHelper.wrapDegrees(p_70663_2_ - p_70663_1_);
		if (var4 > (float) 360.0) {
			var4 = (float) 360.0;
		}
		if (var4 < -(float) 360.0) {
			var4 = -(float) 360.0;
		}
		return p_70663_1_ + var4;
	}
	public static float[] getRotationsNeeded(Entity entity) {
		if (entity == null) {
			return null;
		}

		AxisAlignedBB check = new AxisAlignedBB(entity.posX,entity.posY, entity.posZ, entity.posX , entity.posY, entity.posZ);
		final double diffX = entity.posX - Minecraft.getMinecraft().player.posX;
		final double diffZ = entity.posZ - Minecraft.getMinecraft().player.posZ;
		double diffY;

		if (entity instanceof EntityLivingBase) {
			final EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
			diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (Minecraft.getMinecraft().player.posY + Minecraft.getMinecraft().player.getEyeHeight()+1);
		} else {
			diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D - (Minecraft.getMinecraft().player.posY + Minecraft.getMinecraft().player.getEyeHeight() +1);
		}

		final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
	    float yaw = (float)(Math.atan2(diffZ, diffX) * 180 / 3.141592653589793D) - 90.0F;
	    float pitch = (float)-(Math.atan2(diffY, dist) * 180 / 3.141592653589793D);
		return new float[] { Minecraft.getMinecraft().player.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().player.rotationYaw), Minecraft.getMinecraft().player.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().player.rotationPitch) };
	
	}
}
