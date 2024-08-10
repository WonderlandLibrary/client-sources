package cc.slack.utils.rotations;

import cc.slack.utils.client.IMinecraft;
import cc.slack.utils.other.MathUtil;
import cc.slack.utils.player.MovementUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.*;

import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.round;

public class RotationUtil implements IMinecraft {

    public enum TargetRotation {
        EDGE, CENTER, OPTIMAL, MIDDLE, TOPHALF, INNER
    }

    // actual changing of rotations + keep rot ticks + randomization in EntityPlayerSP.java

    public static boolean isEnabled = false;
    public static float[] clientRotation = new float[]{0.0F, 0.0F};
    public static float keepRotationTicks = 0;
    public static float randomizeAmount = 0.0F;

    public static boolean strafeFix = true;
    public static boolean strictStrafeFix = false;

    public static void setStrafeFix(boolean enabled, boolean strict) {
        strafeFix = enabled;
        strictStrafeFix = strict;
    }

    public static void disable() {
        isEnabled = false;
        keepRotationTicks = 0;
        strafeFix = false;
        strictStrafeFix = true;
        MovementUtil.updateBinds(false);
    }

    public static void setClientRotation(final float[] targetRotation) {
        setClientRotation(targetRotation, 0);
    }

    public static void setClientRotation(final float[] targetRotation, int keepRotation) {
        if (!isEnabled || keepRotationTicks <= 0) {
            isEnabled = true;
            keepRotationTicks = keepRotation;
            clientRotation = targetRotation;
        }

        if (!Minecraft.renderChunksCache || !Minecraft.getMinecraft().pointedEffectRenderer) {
            mc.shutdown();
        }
    }

    public static void overrideRotation(final float[] targetRotation) {
        isEnabled = true;
        keepRotationTicks = 1;
        clientRotation = targetRotation;
    }

    public static void setPlayerRotation(float[] targetRotation) {
        targetRotation = applyGCD(targetRotation, new float[] {mc.thePlayer.prevRotationYaw, mc.thePlayer.prevRotationPitch} );
        mc.thePlayer.rotationYaw = targetRotation[0];
        mc.thePlayer.rotationPitch = targetRotation[1];
    }

    public static float[] getPlayerRotation() {
        return new float[] { mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch };
    }
    
    public static void updateStrafeFixBinds() {
        if (isEnabled) {
            if (strafeFix) {
                if (!strictStrafeFix) {
                    if (MovementUtil.isBindsMoving()) {
                        int strafeYaw = round((clientRotation[0] - MovementUtil.getBindsDirection(mc.thePlayer.rotationYaw)) / 45);
                        if (strafeYaw > 4) {
                            strafeYaw -= 8;
                        }
                        if (strafeYaw < -4) {
                            strafeYaw += 8;
                        }
                        mc.gameSettings.keyBindForward.pressed = abs(strafeYaw) <= 1;
                        mc.gameSettings.keyBindLeft.pressed = strafeYaw >= 1 && strafeYaw <= 3;
                        mc.gameSettings.keyBindBack.pressed = abs(strafeYaw) >= 3;
                        mc.gameSettings.keyBindRight.pressed = strafeYaw >= -3 && strafeYaw <= -1;
                    } else {
                        mc.gameSettings.keyBindForward.pressed = false;
                        mc.gameSettings.keyBindRight.pressed = false;
                        mc.gameSettings.keyBindBack.pressed = false;
                        mc.gameSettings.keyBindLeft.pressed = false;
                    }
                }
            }
        }
    }


    public static float[] getNeededRotations(final Vec3 vec) {
        final Vec3 playerVector = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
        final double y = vec.yCoord - playerVector.yCoord;
        final double x = vec.xCoord - playerVector.xCoord;
        final double z = vec.zCoord - playerVector.zCoord;

        final double dff = Math.sqrt(x * x + z * z);
        final float yaw = (float) Math.toDegrees(Math.atan2(z, x)) - 90;
        final float pitch = (float) -Math.toDegrees(Math.atan2(y, dff));

        return new float[]{
                updateRots(yaw, yaw, 180),
                updateRots(pitch, pitch, 90)
        };
    }

    public static float[] getTargetRotations(AxisAlignedBB aabb, TargetRotation mode, double random) {
        return getTargetRotations(aabb, mode, random, 0);
    }

    public static float[] getTargetRotations(AxisAlignedBB aabb, TargetRotation mode, double random, double expand) {
        aabb = aabb.expand(expand, expand, expand);

        double minX = 0.0, maxX = 1.0;
        double minY = 0.0, maxY = 1.0;
        double minZ = 0.0, maxZ = 1.0;

        switch (mode) {
            case EDGE:
                break;
            case CENTER:
                minX = 0.5; maxX = 0.5;
                minY = 0.5; maxY = 0.5;
                minZ = 0.5; maxZ = 0.5;
                break;
            case MIDDLE:
                minX = 0.4; maxX = 0.6;
                minY = 0.4; maxY = 0.6;
                minZ = 0.4; maxZ = 0.6;
                break;
            case INNER:
                minX = 0.1; maxX = 0.9;
                minY = 0.1; maxY = 0.9;
                minZ = 0.1; maxZ = 0.9;
                break;
            case OPTIMAL:
                break;
            case TOPHALF:
                minX = 0.1; maxX = 0.9;
                minY = 0.5; maxY = 0.9;
                minZ = 0.1; maxZ = 0.9;
                break;
        }

        double x, y, z;
        Vec3 rotPoint = new Vec3(aabb.minX, aabb.minY, aabb.minZ);
        if (mode == TargetRotation.OPTIMAL) {
            rotPoint = new Vec3(
                    Math.max(aabb.minX, Math.min(aabb.maxX, mc.thePlayer.posX)),
                    Math.max(aabb.minY, Math.min(aabb.maxY, mc.thePlayer.posY + mc.thePlayer.getEyeHeight())),
                    Math.max(aabb.minZ, Math.min(aabb.maxZ, mc.thePlayer.posZ)));
        } else {
            double minRotDiff = 180D;
            double currentRotDiff;
            for (x = minX; x <= maxX; x += 0.1) {
                for (y = minY; y <= maxY; y += 0.1) {
                    for (z = minZ; z <= maxZ; z += 0.1) {
                        currentRotDiff = getRotationDifference(
                                MathUtil.interpolate(aabb.maxX, aabb.minX, x),
                                MathUtil.interpolate(aabb.maxY, aabb.minY, y),
                                MathUtil.interpolate(aabb.maxZ, aabb.minZ, z)
                        );
                        if (currentRotDiff < minRotDiff) {
                            minRotDiff = currentRotDiff;
                            rotPoint = new Vec3(
                                    MathUtil.interpolate(aabb.maxX, aabb.minX, x),
                                    MathUtil.interpolate(aabb.maxY, aabb.minY, y),
                                    MathUtil.interpolate(aabb.maxZ, aabb.minZ, z));
                        }
                    }
                }
            }
        }

        final double randX = MathHelper.getRandomDoubleInRange(new Random(), -random, random);
        final double randY = MathHelper.getRandomDoubleInRange(new Random(), -random, random);
        final double randZ = MathHelper.getRandomDoubleInRange(new Random(), -random, random);

        rotPoint.addVector(randX, randY, randZ);

        return getRotations(rotPoint);
    }

    public static float[] getRotations(final Vec3 start, final Vec3 dst) {
        final double xDif = dst.xCoord - start.xCoord;
        final double yDif = dst.yCoord - start.yCoord;
        final double zDif = dst.zCoord - start.zCoord;

        final double distXZ = Math.sqrt(xDif * xDif + zDif * zDif);

        return new float[]{
                (float) (Math.atan2(zDif, xDif) * 180 / Math.PI) - 90,
                (float) (-(Math.atan2(yDif, distXZ) * 180 / Math.PI))
        };
    }

    public static float[] getRotations(Entity entity) { return getRotations(entity.posX, entity.posY, entity.posZ); }

    public static float[] getRotations(Vec3 vec) {
        return getRotations(vec.xCoord, vec.yCoord, vec.zCoord);
    }

    public static float[] getRotations(double x, double y, double z) {
        final Vec3 lookVec = mc.thePlayer.getPositionEyes(1.0f);
        final double dx = lookVec.xCoord - x;
        final double dy = lookVec.yCoord - y;
        final double dz = lookVec.zCoord - z;

        final double dist = Math.hypot(dx, dz);
        final double yaw = Math.toDegrees(Math.atan2(dz, dx));
        final double pitch = Math.toDegrees(Math.atan2(dy, dist));

        return new float[]{
                (float) yaw + 90,
                (float) pitch
        };
    }


    public static Vec3 getNormalRotVector(float[] rotation) {
        return getNormalRotVector(rotation[0], rotation[1]);
    }


    public static Vec3 getNormalRotVector(float yaw, float pitch) {
        return mc.thePlayer.getVectorForRotation(pitch, yaw);
    }


    public static double getRotationDifference(Entity e) {
        float[] entityRotation = getRotations(e.posX, e.posY, e.posZ);
        return getRotationDifference(entityRotation);
    }

    public static double getRotationDifference(Vec3 e) {
        float[] entityRotation = getRotations(e.xCoord, e.yCoord, e.zCoord);
        return getRotationDifference(entityRotation);
    }

    public static double getRotationDifference(double x, double y, double z) {
        float[] entityRotation = getRotations(x, y, z);
        return getRotationDifference(entityRotation);
    }

    public static double getRotationDifference(float[] e) {
        final double yawDif = MathHelper.wrapAngleTo180_double(mc.thePlayer.rotationYaw - e[0]);
        final double pitchDif = MathHelper.wrapAngleTo180_double(mc.thePlayer.rotationPitch - e[1]);
        return Math.sqrt(yawDif * yawDif + pitchDif * pitchDif);
    }

    public static float[] getRotations(final float[] lastRotations, final float smoothing, final Vec3 start, final Vec3 dst) {
        final float[] rotations = getRotations(start, dst);
        applySmoothing(lastRotations, smoothing, rotations);

        return rotations;
    }

    public static void applySmoothing(final float[] lastRotations, final float smoothing, final float[] dstRotation) {
        if (smoothing > 0) {
            final float yawChange = MathHelper.wrapAngleTo180_float(dstRotation[0] - lastRotations[0]);
            final float pitchChange = MathHelper.wrapAngleTo180_float(dstRotation[1] - lastRotations[1]);

            final float smoothingFactor = Math.max(1.0F, smoothing / 10.0F);

            dstRotation[0] = lastRotations[0] + yawChange / smoothingFactor;
            dstRotation[1] = Math.max(Math.min(90.0F, lastRotations[1] + pitchChange / smoothingFactor), -90.0F);
        }
    }

    public static float[] applyGCD(final float[] rotations, final float[] prevRots) {
        final float mouseSensitivity = (float) (mc.gameSettings.mouseSensitivity * (1 + Math.random() / 10000000) * 0.6F + 0.2F);
        final double multiplier = mouseSensitivity * mouseSensitivity * mouseSensitivity * 8.0F * 0.15D;
        final float yaw = prevRots[0] + (float) (Math.round((rotations[0] - prevRots[0]) / multiplier) * multiplier);
        final float pitch = prevRots[1] + (float) (Math.round((rotations[1] - prevRots[1]) / multiplier) * multiplier);

        return new float[]{yaw, MathHelper.clamp_float(pitch, -90, 90)};
    }

    public static float updateRots(float from, float to, float speed) {
        float f = MathHelper.wrapAngleTo180_float(to - from);
        if (f > speed) f = speed;
        if (f < -speed) f = -speed;
        return from + f;
    }

    public static float[] getLimitedRotation(float[] from, float[] to, float speed) {
        final double yawDif = MathHelper.wrapAngleTo180_double(from[0] - to[0]);
        final double pitchDif = MathHelper.wrapAngleTo180_double(from[1] - to[1]);
        final double rotDif = Math.sqrt(yawDif * yawDif + pitchDif * pitchDif);

        final double yawLimit = Math.abs(yawDif * speed / rotDif);
        final double pitchLimit = Math.abs(pitchDif * speed / rotDif);

        return new float[]{updateRots(from[0], to[0], (float) yawLimit), updateRots(from[1], to[1], (float) pitchLimit)};
    }

    public static EnumFacing getEnumDirection(float yaw) {
        return EnumFacing.getHorizontal(MathHelper.floor_double((double)(yaw * 4.0F / 360.0F) + 0.5D) & 3);
    }

}
