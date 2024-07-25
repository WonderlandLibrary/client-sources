package club.bluezenith.util.player;

import club.bluezenith.events.impl.MoveEvent;
import club.bluezenith.util.MinecraftInstance;
import net.minecraft.potion.Potion;

public class MovementUtil extends MinecraftInstance {
    public static float currentSpeed() {
        return (float) Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
    }
    public static boolean areMovementKeysPressed(){
        return mc.thePlayer.movementInput.moveForward != 0F || mc.thePlayer.movementInput.moveStrafe != 0F;
    }

    public static boolean areStrafeKeysPressed(){
        return mc.thePlayer.movementInput.moveStrafe != 0F;
    }

    public static void setSpeed(float f){
        if(mc.thePlayer == null || !(mc.thePlayer.movementInput.moveForward != 0F || mc.thePlayer.movementInput.moveStrafe != 0F)){
            return;
        }
        float rotationYaw = mc.thePlayer.rotationYaw;
        if (mc.thePlayer.moveForward < 0.0F)
            rotationYaw += 180.0F;
        float forward = 1.0F;
        if (mc.thePlayer.moveForward < 0.0F) {
            forward = -0.5F;
        } else if (mc.thePlayer.moveForward > 0.0F) {
            forward = 0.5F;
        }
        if (mc.thePlayer.moveStrafing > 0.0F)
            rotationYaw -= 90.0F * forward;
        if (mc.thePlayer.moveStrafing < 0.0F)
            rotationYaw += 90.0F * forward;

        float yaw = (float) Math.toRadians(rotationYaw);
        mc.thePlayer.motionX = -Math.sin(yaw) * f;
        mc.thePlayer.motionZ = Math.cos(yaw) * f;
    }
    public static void hClip(float f) {
        float rotationYaw = mc.thePlayer.rotationYaw;
        float yaw = (float) Math.toRadians(rotationYaw);
        mc.thePlayer.setPosition(mc.thePlayer.posX + -Math.sin(yaw) * f, mc.thePlayer.posY, mc.thePlayer.posZ + Math.cos(yaw) * f);
    }
    public static void setSpeed(double f, MoveEvent e){
        if (e.isPost()) return;
        if(mc.thePlayer == null || !(mc.thePlayer.movementInput.moveForward != 0F || mc.thePlayer.movementInput.moveStrafe != 0F)){
            return;
        }
        float rotationYaw = mc.thePlayer.rotationYaw;
        if (mc.thePlayer.moveForward < 0.0F)
            rotationYaw += 180.0F;
        float forward = 1.0F;
        if (mc.thePlayer.moveForward < 0.0F) {
            forward = -0.5F;
        } else if (mc.thePlayer.moveForward > 0.0F) {
            forward = 0.5F;
        }
        if (mc.thePlayer.moveStrafing > 0.0F)
            rotationYaw -= 90.0F * forward;
        if (mc.thePlayer.moveStrafing < 0.0F)
            rotationYaw += 90.0F * forward;

        float yaw = (float) Math.toRadians(rotationYaw);
        e.x = -Math.sin(yaw) * f;
        e.z = Math.cos(yaw) * f;
    }

    public static double getNormalSpeed() {
        double speed = 0.2875D;
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            speed *= 1.0D + 0.2D * (double)(mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }

        return speed;
    }

    public static void stopMoving() {
        mc.thePlayer.motionX *= 0D;
        mc.thePlayer.motionZ *= 0D;
    }

}
