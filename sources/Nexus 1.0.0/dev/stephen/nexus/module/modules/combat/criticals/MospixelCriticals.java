package dev.stephen.nexus.module.modules.combat.criticals;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventAttack;
import dev.stephen.nexus.module.modules.combat.Criticals;
import dev.stephen.nexus.module.setting.impl.newmodesetting.SubMode;
import dev.stephen.nexus.utils.mc.PacketUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class MospixelCriticals extends SubMode<Criticals> {
    public MospixelCriticals(String name, Criticals parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventAttack> eventAttackListener = event -> {
        if (isNull()) {
            return;
        }
        boolean willCritLegit = mc.player.fallDistance > 0.0F && !mc.player.isOnGround() && !mc.player.isClimbing() && !mc.player.isTouchingWater() && !mc.player.hasStatusEffect(StatusEffects.BLINDNESS) && !mc.player.hasVehicle() && event.getTarget() instanceof LivingEntity;

        if (willCritLegit) {
            return;
        }

        PacketUtils.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getPos().x, mc.player.getPos().y + 0.000000271875, mc.player.getPos().z, false));
        PacketUtils.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getPos().x, mc.player.getPos().y + 0., mc.player.getPos().z, false));
    };
}
