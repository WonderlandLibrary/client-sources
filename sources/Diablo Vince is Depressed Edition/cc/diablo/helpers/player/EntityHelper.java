/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.helpers.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class EntityHelper {
    private static final boolean set = false;
    private static EntityPlayer reference;
    private static final Minecraft mc;

    public static void setMotion(double speed) {
        float yaw = EntityHelper.mc.thePlayer.rotationYaw;
        double forward = EntityHelper.mc.thePlayer.moveForward;
        double strafe = EntityHelper.mc.thePlayer.moveStrafing;
        if (forward == 0.0 && strafe == 0.0) {
            EntityHelper.mc.thePlayer.motionX = 0.0;
            EntityHelper.mc.thePlayer.motionZ = 0.0;
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float)(forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (float)(forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            EntityHelper.mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f));
            EntityHelper.mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f));
        }
    }

    public static void setMotionStrafe(double speed, float yaw, double strafe, double forward) {
        if (forward == 0.0 && strafe == 0.0) {
            EntityHelper.mc.thePlayer.motionX = 0.0;
            EntityHelper.mc.thePlayer.motionZ = 0.0;
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float)(forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (float)(forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            EntityHelper.mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f));
            EntityHelper.mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f));
        }
    }

    public static int getHypixelNetworkPing() {
        NetworkPlayerInfo networkPlayerInfo = Minecraft.getMinecraft().getNetHandler().getPlayerInfo(Minecraft.getMinecraft().thePlayer.getUniqueID());
        return networkPlayerInfo == null ? 0 : networkPlayerInfo.getResponseTime();
    }

    public static float getEntityHealthPercent(EntityPlayer entity) {
        float var28 = entity.getHealth() + entity.getAbsorptionAmount();
        float var32 = entity.getMaxHealth() + entity.getAbsorptionAmount() - 0.05f;
        float var37 = 35 + EntityHelper.mc.fontRendererObj.getStringWidth(entity.getName() + 40);
        float var42 = (float)((double)Math.round((double)var28 * 100.0) / 100.0);
        float var46 = 100.0f / var32;
        float var48 = var42 * var46;
        return Float.valueOf(var48).floatValue();
    }

    public static float[] getAngles(Entity e) {
        return new float[]{EntityHelper.getYawChangeToEntity(e) + EntityHelper.mc.thePlayer.rotationYaw, EntityHelper.getPitchChangeToEntity(e) + EntityHelper.mc.thePlayer.rotationPitch};
    }

    public static float[] getAnglesAGC(Entity e) {
        EntityPlayerSP playerSP = EntityHelper.mc.thePlayer;
        double differenceX = e.posX - playerSP.posX;
        double differenceY = e.posY + (double)e.height - (playerSP.posY + (double)playerSP.height);
        double differenceZ = e.posZ - playerSP.posZ;
        float rotationYaw = (float)(Math.atan2(differenceZ, differenceX) * 180.0 / Math.PI) - 90.0f;
        float rotationPitch = (float)(Math.atan2(differenceY, playerSP.getDistanceToEntity(e)) * 180.0 / Math.PI);
        float finishedYaw = playerSP.rotationYaw + MathHelper.wrapAngleTo180_float(rotationYaw - playerSP.rotationYaw);
        float finishedPitch = playerSP.rotationPitch + MathHelper.wrapAngleTo180_float(rotationPitch - playerSP.rotationPitch);
        return new float[]{finishedYaw, -finishedPitch};
    }

    public static float getYawChangeToEntity(Entity entity) {
        double deltaX = entity.posX - EntityHelper.mc.thePlayer.posX;
        double deltaZ = entity.posZ - EntityHelper.mc.thePlayer.posZ;
        double yawToEntity = deltaZ < 0.0 && deltaX < 0.0 ? 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)) : (deltaZ < 0.0 && deltaX > 0.0 ? -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)) : Math.toDegrees(-Math.atan(deltaX / deltaZ)));
        return Double.isNaN((double)EntityHelper.mc.thePlayer.rotationYaw - yawToEntity) ? 0.0f : MathHelper.wrapAngleTo180_float(-(EntityHelper.mc.thePlayer.rotationYaw - (float)yawToEntity));
    }

    public static float getPitchChangeToEntity(Entity entity) {
        double distanceXZ;
        double deltaX = entity.posX - EntityHelper.mc.thePlayer.posX;
        double deltaZ = entity.posZ - EntityHelper.mc.thePlayer.posZ;
        double deltaY = entity.posY - 1.6 + (double)entity.getEyeHeight() - EntityHelper.mc.thePlayer.posY;
        double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / (distanceXZ = (double)MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ))));
        return Double.isNaN((double)EntityHelper.mc.thePlayer.rotationPitch - pitchToEntity) ? 0.0f : -MathHelper.wrapAngleTo180_float(EntityHelper.mc.thePlayer.rotationPitch - (float)pitchToEntity);
    }

    static {
        mc = Minecraft.getMinecraft();
    }
}

