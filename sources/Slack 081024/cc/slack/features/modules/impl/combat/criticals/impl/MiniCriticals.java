package cc.slack.features.modules.impl.combat.criticals.impl;

import cc.slack.events.impl.player.AttackEvent;
import cc.slack.features.modules.impl.combat.criticals.ICriticals;
import cc.slack.utils.network.PacketUtil;

public class MiniCriticals implements ICriticals {

    @Override
    public void onAttack(AttackEvent event) {
        PacketUtil.sendCriticalPacket(0.0001, true);
        PacketUtil.sendCriticalPacket(0.0, false);
    }

    @Override
    public String toString() {
        return "Mini";
    }
}
