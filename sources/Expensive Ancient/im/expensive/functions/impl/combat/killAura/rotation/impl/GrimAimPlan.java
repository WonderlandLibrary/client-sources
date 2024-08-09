package im.expensive.functions.impl.combat.killAura.rotation.impl;

import im.expensive.functions.impl.combat.killAura.rotation.AimPlan;
import im.expensive.functions.impl.combat.killAura.rotation.VecRotation;
import im.expensive.utils.math.SensUtils;
import net.minecraft.util.math.MathHelper;

public class GrimAimPlan implements AimPlan {
    @Override
    public VecRotation getRotation(VecRotation targetRotation, VecRotation previousRotation) {
        float yaw = targetRotation.getYaw();
        float pitch = targetRotation.getPitch();
        float previousYaw = previousRotation.getYaw();
        float previousPitch = previousRotation.getPitch();

        float deltaYaw = MathHelper.wrapDegrees(yaw - previousYaw);
        float deltaPitch = pitch - previousPitch;

        float finalYaw = previousYaw + deltaYaw;
        float finalPitch = previousPitch + deltaPitch;
        finalPitch = MathHelper.clamp(finalPitch, -89.0F, 89.0F);

        return SensUtils.applySensitivityFix(new VecRotation(finalYaw, finalPitch), new VecRotation(previousYaw, previousPitch));
    }

    @Override
    public String getName() {
        return "Grim";
    }
}
