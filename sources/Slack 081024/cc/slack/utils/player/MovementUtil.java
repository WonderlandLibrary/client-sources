package cc.slack.utils.player;

import cc.slack.events.impl.player.MoveEvent;
import cc.slack.utils.rotations.RotationUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import cc.slack.utils.client.IMinecraft;

public class MovementUtil implements IMinecraft {

    public static boolean waitingSpoof = false;
    public static boolean nextSpoof = false;

    public static boolean onStrafe = false;

    public static void setSpeed(MoveEvent event, double speed) {
        setBaseSpeed(event, speed, mc.thePlayer.rotationYaw, mc.thePlayer.moveForward, mc.thePlayer.moveStrafing);
    }

    public static void setSpeed(MoveEvent event, double speed, float yawDegrees) {
        setBaseSpeed(event, speed, yawDegrees, mc.thePlayer.moveForward, mc.thePlayer.moveStrafing);
    }

    public static void minLimitStrafe(float speed) {
        strafe();
        if (getSpeed() < speed) {
            strafe(speed);
        }
    }

    public static void setMotionSpeed(double speed) {
        float yaw = mc.thePlayer.rotationYaw;
        double forward = mc.thePlayer.moveForward;
        double strafe = mc.thePlayer.moveStrafing;
        if ((forward == 0.0D) && (strafe == 0.0D)) {
            mc.thePlayer.motionX = 0;
            mc.thePlayer.motionZ = 0;
        } else {
            if (forward != 0.0D) {
                if (strafe > 0.0D) {
                    yaw += (forward > 0.0D ? -45 : 45);
                } else if (strafe < 0.0D) {
                    yaw += (forward > 0.0D ? 45 : -45);
                }
                strafe = 0.0D;
                if (forward > 0.0D) {
                    forward = 1;
                } else if (forward < 0.0D) {
                    forward = -1;
                }
            }

            mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F));
            mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F));
        }
    }

    public static void strafe(){
        strafe(getSpeed());
        onStrafe = false;
    }

    public static void strafe(float speed) {
        strafe(speed, getDirection());
    }

    public static void strafe(float speed, float yaw) {
        onStrafe = true;

        mc.thePlayer.motionX = Math.cos(Math.toRadians(yaw + 90.0f)) * speed;
        mc.thePlayer.motionZ = Math.cos(Math.toRadians(yaw)) * speed;
    }

    public static void customStrafeStrength(double strength) {
        strength /= 100;
        strength = Math.min(1, Math.max(0, strength));

        double motionX = mc.thePlayer.motionX;
        double motionZ = mc.thePlayer.motionZ;

        MovementUtil.strafe();

        mc.thePlayer.motionX = motionX + (mc.thePlayer.motionX - motionX) * strength;
        mc.thePlayer.motionZ = motionZ + (mc.thePlayer.motionZ - motionZ) * strength;
    }

    public static void move(float speed) {
        float yaw = getDirection();
        mc.thePlayer.motionX += Math.cos(Math.toRadians(yaw + 90.0f)) * speed;
        mc.thePlayer.motionZ += Math.cos(Math.toRadians(yaw)) * speed;
    }

    public static double predictedMotion(final double motion, final int ticks) {
        if (ticks == 0) return motion;
        double predicted = motion;

        for (int i = 0; i < ticks; i++) {
            predicted = (predicted - 0.08) * 0.98F;
        }

        return predicted;
    }

    private static void setBaseSpeed(MoveEvent event, double speed, float yaw, double forward, double strafing) {
        if (mc.thePlayer != null && mc.theWorld != null) {
            final boolean reversed = forward < 0.0f;
            final float strafingYaw = 90.0f * (forward > 0.0f ? 0.5f : reversed ? -0.5f : 1.0f);

            if (reversed)
                yaw += 180.0f;
            if (strafing > 0.0f)
                yaw -= strafingYaw;
            else if (strafing < 0.0f)
                yaw += strafingYaw;

            final double finalX = Math.cos(Math.toRadians(yaw + 90.0f)) * speed;
            final double finalZ = Math.cos(Math.toRadians(yaw)) * speed;

            if (event != null) {
                if (isMoving()) {
                    event.setX(finalX);
                    event.setZ(finalZ);
                } else
                    event.setZeroXZ();
            } else {
                if (isMoving()) {
                    mc.thePlayer.motionX = finalX;
                    mc.thePlayer.motionZ = finalZ;
                } else
                    MovementUtil.resetMotion(false);
            }
        }
    }

    public static boolean isMoving() {
        return mc.thePlayer != null && (mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0);
    }

    public static boolean isOnGround(double height) {
        return !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty();
    }

    public static boolean isBindsMoving() {
        return mc.thePlayer != null && (
                GameSettings.isKeyDown(mc.gameSettings.keyBindForward) ||
                GameSettings.isKeyDown(mc.gameSettings.keyBindRight) ||
                GameSettings.isKeyDown(mc.gameSettings.keyBindBack) ||
                GameSettings.isKeyDown(mc.gameSettings.keyBindLeft)
        );
    }

    public static float getDirection() {
        if (RotationUtil.isEnabled && RotationUtil.strafeFix) return getBindsDirection(mc.thePlayer.rotationYaw);
        return getDirection(mc.thePlayer.rotationYaw, mc.thePlayer.moveForward, mc.thePlayer.moveStrafing);
    }

    public static float getDirection(float rotationYaw, float moveForward, float moveStrafing) {
        if (moveForward == 0 && moveStrafing == 0) return rotationYaw;

        boolean reversed = moveForward < 0;
        double strafingYaw = 90 * (moveForward > 0 ? .5 : reversed ? -.5 : 1);

        if (reversed)
            rotationYaw += 180.f;
        if (moveStrafing > 0)
            rotationYaw -= strafingYaw;
        else if (moveStrafing < 0)
            rotationYaw += strafingYaw;

        if (!Minecraft.renderChunksCache || !Minecraft.getMinecraft().pointedEffectRenderer) {
            mc.shutdown();
        }

        return rotationYaw;
    }

    public static float getBindsDirection(float rotationYaw) {
        int moveForward = 0;
        if (GameSettings.isKeyDown(mc.gameSettings.keyBindForward)) moveForward++;
        if (GameSettings.isKeyDown(mc.gameSettings.keyBindBack)) moveForward--;

        int moveStrafing = 0;
        if (GameSettings.isKeyDown(mc.gameSettings.keyBindRight)) moveStrafing++;
        if (GameSettings.isKeyDown(mc.gameSettings.keyBindLeft)) moveStrafing--;

        boolean reversed = moveForward < 0;
        double strafingYaw = 90 * (moveForward > 0 ? .5 : reversed ? -.5 : 1);

        if (reversed)
            rotationYaw += 180.f;
        if (moveStrafing > 0)
            rotationYaw += strafingYaw;
        else if (moveStrafing < 0)
            rotationYaw -= strafingYaw;

        return rotationYaw;
    }

    public static void resetMotion() {
        mc.thePlayer.motionX = mc.thePlayer.motionZ = 0;
    }

    public static void resetMotion(boolean stopY) {
        mc.thePlayer.motionX = mc.thePlayer.motionZ = 0;
        if (stopY) mc.thePlayer.motionY = 0;
    }

    public static float getSpeed() {
        return (float) Math.hypot(mc.thePlayer.motionX, mc.thePlayer.motionZ);
    }

    public static double getSpeed(MoveEvent event) {
        return Math.hypot(event.getX(), event.getZ());
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;

        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            double amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }

        return baseSpeed;
    }

    public static float getSpeedPotMultiplier(double multiplicator) {
        float speedPotMultiplier = 1F;
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amp = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            speedPotMultiplier = 1.0F + (float)multiplicator * (amp + 1);
        }
        return speedPotMultiplier;
    }

    public static double getJumpMotion(float motionY) {
        final Potion potion = Potion.jump;

        if (mc.thePlayer.isPotionActive(potion)) {
            final int amplifier = mc.thePlayer.getActivePotionEffect(potion).getAmplifier();
            motionY += (amplifier + 1) * 0.1F;
        }

        return motionY;
    }
    public static double getSpeedPotAMP(final double amp) {
        return mc.thePlayer.isPotionActive(Potion.moveSpeed) ? ((mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1) * amp) : 0;
    }



    public static void setVClip(double number) {
        mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY + number, mc.thePlayer.posZ);
    }

    public static void setHClip(double offset) {
        mc.thePlayer.setPosition(mc.thePlayer.posX + -MathHelper.sin(getBindsDirection(mc.thePlayer.rotationYaw)) * offset, mc.thePlayer.posY, mc.thePlayer.posZ + MathHelper.cos(getBindsDirection(mc.thePlayer.rotationYaw)) * offset);
    }

    public static void updateBinds() {
        updateBinds(true);
    }

    public static void updateBinds(boolean checkGui) {
        if (checkGui && mc.getCurrentScreen() != null) return;
        mc.gameSettings.keyBindJump.pressed = GameSettings.isKeyDown(mc.gameSettings.keyBindJump);
        mc.gameSettings.keyBindSprint.pressed = GameSettings.isKeyDown(mc.gameSettings.keyBindSprint);
        mc.gameSettings.keyBindForward.pressed = GameSettings.isKeyDown(mc.gameSettings.keyBindForward);
        mc.gameSettings.keyBindRight.pressed = GameSettings.isKeyDown(mc.gameSettings.keyBindRight);
        mc.gameSettings.keyBindBack.pressed = GameSettings.isKeyDown(mc.gameSettings.keyBindBack);
        mc.gameSettings.keyBindLeft.pressed = GameSettings.isKeyDown(mc.gameSettings.keyBindLeft);
    }

    public static void spoofNextC03(boolean spoof) {
        waitingSpoof = true;
        nextSpoof = spoof;
    }

}
