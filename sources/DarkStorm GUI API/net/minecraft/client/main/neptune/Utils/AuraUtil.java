package net.minecraft.client.main.neptune.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.main.neptune.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.MathHelper;

public class AuraUtil {
    public static float[] getBlockRotations(final int x, final int y, final int z) {
        final Minecraft mc = Wrapper.getMinecraft();
        final Entity temp = new EntitySnowball(mc.theWorld);
        temp.posX = x + 0.5;
        temp.posY = y + 0.5;
        temp.posZ = z + 0.5;
        return getAngles(temp);
    }
    
    public static float[] getAngles(final Entity e) {
        final Minecraft mc = Wrapper.getMinecraft();
        return new float[] { getYawChangeToEntity(e) + mc.thePlayer.rotationYaw, getPitchChangeToEntity(e) + mc.thePlayer.rotationPitch };
    }
    
    public static float[] getRotations(final Location location) {
        final double diffX = location.getX() + 0.5 - Wrapper.getPlayer().posX;
        final double diffZ = location.getZ() + 0.5 - Wrapper.getPlayer().posZ;
        final double diffY = (location.getY() + 0.5) / 2.0 - (Wrapper.getPlayer().posY + Wrapper.getPlayer().getEyeHeight());
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
        return new float[] { yaw, pitch };
    }
    
    public static float[] getRotations(final Entity entity) {
        if (entity == null) {
            return null;
        }
        final double diffX = entity.posX - Wrapper.getPlayer().posX;
        final double diffZ = entity.posZ - Wrapper.getPlayer().posZ;
        double diffY;
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase elb = (EntityLivingBase)entity;
            diffY = elb.posY + (elb.getEyeHeight() - 0.4) - (Wrapper.getPlayer().posY + Wrapper.getPlayer().getEyeHeight());
        }
        else {
            diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0 - (Wrapper.getPlayer().posY + Wrapper.getPlayer().getEyeHeight());
        }
        final double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
        return new float[] { yaw, pitch };
    }
    
    public static float getYawChangeToEntity(final Entity entity) {
        final Minecraft mc = Wrapper.getMinecraft();
        final double deltaX = entity.posX - mc.thePlayer.posX;
        final double deltaZ = entity.posZ - mc.thePlayer.posZ;
        double yawToEntity;
        if (deltaZ < 0.0 && deltaX < 0.0) {
            yawToEntity = 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        else if (deltaZ < 0.0 && deltaX > 0.0) {
            yawToEntity = -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
        }
        else {
            yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }
        return MathHelper.wrapAngleTo180_float(-(mc.thePlayer.rotationYaw - (float)yawToEntity));
    }
    
    public static float getPitchChangeToEntity(final Entity entity) {
        final Minecraft mc = Wrapper.getMinecraft();
        final double deltaX = entity.posX - mc.thePlayer.posX;
        final double deltaZ = entity.posZ - mc.thePlayer.posZ;
        final double deltaY = entity.posY - 1.6 + entity.getEyeHeight() - 0.4 - mc.thePlayer.posY;
        final double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
        final double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return -MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationPitch - (float)pitchToEntity);
    }
}
