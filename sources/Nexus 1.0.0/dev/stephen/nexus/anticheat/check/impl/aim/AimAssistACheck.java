package dev.stephen.nexus.anticheat.check.impl.aim;

import com.google.common.base.Predicate;
import dev.stephen.nexus.anticheat.check.Check;
import net.minecraft.entity.player.PlayerEntity;

public class AimAssistACheck extends Check {
    private final Predicate<Float> validRotation = rotation -> rotation > 3F && rotation < 35F;
    private int buffer;

    public AimAssistACheck(PlayerEntity player) {
        super(player, "AimAssistA");
        buffer = 0;
    }

    @Override
    public void onTick() {
        if (getPlayer() == null) {
            return;
        }
        PlayerEntity player = getPlayer();
        final float deltaPitch = Math.abs(player.getPitch() - player.prevPitch);
        final float deltaYaw = Math.abs(player.getYaw() - player.prevYaw) % 360F;

        final float pitch = Math.abs(player.getPitch());

        final boolean invalidPitch = deltaPitch < 0.009 && validRotation.test(deltaYaw);
        final boolean invalidYaw = deltaYaw < 0.009 && validRotation.test(deltaPitch);

        final boolean exempt = player.hasVehicle();
        final boolean invalid = !exempt && (invalidPitch || invalidYaw) && pitch < 89F;

        if (invalid) {
            if (++buffer > 20) {
                flag("irregular movement");
            }
        } else {
            buffer -= buffer > 0 ? 1 : 0;
        }
        super.onTick();
    }
}
