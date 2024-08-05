package fr.dog.anticheat.check;

import fr.dog.anticheat.Check;
import fr.dog.module.impl.player.Anticheat;

public class NoslowB extends Check {
    public NoslowB(Anticheat anticheat) {
        super("NoslowB", anticheat);
    }

    @Override
    public void onUpdate() {
        if (!anticheat.isEnabled() || !anticheat.noslow.getValue()) {
            return;
        }

        mc.theWorld.playerEntities.forEach(player -> {
            if (player.blockTicks > 3) {
                if (player.sprintTicks > 3) {
                    flagPlayer(player, 1);
                }
            }
        });
    }
}
