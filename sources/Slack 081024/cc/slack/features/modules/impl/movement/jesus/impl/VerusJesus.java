package cc.slack.features.modules.impl.movement.jesus.impl;

import cc.slack.events.impl.player.MoveEvent;
import cc.slack.features.modules.impl.movement.jesus.IJesus;
import cc.slack.utils.player.MovementUtil;

public class VerusJesus implements IJesus {

    @Override
    public void onMove(MoveEvent event) {
        MovementUtil.setSpeed(event, 0.40f);
        event.setY(0.405f);
    }

    @Override
    public String toString() {
        return "Verus";
    }

}
