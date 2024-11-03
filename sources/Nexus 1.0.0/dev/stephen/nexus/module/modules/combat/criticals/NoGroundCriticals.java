package dev.stephen.nexus.module.modules.combat.criticals;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.network.EventPacket;
import dev.stephen.nexus.event.types.TransferOrder;
import dev.stephen.nexus.mixin.accesors.PlayerMoveC2SPacketAccessor;
import dev.stephen.nexus.module.modules.combat.Criticals;
import dev.stephen.nexus.module.setting.impl.newmodesetting.SubMode;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class NoGroundCriticals extends SubMode<Criticals> {
    public NoGroundCriticals(String name, Criticals parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventPacket> eventPacketListener = event -> {
        if (isNull()) {
            return;
        }
        if (event.getOrder() == TransferOrder.SEND) {
            if (event.getPacket() instanceof PlayerMoveC2SPacket packet) {
                PlayerMoveC2SPacketAccessor accessor = (PlayerMoveC2SPacketAccessor) packet;
                accessor.setOnGround(false);
            }
        }
    };
}
