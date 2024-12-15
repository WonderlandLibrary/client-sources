package com.alan.clients.module.impl.combat;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.other.AttackEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.value.impl.NumberValue;
import net.minecraft.network.play.client.C02PacketUseEntity;

@ModuleInfo(aliases = {"module.combat.comboonehit.name", "Insta Kill", "Instant Kill"}, description = "module.combat.comboonehit.description", category = Category.COMBAT)
public final class ComboOneHit extends Module {

    public final NumberValue packets = new NumberValue("Attack Packets", this, 50, 1, 1000, 1);

    @EventLink
    public final Listener<AttackEvent> onAttack = event -> {
        for (int i = 0; i < packets.getValue().intValue(); i++) {
            PacketUtil.send(new C02PacketUseEntity(event.getTarget(), C02PacketUseEntity.Action.ATTACK));
        }
    };
}
