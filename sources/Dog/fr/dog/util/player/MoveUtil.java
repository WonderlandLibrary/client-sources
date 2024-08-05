package fr.dog.util.player;

import fr.dog.event.impl.player.move.MoveInputEvent;
import fr.dog.event.impl.player.move.MovementEvent;
import fr.dog.util.InstanceAccess;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class MoveUtil implements InstanceAccess {

    public static boolean moving() {
        return mc.thePlayer.movementInput.moveForward != 0 || mc.thePlayer.movementInput.moveStrafe != 0;
    }
    public static boolean isDiag(){
        return Math.abs(mc.thePlayer.motionX) > 0.07 && Math.abs(mc.thePlayer.motionZ) > 0.07;
    }

    public static double speed() {
        return Math.hypot(mc.thePlayer.motionX, mc.thePlayer.motionZ);
    }


    public static double direction(float rotationYaw, final double moveForward, final double moveStrafing) {
        if (moveForward < 0F)
            rotationYaw += 180F;

        float forward = 1F;

        if (moveForward < 0F)
            forward = -0.5F;
        else if (moveForward > 0F)
            forward = 0.5F;

        if (moveStrafing > 0F)
            rotationYaw -= 90F * forward;
        if (moveStrafing < 0F)
            rotationYaw += 90F * forward;

        return Math.toRadians(rotationYaw);
    }
    public static double getDirection() {


        float moveForward = mc.thePlayer.moveForward;
        float moveStrafing = mc.thePlayer.moveStrafing;
        float rotationYaw = mc.thePlayer.rotationYaw;


        boolean isGoingBackward = moveForward < 0F;
        boolean isGoingForward = moveForward > 0F;
        float forward = 1F;
        if (isGoingBackward) {
            rotationYaw += 180F;
        }
        if (isGoingBackward) forward = -0.5F;
        else if (isGoingForward) forward = 0.5F;
        if (moveStrafing > 0F) rotationYaw -= 90F * forward;
        if (moveStrafing < 0F) rotationYaw += 90F * forward;
        return Math.toRadians(rotationYaw);
    }

    public static void fixMovement(final MoveInputEvent event, final float yaw) {
        
    }

    public void strafe() {
        strafe(speed());
    }

    public static void strafe(final double speed) {
        if (!moving())
            return;

        final double yaw = getDirection();
        mc.thePlayer.motionX = -MathHelper.sin((float) yaw) * speed;
        mc.thePlayer.motionZ = MathHelper.cos((float) yaw) * speed;
    }
    public static void strafeWithEvent(MovementEvent event, float speed) {
        final double yaw = getDirection();
        event.setX(-MathHelper.sin((float) yaw) * speed);
        event.setZ(MathHelper.cos((float) yaw) * speed);
    }
    public static double getSpeed(Entity entity) {
        return Math.hypot(Math.abs(entity.posX - entity.lastTickPosX), Math.abs(entity.posZ - entity.lastTickPosZ)) * mc.timer.timerSpeed * 20;
    }

}