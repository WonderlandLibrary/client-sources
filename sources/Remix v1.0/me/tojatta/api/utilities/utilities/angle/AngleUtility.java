package me.tojatta.api.utilities.utilities.angle;

import net.minecraft.client.*;
import java.util.*;
import me.tojatta.api.utilities.utilities.vector.impl.*;
import net.minecraft.util.*;

public class AngleUtility
{
    static Minecraft mc;
    private final Random random;
    private float minYawSmoothing;
    private float maxYawSmoothing;
    private float minPitchSmoothing;
    private float maxPitchSmoothing;
    private Vector3<Float> delta;
    private Angle smoothedAngle;
    
    public AngleUtility(final float minYawSmoothing, final float maxYawSmoothing, final float minPitchSmoothing, final float maxPitchSmoothing) {
        this.minYawSmoothing = minYawSmoothing;
        this.maxYawSmoothing = maxYawSmoothing;
        this.minPitchSmoothing = minPitchSmoothing;
        this.maxPitchSmoothing = maxPitchSmoothing;
        this.random = new Random();
        this.delta = new Vector3<Float>(0.0f, 0.0f, 0.0f);
        this.smoothedAngle = new Angle(0.0f, 0.0f);
    }
    
    public static float[] getAngleBlockpos(final BlockPos target) {
        final double xDiff = target.getX() - AngleUtility.mc.thePlayer.posX;
        final double yDiff = target.getY() - AngleUtility.mc.thePlayer.posY;
        final double zDiff = target.getZ() - AngleUtility.mc.thePlayer.posZ;
        final float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(-Math.atan2(target.getY() - 1.0 - (AngleUtility.mc.thePlayer.posY + AngleUtility.mc.thePlayer.getEyeHeight()), Math.hypot(xDiff, zDiff)) * 180.0 / 3.141592653589793);
        if (yDiff > -0.2 && yDiff < 0.2) {
            pitch = (float)(-Math.atan2(target.getY() - 1.0 - (AngleUtility.mc.thePlayer.posY + AngleUtility.mc.thePlayer.getEyeHeight()), Math.hypot(xDiff, zDiff)) * 180.0 / 3.141592653589793);
        }
        else if (yDiff > -0.2) {
            pitch = (float)(-Math.atan2(target.getY() - 1.0 - (AngleUtility.mc.thePlayer.posY + AngleUtility.mc.thePlayer.getEyeHeight()), Math.hypot(xDiff, zDiff)) * 180.0 / 3.141592653589793);
        }
        else if (yDiff < 0.3) {
            pitch = (float)(-Math.atan2(target.getY() - 1.0 - (AngleUtility.mc.thePlayer.posY + AngleUtility.mc.thePlayer.getEyeHeight()), Math.hypot(xDiff, zDiff)) * 180.0 / 3.141592653589793);
        }
        return new float[] { yaw, pitch };
    }
    
    public float randomFloat(final float min, final float max) {
        return min + this.random.nextFloat() * (max - min);
    }
    
    public Angle calculateAngle(final Vector3<Double> destination, final Vector3<Double> source) {
        final Angle angles = new Angle(0.0f, 0.0f);
        final float height = 1.5f;
        this.delta.setX(destination.getX().floatValue() - source.getX().floatValue()).setY(destination.getY().floatValue() + height - (source.getY().floatValue() + height)).setZ(destination.getZ().floatValue() - source.getZ().floatValue());
        final double hypotenuse = Math.hypot(this.delta.getX().doubleValue(), this.delta.getZ().doubleValue());
        final float yawAtan = (float)Math.atan2(this.delta.getZ().floatValue(), this.delta.getX().floatValue());
        final float pitchAtan = (float)Math.atan2(this.delta.getY().floatValue(), hypotenuse);
        final float deg = 57.29578f;
        final float yaw = yawAtan * deg - 90.0f;
        final float pitch = -(pitchAtan * deg);
        return angles.setYaw(yaw).setPitch(pitch).constrantAngle();
    }
    
    public Angle smoothAngle(final Angle destination, final Angle source) {
        return this.smoothedAngle.setYaw(source.getYaw() - destination.getYaw()).setPitch(source.getPitch() - destination.getPitch()).constrantAngle().setYaw(source.getYaw() - this.smoothedAngle.getYaw() / 100.0f * this.randomFloat(this.minYawSmoothing, this.maxYawSmoothing)).setPitch(source.getPitch() - this.smoothedAngle.getPitch() / 100.0f * this.randomFloat(this.minPitchSmoothing, this.maxPitchSmoothing)).constrantAngle();
    }
    
    static {
        AngleUtility.mc = Minecraft.getMinecraft();
    }
}
