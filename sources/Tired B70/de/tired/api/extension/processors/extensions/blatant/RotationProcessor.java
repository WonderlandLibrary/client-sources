package de.tired.api.extension.processors.extensions.blatant;

import de.tired.api.extension.AbstractExtension;
import de.tired.api.util.rotation.Rotations;
import de.tired.interfaces.IHook;
import lombok.Getter;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.util.Random;

public class RotationProcessor extends AbstractExtension {

    @Getter
    private final float sense;

    public RotationProcessor() {
        final float gcd = MC.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        sense = gcd * gcd * gcd * 1.2f;
    }

    private Vec3 getBestVector(Vec3 lookLocation, AxisAlignedBB axisAlignedBB) {
        return new Vec3(MathHelper.clamp_double(lookLocation.xCoord, axisAlignedBB.minX, axisAlignedBB.maxX), MathHelper.clamp_double(lookLocation.yCoord, axisAlignedBB.minY, axisAlignedBB.maxY), MathHelper.clamp_double(lookLocation.zCoord, axisAlignedBB.minZ, axisAlignedBB.maxZ));
    }

    public float[] executeRotation(boolean bestVec, Entity tar, float[] prevRotations) {

        double x, y, z;

        double[] clearRotations = getClearRotations(tar);

        double[] bestVecRotation = getBestVecRotation(tar);

        if (!bestVec) {
            x = clearRotations[0];
            y = clearRotations[1];
            z = clearRotations[2];
        } else {
            x = bestVecRotation[0];
            y = bestVecRotation[1];
            z = bestVecRotation[2];
        }


        final double math = MathHelper.sqrt_double(x * x + z * z);


        float maxRandomFactor = 0;

        if (tar.getDistanceToEntity(MC.thePlayer) < .2) {
            maxRandomFactor = 2;
        } else {
            maxRandomFactor = 7;
        }


        Rotations.pitchDifference = Math.max(Rotations.pitchDifference, maxRandomFactor);

        Rotations.yawDifference = Math.max(Rotations.yawDifference, maxRandomFactor - 4);

        final Random random = new Random();

        float yaw = ((float) (MathHelper.atan2(z, x) * 180.0D / Math.PI) - 90.0F) + MC.thePlayer.getEyeHeight()  + random.nextInt(4);
        float pitch = (float) (-(MathHelper.atan2(y, math) * 180.0D / Math.PI) + random.nextInt(4));

        yaw -= yaw % sense;
        pitch -= pitch % sense;

        boolean rotate = !(MC.thePlayer.getDistanceToEntity(tar) < .4);


        Rotations.noRotate = rotate;

        if (!rotate) {
            yaw = MC.thePlayer.rotationYaw;
            pitch = MC.thePlayer.rotationPitch;
        }

        yaw = updateRotation(prevRotations[0], yaw, 80);
        pitch = updateRotation(prevRotations[1], pitch, 80);

        yaw -= 3;

        return new float[]{yaw, MathHelper.clamp_float(pitch, -90, 90)};
    }

    private float updateRotation(float p_75652_1_, float p_75652_2_, float p_75652_3_) {
        float f = MathHelper.wrapAngleTo180_float(p_75652_2_ - p_75652_1_);

        if (f > p_75652_3_) {
            f = p_75652_3_;
        }

        if (f < -p_75652_3_) {
            f = -p_75652_3_;
        }

        return p_75652_1_ + f;
    }

    public double[] getClearRotations(Entity entity) {
        final double x = entity.posX - MC.thePlayer.posX, y = entity.posY - (MC.thePlayer.posY + (double) MC.thePlayer.getEyeHeight()), z = entity.posZ - MC.thePlayer.posZ;
        return new double[]{x, y, z};
    }

    public double[] getBestVecRotation(Entity entity) {

        final Vec3 vec3 = getBestVector(IHook.MC.thePlayer.getPositionEyes(IHook.MC.timer.renderPartialTicks), entity.getEntityBoundingBox());

        final double x = vec3.xCoord - MC.thePlayer.posX;
        final double y = vec3.yCoord - (MC.thePlayer.posY + (double) MC.thePlayer.getEyeHeight());
        final double z = vec3.zCoord - MC.thePlayer.posZ;
        return new double[]{x, y, z};
    }


}
