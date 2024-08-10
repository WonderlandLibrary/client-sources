package cc.slack.features.modules.impl.movement.jesus.impl;

import cc.slack.events.impl.player.MoveEvent;
import cc.slack.features.modules.impl.movement.jesus.IJesus;
import cc.slack.utils.player.MovementUtil;

public class VanillaJesus implements IJesus {

    @Override
    public void onMove(MoveEvent event) {
        MovementUtil.setSpeed(event, 0.4f);
        event.setY(0.01);
    }

    @Override
    public String toString() {
        return "Vanilla";
    }
}
