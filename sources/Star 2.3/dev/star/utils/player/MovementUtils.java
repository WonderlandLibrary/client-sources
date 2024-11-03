package dev.star.utils.player;

import dev.star.event.impl.player.MoveEvent;
import dev.star.event.impl.player.PlayerMoveUpdateEvent;
import dev.star.utils.Utils;
import dev.star.utils.server.PacketUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

public class MovementUtils implements Utils {

    public static boolean isMoving() {
        if (mc.thePlayer == null) {
            return false;
        }
        return (mc.thePlayer.movementInput.moveForward != 0F || mc.thePlayer.movementInput.moveStrafe != 0F);
    }


    public static double getHorizontalSpeed() {
        return getHorizontalSpeed(mc.thePlayer);
    }

    public static double getHorizontalSpeed(Entity entity) {
        return Math.sqrt(entity.motionX * entity.motionX + entity.motionZ * entity.motionZ);
    }

    public static int getSpeedAmplifier() {
        if(mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            return 1 + mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
        }

        return 0;
    }



    public static void stop() {
        mc.thePlayer.motionX = 0;
        mc.thePlayer.motionZ = 0;
    }

    public static float getMoveYaw(float yaw) {
        Vector2f from = new Vector2f((float) mc.thePlayer.lastTickPosX, (float) mc.thePlayer.lastTickPosZ),
                to = new Vector2f((float) mc.thePlayer.posX, (float) mc.thePlayer.posZ),
                diff = new Vector2f(to.x - from.x, to.y - from.y);

        double x = diff.x, z = diff.y;
        if (x != 0 && z != 0) {
            yaw = (float) Math.toDegrees((Math.atan2(-x, z) + MathHelper.PI2) % MathHelper.PI2);
        }
        return yaw;
    }

    public static void moveFlying(double increase) {
        if (!isMoving()) return;
        final double yaw = direction();
        mc.thePlayer.motionX += -MathHelper.sin((float) yaw) * increase;
        mc.thePlayer.motionZ += MathHelper.cos((float) yaw) * increase;
    }




    public static double getJumpHeight() {
        double jumpY = (double) 0.42F;

        if(mc.thePlayer.isPotionActive(Potion.jump)) {
            jumpY += (double)((float)(mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
        }

        return jumpY;
    }

    public static void jump(MoveEvent event) {
        event.setY(mc.thePlayer.motionY = getJumpHeight());
    }

    public static boolean isGoingDiagonally() {
        return isGoingDiagonally(0.08);
    }

    public static boolean isGoingDiagonally(double amount) {
        return Math.abs(mc.thePlayer.motionX) > amount && Math.abs(mc.thePlayer.motionZ) > amount;
    }


    public static float getPlayerDirection() {
        float direction = mc.thePlayer.rotationYaw;

        boolean forward = Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode());
        boolean back = Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode());
        boolean left = Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode());
        boolean right = Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode());

        if (forward && !back) {
            if (left && !right) {
                direction -= 45;
            } else if (right && !left) {
                direction += 45;
            }
        } else if (back && !forward) {
            if (left && !right) {
                direction -= 135;
            } else if (right && !left) {
                direction += 135;
            } else {
                direction -= 180;
            }
        } else {
            if (left && !right) {
                direction -= 90;
            } else if (right && !left) {
                direction += 90;
            }
        }
        return MathHelper.wrapAngleTo180_float(direction);
    }


    public static double getHorizontalMotion() {
        return Math.hypot(mc.thePlayer.motionX, mc.thePlayer.motionZ);
    }

    public static void strafe(MoveEvent event) {
        strafe(event, getHorizontalMotion());
    }


    public static double direction() {
        float rotationYaw = mc.thePlayer.rotationYaw;

        if (mc.thePlayer.moveForward < 0) {
            rotationYaw += 180;
        }

        float forward = 1;

        if (mc.thePlayer.moveForward < 0) {
            forward = -0.5F;
        } else if (mc.thePlayer.moveForward > 0) {
            forward = 0.5F;
        }

        if (mc.thePlayer.moveStrafing > 0) {
            rotationYaw -= 90 * forward;
        }

        if (mc.thePlayer.moveStrafing < 0) {
            rotationYaw += 90 * forward;
        }

        return Math.toRadians(rotationYaw);
    }

    public static void strafe(MoveEvent event, double speed) {
        float direction = (float) Math.toRadians(getPlayerDirection());



        if (isMoving()) {
            event.setX(mc.thePlayer.motionX = -Math.sin(direction) * speed);
            event.setZ(mc.thePlayer.motionZ = Math.cos(direction) * speed);
        } else {
            event.setX(mc.thePlayer.motionX = 0);
            event.setZ(mc.thePlayer.motionZ = 0);
        }
    }

    public static void partialStrafeMax(double maxStrafe) {
        partialStrafeMax(maxStrafe, mc.thePlayer);
    }

    public static void strafe() {
        strafe(getHorizontalMotion());
    }

    public static void partialStrafeMax(double maxStrafe, Entity entity) {
        double motionX = entity.motionX;
        double motionZ = entity.motionZ;

        strafe(entity);

        entity.motionX = motionX + Math.max(-maxStrafe, Math.min(maxStrafe, entity.motionX - motionX));
        entity.motionZ = motionZ + Math.max(-maxStrafe, Math.min(maxStrafe, entity.motionZ - motionZ));
    }

    public static void partialStrafePercent(double percentage) {
        percentage /= 100;
        percentage = Math.min(1, Math.max(0, percentage));

        double motionX = mc.thePlayer.motionX;
        double motionZ = mc.thePlayer.motionZ;

        strafe();

        mc.thePlayer.motionX = motionX + (mc.thePlayer.motionX - motionX) * percentage;
        mc.thePlayer.motionZ = motionZ + (mc.thePlayer.motionZ - motionZ) * percentage;
    }

    /**
     * Makes the player strafe
     */
    public static void strafe(Entity entity) {
        strafe(speed(), entity);
    }

    /**
     * Makes the player strafe at the specified speed
     */
    public static void strafe(final double speed, Entity entity) {
        if (!isMoving()) {
            return;
        }

        final double yaw = direction();
        entity.motionX = -MathHelper.sin((float) yaw) * speed;
        entity.motionZ = MathHelper.cos((float) yaw) * speed;
    }
    /**
     * Used to get the players speed
     */
    public static double speed() {
        return Math.hypot(mc.thePlayer.motionX, mc.thePlayer.motionZ);
    }


    public static void strafe(final double speed) {
        strafe(speed, mc.thePlayer);
    }


    public static void setSpeed(double moveSpeed, float yaw, double strafe, double forward) {
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
        if (strafe > 0.0D) {
            strafe = 1.0D;
        } else if (strafe < 0.0D) {
            strafe = -1.0D;
        }
        double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
        mc.thePlayer.motionX = forward * moveSpeed * mx + strafe * moveSpeed * mz;
        mc.thePlayer.motionZ = forward * moveSpeed * mz - strafe * moveSpeed * mx;
    }

    public static void setSpeedHypixel(PlayerMoveUpdateEvent event, float moveSpeed, float strafeMotion) {
        float remainder = 1F - strafeMotion;
        if (mc.thePlayer.onGround) {
            setSpeed(moveSpeed);
        } else {
            mc.thePlayer.motionX *= strafeMotion;
            mc.thePlayer.motionZ *= strafeMotion;
            event.setFriction(moveSpeed * remainder);
        }
    }


    public static float ae(float n, float n2, float n3) {
        float n4 = 1.0f;
        if (n2 < 0.0f) {
            n += 180.0f;
            n4 = -0.5f;
        } else if (n2 > 0.0f) {
            n4 = 0.5f;
        }
        if (n3 > 0.0f) {
            n -= 90.0f * n4;
        } else if (n3 < 0.0f) {
            n += 90.0f * n4;
        }
        return n * 0.017453292f;
    }

    public static float n() {
        return ae(mc.thePlayer.rotationYaw, mc.thePlayer.movementInput.moveForward, mc.thePlayer.movementInput.moveStrafe);
    }

    public static void setSpeed(double n) {
        if (n == 0.0) {
            mc.thePlayer.motionZ = 0.0;
            mc.thePlayer.motionX = 0.0;
            return;
        }
        float n3 = n();
        mc.thePlayer.motionX = -Math.sin(n3) * n;
        mc.thePlayer.motionZ = Math.cos(n3) * n;
    }

    public static void setSpeed1(double moveSpeed, float yaw, double strafe, double forward) {
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
        if (strafe > 0.0D) {
            strafe = 1.0D;
        } else if (strafe < 0.0D) {
            strafe = -1.0D;
        }
        double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
        mc.thePlayer.motionX = forward * moveSpeed * mx + strafe * moveSpeed * mz;
        mc.thePlayer.motionZ = forward * moveSpeed * mz - strafe * moveSpeed * mx;
    }

    public static void setSpeed1(double moveSpeed) {
        setSpeed1(moveSpeed, mc.thePlayer.rotationYaw, mc.thePlayer.movementInput.moveStrafe, mc.thePlayer.movementInput.moveForward);
    }



    public static void setSpeed(MoveEvent moveEvent, double moveSpeed, float yaw, double strafe, double forward) {
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
        if (strafe > 0.0D) {
            strafe = 1.0D;
        } else if (strafe < 0.0D) {
            strafe = -1.0D;
        }
        double mx = Math.cos(Math.toRadians((yaw + 90.0F)));
        double mz = Math.sin(Math.toRadians((yaw + 90.0F)));
        moveEvent.setX(forward * moveSpeed * mx + strafe * moveSpeed * mz);
        moveEvent.setZ(forward * moveSpeed * mz - strafe * moveSpeed * mx);
    }

    public static void setSpeed(MoveEvent moveEvent, double moveSpeed) {
        setSpeed(moveEvent, moveSpeed, mc.thePlayer.rotationYaw, mc.thePlayer.movementInput.moveStrafe, mc.thePlayer.movementInput.moveForward);
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = mc.thePlayer.capabilities.getWalkSpeed() * 2.873;
        if (mc.thePlayer.isPotionActive(Potion.moveSlowdown)) {
            baseSpeed /= 1.0 + 0.2 * (mc.thePlayer.getActivePotionEffect(Potion.moveSlowdown).getAmplifier() + 1);
        }
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            baseSpeed *= 1.0 + 0.2 * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return baseSpeed;
    }

    public static void sendFlyingCapabilities(final boolean isFlying, final boolean allowFlying) {
        final PlayerCapabilities playerCapabilities = new PlayerCapabilities();
        playerCapabilities.isFlying = isFlying;
        playerCapabilities.allowFlying = allowFlying;
        PacketUtils.sendPacketNoEvent(new C13PacketPlayerAbilities(playerCapabilities));
    }

    public static double getBaseMoveSpeed2() {
        double baseSpeed = mc.thePlayer.capabilities.getWalkSpeed() * (mc.thePlayer.isSprinting() ? 2.873 : 2.215);
        if (mc.thePlayer.isPotionActive(Potion.moveSlowdown)) {
            baseSpeed /= 1.0 + 0.2 * (mc.thePlayer.getActivePotionEffect(Potion.moveSlowdown).getAmplifier() + 1);
        }
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            baseSpeed *= 1.0 + 0.2 * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return baseSpeed;
    }

    public static double getBaseMoveSpeedStupid() {
        double sped = 0.2873;
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            sped *= 1.0 + 0.2 * (mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return sped;
    }

    public static boolean isOnGround(double height) {
        return !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0, -height, 0)).isEmpty();
    }

    public static float getSpeed() {
        if (mc.thePlayer == null || mc.theWorld == null) return 0;
        return (float) Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
    }

    public static float getMaxFallDist() {
        return mc.thePlayer.getMaxFallHeight() + (mc.thePlayer.isPotionActive(Potion.jump) ? mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1 : 0);
    }

}
