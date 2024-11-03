package dev.stephen.nexus.module.modules.player.regen;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.modules.player.Regen;
import dev.stephen.nexus.module.setting.impl.newmodesetting.SubMode;
import dev.stephen.nexus.utils.mc.PacketUtils;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class VanillaRegen extends SubMode<Regen> {
    public VanillaRegen(String name, Regen parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        if (mc.player.getHealth() < getParentModule().health.getValue()) {
            for (int i = 0; i < getParentModule().packets.getValue(); i++) {
                PacketUtils.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(mc.player.isOnGround()));
            }
        }
    };
}
