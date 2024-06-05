package de.tired.api.util.rotation;

import de.tired.api.util.math.Vec;
import de.tired.interfaces.IHook;
import de.tired.module.ModuleManager;
import de.tired.module.impl.list.combat.KillAura;
import lombok.experimental.UtilityClass;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
@UtilityClass
public class RotationSender implements IHook {

    private final Vec randomVector = new Vec(0, 0);

    public float[] applyMouseSensitivity(float yaw, float pitch, boolean a3) {
        float sensitivity = MC.gameSettings.mouseSensitivity;
        sensitivity = Math.max(0.001F, sensitivity); //to fix 0.0 sensitivity
        int deltaYaw = (int) ((yaw - Rotations.yaw) / ((sensitivity * (sensitivity >= 0.5 ? sensitivity : 1) / 2)));
        int deltaPitch = (int) ((pitch - Rotations.pitch) / ((sensitivity * (sensitivity >= 0.5 ? sensitivity : 1) / 2))) * -1;

        if (a3) {
            deltaYaw -= deltaYaw % 0.5 + 0.25;
            deltaPitch -= deltaPitch % 0.5 + 0.25;
        }
        float f = sensitivity * 0.6F + 0.2F;
        float f1 = f * f * f * 8F;
        float f2 = (float) deltaYaw * f1;
        float f3 = (float) deltaPitch * f1;

        float endYaw = (float) ((double) Rotations.yaw + (double) f2 * 0.15);
        float endPitch = (float) ((double) Rotations.pitch - (double) f3 * 0.15);


        return new float[]{endYaw, endPitch};
    }
    public float[] getBlockRotations(double x, double y, double z) {
        double xPos = x - MC.thePlayer.posX + 0.5D;
        double zPos = z - MC.thePlayer.posZ + 0.5D;
        double yPos = y - (MC.thePlayer.posY + (double)MC.thePlayer.getEyeHeight());
        double dist = MathHelper.sqrt_double(xPos * xPos + zPos * zPos);
        float yaw = (float)(Math.atan2(zPos, xPos) * 180.0D / Math.PI) - 90.0F;
        return new float[]{yaw, (float)(-(Math.atan2(yPos, dist) * 180.0D / Math.PI))};
    }


    public float updateRotation(float currentRotation, float nextRotation, float rotationSpeed) {
        float f = MathHelper.wrapAngleTo180_float(nextRotation - currentRotation);
        if (f > rotationSpeed) {
            f = rotationSpeed;
        }
        if (f < -rotationSpeed) {
            f = -rotationSpeed;
        }
        return currentRotation + f;
    }

    public float[] getEntityRotations(final Entity entity, boolean smooth, boolean predict, boolean bestVec, boolean heuristics) {


        double x, y, z;

        if (!bestVec || heuristics) {
            x = entity.posX;
            y = entity.posY + entity.getEyeHeight();
            z = entity.posZ;
        } else {
            final Vec3 bestVecValue = vec(MC.thePlayer.getPositionEyes(MC.timer.renderPartialTicks), entity.getEntityBoundingBox());
            x = bestVecValue.xCoord;
            y = bestVecValue.yCoord;
            z = bestVecValue.zCoord;
        }

        if (predict) {
            boolean sprinting = entity.isSprinting();
            boolean sprintingPlayer = MC.thePlayer.isSprinting();

            float walkingSpeed = 0.10000000149011612f; //https://minecraft.fandom.com/wiki/Sprinting

            float sprintMultiplication = sprinting ? 1.25f : walkingSpeed;
            float sprintMultiplicationPlayer = sprintingPlayer ? 1.25f : walkingSpeed;

            float xMultiplication = (float) ((entity.posX - entity.prevPosX) * sprintMultiplication);
            float zMultiplication = (float) ((entity.posZ - entity.prevPosZ) * sprintMultiplication);

            float xMultiplicationPlayer = (float) ((MC.thePlayer.posX - MC.thePlayer.prevPosX) * sprintMultiplicationPlayer);
            float zMultiplicationPlayer = (float) ((MC.thePlayer.posZ - MC.thePlayer.prevPosZ) * sprintMultiplicationPlayer);


            if (xMultiplication != 0.0f && zMultiplication != 0.0f || xMultiplicationPlayer != 0.0f && zMultiplicationPlayer != 0.0f) {
                x += xMultiplication + xMultiplicationPlayer;
                z += zMultiplication + zMultiplicationPlayer;
            }
        }

        final double xDiff = x - MC.thePlayer.posX;
        final double zDiff = z - MC.thePlayer.posZ;
        final double yDiff = y - MC.thePlayer.posY - (double) MC.thePlayer.getEyeHeight();
        final double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);

        float yawAngle = (float) (Math.atan2(zDiff, xDiff) * 180.0 / Math.PI) - 90.0f + ModuleManager.getInstance(KillAura.class).randomYaw;
        float pitchAngle = (float) (-Math.atan2(yDiff, dist) * 180.0 / Math.PI) + ModuleManager.getInstance(KillAura.class).randomPitch;


        float yaw = smooth ? updateRotation(Rotations.beforeYaw, yawAngle, 12) : yawAngle;

        float pitch = smooth ? updateRotation(Rotations.pitch, pitchAngle, 12) : pitchAngle;


        pitch = MathHelper.clamp_float(pitch, -90, 90);
        final float[] mouseSensitivity = applyMouseSensitivity(yaw, pitch, false);
        return new float[] {mouseSensitivity[0], mouseSensitivity[1]};
    }

    public static Vec3 vec(Vec3 look, AxisAlignedBB axisAlignedBB) {
        return new Vec3(MathHelper.clamp_double(look.xCoord, axisAlignedBB.minX, axisAlignedBB.maxX), MathHelper.clamp_double(look.yCoord, axisAlignedBB.minY, axisAlignedBB.maxY), MathHelper.clamp_double(look.zCoord, axisAlignedBB.minZ, axisAlignedBB.maxZ));
    }
}
