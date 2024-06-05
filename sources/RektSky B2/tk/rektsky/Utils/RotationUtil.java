package tk.rektsky.Utils;

import net.minecraft.client.*;
import net.minecraft.util.*;

public class RotationUtil
{
    private static float yaw1;
    private static float pitch1;
    private static Minecraft mc;
    public static boolean doReset;
    
    public static float getPitch() {
        return RotationUtil.pitch1;
    }
    
    public static float getYaw() {
        return RotationUtil.yaw1;
    }
    
    public static void reset() {
        if (Minecraft.getMinecraft().thePlayer == null) {
            return;
        }
        setYaw(Minecraft.getMinecraft().thePlayer.rotationYaw);
        setPitch(Minecraft.getMinecraft().thePlayer.rotationPitch);
    }
    
    public static void setPitch(final float pitch) {
        RotationUtil.pitch1 = pitch;
    }
    
    public static void setYaw(final float yaw) {
        RotationUtil.yaw1 = yaw;
        Minecraft.getMinecraft().thePlayer.rotationYawHead = yaw;
    }
    
    public static void faceBlock(double x, double y, double z) {
        --x;
        --y;
        --z;
        for (double xSearch = 0.1; xSearch < 0.9; xSearch += 0.1) {
            for (double ySearch = 0.1; ySearch < 0.9; ySearch += 0.1) {
                for (double zSearch = 0.1; zSearch < 0.9; zSearch += 0.1) {
                    final Vec3 eyesPos = new Vec3(RotationUtil.mc.thePlayer.posX, RotationUtil.mc.thePlayer.getEntityBoundingBox().minY + RotationUtil.mc.thePlayer.getEyeHeight(), RotationUtil.mc.thePlayer.posZ);
                    final Vec3 posVec = new Vec3(x, y, z).addVector(xSearch, ySearch, zSearch);
                    final double diffX = posVec.xCoord - eyesPos.xCoord;
                    final double diffY = posVec.yCoord - eyesPos.yCoord;
                    final double diffZ = posVec.zCoord - eyesPos.zCoord;
                    final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
                    setPitch(MathHelper.wrapAngleTo180_float((float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)))));
                    setYaw(MathHelper.wrapAngleTo180_float((float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f));
                }
            }
        }
    }
    
    static {
        RotationUtil.yaw1 = 0.0f;
        RotationUtil.pitch1 = 0.0f;
        RotationUtil.mc = Minecraft.getMinecraft();
        RotationUtil.doReset = true;
    }
}
