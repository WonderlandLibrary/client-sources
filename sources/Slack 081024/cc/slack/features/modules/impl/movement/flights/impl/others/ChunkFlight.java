// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.movement.flights.impl.others;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.flights.IFlight;

public class ChunkFlight implements IFlight {

    @Override
    public void onUpdate(UpdateEvent event) {
        if (mc.thePlayer.motionY < 0) {
            mc.thePlayer.motionY = -0.09800000190735147;
        }
    }

    @Override
    public String toString() {
        return "Chunk";
    }
}
