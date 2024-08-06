package club.strifeclient.util.player;

import club.strifeclient.Client;
import club.strifeclient.event.implementations.player.MoveEvent;
import club.strifeclient.event.implementations.player.StrafeEvent;
import club.strifeclient.module.implementations.movement.TargetStrafe;
import club.strifeclient.module.implementations.player.Scaffold;
import club.strifeclient.util.math.MathUtil;
import club.strifeclient.util.misc.MinecraftUtil;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.WorldSettings;

import java.security.SecureRandom;

public final class MovementUtil extends MinecraftUtil {

    public static TargetStrafe targetStrafe;

    public static BlockPos getPosition() {
        return new BlockPos(mc.thePlayer.getPositionVector());
    }

    public static boolean canSprint(boolean omni) {
        final Scaffold scaffold = Client.INSTANCE.getModuleManager().getModule(Scaffold.class);
        return ((mc.thePlayer.movementInput.moveForward >= 0.8 || omni) && !mc.thePlayer.isCollidedHorizontally && isMoving() &&
                (!scaffold.isEnabled() || scaffold.isEnabled() && scaffold.sprintSetting.getValue()) &&
                !mc.thePlayer.isSneaking() && (mc.thePlayer.getFoodStats().getFoodLevel() > 6
                || mc.playerController.getCurrentGameType() == WorldSettings.GameType.CREATIVE));
    }

    public static double getRandomHypixelValues() {
        SecureRandom secureRandom = new SecureRandom();
        double value = secureRandom.nextDouble() * (1.0 / System.currentTimeMillis());
        for (int i = 0; i < MathUtil.randomInt(7, 32); i++)
            value *= (1.0 / System.currentTimeMillis());
        return value;
    }

    public static float getRandomHypixelValuesFloat() {
        SecureRandom secureRandom = new SecureRandom();
        float value = secureRandom.nextFloat() * (1.0F / System.currentTimeMillis());
        for (int i = 0; i < MathUtil.randomInt(7, 32); i++)
            value *= (1.0 / System.currentTimeMillis());
        return value;
    }

    public static double getBaseMovementSpeed() {
        return getMovementSpeed(0.2873);
    }

    public static double getMovementSpeed(double baseSpeed) {
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }

    public static float getMinFallDist(float baseDist) {
        if (mc.thePlayer.isPotionActive(Potion.jump)) {
            int amplifier = mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier();
            baseDist += 0.1 * (amplifier + 1);
        }
        return baseDist;
    }

    public static double getBaseJumpHeight() {
        return getJumpHeight(0.42);
    }

    public static double getJumpHeight(double baseHeight) {
        if (mc.thePlayer.isPotionActive(Potion.jump)) {
            int amplifier = mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier();
            baseHeight += 0.1 * (amplifier + 1);
        }
        return baseHeight;
    }

    public static float getDirection() {
        return getDirection(mc.thePlayer.movementInput.moveForward, mc.thePlayer.movementInput.moveStrafe, mc.thePlayer.rotationYaw);
    }

    public static float getDirection(float yaw) {
        return getDirection(mc.thePlayer.movementInput.moveForward, mc.thePlayer.movementInput.moveStrafe, yaw);
    }

    public static float getDirection(float forward, float strafe, float yaw) {
        float result = 90F * (forward > 0 ? 0.5F : forward < 0 ? -0.5F : 1);
        if (forward < 0)
            yaw += 180;
        if(strafe > 0)
            yaw -= result;
        if (strafe < 0)
            yaw += result;
        return MathHelper.deg2Rad * yaw;
    }

    public static double[] getSpeed(float direction, double speed) {
        return new double[]{-MathHelper.sin(direction) * speed, MathHelper.cos(direction) * speed};
    }

    public static void setSpeed(MoveEvent e, double speed) {
        setSpeed(e, speed, mc.thePlayer.movementInput.moveForward, mc.thePlayer.movementInput.moveStrafe, mc.thePlayer.rotationYaw);
    }

    public static void setSpeed(MoveEvent e, double speed, float forward, float strafe, float yaw) {
        float direction = getDirection(forward, strafe, yaw);
        double[] pos = getSpeed(direction, speed);
        e.x = pos[0];
        e.z = pos[1];
    }

    public static void strafe(StrafeEvent e, double friction, float strafeComponent) {
        if (targetStrafe == null)
            targetStrafe = Client.INSTANCE.getModuleManager().getModule(TargetStrafe.class);
        if (!targetStrafe.strafe(e, friction, strafeComponent))
            e.setMotionPartialStrafe(friction, strafeComponent);
    }

    public static void strafeMove(MoveEvent e, double speed) {
        if (targetStrafe == null)
            targetStrafe = Client.INSTANCE.getModuleManager().getModule(TargetStrafe.class);
        if (!targetStrafe.strafeMove(e, speed))
            setSpeed(e, speed);
    }

    public static boolean isMoving() {
        return mc.thePlayer.movementInput.moveForward != 0 || mc.thePlayer.movementInput.moveStrafe != 0;
    }

    public static boolean isOnGround() {
        return mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically;
    }

    public static boolean isMovingOnGround() {
        return isMoving() && isOnGround();
    }

    public static boolean isOnGround(double negate) {
        return !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0, -negate, 0)).isEmpty();
    }

    public static boolean isInLiquid() {
        return mc.thePlayer.isInWater() || mc.thePlayer.isInLava();
    }
}
