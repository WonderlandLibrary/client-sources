package wtf.diablo.client.module.impl.player.scaffoldrecode.rotations;

import net.minecraft.client.Minecraft;

public class RotationsHandler {

    private static final Minecraft mc = Minecraft.getMinecraft();
    /**
     * @return Returns the direction the player is moving
     */
    public static float getMovementDirection(float yaw) {
        if (mc.thePlayer.moveForward < 0F)
            yaw += 180F;

        float forward = 1F;
        float strafe = 0;
        if (mc.thePlayer.moveForward != 0) {
            if (mc.thePlayer.moveForward > 0)
                forward = 0.5F;
            else
                forward = -0.5F;
        }
        if (mc.thePlayer.moveStrafing != 0) {
            if (mc.thePlayer.moveStrafing < 0)
                strafe = 1;
            else
                strafe = -1;
        }
        return yaw + (90F * forward * strafe);
    }

    /**
     *
     * @param yaw
     * @return returns yaw degree * radians (pi / 180), accounts for movementinput
     */
    public static float getRotationDegree(final float yaw) {
        return (float) Math.toRadians(getRotationForInput(yaw));
    }

    public static float getRotationForInput(final float yaw) {
        return getRotationForInput(yaw, mc.thePlayer.moveForward, mc.thePlayer.moveStrafing);
    }

    /**
     * @return Returns the direction the player is moving
     */
    public static float getRotationForInput(float yaw, final float moveForward, final float moveStrafing) {
        if (moveForward < 0)
            yaw += 180F;
        float forward = 1F;
        float strafe = 0;

        if(moveForward != 0) {
            if (moveForward > 0)
                forward = 0.5F;
            else
                forward = -0.5F;
        }
        if(moveStrafing != 0) {
            if (moveStrafing < 0)
                strafe = 1;
            else
                strafe = -1;
        }
        return yaw + (90F * forward * strafe);
    }

}
