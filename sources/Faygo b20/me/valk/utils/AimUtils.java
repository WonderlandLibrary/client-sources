package me.valk.utils;

import java.util.Random;

import me.valk.help.world.Location;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class AimUtils {

	public static float[] getBlockRotations(int x, int y, int z) {
		Minecraft mc = Wrapper.getMinecraft();
		Entity temp = new EntitySnowball(mc.theWorld);
		temp.posX = x + 0.5;
		temp.posY = y + 0.5;
		temp.posZ = z + 0.5;
		return getAngles(temp);
	}

	public static float[] getAngles(Entity e) {
		Minecraft mc = Wrapper.getMinecraft();
		return new float[] { getYawChangeToEntity(e) + mc.thePlayer.rotationYaw,
				getPitchChangeToEntity(e) + mc.thePlayer.rotationPitch };
	}

	public static float[] getRotations(Location location) {
		double diffX = location.getX() + 0.5 - Wrapper.getPlayer().posX;
		double diffZ = location.getZ() + 0.5 - Wrapper.getPlayer().posZ;
		double diffY = (location.getY() + 0.5) / 2.0D - (Wrapper.getPlayer().posY + Wrapper.getPlayer().getEyeHeight());

		double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
		float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
		float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D);

		return new float[] { yaw, pitch };
	}

	 public static double getDistanceBetweenAngles(final float angle1, final float angle2) {
	        float distance = Math.abs(angle1 - angle2) % 360.0f;
	        if (distance > 180.0f) {
	            distance = 360.0f - distance;
	        }
	        return distance;
	    }
	public static float[] getRotations(Entity entity) {
		if (entity == null)
			return null;

		double diffX = entity.posX - Wrapper.getPlayer().posX;
		double diffZ = entity.posZ - Wrapper.getPlayer().posZ;
		double diffY;
		if ((entity instanceof EntityLivingBase)) {
			EntityLivingBase elb = (EntityLivingBase) entity;
			diffY = elb.posY + (elb.getEyeHeight() - 0.4)
					- (Wrapper.getPlayer().posY + Wrapper.getPlayer().getEyeHeight());
		} else {
			diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D
					- (Wrapper.getPlayer().posY + Wrapper.getPlayer().getEyeHeight());
		}
		double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
		float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
		float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D);

		return new float[] { yaw, pitch };
	}

	public static float getYawChangeToEntity(Entity entity) {
		Minecraft mc = Wrapper.getMinecraft();
		double deltaX = entity.posX - mc.thePlayer.posX;
		double deltaZ = entity.posZ - mc.thePlayer.posZ;
		double yawToEntity;
		if ((deltaZ < 0.0D) && (deltaX < 0.0D)) {
			yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
		} else {
			if ((deltaZ < 0.0D) && (deltaX > 0.0D)) {
				yawToEntity = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
			} else {
				yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
			}
		}

		return MathHelper.wrapAngleTo180_float(-(mc.thePlayer.rotationYaw - (float) yawToEntity));
	}

	public static float getPitchChangeToEntity(Entity entity) {
		Minecraft mc = Wrapper.getMinecraft();
		double deltaX = entity.posX - mc.thePlayer.posX;
		double deltaZ = entity.posZ - mc.thePlayer.posZ;
		double deltaY = entity.posY - 1.6D + entity.getEyeHeight() - 0.4 - mc.thePlayer.posY;
		double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);

		double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));

		return -MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationPitch - (float) pitchToEntity);
	}

	public static float[] getBlockRotations(int x, int y, int z, EnumFacing facing) {
		Minecraft mc = Wrapper.getMinecraft();
		Entity temp = new EntitySnowball(mc.theWorld);
		temp.posX = (x + 0.5D);
		temp.posY = (y + 0.5D);
		temp.posZ = (z + 0.5D);
		return getAngles(temp);
	}

	public static void jitterEffect(final Random rand) {
		if (rand.nextBoolean()) {
			if (rand.nextBoolean()) {
				EntityPlayerSP thePlayer = Wrapper.getPlayer();
				thePlayer.rotationPitch -= (float) (rand.nextFloat() * 0.8);
			} else {
				EntityPlayerSP thePlayer2 = Wrapper.getPlayer();
				thePlayer2.rotationPitch += (float) (rand.nextFloat() * 0.8);
			}
		} else if (rand.nextBoolean()) {
			EntityPlayerSP thePlayer3 = Wrapper.getPlayer();
			thePlayer3.rotationYaw -= (float) (rand.nextFloat() * 0.8);
		} else {
			EntityPlayerSP thePlayer4 = Wrapper.getPlayer();
			thePlayer4.rotationYaw += (float) (rand.nextFloat() * 0.8);
		}
	}
}
