package fr.dog.anticheat.check;

import fr.dog.anticheat.Check;
import fr.dog.module.impl.player.Anticheat;

public class NoslowA extends Check {
    public NoslowA(Anticheat anticheat) {
        super("NoslowA", anticheat);
    }

    @Override
    public void onUpdate() {
        if (!anticheat.isEnabled() || !anticheat.noslow.getValue()) {
            return;
        }

        mc.theWorld.playerEntities.forEach(player -> {
            if (player.isBlocking() && player.hurtTime == 0 && player.groundTicks > 3) {
                if (player.blockTicks > 3) {
                    float bpt = (float) (Math.pow(player.posX - player.lastTickPosX, 2) + Math.pow(player.posZ - player.lastTickPosZ, 2));
                    float bps = (float) (Math.sqrt(bpt) * 20) * mc.timer.timerSpeed;
                    if (bps > 3.7) {
                        flagPlayer(player, 1);
                    }
                }
            }
        });
    }
}
