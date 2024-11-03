package dev.stephen.nexus.utils.mc;

import dev.stephen.nexus.utils.Utils;
import dev.stephen.nexus.utils.math.MathUtils;
import dev.stephen.nexus.utils.math.RandomUtil;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Random;

public class AngleSmooth implements Utils {

    private static final Random random = new Random();

    public static float[] smoothRotationsUsingExp(float[] currentRotations, float[] targetRotations, float speed, PlayerEntity target) {
        float currentYaw = currentRotations[0];
        float currentPitch = currentRotations[1];
        float targetYaw = targetRotations[0];
        float targetPitch = targetRotations[1];

        float partialtick = mc.getRenderTickCounter().getTickDelta(false);
        float smoothingFactorYaw = 1.0f - (float) Math.exp(-speed * partialtick);
        float smoothingFactorPitch = 1.0f - (float) Math.exp(-speed * partialtick);

        float newYaw = currentYaw + (targetYaw - currentYaw) * smoothingFactorYaw;
        float newPitch = currentPitch + (targetPitch - currentPitch) * smoothingFactorPitch;

        return new float[]{newYaw, newPitch};
    }


    public static float[] smoothRotationsUsingBezier(float[] currentRotations, float[] targetRotations, float speed, PlayerEntity target) {
        float currentYaw = currentRotations[0];
        float currentPitch = currentRotations[1];
        float targetYaw = targetRotations[0];
        float targetPitch = targetRotations[1];

        float controlYaw = currentYaw + (targetYaw - currentYaw) * 0.5f;
        float controlPitch = currentPitch + (targetPitch - currentPitch) * 0.5f;

        float t = Math.min(1.0f, speed * 0.01f);

        float newYaw = (float) (Math.pow(1 - t, 2) * currentYaw + 2 * (1 - t) * t * controlYaw + Math.pow(t, 2) * targetYaw);

        float newPitch = (float) (Math.pow(1 - t, 2) * currentPitch + 2 * (1 - t) * t * controlPitch + Math.pow(t, 2) * targetPitch);

        return new float[]{newYaw, newPitch};
    }


    public static float smoothRotationsUsingFov(float[] currentRotations, float[] targetRotations, float speed, PlayerEntity target) {
        return (float) (speed * (Math.abs(CombatUtils.fovFromEntity(target)) * 2 / 180));
    }

    public static float[] smoothRotationsUsingNoise(float[] prevRotation, float[] targetRotation, float speed, float speed2, PlayerEntity target) {
        double deltaYaw = MathUtils.wrapAngleTo180_float(targetRotation[0] - prevRotation[0]);
        double deltaPitch = targetRotation[1] - prevRotation[1];
        double smoothValue = RandomUtil.randomNoise(speed, speed2);
        float smoothYaw = (float) (deltaYaw * smoothValue);
        float smoothPitch = (float) (deltaPitch * smoothValue);

        return new float[]{prevRotation[0] + smoothYaw, prevRotation[1] + smoothPitch};
    }

    private static float lastStoppedYaw = 0;
    private static int slowDowns = 0;

    public static float[] smoothRotationsUsingMousePad(float[] prevRotation, float[] targetRotation, float yawThreshold, PlayerEntity target) {
        float currentYaw = prevRotation[0];
        float targetYaw = targetRotation[0];
        float targetPitch = targetRotation[1];

        if (Math.abs(currentYaw - targetYaw) <= 5) {
            lastStoppedYaw = 0;
            slowDowns = 0;
        } else if (lastStoppedYaw == 0) {
            lastStoppedYaw = prevRotation[0];
            slowDowns = 0;
        } else {
            if (Math.abs(currentYaw - lastStoppedYaw) >= yawThreshold) {
                if (slowDowns == 3) {
                    slowDowns = 0;
                    lastStoppedYaw = currentYaw;
                } else {
                    slowDowns++;
                }

                return new float[]{prevRotation[0], prevRotation[1]};
            }
        }

        return new float[]{targetYaw, targetPitch};
    }
}
