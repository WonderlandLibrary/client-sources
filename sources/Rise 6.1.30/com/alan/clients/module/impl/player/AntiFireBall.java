package com.alan.clients.module.impl.player;

import com.alan.clients.component.impl.player.BadPacketsComponent;
import com.alan.clients.component.impl.player.RotationComponent;
import com.alan.clients.component.impl.player.rotationcomponent.MovementFix;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.rotation.RotationUtil;
import com.alan.clients.value.impl.BooleanValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0APacketAnimation;

@ModuleInfo(aliases = {"module.player.antifireball.name"}, description = "module.player.antifireball.description", category = Category.PLAYER)
public class AntiFireBall extends Module {

    private final BooleanValue rotate = new BooleanValue("Rotate", this, true);

    @EventLink
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        if (BadPacketsComponent.bad()) return;

        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityFireball && entity.getDistanceToEntity(mc.thePlayer) < 5) {
                // this shit flags sometimes somehow
                if (this.rotate.getValue()) {
                    RotationComponent.setRotations(RotationUtil.calculate(entity), 10, MovementFix.OFF);

                    if (getModule(Scaffold.class).isEnabled()) {
                        MoveUtil.strafe(-0.1);
                    }
                }
                PacketUtil.sendNoEvent(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
                PacketUtil.sendNoEvent(new C0APacketAnimation());
                break;
            }
        }
    };
}