package ClassSub;

import java.util.*;

public class Class318
{
    private float minYawSmoothing;
    private float maxYawSmoothing;
    private float minPitchSmoothing;
    private float maxPitchSmoothing;
    private Class266<Float> delta;
    private Class274 smoothedAngle;
    private final Random random;
    private float height;
    
    
    public Class318(final float minYawSmoothing, final float maxYawSmoothing, final float minPitchSmoothing, final float maxPitchSmoothing) {
        this.height = Class262.getRandomInRange(1.1f, 1.8f);
        this.minYawSmoothing = minYawSmoothing;
        this.maxYawSmoothing = maxYawSmoothing;
        this.minPitchSmoothing = minPitchSmoothing;
        this.maxPitchSmoothing = maxPitchSmoothing;
        this.random = new Random();
        this.delta = new Class266<Float>(0.0f, 0.0f, 0.0f);
        this.smoothedAngle = new Class274(Float.valueOf(0.0f), Float.valueOf(0.0f));
    }
    
    public float randomFloat(final float n, final float n2) {
        return n + this.random.nextFloat() * (n2 - n);
    }
    
    public Class274 calculateAngle(final Class266<Double> class266, final Class266<Double> class267) {
        final Class274 class268 = new Class274(Float.valueOf(0.0f), Float.valueOf(0.0f));
        this.delta.setX(class266.getX().floatValue() - class267.getX().floatValue()).setY(class266.getY().floatValue() + this.height - (class267.getY().floatValue() + this.height)).setZ(class266.getZ().floatValue() - class267.getZ().floatValue());
        final double hypot = Math.hypot(this.delta.getX().doubleValue(), this.delta.getZ().doubleValue());
        final float n = (float)Math.atan2(this.delta.getZ().floatValue(), this.delta.getX().floatValue());
        final float n2 = (float)Math.atan2(this.delta.getY().floatValue(), hypot);
        final float n3 = 57.29578f;
        return class268.setYaw(n * n3 - 90.0f).setPitch(-(n2 * n3)).constrantAngle();
    }
    
    public void setHeight(final float height) {
        this.height = height;
    }
    
    public Class274 smoothAngle(final Class274 class274, final Class274 class275, final float n, final float n2) {
        return this.smoothedAngle.setYaw(class275.getYaw() - class274.getYaw() - ((Math.abs(class275.getYaw() - class274.getYaw()) > 5.0f) ? (Math.abs(class275.getYaw() - class274.getYaw()) / Math.abs(class275.getYaw() - class274.getYaw()) * 2.0f / 2.0f) : 0.0f)).setPitch(class275.getPitch() - class274.getPitch()).constrantAngle().setYaw(class275.getYaw() - this.smoothedAngle.getYaw() / n2 * this.randomFloat(this.minYawSmoothing, this.maxYawSmoothing)).constrantAngle().setPitch(class275.getPitch() - this.smoothedAngle.getPitch() / n * this.randomFloat(this.minPitchSmoothing, this.maxPitchSmoothing)).constrantAngle();
    }
}
