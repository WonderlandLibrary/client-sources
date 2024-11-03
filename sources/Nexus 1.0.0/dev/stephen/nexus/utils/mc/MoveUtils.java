package dev.stephen.nexus.utils.mc;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.module.modules.combat.TargetStrafe;
import dev.stephen.nexus.utils.Utils;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MoveUtils implements Utils {

    public static final double WALK_SPEED = 0.221;
    public static final double BUNNY_SLOPE = 0.66;
    public static final double MOD_SPRINTING = 1.3F;
    public static final double MOD_SNEAK = 0.3F;
    public static final double MOD_ICE = 2.5F;
    public static final double MOD_WEB = 0.105 / WALK_SPEED;
    public static final double JUMP_HEIGHT = 0.42F;
    public static final double BUNNY_FRICTION = 159.9F;
    public static final double Y_ON_GROUND_MIN = 0.00001;
    public static final double Y_ON_GROUND_MAX = 0.0626;

    public static final double AIR_FRICTION = 0.9800000190734863D;
    public static final double WATER_FRICTION = 0.800000011920929D;
    public static final double LAVA_FRICTION = 0.5D;
    public static final double MOD_SWIM = 0.115F / WALK_SPEED;
    public static final double[] MOD_DEPTH_STRIDER = {
            1.0F,
            0.1645F / MOD_SWIM / WALK_SPEED,
            0.1995F / MOD_SWIM / WALK_SPEED,
            1.0F / MOD_SWIM,
    };

    public static final double UNLOADED_CHUNK_MOTION = -0.09800000190735147;
    public static final double HEAD_HITTER_MOTION = -0.0784000015258789;
    public static final double STANDING_MOTION_Y = -0.0784000015258789;

    /**
     * Gets the direction of were the player is looking
     */
    public static double getDirection() {
        float rotationYaw = mc.player.getYaw();

        if (Client.INSTANCE.getModuleManager().getModule(TargetStrafe.class).isEnabled() && Client.INSTANCE.getModuleManager().getModule(TargetStrafe.class).active && Client.INSTANCE.getModuleManager().getModule(TargetStrafe.class).target != null)
            rotationYaw = Client.INSTANCE.getModuleManager().getModule(TargetStrafe.class).yaw;

        if (mc.player.input.movementForward < 0F) {
            rotationYaw += 180F;
        }

        float forward = 1F;

        if (mc.player.input.movementForward < 0F) {
            forward = -0.5F;
        } else if (mc.player.input.movementForward > 0F) {
            forward = 0.5F;
        }

        if (mc.player.input.movementSideways > 0F) {
            rotationYaw -= 90F * forward;
        }
        if (mc.player.input.movementSideways < 0F) {
            rotationYaw += 90F * forward;
        }

        return Math.toRadians(rotationYaw);
    }

    public static double getDirection2() {
        float rotationYaw = mc.player.getYaw();

        if (Client.INSTANCE.getModuleManager().getModule(TargetStrafe.class).isEnabled() && Client.INSTANCE.getModuleManager().getModule(TargetStrafe.class).active && Client.INSTANCE.getModuleManager().getModule(TargetStrafe.class).target != null)
            rotationYaw = Client.INSTANCE.getModuleManager().getModule(TargetStrafe.class).yaw;

        if (mc.player.input.movementForward < 0F) {
            rotationYaw += 180F;
        }

        float forward = 1F;

        if (mc.player.input.movementForward < 0F) {
            forward = -0.5F;
        } else if (mc.player.input.movementForward > 0F) {
            forward = 0.5F;
        }

        if (mc.player.input.movementSideways > 0F) {
            rotationYaw -= 70 * forward;
        }
        if (mc.player.input.movementSideways < 0F) {
            rotationYaw += 70 * forward;
        }

        return Math.toRadians(rotationYaw);
    }

    /**
     * Gets the direction of were the player is looking with custom yaw, forward and strafe
     */
    public static double direction(float rotationYaw, double moveForward, double moveStrafing) {
        float rotationYawCalced = rotationYaw;

        if (moveForward < 0f) {
            rotationYawCalced += 180f;
        }

        float forward = 1f;

        if (moveForward < 0f) {
            forward = -0.5f;
        } else if (moveForward > 0f) {
            forward = 0.5f;
        }

        if (moveStrafing > 0f) {
            rotationYawCalced -= 90f * forward;
        }
        if (moveStrafing < 0f) {
            rotationYawCalced += 90f * forward;
        }

        return Math.toRadians(rotationYawCalced);
    }

    /**
     * Gets the players allowed max speed
     */
    public static double getAllowedHorizontalDistance() {
        double horizontalDistance;
        boolean useBaseModifiers = false;
        PlayerEntity player = mc.player;
        if (player.isInLava() || player.isTouchingWater()) {
            horizontalDistance = MOD_SWIM * WALK_SPEED;

            int depthStriderLevel = depthStriderLevel(player);
            if (depthStriderLevel > 0) {
                horizontalDistance *= MOD_DEPTH_STRIDER[depthStriderLevel];
                useBaseModifiers = true;
            }

        } else if (player.isSneaking()) {
            horizontalDistance = MOD_SNEAK * WALK_SPEED;
        } else {
            horizontalDistance = WALK_SPEED;
            useBaseModifiers = true;
        }

        if (useBaseModifiers) {
            if (player.isSprinting()) {
                horizontalDistance *= MOD_SPRINTING;
            }

            if (player.hasStatusEffect(StatusEffects.SPEED)) {
                horizontalDistance *= 1 + (0.2 * (player.getStatusEffect(StatusEffects.SPEED).getAmplifier() + 1));
            }

            if (player.hasStatusEffect(StatusEffects.SLOWNESS)) {
                horizontalDistance = 0.29;
            }
        }

        return horizontalDistance;
    }

    /**
     * Sets players speed, with floats
     */
    public static void strafe(final float speed) {
        if (!isMoving()) {
            return;
        }

        final double yaw = getDirection();

        mc.player.setVelocity(-MathHelper.sin((float) yaw) * speed, mc.player.getVelocity().y, MathHelper.cos((float) yaw) * speed);
    }

    /**
     * Used to get the players speed, with doubles
     */
    public static void strafe(final double speed) {
        if (!isMoving()) {
            return;
        }

        final double yaw = getDirection();

        mc.player.setVelocity(-MathHelper.sin((float) yaw) * speed, mc.player.getVelocity().y, MathHelper.cos((float) yaw) * speed);
    }


    /**
     * Used to get the players speed, with doubles and a custom yaw
     */
    public static void strafe(final double speed, float yaw) {
        yaw = (float) Math.toRadians(yaw);
        mc.player.setVelocity(-MathHelper.sin(yaw) * speed, mc.player.getVelocity().y, MathHelper.cos(yaw) * speed);
    }

    /**
     * Sets current speed to itself make strafe
     */
    public static void strafe() {
        strafe(getSpeed());
    }

    /**
     * Kills the players motion
     */
    public static void stop() {
        setMotionZ(0);
        setMotionX(0);
    }

    /**
     * Kills the players motion fully
     */
    public static void fullStop() {
        setMotionZ(0);
        setMotionX(0);
        setMotionY(0);
    }

    /**
     * Gets the players speed
     */
    public static double getSpeed() {
        return Math.hypot(mc.player.getVelocity().x, mc.player.getVelocity().z);
    }

    /**
     * Gets the players base speed
     */
    public static double getBaseMoveSpeed() {
        double speed;
        boolean useModifiers = false;

        if (mc.player.isInFluid()) {
            speed = MOD_SWIM * WALK_SPEED;

            final int level = depthStriderLevel(mc.player);

            if (level > 0) {
                speed *= MOD_DEPTH_STRIDER[level];
                useModifiers = true;
            }
        } else if (mc.player.isSneaking()) {
            speed = MOD_SNEAK * WALK_SPEED;
        } else {
            speed = WALK_SPEED;
            useModifiers = true;
        }

        if (useModifiers) {
            if (enoughMovementForSprinting())
                speed *= MOD_SPRINTING;

            if (mc.player.hasStatusEffect(StatusEffects.SPEED))
                speed *= 1 + (.2 * (mc.player.getStatusEffect(StatusEffects.SPEED).getAmplifier() + 1));

            if (mc.player.hasStatusEffect(StatusEffects.SLOWNESS))
                speed = .29;
        }

        return speed;
    }

    public static boolean enoughMovementForSprinting() {
        return Math.abs(mc.player.input.movementForward) >= .8f || Math.abs(mc.player.input.movementSideways) >= .8f;
    }

    /**
     * Checks if the player is moving using Motion
     */
    public static boolean isMoving() {
        return mc.player.getVelocity().x != 0 || mc.player.getVelocity().z != 0;
    }

    /**
     * Checks if the player is moving using Motion
     */
    public static boolean isMoving(LivingEntity entity) {
        return entity.getVelocity().x != 0 || entity.getVelocity().z != 0;
    }

    /**
     * Checks if the player is moving using Keybinds
     */
    public static boolean isMoving2() {
        return mc.options.leftKey.isPressed() || mc.options.rightKey.isPressed() || mc.options.backKey.isPressed() || mc.options.forwardKey.isPressed();
    }

    /**
     * Sets the players motion X
     */
    public static void setMotionX(double motionX) {
        mc.player.setVelocity(motionX, mc.player.getVelocity().y, mc.player.getVelocity().z);
    }

    /**
     * Sets the players motion Y
     */
    public static void setMotionY(double motionY) {
        mc.player.setVelocity(mc.player.getVelocity().x, motionY, mc.player.getVelocity().z);
    }

    /**
     * Sets the players motion Z
     */
    public static void setMotionZ(double motionZ) {
        mc.player.setVelocity(mc.player.getVelocity().x, mc.player.getVelocity().y, motionZ);
    }

    /**
     * Gets the players motion X
     */
    public static double getMotionX() {
        return mc.player.getVelocity().y;
    }

    /**
     * Gets the players motion Y
     */
    public static double getMotionY() {
        return mc.player.getVelocity().y;
    }

    /**
     * Gets the players motion Z
     */
    public static double getMotionZ() {
        return mc.player.getVelocity().y;
    }

    /**
     * Gets the players Depth Strider Level
     */
    private static int depthStriderLevel(PlayerEntity player) {
        return EnchantmentHelper.getEquipmentLevel(mc.world.getRegistryManager().get(Enchantments.DEPTH_STRIDER.getRegistryRef()).getEntry(Enchantments.DEPTH_STRIDER).get(), player);
    }

    /**
     * Gets the players speed Level
     */
    public static double getSpeedAmplifier(PlayerEntity player) {
        if (player.hasStatusEffect(StatusEffects.SPEED)) {
            return 1 + player.getStatusEffect(StatusEffects.SPEED).getAmplifier();
        }
        return 0;
    }

    /**
     * Gets the players predicted jump motion the specified amount of ticks ahead
     *
     * @return predicted jump motion
     */
    public static double predictedMotion(final double motion, final int ticks) {
        if (ticks == 0) return motion;
        double predicted = motion;

        for (int i = 0; i < ticks; i++) {
            predicted = (predicted - 0.08) * 0.98F;
        }

        return predicted;
    }

    /**
     * Checks if the player is going diagonally
     */
    public static boolean isGoingDiagonally() {
        return Math.abs(mc.player.getVelocity().x) > 0.04 && Math.abs(mc.player.getVelocity().z) > 0.04;
    }

    /**
     * Checks if the player is going diagonally
     */
    public static boolean isGoingDiagonally(double a) {
        return Math.abs(mc.player.getVelocity().x) > a && Math.abs(mc.player.getVelocity().z) > a;
    }

    /**
     * Strafes at a certain speed percentage
     **/
    public static void partialStrafePercent(double percentage) {
        percentage /= 100;
        percentage = Math.min(1, Math.max(0, percentage));

        double motionX = mc.player.getVelocity().x;
        double motionZ = mc.player.getVelocity().z;

        MoveUtils.strafe();

        mc.player.getVelocity().x = motionX + (mc.player.getVelocity().x - motionX) * percentage;
        mc.player.getVelocity().z = motionZ + (mc.player.getVelocity().z - motionZ) * percentage;
    }

    public static void hypixelTeleport(Vec3d endPos) {
        double dist = Math.sqrt(mc.player.squaredDistanceTo(endPos));

        double distanceEntreLesPackets = 0.31 + MoveUtils.getSpeedAmplifier(mc.player) / 20;

        double xtp, ytp, ztp;

        if (dist > distanceEntreLesPackets) {
            double nbPackets = Math.round(dist / distanceEntreLesPackets + 0.49999999999) - 1;

            xtp = mc.player.getX();
            ytp = mc.player.getY();
            ztp = mc.player.getZ();

            double count = 0;
            for (int i = 1; i < nbPackets; i++) {
                double xdi = (endPos.getX() - mc.player.getX()) / (nbPackets);
                xtp += xdi;

                double zdi = (endPos.getZ() - mc.player.getZ()) / (nbPackets);
                ztp += zdi;

                double ydi = (endPos.getY() - mc.player.getY()) / (nbPackets);
                ytp += ydi;
                count++;

                BlockPos blockPos = new BlockPos(MathHelper.floor(xtp), MathHelper.floor(ytp - 1), MathHelper.floor(ztp));

                if (!mc.world.getBlockState(blockPos).isFullCube(mc.world, blockPos)) {
                    if (count <= 2) {
                        ytp += 2E-8;
                    } else if (count >= 4) {
                        count = 0;
                    }
                }

                ChatUtils.addMessageToChat(xtp + " " + ytp + " " + ztp);
                PlayerMoveC2SPacket.PositionAndOnGround Packet = new PlayerMoveC2SPacket.PositionAndOnGround(xtp, ytp, ztp, false);
                PacketUtils.sendPacket(Packet);
            }

            mc.player.setPosition(endPos.getX() + 0.5, endPos.getY(), endPos.getZ() + 0.5);
        } else {
            mc.player.setPosition(endPos.getX(), endPos.getY(), endPos.getZ());
        }
    }

    public static void adjustMotionYForFall() {
        if (!mc.player.isOnGround()) {
            double gravity = -0.08D;
            double airResistance = 0.98D;
            mc.player.getVelocity().y += gravity;
            mc.player.getVelocity().y *= airResistance;
        }
    }
}
