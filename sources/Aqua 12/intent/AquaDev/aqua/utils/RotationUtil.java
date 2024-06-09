// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.utils;

import net.minecraft.entity.Entity;
import intent.AquaDev.aqua.Aqua;
import net.minecraft.util.MathHelper;
import java.util.Random;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.Minecraft;

public class RotationUtil
{
    public static float yaw;
    public static float renderYawOffset;
    public static float pitch;
    public static boolean RotationInUse;
    static Minecraft mc;
    
    public static float[] Intavee(final EntityPlayerSP player, final EntityLivingBase target) {
        final float yawRandom = (float)MathHelper.getRandomDoubleInRange(new Random(), -0.2, 0.2);
        final float pitchRandom = (float)MathHelper.getRandomDoubleInRange(new Random(), -0.02, 0.02);
        final double posX = Aqua.setmgr.getSetting("KillauraRandom").isState() ? (target.posX - player.posX + yawRandom) : (target.posX - player.posX);
        final double posY = Aqua.setmgr.getSetting("KillauraClampPitch").isState() ? (MathHelper.clamp_double(player.posY + player.getEyeHeight(), target.getEntityBoundingBox().minY, target.getEntityBoundingBox().maxY) - (player.posY + player.getEyeHeight())) : (Aqua.setmgr.getSetting("KillauraRandom").isState() ? (target.posY + target.getEyeHeight() - (player.posY + player.getEyeHeight() - pitchRandom)) : (target.posY + target.getEyeHeight() - (player.posY + player.getEyeHeight())));
        final double posZ = Aqua.setmgr.getSetting("KillauraRandom").isState() ? (target.posZ - player.posZ - yawRandom) : (target.posZ - player.posZ);
        final double var14 = MathHelper.sqrt_double(posX * posX + posZ * posZ);
        float yaw = (float)(Math.atan2(posZ, posX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(-(Math.atan2(posY, var14) * 180.0 / 3.141592653589793));
        if (Aqua.setmgr.getSetting("KillauraMouseSensiFix").isState()) {
            final float f2 = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6f + 0.2f;
            final float f3 = f2 * f2 * f2 * 1.2f;
            yaw -= yaw % f3;
            pitch -= pitch % (f3 * f2);
        }
        return new float[] { yaw, pitch };
    }
    
    public static float[] lookAtPosBed(final double x, final double y, final double z) {
        double dirx = RotationUtil.mc.thePlayer.posX - x;
        double diry = RotationUtil.mc.thePlayer.posY - y;
        double dirz = RotationUtil.mc.thePlayer.posZ - z;
        final double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
        dirx /= len;
        diry /= len;
        dirz /= len;
        float yaw = (float)Math.atan2(dirz, dirx);
        float pitch = (float)((float)Math.asin(diry) + 0.3);
        pitch = (float)(pitch * 180.0 / 3.141592653589793);
        yaw = (float)(yaw * 180.0 / 3.141592653589793);
        yaw += 90.0;
        final float f2 = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6f + 0.2f;
        final float f3 = f2 * f2 * f2 * 1.2f;
        yaw -= yaw % f3;
        pitch -= pitch % (f3 * f2);
        return new float[] { yaw, pitch };
    }
    
    public static void setRotation(final float y, float p) {
        if (p > 90.0f) {
            p = 90.0f;
        }
        else if (p < -90.0f) {
            p = -90.0f;
        }
        RotationUtil.yaw = y;
        RotationUtil.pitch = p;
        RotationUtil.RotationInUse = true;
    }
    
    public static boolean setYaw(final float y, final float speed) {
        setRotation(RotationUtil.yaw, RotationUtil.pitch);
        if (speed >= 360.0f) {
            RotationUtil.yaw = y;
            return true;
        }
        if (isInRange(RotationUtil.yaw, y, speed) || speed >= 360.0f) {
            RotationUtil.yaw = y;
            return true;
        }
        if (getRotation(RotationUtil.yaw, y) < 0) {
            RotationUtil.yaw -= speed;
        }
        else {
            RotationUtil.yaw += speed;
        }
        return false;
    }
    
    public static int getRotation(double before, float after) {
        while (before > 360.0) {
            before -= 360.0;
        }
        while (before < 0.0) {
            before += 360.0;
        }
        while (after > 360.0f) {
            after -= 360.0f;
        }
        while (after < 0.0f) {
            after += 360.0f;
        }
        if (before > after) {
            if (before - after > 180.0) {
                return 1;
            }
            return -1;
        }
        else {
            if (after - before > 180.0) {
                return -1;
            }
            return 1;
        }
    }
    
    public static boolean isInRange(double before, float after, final float max) {
        while (before > 360.0) {
            before -= 360.0;
        }
        while (before < 0.0) {
            before += 360.0;
        }
        while (after > 360.0f) {
            after -= 360.0f;
        }
        while (after < 0.0f) {
            after += 360.0f;
        }
        if (before > after) {
            return (before - after > 180.0 && 360.0 - before - after <= max) || before - after <= max;
        }
        return (after - before > 180.0 && 360.0f - after - before <= max) || after - before <= max;
    }
    
    public static boolean setPitch(float p, final float speed) {
        if (p > 90.0f) {
            p = 90.0f;
        }
        else if (p < -90.0f) {
            p = -90.0f;
        }
        if (Math.abs(RotationUtil.pitch - p) <= speed || speed >= 360.0f) {
            RotationUtil.pitch = p;
            return false;
        }
        if (p < RotationUtil.pitch) {
            RotationUtil.pitch -= speed;
        }
        else {
            RotationUtil.pitch += speed;
        }
        return true;
    }
    
    public static float calculateCorrectYawOffset(final float yaw) {
        final double xDiff = RotationUtil.mc.thePlayer.posX - RotationUtil.mc.thePlayer.prevPosX;
        final double zDiff = RotationUtil.mc.thePlayer.posZ - RotationUtil.mc.thePlayer.prevPosZ;
        final float dist = (float)(xDiff * xDiff + zDiff * zDiff);
        float offset;
        float renderYawOffset = offset = RotationUtil.mc.thePlayer.renderYawOffset;
        if (dist > 0.0025000002f) {
            offset = (float)MathHelper.func_181159_b(zDiff, xDiff) * 180.0f / 3.1415927f - 90.0f;
        }
        if (RotationUtil.mc.thePlayer != null && RotationUtil.mc.thePlayer.swingProgress > 0.0f) {
            offset = yaw;
        }
        final float offsetDiff = MathHelper.wrapAngleTo180_float(offset - renderYawOffset);
        renderYawOffset += offsetDiff * 0.3f;
        float yawOffsetDiff = MathHelper.wrapAngleTo180_float(yaw - renderYawOffset);
        if (yawOffsetDiff < -75.0f) {
            yawOffsetDiff = -75.0f;
        }
        if (yawOffsetDiff >= 75.0f) {
            yawOffsetDiff = 75.0f;
        }
        renderYawOffset = yaw - yawOffsetDiff;
        if (yawOffsetDiff * yawOffsetDiff > 2500.0f) {
            renderYawOffset += yawOffsetDiff * 0.2f;
        }
        return renderYawOffset;
    }
    
    public static float getAngle(final Entity entity) {
        final double x = RenderUtil.interpolate(entity.posX, entity.lastTickPosX, 1.0) - RenderUtil.interpolate(RotationUtil.mc.thePlayer.posX, RotationUtil.mc.thePlayer.lastTickPosX, 1.0);
        final double z = RenderUtil.interpolate(entity.posZ, entity.lastTickPosZ, 1.0) - RenderUtil.interpolate(RotationUtil.mc.thePlayer.posZ, RotationUtil.mc.thePlayer.lastTickPosZ, 1.0);
        final float yaw = (float)(-Math.toDegrees(Math.atan2(x, z)));
        return (float)(yaw - RenderUtil.interpolate(RotationUtil.mc.thePlayer.rotationYaw, RotationUtil.mc.thePlayer.prevRotationYaw, 1.0));
    }
    
    static {
        RotationUtil.mc = Minecraft.getMinecraft();
    }
}
