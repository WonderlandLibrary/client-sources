// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.utils.player;

import net.minecraft.util.BlockPos;
import net.minecraft.block.BlockAir;
import xyz.niggfaclient.utils.other.MathUtils;
import net.minecraft.entity.Entity;
import org.lwjgl.input.Keyboard;
import xyz.niggfaclient.module.impl.combat.KillAura;
import xyz.niggfaclient.module.ModuleManager;
import xyz.niggfaclient.module.impl.movement.TargetStrafe;
import xyz.niggfaclient.events.impl.MoveEvent;
import net.minecraft.potion.Potion;
import net.minecraft.client.Minecraft;

public class MoveUtils
{
    public static Minecraft mc;
    
    public static double getJumpHeight() {
        final double baseJumpHeight = 0.41999998688697815;
        if (MoveUtils.mc.thePlayer.isInLiquid()) {
            return 0.13500000536441803;
        }
        if (MoveUtils.mc.thePlayer.isPotionActive(Potion.jump)) {
            return baseJumpHeight + 0.1 * MoveUtils.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier();
        }
        return baseJumpHeight;
    }
    
    public static double getLastTickDistance() {
        return Math.hypot(MoveUtils.mc.thePlayer.posX - MoveUtils.mc.thePlayer.prevPosX, MoveUtils.mc.thePlayer.posZ - MoveUtils.mc.thePlayer.prevPosZ);
    }
    
    public static void resetLastTickDistance() {
        MoveUtils.mc.thePlayer.prevPosX = MoveUtils.mc.thePlayer.posX;
        MoveUtils.mc.thePlayer.prevPosZ = MoveUtils.mc.thePlayer.posZ;
    }
    
    public static double getBlocksPerSecond() {
        if (MoveUtils.mc.thePlayer == null || MoveUtils.mc.thePlayer.ticksExisted < 1) {
            return 0.0;
        }
        return MoveUtils.mc.thePlayer.getDistance(MoveUtils.mc.thePlayer.lastTickPosX, MoveUtils.mc.thePlayer.lastTickPosY, MoveUtils.mc.thePlayer.lastTickPosZ) * (MoveUtils.mc.timer.ticksPerSecond * MoveUtils.mc.timer.timerSpeed);
    }
    
    public static double getBaseMoveSpeed(final double multiplier) {
        double speed = 0.2873;
        if (MoveUtils.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = MoveUtils.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            speed *= 1.0 + multiplier * (amplifier + 1);
        }
        return speed;
    }
    
    public static double getBaseMoveSpeed() {
        double speed = 0.2873;
        if (MoveUtils.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = MoveUtils.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            speed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return speed;
    }
    
    public static double getSpeed() {
        return (float)Math.sqrt(MoveUtils.mc.thePlayer.motionX * MoveUtils.mc.thePlayer.motionX + MoveUtils.mc.thePlayer.motionZ * MoveUtils.mc.thePlayer.motionZ);
    }
    
    public static double getSpeed(final MoveEvent moveEvent) {
        return (float)Math.sqrt(moveEvent.getX() * moveEvent.getX() + moveEvent.getZ() * moveEvent.getZ());
    }
    
    public static boolean isMoving() {
        return MoveUtils.mc.thePlayer.movementInput.moveForward != 0.0f || MoveUtils.mc.thePlayer.movementInput.moveStrafe != 0.0f;
    }
    
    public static float getDirection() {
        return getDirection(MoveUtils.mc.thePlayer.rotationYaw, MoveUtils.mc.thePlayer.moveForward, MoveUtils.mc.thePlayer.moveStrafing);
    }
    
    public static float getDirection(final float rotationYaw, final float moveForward, final float moveStrafing) {
        float direction = rotationYaw;
        if (moveForward > 0.0f) {
            if (moveStrafing > 0.0f) {
                direction -= 45.0f;
            }
            else if (moveStrafing < 0.0f) {
                direction += 45.0f;
            }
        }
        else if (moveForward < 0.0f) {
            if (moveStrafing > 0.0f) {
                direction -= 135.0f;
            }
            else if (moveStrafing < 0.0f) {
                direction += 135.0f;
            }
            else {
                direction -= 180.0f;
            }
        }
        else if (moveStrafing > 0.0f) {
            direction -= 90.0f;
        }
        else if (moveStrafing < 0.0f) {
            direction += 90.0f;
        }
        return direction;
    }
    
    public static void setSpeed(final MoveEvent e, final double speed) {
        if (isMoving()) {
            final TargetStrafe targetStrafe = ModuleManager.getModule(TargetStrafe.class);
            final KillAura killAura = ModuleManager.getModule(KillAura.class);
            if (targetStrafe.isEnabled() && (!TargetStrafe.holdSpace.getValue() || Keyboard.isKeyDown(57))) {
                final Entity target = killAura.target;
                if (killAura.isEnabled() && target != null) {
                    final float dist = MoveUtils.mc.thePlayer.getDistanceToEntity(target);
                    final double radius = TargetStrafe.range.getValue();
                    setSpeed(e, speed, RotationUtils.getYawToEntity(target), (dist <= radius + 1.0E-4) ? 0.0f : 1.0f, (dist <= radius + 1.0) ? ((float)targetStrafe.strafeDirection) : 0.0f);
                    return;
                }
            }
            setSpeed(e, speed, MoveUtils.mc.thePlayer.rotationYaw, MoveUtils.mc.thePlayer.moveForward, MoveUtils.mc.thePlayer.moveStrafing);
        }
        else {
            setSpeed(e, 0.0, MoveUtils.mc.thePlayer.rotationYaw, MoveUtils.mc.thePlayer.moveForward, MoveUtils.mc.thePlayer.moveStrafing);
        }
    }
    
    public static void setSpeed(final MoveEvent e, final double moveSpeed, final float rotationYaw, final float moveForward, final float moveStrafing) {
        final float direction = (float)Math.toRadians(getDirection(rotationYaw, moveForward, moveStrafing));
        if (e != null) {
            e.setX(-Math.sin(direction) * moveSpeed);
            e.setZ(Math.cos(direction) * moveSpeed);
        }
        else {
            MoveUtils.mc.thePlayer.motionX = -Math.sin(direction) * moveSpeed;
            MoveUtils.mc.thePlayer.motionZ = Math.cos(direction) * moveSpeed;
        }
    }
    
    public static double getRandomHypixelValues() {
        double value = 1.0 / System.currentTimeMillis();
        for (int i = 0; i < MathUtils.getRandomInRange(4, 20); ++i) {
            value *= 1.0 / System.currentTimeMillis();
        }
        return value;
    }
    
    public static boolean isOverVoid() {
        for (double posY = MoveUtils.mc.thePlayer.posY; posY > 0.0; --posY) {
            if (!(MoveUtils.mc.theWorld.getBlockState(new BlockPos(MoveUtils.mc.thePlayer.posX, posY, MoveUtils.mc.thePlayer.posZ)).getBlock() instanceof BlockAir)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isOnGround() {
        return MoveUtils.mc.thePlayer.onGround;
    }
    
    public static boolean isOnGround(final double height) {
        return !MoveUtils.mc.theWorld.getCollidingBoundingBoxes(MoveUtils.mc.thePlayer, MoveUtils.mc.thePlayer.getEntityBoundingBox().offset(0.0, -height, 0.0)).isEmpty();
    }
    
    public static boolean isMovingOnGround() {
        return isMoving() && isOnGround();
    }
    
    static {
        MoveUtils.mc = Minecraft.getMinecraft();
    }
}
