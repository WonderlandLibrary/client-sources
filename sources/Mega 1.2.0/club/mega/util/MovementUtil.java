package club.mega.util;

import club.mega.event.impl.EventMovePlayer;
import club.mega.interfaces.MinecraftInterface;

public final class MovementUtil implements MinecraftInterface {

    public static void setSpeed(final EventMovePlayer event, final double speed) {
        setSpeed(event, speed, MC.thePlayer.moveForward, MC.thePlayer.moveStrafing, MC.thePlayer.rotationYaw);
    }

    public static void setSpeed(final EventMovePlayer e, final double speed, final float forward, final float strafing, float yaw) {
        if (forward == 0.0F && strafing == 0.0F) return;
        final boolean reversed = forward < 0.0f;
        float strafingYaw = 90.0f * (forward > 0.0f ? 0.5f : reversed ? -0.5f : 1.0f);
        if (reversed) yaw += 180.0f;
        if (strafing > 0.0f) yaw -= strafingYaw;
        else if (strafing < 0.0f) yaw += strafingYaw;
        final double x = Math.cos(StrictMath.toRadians(yaw + 90.0f));
        final double z = Math.cos(StrictMath.toRadians(yaw));
        e.setX(x * speed);
        e.setZ(z * speed);
    }

    public static float getMoveDirection() {
        float yaw = MC.thePlayer.rotationYaw;
        if (MC.thePlayer.moveForward < 0.0F)
            yaw += 180.0F;
        float forward = 1.0F;
        if (MC.thePlayer.moveForward < 0.0F) {
            forward = -0.5F;
        } else if (MC.thePlayer.moveForward > 0.0F) {
            forward = 0.5F;
        }
        if (MC.thePlayer.moveStrafing > 0.0F)
            yaw -= 90.0F * forward;
        if (MC.thePlayer.moveStrafing < 0.0F)
            yaw += 90.0F * forward;
        yaw *= 0.017453292F;
        return yaw;
    }

    public static boolean isMoving() {
        return MC.thePlayer.moveForward != 0 || MC.thePlayer.moveStrafing != 0;
    }

    public static void jump() {
        if (isMoving() && MC.thePlayer.onGround)
            MC.thePlayer.jump();
    }
}
