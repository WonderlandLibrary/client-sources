/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import us.amerikan.utils.Rotation;

public class RotationHandler {
    private static Minecraft mc = Minecraft.getMinecraft();
    public static float yaw = 0.0f;
    public static float pitch = 0.0f;
    public static float lastYaw = 0.0f;
    public static float lastPitch = 0.0f;
    public static boolean UseRotation;
    private static float range;
    private static boolean useRange;
    private static int currentItem;
    public static int lastItem;
    private static boolean useItem;
    private static double xPos;
    private static double yPos;
    private static double zPos;
    private static boolean onGround;
    public static double lastXPos;
    public static double lastYPos;
    public static double lastZPos;
    private static boolean usePos;

    static {
        range = 4.0f;
        currentItem = 0;
        lastItem = 0;
        xPos = 0.0;
        yPos = 0.0;
        zPos = 0.0;
        onGround = false;
        lastXPos = 0.0;
        lastYPos = 0.0;
        lastZPos = 0.0;
    }

    public static void onTick() {
        if (!UseRotation) {
            yaw = Minecraft.thePlayer.rotationYaw;
            pitch = Minecraft.thePlayer.rotationPitch;
        }
        range = 3.5f;
        useRange = false;
        currentItem = Minecraft.thePlayer.inventory.currentItem;
        useItem = false;
        xPos = Minecraft.thePlayer.posX;
        yPos = Minecraft.thePlayer.posY;
        zPos = Minecraft.thePlayer.posZ;
        onGround = Minecraft.thePlayer.onGround;
        usePos = false;
    }

    public static boolean server_yaw(float y2, float max) {
        RotationHandler.setRotation(yaw, pitch);
        if (max >= 360.0f) {
            yaw = y2;
            return true;
        }
        int getRotation = Rotation.getRotation(yaw, y2);
        float ran = (float)(Math.random() * 5.0 - 5.0);
        if (Rotation.isInRange(yaw, y2, max) || max >= 360.0f) {
            yaw = y2;
            return true;
        }
        yaw = Rotation.getRotation(yaw, y2) < 0 ? yaw - max + ran : yaw + max + ran;
        return false;
    }

    public static boolean server_pitch(float p2, float max) {
        if (p2 > 90.0f) {
            p2 = 90.0f;
        } else if (p2 < -90.0f) {
            p2 = -90.0f;
        }
        float ran = (float)(Math.random() * 5.0 - 5.0);
        if (Math.abs(pitch - p2) <= max || max >= 360.0f) {
            pitch = p2;
            return true;
        }
        pitch = p2 < pitch ? pitch - max + ran : pitch + max + ran;
        return false;
    }

    public static boolean canblock1() {
        return true;
    }

    public static void setRotation(float y2, float p2) {
        if (p2 > 90.0f) {
            p2 = 90.0f;
        } else if (p2 < -90.0f) {
            p2 = -90.0f;
        }
        yaw = y2;
        pitch = p2;
        UseRotation = true;
    }

    public static float getYaw() {
        if (UseRotation) {
            return yaw;
        }
        Minecraft.getMinecraft();
        return Minecraft.thePlayer.rotationYaw;
    }

    public static float[] getBlockRotations(BlockPos block) {
        EntitySnowball p_70625_1_ = new EntitySnowball(RotationHandler.mc.theWorld, block.getX(), block.getY(), block.getZ());
        double var4 = p_70625_1_.posX - Minecraft.thePlayer.posX;
        double var8 = p_70625_1_.posZ - Minecraft.thePlayer.posZ;
        double var6 = (p_70625_1_.getEntityBoundingBox().minY + p_70625_1_.getEntityBoundingBox().maxY) / 2.0 - Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight();
        double var141 = MathHelper.sqrt_double(var4 * var4 + var8 * var8);
        float var12 = (float)(Math.atan2(var8, var4) * 180.0 / 3.141592653589793) - 90.0f;
        float var13 = (float)(-(Math.atan2(var6, var141) * 180.0 / 3.141592653589793));
        return new float[]{var12, var13};
    }

    public static float[] faceTileEntity(TileEntityChest p_70625_1_, float p_70625_2_, float p_70625_3_) {
        Minecraft.getMinecraft();
        double var4 = (double)p_70625_1_.getPos().getX() - Minecraft.thePlayer.posX + 0.25;
        Minecraft.getMinecraft();
        double var8 = (double)p_70625_1_.getPos().getZ() - Minecraft.thePlayer.posZ + 0.25;
        TileEntityChest var14 = p_70625_1_;
        Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        double var6 = (double)var14.getPos().getY() + 0.5 - Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight() + 0.22;
        double var141 = MathHelper.sqrt_double(var4 * var4 + var8 * var8);
        float yaw = (float)(Math.atan2(var8, var4) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(-(Math.atan2(var6, var141) * 180.0 / 3.141592653589793));
        return new float[]{yaw, pitch};
    }

    public static float getYawChange(float yaw, double posX, double posZ) {
        Minecraft.getMinecraft();
        double deltaX = posX - Minecraft.thePlayer.posX;
        Minecraft.getMinecraft();
        double deltaZ = posZ - Minecraft.thePlayer.posZ;
        double yawToEntity = 0.0;
        if (deltaZ < 0.0 && deltaX < 0.0) {
            if (deltaX != 0.0) {
                yawToEntity = 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
            }
        } else if (deltaZ < 0.0 && deltaX > 0.0) {
            if (deltaX != 0.0) {
                yawToEntity = -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
            }
        } else if (deltaZ != 0.0) {
            yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }
        return MathHelper.wrapAngleTo180_float(-(yaw - (float)yawToEntity));
    }

    public static float getPitch() {
        if (UseRotation) {
            return pitch;
        }
        Minecraft.getMinecraft();
        return Minecraft.thePlayer.rotationPitch;
    }
}

