package cc.slack.features.modules.impl.movement.steps.impl;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.steps.IStep;

public class VanillaStep implements IStep {

    @Override
    public void onUpdate(UpdateEvent event) {
        mc.thePlayer.stepHeight = 1f;
    }

    @Override
    public String toString() {
        return "Vanilla";
    }
}
