package dev.stephen.nexus.module.modules.combat.criticals;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventAttack;
import dev.stephen.nexus.module.modules.combat.Criticals;
import dev.stephen.nexus.module.setting.impl.newmodesetting.SubMode;
import dev.stephen.nexus.utils.mc.PacketUtils;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class VulcanCriticals extends SubMode<Criticals> {
    public VulcanCriticals(String name, Criticals parentModule) {
        super(name, parentModule);
    }

    private int a = 0;

    @EventLink
    public final Listener<EventAttack> eventAttackListener = event -> {
        if (isNull()) {
            return;
        }
        a++;
        if (a > 7) {
            PacketUtils.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getPos().x, mc.player.getPos().y + 0.16477328182606651, mc.player.getPos().z, false));
            PacketUtils.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getPos().x, mc.player.getPos().y + 0.08307781780646721, mc.player.getPos().z, false));
            PacketUtils.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getPos().x, mc.player.getPos().y + 0.0030162615090425808, mc.player.getPos().z, false));
            a = 0;
        }
    };
}
