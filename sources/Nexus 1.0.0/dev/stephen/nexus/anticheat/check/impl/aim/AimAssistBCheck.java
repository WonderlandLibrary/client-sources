package dev.stephen.nexus.anticheat.check.impl.aim;

import dev.stephen.nexus.anticheat.check.Check;
import net.minecraft.entity.player.PlayerEntity;

public class AimAssistBCheck extends Check {
    private float lastDeltaYaw, lastLastDeltaYaw;

    public AimAssistBCheck(PlayerEntity player) {
        super(player, "AimAssistB");
    }

    @Override
    public void onTick() {
        if (getPlayer() == null) {
            return;
        }
        PlayerEntity player = getPlayer();
        final float deltaYaw = Math.abs(player.getYaw() - player.prevYaw);

        if (deltaYaw < 5F && lastDeltaYaw > 20F && lastLastDeltaYaw < 5F) {
            flag("snappy rotations");
        }

        lastLastDeltaYaw = lastDeltaYaw;
        lastDeltaYaw = deltaYaw;
        super.onTick();
    }
}
