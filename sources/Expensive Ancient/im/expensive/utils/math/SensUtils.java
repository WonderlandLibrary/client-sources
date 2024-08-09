package im.expensive.utils.math;

import im.expensive.functions.impl.combat.killAura.rotation.VecRotation;
import im.expensive.utils.client.IMinecraft;
import lombok.experimental.UtilityClass;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;

@UtilityClass
public class SensUtils implements IMinecraft {
    public static Vector2f applySensitivityFix(Vector2f currentRotation, Vector2f previousRotation) {
        float greatestCommonDivisor = getGCD();

        float yawDifference = currentRotation.x - previousRotation.x;
        float pitchDifference = currentRotation.y - previousRotation.y;

        float yawRemainder = yawDifference % greatestCommonDivisor;
        float pitchRemainder = pitchDifference % greatestCommonDivisor;

        float correctedYaw = currentRotation.x - yawRemainder;
        float correctedPitch = currentRotation.y - pitchRemainder;

        float clampedPitch = MathHelper.clamp(correctedPitch, -90, 90);
        return new Vector2f(correctedYaw, clampedPitch);
    }
    public static VecRotation applySensitivityFix(VecRotation currentRotation2, VecRotation previousRotation2) {
        float greatestCommonDivisor = getGCD();

        float yawDifference = currentRotation2.getYaw() - previousRotation2.getYaw();
        float pitchDifference = currentRotation2.getPitch() - previousRotation2.getPitch();

        float yawRemainder = yawDifference % greatestCommonDivisor;
        float pitchRemainder = pitchDifference % greatestCommonDivisor;

        float correctedYaw = currentRotation2.getYaw() - yawRemainder;
        float correctedPitch = currentRotation2.getPitch() - pitchRemainder;

        float clampedPitch = MathHelper.clamp(correctedPitch, -90, 90);
        return new VecRotation(correctedYaw, clampedPitch);
    }
    public static float getGCD() {
        float mouseSensitivity = (float) mc.gameSettings.mouseSensitivity;
        float sensitivityMultiplier = 0.6f;
        float sensitivityOffset = 0.2f;
        float sensitivityScalingFactor = 8.0f;
        float gcdScalingFactor = 0.15f;

        float adjustedSensitivity = mouseSensitivity * sensitivityMultiplier + sensitivityOffset;
        float sensitivitySquared = adjustedSensitivity * adjustedSensitivity;
        float sensitivityCubed = sensitivitySquared * adjustedSensitivity;
        float scaledSensitivityCubed = sensitivityCubed * sensitivityScalingFactor;

        return scaledSensitivityCubed * gcdScalingFactor;
    }
}