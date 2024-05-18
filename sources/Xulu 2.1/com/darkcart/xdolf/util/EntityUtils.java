package com.darkcart.xdolf.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class EntityUtils {
	public static boolean lookChanged;
	public static float yaw;
	public static float pitch;
	private static final List<Entity> loadedEntities = Minecraft.getMinecraft().world.loadedEntityList;

	public static synchronized boolean faceEntityClient(Entity entity) {
		float[] rotations = getRotationsNeeded(entity);
		if (rotations != null) {
			EntityPlayerSP player = Minecraft.getMinecraft().player;
			player.rotationYaw = limitAngleChange(player.prevRotationYaw, rotations[0], 55.0F);
			player.rotationPitch = rotations[1];
			return player.rotationYaw == rotations[0];
		}
		return true;
	}

	public static synchronized boolean faceEntityPacket(Entity entity) {
		float[] rotations = getRotationsNeeded(entity);
		if (rotations != null) {
			yaw = limitAngleChange(yaw, rotations[0], 30.0F);
			pitch = rotations[1];
			return yaw == rotations[0];
		}
		return true;
	}

	public static float[] getRotationsNeeded(Entity entity) {
		if (entity == null) {
			return null;
		}
		double diffX = entity.posX - Minecraft.getMinecraft().player.posX;
		double diffY = entity.posY - Minecraft.getMinecraft().player.posY;

		if ((entity instanceof EntityLivingBase)) {
			EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
			diffY = entityLivingBase.posY + entityLivingBase.getEyeHeight() * 0.9D
					- (Minecraft.getMinecraft().player.posY + Minecraft.getMinecraft().player.getEyeHeight());
		} else {
			diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D
					- (Minecraft.getMinecraft().player.posY + Minecraft.getMinecraft().player.getEyeHeight());
		}
		double diffZ = entity.posZ - Minecraft.getMinecraft().player.posZ;
		double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
		float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
		float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D);
		return new float[] {
				Minecraft.getMinecraft().player.rotationYaw
						+ MathHelper.wrapDegrees(yaw - Minecraft.getMinecraft().player.rotationYaw),
				Minecraft.getMinecraft().player.rotationPitch
						+ MathHelper.wrapDegrees(pitch - Minecraft.getMinecraft().player.rotationPitch) };
	}

	public static final float limitAngleChange(float current, float intended, float maxChange) {
		float change = intended - current;
		if (change > maxChange) {
			change = maxChange;
		} else if (change < -maxChange) {
			change = -maxChange;
		}
		return current + change;
	}


}
