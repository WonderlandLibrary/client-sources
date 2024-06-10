package xyz.gucciclient.utils;

public class MovementUtils
{
    public static boolean isMoving() {
        return Wrapper.getPlayer().moveForward != 0.0f || Wrapper.getPlayer().moveStrafing != 0.0f;
    }
    
    public static float getSpeed() {
        final float speed = (float)Math.sqrt(Wrapper.getPlayer().motionX * Wrapper.getPlayer().motionX + Wrapper.getPlayer().motionZ * Wrapper.getPlayer().motionZ);
        return speed;
    }
    
    public static void setSpeed(final double speed) {
        Wrapper.getPlayer().motionX = -(Math.sin(getDirection()) * speed);
        Wrapper.getPlayer().motionZ = Math.cos(getDirection()) * speed;
    }
    
    public static float getDirection() {
        float var1 = Wrapper.getPlayer().rotationYaw;
        if (Wrapper.getPlayer().moveForward < 0.0f) {
            var1 += 180.0f;
        }
        float forward = 1.0f;
        if (Wrapper.getPlayer().moveForward < 0.0f) {
            forward = -0.5f;
        }
        else if (Wrapper.getPlayer().moveForward > 0.0f) {
            forward = 0.5f;
        }
        else {
            forward = 1.0f;
        }
        if (Wrapper.getPlayer().moveStrafing > 0.0f) {
            var1 -= 90.0f * forward;
        }
        if (Wrapper.getPlayer().moveStrafing < 0.0f) {
            var1 += 90.0f * forward;
        }
        var1 *= 0.017453292f;
        return var1;
    }
}
