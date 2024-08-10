package cc.slack.features.modules.impl.movement.nowebs.impl;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.nowebs.INoWeb;

public class FastFallNoWeb implements INoWeb {

    @Override
    public void onUpdate(UpdateEvent event) {
        if (mc.thePlayer.onGround) mc.thePlayer.jump();
        if (mc.thePlayer.motionY > 0f) {
            mc.thePlayer.motionY -= mc.thePlayer.motionY * 2;
        }
    }

    @Override
    public String toString() {
        return "Fast Fall";
    }
}
