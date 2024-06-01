package io.github.liticane.clients.util.player;

import io.github.liticane.clients.feature.event.impl.input.MoveInputEvent;
import io.github.liticane.clients.feature.event.impl.other.MoveEvent;
import io.github.liticane.clients.util.interfaces.IMethods;
import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class MoveUtil implements IMethods {
    public static final double WALK_SPEED = .221;
    public static final double WEB_SPEED = .105 / WALK_SPEED;
    public static final double SWIM_SPEED = .115f / WALK_SPEED;
    public static final double SNEAK_SPEED = .3f;
    public static final double SPRINTING_SPEED = 1.3f;

    public static final double[] DEPTH_STRIDER = {
            1.f, .1645f / SWIM_SPEED / WALK_SPEED, .1995f / SWIM_SPEED / WALK_SPEED, 1.f / SWIM_SPEED
    };
    public static double getSpeed(MoveEvent moveEvent) {
        return mc.player == null ? 0 : Math.sqrt(moveEvent.getX() * moveEvent.getX() + moveEvent.getZ() * moveEvent.getZ());
    }

    public static Item getHeldItem() {
        if (mc.player == null || mc.player.getCurrentEquippedItem() == null) return null;
        return mc.player.getCurrentEquippedItem().getItem();
    }
    public static boolean hasAppliedSpeedII(EntityPlayer player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            if (effect.getPotionID() == 1 && effect.getAmplifier() == 1) {
                return true;
            }
        }
        return false;
    }
    public static Block block(final double x, final double y, final double z) {
        return mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
    }
    public static double speedPotionAmp(final double amp) {
        return mc.player.isPotionActive(Potion.moveSpeed) ? ((mc.player.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1) * amp) : 0;
    }
    public static void sendClick(final int button, final boolean state) {
        final int keyBind = button == 0 ? mc.settings.keyBindAttack.getKeyCode() : mc.settings.keyBindUseItem.getKeyCode();

        KeyBinding.setKeyBindState(keyBind, state);

        if (state) {
            KeyBinding.onTick(keyBind);
        }
    }
    public static void fixMovement(final MoveInputEvent event, final float yaw) {
        final float forward = event.getForward();
        final float strafe = event.getStrafe();

        final double angle = MathHelper.wrapAngleTo180_double(Math.toDegrees(direction(mc.player.rotationYaw, forward, strafe)));

        if (forward == 0 && strafe == 0) {
            return;
        }

        float closestForward = 0, closestStrafe = 0, closestDifference = Float.MAX_VALUE;

        for (float predictedForward = -1F; predictedForward <= 1F; predictedForward += 1F) {
            for (float predictedStrafe = -1F; predictedStrafe <= 1F; predictedStrafe += 1F) {
                if (predictedStrafe == 0 && predictedForward == 0) continue;

                final double predictedAngle = MathHelper.wrapAngleTo180_double(Math.toDegrees(direction(yaw, predictedForward, predictedStrafe)));
                final double difference = Math.abs(angle - predictedAngle);

                if (difference < closestDifference) {
                    closestDifference = (float) difference;
                    closestForward = predictedForward;
                    closestStrafe = predictedStrafe;
                }
            }
        }

        event.setForward(closestForward);
        event.setStrafe(closestStrafe);
    }
    public static double direction(float rotationYaw, final double moveForward, final double moveStrafing) {
        if (moveForward < 0F) rotationYaw += 180F;

        float forward = 1F;

        if (moveForward < 0F) forward = -0.5F;
        else if (moveForward > 0F) forward = 0.5F;

        if (moveStrafing > 0F) rotationYaw -= 90F * forward;
        if (moveStrafing < 0F) rotationYaw += 90F * forward;

        return Math.toRadians(rotationYaw);
    }

    public static void jump(MoveEvent event) {
        double jumpY = mc.player.getJumpUpwardsMotion();

        if (mc.player.isPotionActive(Potion.jump))
            jumpY += (float) (mc.player.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F;

        event.setY(mc.player.motionY = jumpY);
    }


    public static void strafe(MoveEvent event, double speed) {
        float direction = (float) Math.toRadians(getPlayerDirection());

        if (isMoving()) {
            event.setX(mc.player.motionX = -Math.sin(direction) * speed);
            event.setZ(mc.player.motionZ = Math.cos(direction) * speed);
        } else {
            event.setX(mc.player.motionX = 0);
            event.setZ(mc.player.motionZ = 0);
        }
    }

    public static void strafe(double speed) {
        float direction = (float) Math.toRadians(getPlayerDirection());

        if (isMoving()) {
            mc.player.motionX = -Math.sin(direction) * speed;
            mc.player.motionZ = Math.cos(direction) * speed;
        } else {
            mc.player.motionX = 0;
            mc.player.motionZ = 0;
        }
    }

    public static float getPlayerDirection() {
        float direction = mc.player.rotationYaw;

        if (mc.player.moveForward > 0) {
            if (mc.player.moveStrafing > 0) {
                direction -= 45;
            } else if (mc.player.moveStrafing < 0) {
                direction += 45;
            }
        } else if (mc.player.moveForward < 0) {
            if (mc.player.moveStrafing > 0) {
                direction -= 135;
            } else if (mc.player.moveStrafing < 0) {
                direction += 135;
            } else {
                direction -= 180;
            }
        } else {
            if (mc.player.moveStrafing > 0) {
                direction -= 90;
            } else if (mc.player.moveStrafing < 0) {
                direction += 90;
            }
        }

        return direction;
    }

    public static boolean isMoving() {
        return mc.player.moveForward != 0 || mc.player.moveStrafing != 0;
    }

    public static boolean isInLiquid() {
        return mc.player.isInWater() || mc.player.isInLava();
    }

    public static boolean enoughMovementForSprinting() {
        return Math.abs(mc.player.moveForward) >= .8f || Math.abs(mc.player.moveStrafing) >= .8f;
    }

    public static double baseSpeed() {
        double speed;
        boolean useModifiers = false;

        if (mc.player.isInWeb)
            speed = WEB_SPEED * WALK_SPEED;
        else if (MoveUtil.isInLiquid()) {
            speed = SWIM_SPEED * WALK_SPEED;

            final int level = EnchantmentHelper.getDepthStriderModifier(mc.player);

            if (level > 0) {
                speed *= DEPTH_STRIDER[level];
                useModifiers = true;
            }
        } else if (mc.player.isSneaking()) {
            speed = SNEAK_SPEED * WALK_SPEED;
        } else {
            speed = WALK_SPEED;
            useModifiers = true;
        }

        if (useModifiers) {
            if (enoughMovementForSprinting())
                speed *= SPRINTING_SPEED;

            if (mc.player.isPotionActive(Potion.moveSpeed))
                speed *= 1 + (.2 * (mc.player.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1));

            if (mc.player.isPotionActive(Potion.moveSlowdown))
                speed = .29;
        }

        return speed;
    }
}
