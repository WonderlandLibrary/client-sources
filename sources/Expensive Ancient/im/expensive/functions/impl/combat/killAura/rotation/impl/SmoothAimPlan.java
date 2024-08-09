package im.expensive.functions.impl.combat.killAura.rotation.impl;

import im.expensive.functions.impl.combat.killAura.rotation.AimPlan;
import im.expensive.functions.impl.combat.killAura.rotation.VecRotation;
import im.expensive.utils.math.SensUtils;
import net.minecraft.util.math.MathHelper;

public class SmoothAimPlan implements AimPlan {

    @Override
    public VecRotation getRotation(VecRotation targetRotation, VecRotation previousRotation) {
        float yaw = targetRotation.getYaw();
        float pitch = targetRotation.getPitch();
        float previousYaw = previousRotation.getYaw();
        float previousPitch = previousRotation.getPitch();

        float deltaYaw = MathHelper.wrapDegrees(yaw - previousYaw);
        float deltaPitch = pitch - previousPitch;
        final double distance = Math.sqrt(deltaYaw * deltaYaw + deltaPitch * deltaPitch);
        final double distributionYaw = Math.abs(deltaYaw / distance);
        final double distributionPitch = Math.abs(deltaPitch / distance);

        double maxYaw = 90 * distributionYaw;
        final double maxPitch = 40 * distributionPitch;


        float moveYaw = (float) Math.max(Math.min(deltaYaw, maxYaw), -maxYaw);
        final float movePitch = (float) Math.max(Math.min(deltaPitch, maxPitch), -maxPitch);

        float finalYaw = previousYaw + moveYaw;
        float finalPitch = previousPitch + movePitch;
        finalPitch = MathHelper.clamp(finalPitch, -89.0F, 89.0F);

        return SensUtils.applySensitivityFix(new VecRotation(finalYaw, finalPitch), new VecRotation(previousYaw, previousPitch));
    }

    @Override
    public String getName() {
        return "Smooth";
    }
}
