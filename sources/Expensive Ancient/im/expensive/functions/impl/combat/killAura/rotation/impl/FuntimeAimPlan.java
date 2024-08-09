package im.expensive.functions.impl.combat.killAura.rotation.impl;

import im.expensive.functions.impl.combat.killAura.rotation.AimPlan;
import im.expensive.functions.impl.combat.killAura.rotation.VecRotation;
import im.expensive.utils.math.SensUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;

import static net.minecraft.util.math.MathHelper.wrapDegrees;

public class FuntimeAimPlan implements AimPlan {

    float prevYaw;

    @Override
    public VecRotation getRotation(VecRotation targetRotation, VecRotation previousRotation) {
        float yaw = targetRotation.getYaw();
        float pitch = targetRotation.getPitch();
        float previousYaw = previousRotation.getYaw();
        float previousPitch = previousRotation.getPitch();

        float yawDifference = wrapDegrees(yaw - previousYaw);
        float pitchDifference = pitch - previousPitch;

        float scaleFactor = 0.35f;
        float clampedYaw = Math.min(Math.max(Math.abs((int) yawDifference), 1.0f), 90.0F);
        float clampedPitch = Math.min(Math.max(Math.abs(pitchDifference) * scaleFactor, 1.0f), 90.0F);
        if (Math.abs(clampedYaw - this.prevYaw) <= 3.0f) {
            clampedYaw = this.prevYaw + 3.1f;
        }
        float normalizedYaw = yawDifference > 0 ? clampedYaw : -clampedYaw;
        float normalizedPitch = pitchDifference > 0 ? clampedPitch : -clampedPitch;

        float moveYaw = previousYaw + normalizedYaw;
        float movePitch = previousPitch + normalizedPitch;
        prevYaw = clampedYaw;
        return SensUtils.applySensitivityFix(new VecRotation(moveYaw, MathHelper.clamp(movePitch, -90, 90)), new VecRotation(previousYaw, previousPitch));
    }

    @Override
    public String getName() {
        return "FunTime";
    }
}
