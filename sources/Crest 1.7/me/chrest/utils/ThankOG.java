// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.utils;

import net.minecraft.util.MathHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumFacing;

public class ThankOG
{
    public static float[] getBlockRotations(final int x, final int y, final int z, final EnumFacing facing) {
        final Minecraft mc = Minecraft.getMinecraft();
        final Entity temp = new EntitySnowball(mc.theWorld);
        temp.posX = x + 0.5;
        temp.posY = y + 0.5;
        temp.posZ = z + 0.5;
        return getAngles(temp);
    }
    
    public static float[] getAngles(final Entity e) {
        final Minecraft mc = Minecraft.getMinecraft();
        return new float[] { getYawChangeToEntity(e) + mc.thePlayer.rotationYaw, getPitchChangeToEntity(e) + mc.thePlayer.rotationPitch };
    }
    
    public static float[] getRotations(final Entity entity) {
        if (entity == null) {
            return null;
        }
        final double diffX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
        final double diffZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double diffY1;
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase elb = (EntityLivingBase)entity;
            diffY1 = elb.posY + (elb.getEyeHeight() - 0.4) - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        }
        else {
            diffY1 = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0 - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        }
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(diffY1, dist) * 180.0 / 3.141592653589793));
        return new float[] { yaw, pitch };
    }
    
    public static float getYawChangeToEntity(final Entity entity) {
        final Minecraft mc = Minecraft.getMinecraft();
        final double deltaX = entity.posX - mc.thePlayer.posX;
        final double deltaZ = entity.posZ - mc.thePlayer.posZ;
        double yawToEntity1 = 0.0;
        if (deltaZ < 0.0 && deltaX < 0.0) {
            yawToEntity1 = 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        else if (deltaZ < 0.0 && deltaX > 0.0) {
            final double yawToEntity2 = -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        else {
            Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }
        return MathHelper.wrapAngleTo180_float(-(mc.thePlayer.rotationYaw - (float)yawToEntity1));
    }
    
    public static float getPitchChangeToEntity(final Entity entity) {
        final Minecraft mc = Minecraft.getMinecraft();
        final double deltaX = entity.posX - mc.thePlayer.posX;
        final double deltaZ = entity.posZ - mc.thePlayer.posZ;
        final double deltaY = entity.posY - 1.6 + entity.getEyeHeight() - 0.4 - mc.thePlayer.posY;
        final double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
        final double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return -MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationPitch - (float)pitchToEntity);
    }
}
