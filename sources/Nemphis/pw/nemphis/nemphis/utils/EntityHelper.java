/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class EntityHelper {
    private static Minecraft mc = Minecraft.getMinecraft();
    public static boolean aacdamage;
    public static double aacdamagevalue;

    public static void damage(double value, boolean aac) {
        if (aac) {
            if (!EntityHelper.mc.thePlayer.onGround) {
                return;
            }
            aacdamage = true;
            aacdamagevalue = value + 2.85;
            EntityHelper.mc.thePlayer.motionY = -100.0;
            EntityHelper.mc.thePlayer.jump();
        } else {
            int i = 0;
            while (i < 30) {
                EntityHelper.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(EntityHelper.mc.thePlayer.posX, EntityHelper.mc.thePlayer.posY + 0.06, EntityHelper.mc.thePlayer.posZ, false));
                EntityHelper.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(EntityHelper.mc.thePlayer.posX, EntityHelper.mc.thePlayer.posY, EntityHelper.mc.thePlayer.posZ, false));
                ++i;
            }
            EntityHelper.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(EntityHelper.mc.thePlayer.posX, EntityHelper.mc.thePlayer.posY + 1.0, EntityHelper.mc.thePlayer.posZ, false));
            EntityHelper.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(EntityHelper.mc.thePlayer.posX, EntityHelper.mc.thePlayer.posY + 1.0, EntityHelper.mc.thePlayer.posZ, false));
        }
    }

    public static float getYawChangeToEntity(Entity entity) {
        double deltaX = entity.posX - EntityHelper.mc.thePlayer.posX;
        double deltaZ = entity.posZ - EntityHelper.mc.thePlayer.posZ;
        double yawToEntity = deltaZ < 0.0 && deltaX < 0.0 ? 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)) : (deltaZ < 0.0 && deltaX > 0.0 ? -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)) : Math.toDegrees(- Math.atan(deltaX / deltaZ)));
        return MathHelper.wrapAngleTo180_float(- EntityHelper.mc.thePlayer.rotationYaw - (float)yawToEntity);
    }

    public static float getPitchChangeToEntity(Entity entity) {
        double deltaX = entity.posX - EntityHelper.mc.thePlayer.posX;
        double deltaZ = entity.posZ - EntityHelper.mc.thePlayer.posZ;
        double deltaY = entity.posY - 1.6 + (double)entity.getEyeHeight() - EntityHelper.mc.thePlayer.posY;
        double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
        double pitchToEntity = - Math.toDegrees(Math.atan(deltaY / distanceXZ));
        return - MathHelper.wrapAngleTo180_float(EntityHelper.mc.thePlayer.rotationPitch - (float)pitchToEntity);
    }

    public static float[] getAngles(Entity e) {
        return new float[]{EntityHelper.getYawChangeToEntity(e) + EntityHelper.mc.thePlayer.rotationYaw, EntityHelper.getPitchChangeToEntity(e) + EntityHelper.mc.thePlayer.rotationPitch};
    }

    public static double getDirectionCheckVal(Entity e, Vec3 lookVec) {
        return EntityHelper.directionCheck(EntityHelper.mc.thePlayer.posX, EntityHelper.mc.thePlayer.posY + (double)EntityHelper.mc.thePlayer.getEyeHeight(), EntityHelper.mc.thePlayer.posZ, lookVec.xCoord, lookVec.yCoord, lookVec.zCoord, e.posX, e.posY + (double)e.height / 2.0, e.posZ, e.width, e.height, 5.0);
    }

    private static double directionCheck(double sourceX, double sourceY, double sourceZ, double dirX, double dirY, double dirZ, double targetX, double targetY, double targetZ, double targetWidth, double targetHeight, double precision) {
        double dirLength = Math.sqrt(dirX * dirX + dirY * dirY + dirZ * dirZ);
        if (dirLength == 0.0) {
            dirLength = 1.0;
        }
        double dX = targetX - sourceX;
        double dY = targetY - sourceY;
        double dZ = targetZ - sourceZ;
        double targetDist = Math.sqrt(dX * dX + dY * dY + dZ * dZ);
        double xPrediction = targetDist * dirX / dirLength;
        double yPrediction = targetDist * dirY / dirLength;
        double zPrediction = targetDist * dirZ / dirLength;
        double off = 0.0;
        off += Math.max(Math.abs(dX - xPrediction) - (targetWidth / 2.0 + precision), 0.0);
        off += Math.max(Math.abs(dZ - zPrediction) - (targetWidth / 2.0 + precision), 0.0);
        if ((off += Math.max(Math.abs(dY - yPrediction) - (targetHeight / 2.0 + precision), 0.0)) > 1.0) {
            off = Math.sqrt(off);
        }
        return off;
    }

    public static Float[] getPlayerColor(EntityPlayer e) {
        float red = 0.0f;
        float green = 0.0f;
        float blue = 0.0f;
        if (e.getDisplayName().getFormattedText().startsWith("\u00a71")) {
            red = 0.20392157f;
            green = 0.59607846f;
            blue = 0.85882354f;
        }
        if (e.getDisplayName().getFormattedText().startsWith("\u00a72")) {
            red = 0.05490196f;
            green = 0.6745098f;
            blue = 0.31764707f;
        }
        if (e.getDisplayName().getFormattedText().startsWith("\u00a73")) {
            red = 0.02745098f;
            green = 0.67058825f;
            blue = 0.627451f;
        }
        if (e.getDisplayName().getFormattedText().startsWith("\u00a74")) {
            red = 0.78039217f;
            green = 0.17254902f;
            blue = 0.32156864f;
        }
        if (e.getDisplayName().getFormattedText().startsWith("\u00a75")) {
            red = 0.49411765f;
            green = 0.20392157f;
            blue = 0.6156863f;
        }
        if (e.getDisplayName().getFormattedText().startsWith("\u00a76")) {
            red = 0.94509804f;
            green = 0.5372549f;
            blue = 0.1764706f;
        }
        if (e.getDisplayName().getFormattedText().startsWith("\u00a77")) {
            red = 0.20392157f;
            green = 0.20392157f;
            blue = 0.20392157f;
        }
        if (e.getDisplayName().getFormattedText().startsWith("\u00a78")) {
            red = 0.09019608f;
            green = 0.09019608f;
            blue = 0.09019608f;
        }
        if (e.getDisplayName().getFormattedText().startsWith("\u00a70")) {
            red = 0.0f;
            green = 0.0f;
            blue = 0.0f;
        }
        if (e.getDisplayName().getFormattedText().startsWith("\u00a79")) {
            red = 0.0f;
            green = 0.40392157f;
            blue = 0.41568628f;
        }
        if (e.getDisplayName().getFormattedText().startsWith("\u00a7a")) {
            red = 0.24313726f;
            green = 0.8627451f;
            blue = 0.5058824f;
        }
        if (e.getDisplayName().getFormattedText().startsWith("\u00a7b")) {
            red = 0.36862746f;
            green = 0.98039216f;
            blue = 0.96862745f;
        }
        if (e.getDisplayName().getFormattedText().startsWith("\u00a7c")) {
            red = 0.96862745f;
            green = 0.36078432f;
            blue = 0.29803923f;
        }
        if (e.getDisplayName().getFormattedText().startsWith("\u00a7d")) {
            red = 0.60784316f;
            green = 0.34901962f;
            blue = 0.7137255f;
        }
        if (e.getDisplayName().getFormattedText().startsWith("\u00a7e")) {
            red = 0.94509804f;
            green = 0.76862746f;
            blue = 0.05882353f;
        }
        if (e.getDisplayName().getFormattedText().startsWith("\u00a7f")) {
            red = 0.16078432f;
            green = 0.77254903f;
            blue = 1.0f;
        }
        return new Float[]{Float.valueOf(red), Float.valueOf(green), Float.valueOf(blue)};
    }
}

