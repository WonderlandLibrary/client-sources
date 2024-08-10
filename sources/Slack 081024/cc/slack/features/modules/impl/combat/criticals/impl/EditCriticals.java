package cc.slack.features.modules.impl.combat.criticals.impl;

import cc.slack.events.impl.player.AttackEvent;
import cc.slack.features.modules.impl.combat.criticals.ICriticals;
import cc.slack.utils.player.MovementUtil;

public class EditCriticals implements ICriticals {

    @Override
    public void onAttack(AttackEvent event) {
        MovementUtil.spoofNextC03(false);
    }

    @Override
    public String toString() {
        return "Edit";
    }
}
