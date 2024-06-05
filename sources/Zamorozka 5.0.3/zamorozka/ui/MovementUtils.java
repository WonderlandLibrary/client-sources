package zamorozka.ui;

public final class MovementUtils extends MinecraftInstance {

    public static float getSpeed() {
        return (float) Math.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ);
    }

    public static void strafe() {
    	if(mc.gameSettings.keyBindBack.isKeyDown())
    		return;
        strafe(getSpeed());
    }

    public static boolean isMoving() {
        return mc.player != null && (mc.player.movementInput.moveForward != 0F || mc.player.movementInput.moveStrafe != 0F);
    }

    public static boolean hasMotion() {
        return mc.player.motionX != 0D && mc.player.motionZ != 0D && mc.player.motionY != 0D;
    }

    public static void strafe(final float speed) {
        if(!isMoving())
            return;
        final double yaw = getDirection();
        mc.player.motionX = -Math.sin(yaw) * speed;
        mc.player.motionZ = Math.cos(yaw) * speed;
    }

    public static void forward(final double length) {
        final double yaw = Math.toRadians(mc.player.rotationYaw);
        mc.player.setPosition(mc.player.posX + (-Math.sin(yaw) * length), mc.player.posY, mc.player.posZ + (Math.cos(yaw) * length));
    }

    public static double getDirection() {
        float rotationYaw = mc.player.rotationYaw;

        if(mc.player.moveForward < 0F)
            rotationYaw += 180F;

        float forward = 1F;
        if(mc.player.moveForward < 0F)
            forward = -0.5F;
        else if(mc.player.moveForward > 0F)
            forward = 0.5F;

        if(mc.player.moveStrafing > 0F)
            rotationYaw -= 90F * forward;

        if(mc.player.moveStrafing < 0F)
            rotationYaw += 90F * forward;

        return Math.toRadians(rotationYaw);
    }
    
    public static float getDirection2() {
        float var1 = mc.player.rotationYaw;
        if (mc.player.moveForward < 0.0f) {
            var1 += 180.0f;
        }
        float forward = 1.0f;
        if (mc.player.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (mc.player.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (mc.player.moveStrafing > 0.0f) {
            var1 -= 90.0f * forward;
        }
        if (mc.player.moveStrafing < 0.0f) {
            var1 += 90.0f * forward;
        }
        return var1 *= 0.017453292f;
    }
    
}