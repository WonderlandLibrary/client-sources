package koks.utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

/**
 * @author avox | lmao | kroko
 * @created on 02.09.2020 : 23:25
 */
public class RotationUtil {

    private final Minecraft mc = Minecraft.getMinecraft();
    private final RandomUtil randomUtil = new RandomUtil();
    private final TimeUtil timeUtil = new TimeUtil();
    private double addRotation;

    public Vec3 bestRotationVector(Entity entity) {
        Vec3 playerVector = mc.thePlayer.getPositionEyes(1.0F);
        Vec3 bestVector = new Vec3(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);

        int range = 10;
        float width = entity.width / (range * 2.5F);

        for (int y = 0; y < 30; y++) {
            for (int x = -range; x < range; x++) {
                for (int z = -range; z < range; z++) {
                    Vec3 currentVector = new Vec3(entity.posX + width * x, entity.posY + (entity.getEyeHeight() / (range * 2.5F)) * y, entity.posZ + width * z);

                    if (mc.thePlayer.getEntityWorld().rayTraceBlocks(playerVector, currentVector) != null) continue;

                    if (playerVector.distanceTo(currentVector) < playerVector.distanceTo(bestVector))
                        bestVector = currentVector;
                }
            }
        }

        return bestVector;
    }

    public float[] faceEntityWithVector(Entity entity, float currentYaw, float currentPitch, boolean smooth, boolean failing) {
        try {
            if (entity instanceof EntityLivingBase && mc.thePlayer.getDistanceToEntity(entity) >= 0.2) {
                Vec3 rotations = bestRotationVector(entity);
                double x = rotations.xCoord - mc.thePlayer.posX;
                double y = rotations.yCoord - mc.thePlayer.posY - mc.thePlayer.getEyeHeight();
                double z = rotations.zCoord - mc.thePlayer.posZ;

                double distance = mc.thePlayer.getDistanceToEntity(entity);
                double angle = MathHelper.sqrt_double(x * x + z * z);
                float speed = (float) ((22 + distance * (5 + randomUtil.randomGaussian(1.25F))) + randomUtil.randomGaussian(4));
                if (timeUtil.hasReached(500 + randomUtil.randomLong(-200, 500))) {
                    addRotation = randomUtil.randomGaussian(5 / distance);
                    timeUtil.reset();
                }
                float yawAngle = (float) (MathHelper.func_181159_b(z, x) * 180.0D / Math.PI) - 90.0F;
                float pitchAngle = (float) (-(MathHelper.func_181159_b(y, angle) * 180.0D / Math.PI));
                yawAngle += addRotation;
                pitchAngle += addRotation;
                float yaw = updateRotation(currentYaw, failing ? currentYaw - 10 : yawAngle, smooth ? speed : 1000);
                float pitch = updateRotation(currentPitch, failing ? currentPitch + 10 : pitchAngle, smooth ? speed : 1000);

                float[] yawDiff = calculateDiff(currentYaw, yaw);
                float[] pitchDiff = calculateDiff(currentPitch, pitch);
                float[] fixed = fixedSensitivity(mc.gameSettings.mouseSensitivity, yawDiff[0], pitchDiff[0]);
                yawDiff[0] = fixed[0];
                pitchDiff[0] = fixed[1];
                if (yawDiff[1] == 1) {
                    if (yaw > currentYaw) currentYaw -= yawDiff[0];
                    else if (yaw < currentYaw) currentYaw += yawDiff[0];
                } else {
                    if (yaw > currentYaw) currentYaw += yawDiff[0];
                    else if (yaw < currentYaw) currentYaw -= yawDiff[0];
                }
                if (pitchDiff[1] == 1) {
                    if (pitch > currentPitch) currentPitch -= pitchDiff[0];
                    else if (pitch < currentPitch) currentPitch += pitchDiff[0];
                } else {
                    if (pitch > currentPitch) currentPitch += pitchDiff[0];
                    else if (pitch < currentPitch) currentPitch -= pitchDiff[0];
                }
            }
            return new float[]{currentYaw, currentPitch};

        } catch (Exception e) {
            return new float[]{0, 0};
        }
    }

    public float[] faceEntity(Entity entity, float currentYaw, float currentPitch, boolean smooth) {
        double x = entity.posX - mc.thePlayer.posX;
        double y = entity.posY + (double) entity.getEyeHeight() - (mc.thePlayer.posY + (double) mc.thePlayer.getEyeHeight());
        double z = entity.posZ - mc.thePlayer.posZ;

        double distance = mc.thePlayer.getDistanceToEntity(entity);
        double angle = MathHelper.sqrt_double(x * x + z * z);
        float yawAngle = (float) (MathHelper.func_181159_b(z, x) * 180.0D / Math.PI) - 90.0F;
        float pitchAngle = (float) (-(MathHelper.func_181159_b(y, angle) * 180.0D / Math.PI));
        float speed = (float) ((22 + distance * (5 + randomUtil.randomGaussian(1.25F))) + randomUtil.randomGaussian(4));

        float yaw = updateRotation(currentYaw, yawAngle, smooth ? speed : 1000);
        float pitch = updateRotation(currentPitch, pitchAngle, smooth ? speed : 1000);

        float[] yawDiff = calculateDiff(currentYaw, yaw);
        float[] pitchDiff = calculateDiff(currentPitch, pitch);
        float[] fixed = fixedSensitivity(mc.gameSettings.mouseSensitivity, yawDiff[0], pitchDiff[0]);
        yawDiff[0] = fixed[0];
        pitchDiff[0] = fixed[1];
        float der = yawDiff[0] / 0.15f;
        float der2 = pitchDiff[0] / 0.15f;
        if (der > 0.01 && der < 0.99) {
            System.out.println("yaw " + der);
        }
        if (der2 > 0.01 && der2 < 0.99) {
            System.out.println("pitch " + der2);
        }
        if (yawDiff[1] == 1) {
            if (yaw > currentYaw) currentYaw -= yawDiff[0];
            else if (yaw < currentYaw) currentYaw += yawDiff[0];
        } else {
            if (yaw > currentYaw) currentYaw += yawDiff[0];
            else if (yaw < currentYaw) currentYaw -= yawDiff[0];
        }
        if (pitchDiff[1] == 1) {
            if (pitch > currentPitch) currentPitch -= pitchDiff[0];
            else if (pitch < currentPitch) currentPitch += pitchDiff[0];
        } else {
            if (pitch > currentPitch) currentPitch += pitchDiff[0];
            else if (pitch < currentPitch) currentPitch -= pitchDiff[0];
        }
        return new float[]{currentYaw, currentPitch};
    }

    public float[] faceBlock(BlockPos pos, boolean scaffoldFix, float currentYaw, float currentPitch, float speed) {
        double x = (pos.getX() + (scaffoldFix ? 0.5F : 0.0F)) - mc.thePlayer.posX;
        double y = (pos.getY() - (scaffoldFix ? 3.0F : 0.0F)) - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight());
        double z = (pos.getZ() + (scaffoldFix ? 0.5F : 0.0F)) - mc.thePlayer.posZ;

        double calculate = MathHelper.sqrt_double(x * x + z * z);
        float calcYaw = (float) (MathHelper.func_181159_b(z, x) * 180.0D / Math.PI) - 90.0F;
        float calcPitch = (float) -(MathHelper.func_181159_b(y, calculate) * 180.0D / Math.PI);
        float finalPitch = calcPitch >= 90 ? 90 : calcPitch;
        float yaw = updateRotation(currentYaw, calcYaw, speed);
        float pitch = updateRotation(currentPitch, finalPitch, speed);

        float sense = mc.gameSettings.mouseSensitivity * 0.8F;
        float fix = sense * sense * sense * 1.2F;
        yaw -= yaw % fix;
        pitch -= pitch % fix;

        return new float[]{yaw, pitch};
    }

    public float updateRotation(float current, float intended, float increment) {
        float f = MathHelper.wrapAngleTo180_float(intended - current);

        if (f > increment) {
            f = increment;
        }

        if (f < -increment) {
            f = -increment;
        }

        return current + f;
    }

    public float[] calculateDiff(float v1, float v2) {
        float y = Math.abs(v1 - v2);
        if (y < 0) y += 360;
        if (y > 360) y -= 360;
        float y1 = 360 - y;
        float oneoranother = 0;
        if (y > y1) oneoranother++;
        if (y > y1) y = y1;
        return new float[]{y, oneoranother};
    }

    public float[] fixedSensitivity(float sensitivity, float yawdiff, float pitchdiff) {
        float f = sensitivity * 0.6F + 0.2F;
        float gcd = f * f * f * 8f;
        yawdiff = (int) ((yawdiff) / gcd / 0.15f);
        pitchdiff = (int) ((pitchdiff) / gcd / 0.15f);
        yawdiff = yawdiff * gcd * 0.15f;
        pitchdiff = pitchdiff * gcd * 0.15f;
        return new float[]{yawdiff, pitchdiff};
    }

    /*        float sense = mc.gameSettings.mouseSensitivity * 0.8F;
        float fix = sense * sense * sense * 1.2F;
        yaw -= yaw % fix;
        pitch -= pitch % fix;*/

}