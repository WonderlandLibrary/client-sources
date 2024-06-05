package me.vaziak.sensation.utils.math;

import java.util.Random;

/**
 * Created by Tojatta on 12/17/2016.
 */
public class AngleUtility {
    private float minYawSmoothing, maxYawSmoothing, minPitchSmoothing, maxPitchSmoothing;
    private Vector.Vector3<Float> delta;
    private Angle smoothedAngle;
    private final Random random;
    private float height = MathUtils.getRandomInRange(1.1F, 1.8F);

    public AngleUtility(float minYawSmoothing, float maxYawSmoothing, float minPitchSmoothing, float maxPitchSmoothing) {
        this.minYawSmoothing = minYawSmoothing;
        this.maxYawSmoothing = maxYawSmoothing;
        this.minPitchSmoothing = minPitchSmoothing;
        this.maxPitchSmoothing = maxPitchSmoothing;
        random = new Random();
        delta = new Vector.Vector3<>(0F, 0F, 0F);
        smoothedAngle = new Angle(0F, 0F);
    }

    public float randomFloat(float min, float max) {
        return min + (random.nextFloat() * (max - min));
    }

    public Angle calculateAngle(Vector.Vector3<Double> destination, Vector.Vector3<Double> source) {

        Angle angles = new Angle(0F, 0F);
        delta.setX(destination.getX().floatValue() - source.getX().floatValue())
                .setY((destination.getY().floatValue() + height) - (source.getY().floatValue() + height))
                .setZ(destination.getZ().floatValue() - source.getZ().floatValue());

        double hypotenuse = Math.hypot(delta.getX().doubleValue(), delta.getZ().doubleValue());
        float yawAtan = ((float) Math.atan2(delta.getZ().floatValue(), delta.getX().floatValue()));
        float pitchAtan = ((float) Math.atan2(delta.getY().floatValue(), hypotenuse));
        float deg = ((float) (180 / Math.PI));
        float yaw = ((yawAtan * deg) - 90F);
        float pitch = -(pitchAtan * deg);

        return angles.setYaw(yaw).setPitch(pitch).constrantAngle();
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Angle smoothAngle(Angle destination, Angle source, float pitch, float yaw) {
        return smoothedAngle
                .setYaw(source.getYaw() - destination.getYaw() - (Math.abs(source.getYaw() - destination.getYaw()) > 5 ? Math.abs(source.getYaw() - destination.getYaw()) / Math.abs(source.getYaw() - destination.getYaw()) * 2 / 2 : 0))
                .setPitch(source.getPitch() - destination.getPitch())
                .constrantAngle()
                .setYaw(source.getYaw() - smoothedAngle.getYaw() / yaw * randomFloat(minYawSmoothing, maxYawSmoothing))
                .constrantAngle()
                .setPitch(source.getPitch() - smoothedAngle.getPitch() / pitch * randomFloat(minPitchSmoothing, maxPitchSmoothing))
                .constrantAngle();
    }
    
    public Angle smoothAngle2(Angle destination, Angle source) {
        return this.smoothedAngle
                .setYaw(source.getYaw() - destination.getYaw())
                .setPitch(source.getPitch() - destination.getPitch())
                .constrantAngle()
                .setYaw(source.getYaw() - this.smoothedAngle.getYaw() / 100 * randomFloat(minYawSmoothing, maxYawSmoothing))
                .setPitch(source.getPitch() - this.smoothedAngle.getPitch() / 100 * randomFloat(minPitchSmoothing, maxPitchSmoothing))
                .constrantAngle();
    }
    
    public Angle calculateAngle2(Vector.Vector3<Integer> destination, Vector.Vector3<Double> source) {

        Angle angles = new Angle(0F, 0F);

        this.delta
                .setX(destination.getX().floatValue() - source.getX().floatValue())
                .setY((destination.getY().floatValue() + height) - (source.getY().floatValue() + height))
                .setZ(destination.getZ().floatValue() - source.getZ().floatValue());

        double hypotenuse = Math.hypot(this.delta.getX().doubleValue(), this.delta.getZ().doubleValue());

        float yawAtan = ((float) Math.atan2(this.delta.getZ().floatValue(), this.delta.getX().floatValue()));
        float pitchAtan = ((float) Math.atan2(this.delta.getY().floatValue(), hypotenuse));

        float deg = ((float) (180 / Math.PI));

        float yaw = ((yawAtan * deg) - 90F);
        float pitch = -(pitchAtan * deg);

        return angles.setYaw(yaw).setPitch(pitch).constrantAngle();
    }
    
    public static class Angle extends Vector.Vector2<Float> {
    	public int calls;
    	public int requests;
    	public float lastPitch;
        public Angle(Float x, Float y) {
            super(x, y);
        }

        public Angle setYaw(Float yaw) {
            setX(yaw);
            return this;
        }

        public Angle setPitch(Float pitch) {
            setY(pitch);
            return this;
        }

        public Float getYaw() {
            return getX().floatValue();
        }

        public Float getPitch() {
        	return (lastPitch = getY().floatValue());

        }

        public Angle constrantAngle() {

            this.setYaw(this.getYaw() % 360F);
            this.setPitch(this.getPitch() % 360F);

            while (this.getYaw() <= -180F) {
                this.setYaw(this.getYaw() + 360F);
            }

            while (this.getPitch() <= -180F) {
                this.setPitch(this.getPitch() + 360F);
            }

            while (this.getYaw() > 180F) {
                this.setYaw(this.getYaw() - 360F);
            }

            while (this.getPitch() > 180F) {
                this.setPitch(this.getPitch() - 360F);
            }

            return this;
        }
    }
}