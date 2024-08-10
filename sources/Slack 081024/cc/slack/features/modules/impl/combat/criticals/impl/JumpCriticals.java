package cc.slack.features.modules.impl.combat.criticals.impl;

import cc.slack.events.impl.player.AttackEvent;
import cc.slack.features.modules.impl.combat.criticals.ICriticals;

public class JumpCriticals implements ICriticals {

    @Override
    public void onAttack(AttackEvent event) {
        if (mc.thePlayer.onGround) mc.thePlayer.jump();
    }

    @Override
    public String toString() {
        return "Jump";
    }
}
