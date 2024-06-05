package client.metaware.impl.utils.util.player;

import client.metaware.Metaware;
import client.metaware.impl.event.impl.player.MovePlayerEvent;
import client.metaware.impl.event.impl.player.PlayerStrafeEvent;
import client.metaware.impl.event.impl.player.UpdatePlayerEvent;
import client.metaware.impl.module.combat.TargetStrafeV2;
import client.metaware.impl.utils.util.other.MathUtils;
import net.minecraft.block.*;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

import javax.vecmath.Vector2d;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MovementUtils {

    private static final List<Double> frictionValues = Arrays.asList(0.0, 0.0, 0.0);
	static Minecraft mc = Minecraft.getMinecraft();

    public static boolean checkNoBlockAbove() {
        return !mc.theWorld
                .checkBlockCollision(
                        mc.thePlayer.getEntityBoundingBox()
                                .addCoord(0.0D, 1.0D, 0.0D));
    }


    public static double[] yawPos(double value) {
        double yaw = Math.toRadians(mc.thePlayer.currentEvent.getYaw());
        return new double[] {-Math.sin(yaw) * value, Math.cos(yaw) * value};
    }

    public static boolean isInLiquid() {
        return mc.thePlayer.isInWater() || mc.thePlayer.isInLava();
    }

    //very simple but needs to be used for verus or else poo poo shiz
    public static void strafe(double speed){
        if(!mc.thePlayer.isMoving()) return;
        double yaw = direction();
        mc.thePlayer.motionX = -Math.sin(yaw) * speed;
        mc.thePlayer.motionZ = Math.cos(yaw) * speed;
    }

    public static double direction(){
        float rotationYaw = mc.thePlayer.rotationYaw;
        if (mc.thePlayer.moveForward < 0f) rotationYaw += 180f;
        float forward = 1f;
        if (mc.thePlayer.moveForward < 0f) forward = -0.5f; else if (mc.thePlayer.moveForward > 0f) forward = 0.5f;
        if (mc.thePlayer.moveStrafing > 0f) rotationYaw -= 90f * forward;
        if (mc.thePlayer.moveStrafing < 0f) rotationYaw += 90f * forward;
        return Math.toRadians(rotationYaw);
    }

    public static void damagePlayer(final boolean groundCheck) {
        for(int i = 0; i <= 4 / 0.0625; i++) {
            mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0625, mc.thePlayer.posZ, false));
            mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
        }
        mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer(true));
    }

    public static double getMotion(final EntityLivingBase player) {
        return MathUtils.distance(player.prevPosX, player.prevPosZ, player.posX, player.posZ);
    }

    public static boolean isOverVoid() {
        for (double posY = mc.thePlayer.posY; posY > 0.0; posY--) {
            if (!(mc.theWorld.getBlockState(
                    new BlockPos(mc.thePlayer.posX, posY, mc.thePlayer.posZ)).getBlock() instanceof BlockAir))
                return false;
        }

        return true;
    }

    public static float getSensitivityMultiplier() {
        float f = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6F + 0.2F;
        return (f * f * f * 8.0F) * 0.15F;
    }

    public static void placeHeldItemUnderPlayer() {
        final BlockPos blockPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.getEntityBoundingBox().minY + 1,
                mc.thePlayer.posZ);
        final Vec3d vec = new Vec3d(blockPos).addVector(0.4F, 0.4F, 0.4F);
        mc.playerController.onPlayerRightClick3d(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), blockPos, EnumFacing.UP,
                vec.scale(0.4));
    }

    public static double getBaseSpeed() {
        double baseSpeed = 0.4225;
        return baseSpeed;
    }

    public static double getBaseSpeed(ACType value) {
        double baseSpeed = value == ACType.MINEPLEX ? 0.4225 : value == ACType.VERUS ? 0.24 : 0.2873;
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amp = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
            baseSpeed *= 1 + (value == ACType.VERUS ? 0.05 : 0.2) * amp;
        }
        return baseSpeed;
    }

	public static double getSpeed() {
		double defaultSpeed = 0.2873;
		if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
			defaultSpeed *= 1.0 + 0.2 * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
		}
		return defaultSpeed;
	}

    public static Block getBlock(BlockPos pos) {
        return mc.theWorld.getBlockState(pos).getBlock();
    }

    public static boolean isOnIce() {
        final Block blockUnder = getBlockUnder(mc.thePlayer, 1);
        return blockUnder instanceof BlockIce || blockUnder instanceof BlockPackedIce;
    }

    public static Block getBlockUnder(final EntityLivingBase entity, final double offset) {
        return getBlock(new BlockPos(
                entity.posX,
                entity.posY - offset,
                entity.posZ));
    }

    public static double calculateFriction(double moveSpeed, double lastDist, double baseMoveSpeed) {
        frictionValues.set(0, lastDist - (lastDist / 160.0 - 1.0E-3));
        frictionValues.set(1, lastDist - ((moveSpeed - lastDist) / 33.3D));
        double materialFriction =
                mc.thePlayer.isInWater() ?
                        0.89F :
                        mc.thePlayer.isInLava() ?
                                0.535F :
                                0.98F;
        frictionValues.set(2, lastDist - (baseMoveSpeed * (1.0D - materialFriction)));
        return Collections.min(frictionValues);
    }


    public static float getMovementDirection() {
        final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
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


    public static void setSpeed(PlayerStrafeEvent event, double speed) {
        final EntityPlayerSP player = mc.thePlayer;
        final TargetStrafeV2 targetStrafe = Metaware.INSTANCE.getModuleManager().getModuleByClass(TargetStrafeV2.class);

        if (targetStrafe.shouldStrafe()) {
            float dist = mc.thePlayer.getDistanceToEntity(targetStrafe.currentTarget);
            double radius = targetStrafe.radiusProperty.getValue();
            if (targetStrafe.shouldAdaptSpeed())
                speed = Math.min(speed, targetStrafe.getAdaptedSpeed());
            setSpeed(event, speed, dist <= radius ? 0 : 1, dist <= radius + 0.2D ? targetStrafe.direction : 0, RotationUtils.getYawBetween(getMovementDirection(), player.posX, player.posZ, targetStrafe.currentPoint.x, targetStrafe.currentPoint.z));
            event.setYaw(RotationUtils.getYawBetween(getMovementDirection(), player.posX, player.posZ, targetStrafe.currentPoint.x, targetStrafe.currentPoint.z));
            return;
        }
        setSpeed(event, speed, player.moveForward, player.moveStrafing, player.rotationYaw);
    }

    public static void setSpeed(PlayerStrafeEvent event, double speed, float forward, float strafing, float yaw) {
        boolean reversed = forward < 0.0f;
        float strafingYaw = 90.0f *
                (forward > 0.0f ? 0.5f : reversed ? -0.5f : 1.0f);

        if (reversed)
            yaw += 180.0f;
        if (strafing > 0.0f)
            yaw -= strafingYaw;
        else if (strafing < 0.0f)
            yaw += strafingYaw;

        double x = Math.cos(Math.toRadians(yaw + 90.0f));
        double z = Math.cos(Math.toRadians(yaw));

        event.setFriction(speed);
        event.setForward(forward);
        event.setStrafe(strafing);

    }

    public static void setSpeed(MovePlayerEvent e, double speed) {
        final EntityPlayerSP player = mc.thePlayer;
        final TargetStrafeV2 targetStrafe = Metaware.INSTANCE.getModuleManager().getModuleByClass(TargetStrafeV2.class);

        if (targetStrafe.shouldStrafe()) {
            float dist = mc.thePlayer.getDistanceToEntity(targetStrafe.currentTarget);
            double radius = targetStrafe.radiusProperty.getValue();
            if (targetStrafe.shouldAdaptSpeed())
                speed = Math.min(speed, targetStrafe.getAdaptedSpeed());
            setSpeed(e, speed, dist <= radius ? 0 : 1, dist <= radius + 0.2D ? targetStrafe.direction : 0, RotationUtils.getYawBetween(mc.thePlayer.currentEvent.getYaw(), player.posX, player.posZ, targetStrafe.currentPoint.x, targetStrafe.currentPoint.z));
            return;
        }
        setSpeed(e, speed, player.moveForward, player.moveStrafing, player.rotationYaw);
    }

    public static void setSpeed(MovePlayerEvent e, double speed, float forward, float strafing, float yaw) {
        if (forward == 0.0F && strafing == 0.0F) return;

        boolean reversed = forward < 0.0f;
        float strafingYaw = 90.0f *
                (forward > 0.0f ? 0.5f : reversed ? -0.5f : 1.0f);

        if (reversed)
            yaw += 180.0f;
        if (strafing > 0.0f)
            yaw -= strafingYaw;
        else if (strafing < 0.0f)
            yaw += strafingYaw;

        double x = StrictMath.cos(StrictMath.toRadians(yaw + 90.0f));
        double z = StrictMath.cos(StrictMath.toRadians(yaw));

        e.setX(x * speed);
        e.setZ(z * speed);
    }

    public static void setSpeed(UpdatePlayerEvent event, double speed) {
        final EntityPlayerSP player = mc.thePlayer;
        final TargetStrafeV2 targetStrafe = Metaware.INSTANCE.getModuleManager().getModuleByClass(TargetStrafeV2.class);

        if (targetStrafe.shouldStrafe()) {
            float dist = mc.thePlayer.getDistanceToEntity(targetStrafe.currentTarget);
            double radius = targetStrafe.radiusProperty.getValue();
            if (targetStrafe.shouldAdaptSpeed())
                speed = Math.min(speed, targetStrafe.getAdaptedSpeed());
            setSpeed(event, speed, dist <= radius ? 0 : 1, dist <= radius + 0.2D ? targetStrafe.direction : 0, RotationUtils.getYawBetween(getMovementDirection(), event.getPosX(), event.getPosZ(), targetStrafe.currentPoint.x, targetStrafe.currentPoint.z));
            return;
        }
        setSpeed(event, speed, player.moveForward, player.moveStrafing, player.rotationYaw);
    }

    public static void setSpeed(UpdatePlayerEvent event, double speed, float forward, float strafing, float yaw) {
        if (forward == 0.0F && strafing == 0.0F) return;

        boolean reversed = forward < 0.0f;
        float strafingYaw = 90.0f *
                (forward > 0.0f ? 0.5f : reversed ? -0.5f : 1.0f);

        if (reversed)
            yaw += 180.0f;
        if (strafing > 0.0f)
            yaw -= strafingYaw;
        else if (strafing < 0.0f)
            yaw += strafingYaw;

        double x = StrictMath.cos(StrictMath.toRadians(yaw + 90.0f));
        double z = StrictMath.cos(StrictMath.toRadians(yaw));

        mc.thePlayer.motionX = x * speed;
        mc.thePlayer.motionZ = z * speed;
    }

    public static void setSpeed12(double speed) {
        final EntityPlayerSP player = mc.thePlayer;
        final TargetStrafeV2 targetStrafe = Metaware.INSTANCE.getModuleManager().getModuleByClass(TargetStrafeV2.class);

        if (targetStrafe.shouldStrafe()) {
            float dist = mc.thePlayer.getDistanceToEntity(targetStrafe.currentTarget);
            double radius = targetStrafe.radiusProperty.getValue();
            if (targetStrafe.shouldAdaptSpeed())
                speed = Math.min(speed, targetStrafe.getAdaptedSpeed());
            setSpeed12(speed, dist <= radius ? 0 : 1, dist <= radius + 0.2D ? targetStrafe.direction : 0, RotationUtils.getYawBetween(getMovementDirection(), player.posX, player.posZ, targetStrafe.currentPoint.x, targetStrafe.currentPoint.z));
            return;
        }
        setSpeed12(speed, player.moveForward, player.moveStrafing, player.rotationYaw);
    }

    public static void setSpeed12(double speed, float forward, float strafing, float yaw) {
        if (forward == 0.0F && strafing == 0.0F) return;

        boolean reversed = forward < 0.0f;
        float strafingYaw = 90.0f *
                (forward > 0.0f ? 0.5f : reversed ? -0.5f : 1.0f);

        if (reversed)
            yaw += 180.0f;
        if (strafing > 0.0f)
            yaw -= strafingYaw;
        else if (strafing < 0.0f)
            yaw += strafingYaw;

        double x = StrictMath.cos(StrictMath.toRadians(yaw + 90.0f));
        double z = StrictMath.cos(StrictMath.toRadians(yaw));

        mc.thePlayer.motionX = x * speed;
        mc.thePlayer.motionZ = z * speed;
    }

    public static Vector2d getMotion(double moveSpeed) {
        MovementInput movementInput = mc.thePlayer.movementInput;

        double moveForward = movementInput.moveForward;
        double moveStrafe = movementInput.moveStrafe;

        double rotationYaw = mc.thePlayer.rotationYaw;
        if (moveForward != 0.0D || moveStrafe != 0.0D) {
            if (moveStrafe > 0) {
                moveStrafe = 1;
            } else if (moveStrafe < 0) {
                moveStrafe = -1;
            }
            if (moveForward != 0.0D) {
                if (moveStrafe > 0.0D) {
                    rotationYaw += moveForward > 0.0D ? -45 : 45;
                } else if (moveStrafe < 0.0D) {
                    rotationYaw += moveForward > 0.0D ? 45 : -45;
                }
                moveStrafe = 0.0D;
                if (moveForward > 0.0D) {
                    moveForward = 1.0D;
                } else if (moveForward < 0.0D) {
                    moveForward = -1.0D;
                }
            }
            double cos = Math.cos(Math.toRadians(rotationYaw + 90.0F));
            double sin = Math.sin(Math.toRadians(rotationYaw + 90.0F));
            return new Vector2d(moveForward * moveSpeed * cos
                    + moveStrafe * moveSpeed * sin, moveForward * moveSpeed * sin
                    - moveStrafe * moveSpeed * cos);
        }
        return new Vector2d(0, 0);
    }

    public static void sendMotion(double speed, double dist) {
        mc.timer.timerSpeed = 1.0f;
        Vector2d motion = new Vector2d(0, 0);
        final double x = mc.thePlayer.posX;
        final double y = mc.thePlayer.posY;
        final double z = mc.thePlayer.posZ;
        double d = dist;
        while (d < speed) {
            motion = getMotion(d);
            mc.getNetHandler().addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x + motion.x, y, z + motion.y, mc.thePlayer.onGround));
            d += dist;
        }
        if((mc.theWorld.getBlockState(new BlockPos(x + motion.x, y, z + motion.y)).getBlock() instanceof BlockAir)) {
            mc.thePlayer.setPosition(x + motion.x, mc.thePlayer.posY, z + motion.y);
        }
    }

    public static boolean isOnGround() {
//        List<AxisAlignedBB> collidingList = Wrapper.getWorld().getCollidingBoundingBoxes(Wrapper.getPlayer(), Wrapper.getPlayer().getEntityBoundingBox().offset(0.0, -(0.01 - MIN_DIF), 0.0));
//        return collidingList.size() > 0;
        return mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically;
    }

    public static void setMotion(double speed, double nig) {
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if (forward == 0.0D && strafe == 0.0D) {
            mc.thePlayer.motionX = 0.0D;
            mc.thePlayer.motionZ = 0.0D;
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += ((forward > 0.0D) ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += ((forward > 0.0D) ? 45 : -45);
                }
                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1.0D;
                } else if (forward < 0.0D) {
                    forward = -1.0D;
                }
            }
            mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians((yaw + 90.0F))) + strafe * speed * Math.sin(Math.toRadians((yaw + 90.0F)));
            mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians((yaw + 90.0F))) - strafe * speed * Math.cos(Math.toRadians((yaw + 90.0F)));
        }
    }

    public static double getJumpBoostModifier(double baseJumpHeight) {
        if (mc.thePlayer.isPotionActive(Potion.jump)) {
            int amplifier = mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier();
            baseJumpHeight += (double) ((float) (amplifier + 1) * 0.1F);
        }

        return baseJumpHeight;
    }

    public static boolean isOnGround(double height) {
        if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public static int getSpeedEffect() {
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed))
            return mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
        else
            return 0;
    }
}
