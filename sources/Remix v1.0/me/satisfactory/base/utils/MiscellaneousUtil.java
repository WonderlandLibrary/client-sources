package me.satisfactory.base.utils;

import net.minecraft.client.*;
import java.util.*;
import java.util.concurrent.*;
import net.minecraft.potion.*;
import me.satisfactory.base.events.*;
import net.minecraft.util.*;

public class MiscellaneousUtil
{
    public static void addChatMessage(final String str) {
        final Object chat = new ChatComponentText(str);
        if (str != null) {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage((IChatComponent)chat);
        }
    }
    
    public static void sendInfo(final String str) {
        addChatMessage("§2[Remix]: §f" + str);
    }
    
    public static int getRandom(final int cap) {
        final Random rng = new Random();
        return rng.nextInt(cap);
    }
    
    public static double random(final double min, final double max) {
        if (min < max) {
            return ThreadLocalRandom.current().nextDouble(min, max);
        }
        return max;
    }
    
    public static float random(final float min, final float max) {
        final Random random = new Random();
        return min + random.nextFloat() * (max - min);
    }
    
    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
    
    public static void setMoveSpeed(final EventMove event, final double speed) {
        final MovementInput movementInput = Minecraft.getMinecraft().thePlayer.movementInput;
        double forward = MovementInput.moveForward;
        final MovementInput movementInput2 = Minecraft.getMinecraft().thePlayer.movementInput;
        double strafe = MovementInput.moveStrafe;
        float yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            event.setX(0.0);
            event.setZ(0.0);
        }
        else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                }
                else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                }
                else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            event.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)));
            event.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)));
        }
    }
    
    public static void setSpeed(final double speed) {
        double yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        final boolean isMoving = Minecraft.getMinecraft().thePlayer.moveForward != 0.0f || Minecraft.getMinecraft().thePlayer.moveStrafing != 0.0f;
        final boolean isMovingForward = Minecraft.getMinecraft().thePlayer.moveForward > 0.0f;
        final boolean isMovingBackward = Minecraft.getMinecraft().thePlayer.moveForward < 0.0f;
        final boolean isMovingRight = Minecraft.getMinecraft().thePlayer.moveStrafing > 0.0f;
        final boolean isMovingLeft = Minecraft.getMinecraft().thePlayer.moveStrafing < 0.0f;
        final boolean isMovingSideways = isMovingLeft || isMovingRight;
        final boolean bl2;
        final boolean isMovingStraight = bl2 = (isMovingForward || isMovingBackward);
        if (isMoving) {
            if (isMovingForward && !isMovingSideways) {
                yaw += 0.0;
            }
            else if (isMovingBackward && !isMovingSideways) {
                yaw += 180.0;
            }
            else if (isMovingForward && isMovingLeft) {
                yaw += 45.0;
            }
            else if (isMovingForward) {
                yaw -= 45.0;
            }
            else if (!isMovingStraight && isMovingLeft) {
                yaw += 90.0;
            }
            else if (!isMovingStraight && isMovingRight) {
                yaw -= 90.0;
            }
            else if (isMovingBackward && isMovingLeft) {
                yaw += 135.0;
            }
            else if (isMovingBackward) {
                yaw -= 135.0;
            }
            yaw = Math.toRadians(yaw);
            Minecraft.getMinecraft().thePlayer.motionX = -Math.sin(yaw) * speed;
            Minecraft.getMinecraft().thePlayer.motionZ = Math.cos(yaw) * speed;
        }
    }
}
