package cc.slack.features.modules.impl.movement.nowebs.impl;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.nowebs.INoWeb;

public class VanillaNoWeb implements INoWeb {

    @Override
    public void onUpdate(UpdateEvent event) {
        mc.thePlayer.isInWeb = false;
    }

    @Override
    public String toString() {
        return "Vanilla";
    }
}
