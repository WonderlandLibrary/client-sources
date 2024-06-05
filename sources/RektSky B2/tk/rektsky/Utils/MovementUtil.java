package tk.rektsky.Utils;

import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.entity.*;

public class MovementUtil
{
    private static Minecraft mc;
    
    public static void teleportUp(final float d) {
        MovementUtil.mc.thePlayer.setPosition(MovementUtil.mc.thePlayer.posX, MovementUtil.mc.thePlayer.posY + d, MovementUtil.mc.thePlayer.posZ);
    }
    
    public static void teleportForward(final float d) {
        final double playerYaw = Math.toRadians(MovementUtil.mc.thePlayer.rotationYaw);
        MovementUtil.mc.thePlayer.setPosition(MovementUtil.mc.thePlayer.posX + d * -Math.sin(playerYaw), MovementUtil.mc.thePlayer.posY, MovementUtil.mc.thePlayer.posZ + d * Math.cos(playerYaw));
    }
    
    public static void teleportForwardPacket(final float d) {
        final double playerYaw = Math.toRadians(MovementUtil.mc.thePlayer.rotationYaw);
        MovementUtil.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(MovementUtil.mc.thePlayer.posX + d * -Math.sin(playerYaw), MovementUtil.mc.thePlayer.posY, MovementUtil.mc.thePlayer.posZ + d * Math.cos(playerYaw), true));
    }
    
    public static void teleportUpPacket(final float d) {
        MovementUtil.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(MovementUtil.mc.thePlayer.posX, MovementUtil.mc.thePlayer.posY + d, MovementUtil.mc.thePlayer.posZ, true));
    }
    
    public static float getSpeed() {
        return (float)Math.sqrt(MovementUtil.mc.thePlayer.motionX * MovementUtil.mc.thePlayer.motionX + MovementUtil.mc.thePlayer.motionZ * MovementUtil.mc.thePlayer.motionZ);
    }
    
    public static boolean isMoving() {
        return MovementUtil.mc.thePlayer != null && (MovementUtil.mc.thePlayer.movementInput.moveForward != 0.0f || MovementUtil.mc.thePlayer.movementInput.moveStrafe != 0.0f);
    }
    
    public static void strafe(final Double speed) {
        if (!isMoving()) {
            return;
        }
        final float yaw = (float)getDirection();
        final EntityPlayerSP thePlayer = MovementUtil.mc.thePlayer;
        thePlayer.motionX = -Math.sin(yaw) * speed;
        thePlayer.motionZ = Math.cos(yaw) * speed;
    }
    
    public static double getDirection() {
        final EntityPlayerSP thePlayer = MovementUtil.mc.thePlayer;
        Float rotationYaw = thePlayer.rotationYaw;
        if (thePlayer.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        Float forward = 1.0f;
        if (thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        }
        else if (thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (thePlayer.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (thePlayer.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return Math.toRadians(rotationYaw);
    }
    
    static {
        MovementUtil.mc = Minecraft.getMinecraft();
    }
}
