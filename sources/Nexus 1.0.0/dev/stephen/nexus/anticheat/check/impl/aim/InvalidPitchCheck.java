package dev.stephen.nexus.anticheat.check.impl.aim;

import dev.stephen.nexus.anticheat.check.Check;
import net.minecraft.entity.player.PlayerEntity;

public class InvalidPitchCheck extends Check {
    public InvalidPitchCheck(PlayerEntity player) {
        super(player, "InvalidPitch");
    }

    @Override
    public void onTick() {
        if (getPlayer() == null) {
            return;
        }
        PlayerEntity player = getPlayer();
        final float pitch = Math.abs(player.getPitch());

        final float threshold = player.isHoldingOntoLadder() ? 91.2F : 90.f;

        if (pitch > threshold) {
            flag("pitch=" + pitch);
        }
        super.onTick();
    }
}
