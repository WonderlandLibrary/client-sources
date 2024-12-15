package com.alan.clients.module.impl.combat.velocity;

import com.alan.clients.component.impl.player.BadPacketsComponent;
import com.alan.clients.component.impl.player.TargetComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.module.impl.combat.Velocity;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.value.Mode;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C0APacketAnimation;

import java.util.List;

public final class GrimReduceVelocity extends Mode<Velocity> {

    public GrimReduceVelocity(String name, Velocity parent) {
        super(name, parent);
    }

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        if (getParent().onSwing.getValue() && !mc.thePlayer.isSwingInProgress ||
                mc.thePlayer.ticksExisted <= 20) return;

        List<EntityLivingBase> targets = TargetComponent.getTargets(7);

        if (targets.isEmpty()) return;

        if (mc.thePlayer.ticksSinceVelocity <= 14 && !BadPacketsComponent.bad()) {
            PacketUtil.send(new C0APacketAnimation());
            mc.playerController.attackEntity(mc.thePlayer, targets.get(0));
        }
    };
}
