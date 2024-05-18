package baritone.api.utils;

public class Rotation
{
    private float yaw;
    private float pitch;
    
    public Rotation(final float yaw, final float pitch) {
        super();
        this.yaw = yaw;
        this.pitch = pitch;
    }
    
    public float getYaw() {
        return this.yaw;
    }
    
    public float getPitch() {
        return this.pitch;
    }
    
    public Rotation add(final Rotation rotation) {
        return new Rotation(this.yaw + rotation.yaw, this.pitch + rotation.pitch);
    }
    
    public Rotation subtract(final Rotation rotation) {
        return new Rotation(this.yaw - rotation.yaw, this.pitch - rotation.pitch);
    }
    
    public Rotation clamp() {
        return new Rotation(this.yaw, clampPitch(this.pitch));
    }
    
    public Rotation normalize() {
        return new Rotation(normalizeYaw(this.yaw), this.pitch);
    }
    
    public Rotation normalizeAndClamp() {
        return new Rotation(normalizeYaw(this.yaw), clampPitch(this.pitch));
    }
    
    public boolean isReallyCloseTo(final Rotation rotation) {
        return this.yawIsReallyClose(rotation) && Math.abs(this.pitch - rotation.pitch) < 0.01;
    }
    
    public boolean yawIsReallyClose(final Rotation rotation) {
        final float abs;
        return (abs = Math.abs(normalizeYaw(this.yaw) - normalizeYaw(rotation.yaw))) < 0.01 || abs > 359.99;
    }
    
    public static float clampPitch(final float n) {
        return Math.max(-90.0f, Math.min(90.0f, n));
    }
    
    public static float normalizeYaw(float n) {
        if ((n %= 360.0f) < -180.0f) {
            n += 360.0f;
        }
        if (n > 180.0f) {
            n -= 360.0f;
        }
        return n;
    }
    
    @Override
    public String toString() {
        return "Yaw: " + this.yaw + ", Pitch: " + this.pitch;
    }
}
