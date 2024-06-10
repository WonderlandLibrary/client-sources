 package me.vene.skilled.utilities;

 import java.util.Random;
 import net.minecraft.client.Minecraft;
 import net.minecraft.entity.Entity;
 import net.minecraft.entity.monster.EntityEnderman;
 import net.minecraft.util.MathHelper;
 
public class MathUtil
{
    public static final Random random;
    private static final double EPSILON = 1.0E-12;
    
    public static double map(final double valueCoord1, final double startCoord1, final double endCoord1, final double startCoord2, final double endCoord2) {
        if (Math.abs(endCoord1 - startCoord1) < 1.0E-12) {
            throw new ArithmeticException("/ 0");
        }
        final double ratio = (endCoord2 - startCoord2) / (endCoord1 - startCoord1);
        return ratio * (valueCoord1 - startCoord1) + startCoord2;
    }
    
    public static double clamp(final double value, final double min, final double max) {
        return Math.min(max, Math.max(min, value));
    }
    
    public static double getDistanceBetweenAngles(final float angle1, final float angle2) {
        float d = Math.abs(angle1 - angle2) % 360.0f;
        if (d > 180.0f) {
            d = 360.0f - d;
        }
        return d;
    }
    
    public static float[] getAngle(final Entity entity) {
        final double x = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
        final double z = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        final double y = (entity instanceof EntityEnderman) ? (entity.posY - Minecraft.getMinecraft().thePlayer.posY) : (entity.posY + (entity.getEyeHeight() - 0.4) - Minecraft.getMinecraft().thePlayer.posY + (Minecraft.getMinecraft().thePlayer.getEyeHeight() - 0.4));
        final double helper = MathHelper.sqrt_double(x * x + z * z);
        float newYaw = (float)Math.toDegrees(-Math.atan(x / z));
        final float newPitch = (float)(-Math.toDegrees(Math.atan(y / helper)));
        if (z < 0.0 && x < 0.0) {
            newYaw = (float)(90.0 + Math.toDegrees(Math.atan(z / x)));
        }
        else if (z < 0.0 && x > 0.0) {
            newYaw = (float)(-90.0 + Math.toDegrees(Math.atan(z / x)));
        }
        return new float[] { newPitch, newYaw };
    }
    
    static {
        random = new Random();
    }
}
