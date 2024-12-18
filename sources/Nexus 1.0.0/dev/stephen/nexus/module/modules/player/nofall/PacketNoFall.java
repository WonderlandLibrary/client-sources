package dev.stephen.nexus.module.modules.player.nofall;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.modules.player.NoFall;
import dev.stephen.nexus.module.setting.impl.newmodesetting.SubMode;
import dev.stephen.nexus.utils.mc.PacketUtils;
import dev.stephen.nexus.utils.mc.PlayerUtil;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class PacketNoFall extends SubMode<NoFall> {
    public PacketNoFall(String name, NoFall parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        if (PlayerUtil.isOverVoid()) {
            return;
        }
        if (mc.player.fallDistance >= getParentModule().minFallDistance.getValue()) {
            PacketUtils.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
            mc.player.fallDistance = 0;
        }
    };
}
