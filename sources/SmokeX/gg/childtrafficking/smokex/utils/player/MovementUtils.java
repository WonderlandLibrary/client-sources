// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.utils.player;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.Packet;
import gg.childtrafficking.smokex.utils.system.NetworkUtils;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSword;
import gg.childtrafficking.smokex.module.modules.movement.TargetStrafeModule;
import gg.childtrafficking.smokex.event.events.player.EventMove;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.block.BlockAir;
import gg.childtrafficking.smokex.module.modules.player.NoSlowdownModule;
import gg.childtrafficking.smokex.module.ModuleManager;
import gg.childtrafficking.smokex.module.modules.player.ScaffoldModule;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MathHelper;
import gg.childtrafficking.smokex.utils.system.TimerUtil;
import net.minecraft.client.Minecraft;

public final class MovementUtils
{
    private static final Minecraft mc;
    private static final TimerUtil ncpBoostTimer;
    
    public static float getMovementDirection() {
        final EntityPlayerSP player = MovementUtils.mc.thePlayer;
        final float forward = player.moveForward;
        final float strafe = player.moveStrafing;
        float direction = 0.0f;
        if (forward < 0.0f) {
            direction += 180.0f;
            if (strafe > 0.0f) {
                direction += 45.0f;
            }
            else if (strafe < 0.0f) {
                direction -= 45.0f;
            }
        }
        else if (forward > 0.0f) {
            if (strafe > 0.0f) {
                direction -= 45.0f;
            }
            else if (strafe < 0.0f) {
                direction += 45.0f;
            }
        }
        else if (strafe > 0.0f) {
            direction -= 90.0f;
        }
        else if (strafe < 0.0f) {
            direction += 90.0f;
        }
        direction += player.rotationYaw;
        return MathHelper.wrapAngleTo180_float(direction);
    }
    
    public static double[] getXZ(final double moveSpeed) {
        double forward = MovementUtils.mc.thePlayer.movementInput.moveForward;
        double strafe = MovementUtils.mc.thePlayer.movementInput.moveStrafe;
        float yaw = MovementUtils.mc.thePlayer.rotationYaw;
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
        if (strafe > 0.0) {
            strafe = 1.0;
        }
        else if (strafe < 0.0) {
            strafe = -1.0;
        }
        final double mx = Math.cos(Math.toRadians(yaw + 90.0f));
        final double mz = Math.sin(Math.toRadians(yaw + 90.0f));
        final double x = forward * moveSpeed * mx + strafe * moveSpeed * mz;
        final double z = forward * moveSpeed * mz - strafe * moveSpeed * mx;
        return new double[] { x, z };
    }
    
    public static int getJumpBoostModifier() {
        final PotionEffect effect = MovementUtils.mc.thePlayer.getActivePotionEffect(Potion.jump.id);
        if (effect != null) {
            return effect.getAmplifier() + 1;
        }
        return 0;
    }
    
    public static boolean isBlockAbove() {
        return MovementUtils.mc.theWorld.checkBlockCollision(MovementUtils.mc.thePlayer.getEntityBoundingBox().addCoord(0.0, 1.0, 0.0));
    }
    
    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (MovementUtils.mc.thePlayer != null && MovementUtils.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = MovementUtils.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
    
    public static boolean canSprint() {
        return (!ModuleManager.getInstance(ScaffoldModule.class).isToggled() || ModuleManager.getInstance(ScaffoldModule.class).sprintProperty.getValue()) && MovementUtils.mc.thePlayer.movementInput.moveForward >= 0.8f && !MovementUtils.mc.thePlayer.isCollidedHorizontally && (MovementUtils.mc.thePlayer.getFoodStats().getFoodLevel() > 6 || MovementUtils.mc.thePlayer.capabilities.allowFlying) && !MovementUtils.mc.thePlayer.isSneaking() && (!MovementUtils.mc.thePlayer.isUsingItem() || ModuleManager.getInstance(NoSlowdownModule.class).isToggled());
    }
    
    public static double getBaseJumpMotion() {
        double baseJumpMotion = 0.41999998688697815;
        if (MovementUtils.mc.thePlayer.isPotionActive(Potion.jump)) {
            baseJumpMotion += (MovementUtils.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1f;
        }
        return baseJumpMotion;
    }
    
    public static boolean isOverVoid() {
        for (double posY = MovementUtils.mc.thePlayer.posY; posY > 0.0; --posY) {
            if (!(MovementUtils.mc.theWorld.getBlockState(new BlockPos(MovementUtils.mc.thePlayer.posX, posY, MovementUtils.mc.thePlayer.posZ)).getBlock() instanceof BlockAir)) {
                return false;
            }
        }
        return true;
    }
    
    public static double getBaseMoveSpeed2() {
        double baseSpeed = 0.2873;
        if (MovementUtils.mc.thePlayer != null && MovementUtils.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = MovementUtils.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.11 * (amplifier + 1);
        }
        return baseSpeed;
    }
    
    public static boolean isOnGround() {
        return MovementUtils.mc.thePlayer.onGround && MovementUtils.mc.thePlayer.isCollidedVertically;
    }
    
    public static boolean isOnGround(final double height) {
        return !MovementUtils.mc.theWorld.getCollidingBoundingBoxes(MovementUtils.mc.thePlayer, MovementUtils.mc.thePlayer.getEntityBoundingBox().offset(0.0, -height, 0.0)).isEmpty();
    }
    
    public static int getSpeedAmplifier() {
        return MovementUtils.mc.thePlayer.isPotionActive(Potion.moveSpeed) ? (MovementUtils.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1) : 0;
    }
    
    public static void setMotion(final double speed) {
        double forward = MovementUtils.mc.thePlayer.movementInput.moveForward;
        double strafe = MovementUtils.mc.thePlayer.movementInput.moveStrafe;
        float yaw = MovementUtils.mc.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            MovementUtils.mc.thePlayer.motionX = 0.0;
            MovementUtils.mc.thePlayer.motionZ = 0.0;
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
            MovementUtils.mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f));
            MovementUtils.mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f));
        }
    }
    
    public static void setSpeed(final EventMove e, final double speed) {
        final EntityPlayerSP player = MovementUtils.mc.thePlayer;
        setSpeed(e, speed, player.moveForward, player.moveStrafing, player.rotationYaw);
    }
    
    public static void setSpeed(final EventMove e, final double speed, final float forward, final float strafing, final float yaw) {
        final EntityPlayerSP player = MovementUtils.mc.thePlayer;
        if (ModuleManager.getInstance(TargetStrafeModule.class).shouldStrafe() && MovementUtils.mc.gameSettings.keyBindJump.isKeyDown()) {
            ModuleManager.getInstance(TargetStrafeModule.class).setSpeed(e, speed);
        }
        else {
            rawSetSpeed(e, speed, player.moveForward, player.moveStrafing, player.rotationYaw);
        }
    }
    
    public static void rawSetSpeed(final EventMove e, final double speed, final float forward, final float strafing, float yaw) {
        if (forward == 0.0f && strafing == 0.0f) {
            return;
        }
        final boolean reversed = forward < 0.0f;
        final float strafingYaw = 90.0f * ((forward > 0.0f) ? 0.5f : (reversed ? -0.5f : 1.0f));
        if (reversed) {
            yaw += 180.0f;
        }
        if (strafing > 0.0f) {
            yaw -= strafingYaw;
        }
        else if (strafing < 0.0f) {
            yaw += strafingYaw;
        }
        final double x = StrictMath.cos(StrictMath.toRadians(yaw + 90.0f));
        final double z = StrictMath.cos(StrictMath.toRadians(yaw));
        e.setX(x * speed);
        e.setZ(z * speed);
    }
    
    public static void ncpBoost() {
        ncpBoost(1);
    }
    
    public static void ncpBoost(final int strength) {
        if (MovementUtils.mc.thePlayer.getHeldItem() != null && MovementUtils.mc.thePlayer.isCollidedVertically && (MovementUtils.mc.thePlayer.getHeldItem().getItem() instanceof ItemSword || MovementUtils.mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock)) {
            NetworkUtils.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            for (int i = 0; i < 3 * strength; ++i) {
                NetworkUtils.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(MovementUtils.mc.thePlayer.posX + 0.0626 + Math.random() / 500.0, MovementUtils.mc.thePlayer.posY, MovementUtils.mc.thePlayer.posZ, true));
                NetworkUtils.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(MovementUtils.mc.thePlayer.posX, MovementUtils.mc.thePlayer.posY, MovementUtils.mc.thePlayer.posZ, true));
            }
        }
    }
    
    public static boolean isMoving() {
        return MovementUtils.mc.thePlayer.movementInput.moveForward != 0.0f || MovementUtils.mc.thePlayer.movementInput.moveStrafe != 0.0f;
    }
    
    public static boolean isMoving(final EntityPlayer player) {
        return player.moveForward != 0.0f || player.moveStrafing != 0.0f;
    }
    
    static {
        mc = Minecraft.getMinecraft();
        ncpBoostTimer = new TimerUtil();
    }
}
