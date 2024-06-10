// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.utilities;

import net.minecraft.util.MathHelper;

public final class RotationUtils
{
    private static float silentYaw;
    private static float realYaw;
    
    static {
        RotationUtils.silentYaw = 0.0f;
        RotationUtils.realYaw = 0.0f;
    }
    
    public static float getRealYaw() {
        return RotationUtils.realYaw;
    }
    
    public static void setRealYaw(final float yaw) {
        RotationUtils.realYaw = yaw;
    }
    
    public static float getSilentYaw() {
        return RotationUtils.silentYaw;
    }
    
    public static void setSilentYaw(final float yaw) {
        RotationUtils.silentYaw = yaw;
    }
    
    public static Object[] updateRotation(final float current, final float target, final float maxIncrease) {
        float angle = MathHelper.wrapAngleTo180_float(target - current);
        boolean aiming = true;
        if (angle > maxIncrease) {
            angle = maxIncrease;
            aiming = false;
        }
        if (angle < -maxIncrease) {
            angle = -maxIncrease;
            aiming = false;
        }
        return new Object[] { current + angle, aiming };
    }
}
