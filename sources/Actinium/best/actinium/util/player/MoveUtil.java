package best.actinium.util.player;

import best.actinium.util.IAccess;
import best.actinium.event.impl.move.MoveEvent;
import best.actinium.event.impl.move.MoveInputEvent;
import best.actinium.util.math.RandomUtil;
import best.actinium.util.render.ChatUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.block.BlockSlime;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class MoveUtil implements IAccess {
    public static final double WALK_SPEED = .221;
    public static final double WEB_SPEED = .105 / WALK_SPEED;
    public static final double SWIM_SPEED = .115f / WALK_SPEED;
    public static final double SNEAK_SPEED = .3f;
    public static final double SPRINTING_SPEED = 1.3f;
    public static final double[] DEPTH_STRIDER = {
            1.f, .1645f / SWIM_SPEED / WALK_SPEED, .1995f / SWIM_SPEED / WALK_SPEED, 1.f / SWIM_SPEED
    };

    public static double getSpeed() {
        return mc.thePlayer == null ? 0 : Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX
                + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
    }

    public static void strafe(MoveEvent event, double speed) {
        float direction = (float) Math.toRadians(getthePlayerDirection());

        if (isMoving()) {
            event.setX(mc.thePlayer.motionX = -Math.sin(direction) * speed);
            event.setZ(mc.thePlayer.motionZ = Math.cos(direction) * speed);
        } else {
            event.setX(mc.thePlayer.motionX = 0);
            event.setZ(mc.thePlayer.motionZ = 0);
        }
    }

    public static void strafe(double speed) {
        float direction = (float) Math.toRadians(getthePlayerDirection());

        if (isMoving()) {
            mc.thePlayer.motionX = -Math.sin(direction) * speed;
            mc.thePlayer.motionZ = Math.cos(direction) * speed;
        } else {
            mc.thePlayer.motionX = 0;
            mc.thePlayer.motionZ = 0;
        }
    }

    public static void strafe() {
        float direction = (float) Math.toRadians(getthePlayerDirection());

        if (isMoving()) {
            mc.thePlayer.motionX = -Math.sin(direction) * baseSpeed();
            mc.thePlayer.motionZ = Math.cos(direction) * baseSpeed();
        } else {
            mc.thePlayer.motionX = 0;
            mc.thePlayer.motionZ = 0;
        }
    }

    public static float getthePlayerDirection() {
        float direction = mc.thePlayer.rotationYaw;

        if (mc.thePlayer.moveForward > 0) {
            if (mc.thePlayer.moveStrafing > 0) {
                direction -= 45;
            } else if (mc.thePlayer.moveStrafing < 0) {
                direction += 45;
            }
        } else if (mc.thePlayer.moveForward < 0) {
            if (mc.thePlayer.moveStrafing > 0) {
                direction -= 135;
            } else if (mc.thePlayer.moveStrafing < 0) {
                direction += 135;
            } else {
                direction -= 180;
            }
        } else {
            if (mc.thePlayer.moveStrafing > 0) {
                direction -= 90;
            } else if (mc.thePlayer.moveStrafing < 0) {
                direction += 90;
            }
        }

        return direction;
    }

    public static boolean isMoving() {
        return mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0;
    }

    public static boolean isInLiquid() {
        return mc.thePlayer.isInWater() || mc.thePlayer.isInLava();
    }

    public static boolean enoughMovementForSprinting() {
        return Math.abs(mc.thePlayer.moveForward) >= .8f || Math.abs(mc.thePlayer.moveStrafing) >= .8f;
    }

    public static double baseSpeed() {
        double speed;
        boolean useModifiers = false;

        if(mc.thePlayer.isInWeb)
            speed = WEB_SPEED * WALK_SPEED;
        else if (MoveUtil.isInLiquid()) {
            speed = SWIM_SPEED * WALK_SPEED;

            final int level = EnchantmentHelper.getDepthStriderModifier(mc.thePlayer);

            if (level > 0) {
                speed *= DEPTH_STRIDER[level];
                useModifiers = true;
            }
        } else if (mc.thePlayer.isSneaking()) {
            speed = SNEAK_SPEED * WALK_SPEED;
        } else {
            speed = WALK_SPEED;
            useModifiers = true;
        }

        if (useModifiers) {
            if (enoughMovementForSprinting())
                speed *= SPRINTING_SPEED;

            if (mc.thePlayer.isPotionActive(Potion.moveSpeed))
                speed *= 1 + (.2 * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1));

            if (mc.thePlayer.isPotionActive(Potion.moveSlowdown))
                speed = .29;
        }

        return speed;
    }

    public static float getPerfectValue(float noSpeed, float speed1, float speed2) {
        float speed = 0;

        for (PotionEffect effect : mc.thePlayer.getActivePotionEffects()) {
            if (effect.getPotionID() == 1) {
                speed = switch (effect.getAmplifier()) {
                    case 1 -> speed2;
                    case 0 -> speed1;
                    default -> 0;
                };
            }
        }

        if (!mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            speed = noSpeed;
        }

        return speed;
    }

    public static double getVerusLimit(boolean dif) {
        double speed = 0;

        if (mc.thePlayer.fallDistance < 0.2) {
            if (mc.thePlayer.isSprinting()) {
                speed = getPerfectValue(mc.thePlayer.onGround ? 0.54f : 0.46f, mc.thePlayer.onGround ? 0.62f : 0.62f, mc.thePlayer.onGround ? 0.7f : 0.81f);
            } else {
                speed = MoveUtil.baseSpeed() * 1.02f;
            }
        }

        if(dif) {
            if (mc.thePlayer.fallDistance > 0.2) {
                speed = MoveUtil.baseSpeed();
            }
        }

        return speed;
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

    public static void fixMovement(final MoveInputEvent event, final float yaw) {
        final float forward = event.getForward();
        final float strafe = event.getStrafe();

        final double angle = MathHelper.wrapAngleTo180_double(Math.toDegrees(direction(mc.thePlayer.rotationYaw, forward, strafe)));

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
}