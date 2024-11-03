package dev.stephen.nexus.module.modules.player.nofall;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.network.EventPacket;
import dev.stephen.nexus.event.types.TransferOrder;
import dev.stephen.nexus.mixin.accesors.PlayerMoveC2SPacketAccessor;
import dev.stephen.nexus.module.modules.player.NoFall;
import dev.stephen.nexus.module.setting.impl.newmodesetting.SubMode;
import dev.stephen.nexus.utils.mc.MoveUtils;
import dev.stephen.nexus.utils.mc.PlayerUtil;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class VulcanNoFall extends SubMode<NoFall> {
    public VulcanNoFall(String name, NoFall parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventPacket> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        if (PlayerUtil.isOverVoid()) {
            return;
        }
        if (event.getOrder() == TransferOrder.SEND) {
            if (event.getPacket() instanceof PlayerMoveC2SPacket packet) {
                PlayerMoveC2SPacketAccessor accessor = (PlayerMoveC2SPacketAccessor) packet;

                if (mc.player.fallDistance >= getParentModule().minFallDistance.getValue()) {
                    accessor.setOnGround(true);
                    mc.player.fallDistance = 0f;
                    MoveUtils.setMotionY(0);
                }
            }
        }
    };
}
