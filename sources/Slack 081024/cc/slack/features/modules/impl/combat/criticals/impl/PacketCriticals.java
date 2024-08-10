package cc.slack.features.modules.impl.combat.criticals.impl;

import cc.slack.events.impl.player.AttackEvent;
import cc.slack.features.modules.impl.combat.criticals.ICriticals;
import cc.slack.utils.network.PacketUtil;
import cc.slack.utils.player.PlayerUtil;

public class PacketCriticals implements ICriticals {

    @Override
    public void onAttack(AttackEvent event) {
        PacketUtil.sendCriticalPacket(PlayerUtil.HEAD_HITTER_MOTIONY, false);
    }

    @Override
    public String toString() {
        return "Packet";
    }
}
