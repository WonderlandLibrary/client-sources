package com.polarware.module.impl.combat.criticals;

import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.other.AttackEvent;
import com.polarware.module.impl.combat.CriticalsModule;
import com.polarware.util.packet.PacketUtil;
import com.polarware.value.Mode;
import net.minecraft.network.play.client.C02PacketUseEntity;

public final class PolarCriticals extends Mode<CriticalsModule> {

    public PolarCriticals(String name, CriticalsModule parent) {
        super(name, parent);
    }

    @EventLink
    public final Listener<AttackEvent> onAttack = event -> {
        PacketUtil.sendNoEvent(new C02PacketUseEntity(event.getTarget(), C02PacketUseEntity.Action.ATTACK));
    };

}