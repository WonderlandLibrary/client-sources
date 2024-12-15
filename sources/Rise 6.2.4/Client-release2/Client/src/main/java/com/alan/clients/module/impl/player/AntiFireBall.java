package com.alan.clients.module.impl.player;

import com.alan.clients.component.impl.player.BadPacketsComponent;
import com.alan.clients.component.impl.player.RotationComponent;
import com.alan.clients.component.impl.player.rotationcomponent.MovementFix;
import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.other.TickEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.util.rotation.RotationUtil;
import com.alan.clients.value.impl.BooleanValue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0APacketAnimation;
import rip.vantage.commons.util.time.StopWatch;

import java.util.HashSet;

@ModuleInfo(aliases = {"module.player.antifireball.name"}, description = "module.player.antifireball.description", category = Category.PLAYER)
public class AntiFireBall extends Module {

    private final BooleanValue rotate = new BooleanValue("Rotate", this, true);
    private final BooleanValue badPacketsCheck = new BooleanValue("Bad Packets Check", this, false);
    public final StopWatch stopWatch = new StopWatch();
    public int delay = 0;
    private final HashSet<java.util.UUID> attackedFireballs = new HashSet<>();

    @EventLink(value = -100)
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {
        attackThisNigger();
    };

    public final void attackThisNigger() {
        if ((BadPacketsComponent.bad() && badPacketsCheck.getValue()) || !stopWatch.finished(delay)) return;
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityFireball && entity.getDistanceToEntity(mc.thePlayer) < 6) {
//                ChatUtil.display("FIREBALL!");
                if (this.rotate.getValue()) {
                    RotationComponent.setRotations(RotationUtil.calculate(entity), 10, MovementFix.OFF);
                }
                MoveUtil.strafe(0);
//                ChatUtil.display("ATTACKING FIREBALL!");
                if (entity.getDistanceToEntity(mc.thePlayer) <= 3  && !attackedFireballs.contains(entity.getUniqueID())) {
                    PacketUtil.send(new C0APacketAnimation());
                    PacketUtil.send(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
                    attackedFireballs.add(entity.getUniqueID());
                    break;
                }
                PlayerUtil.sendClick(0, true);
                PlayerUtil.sendClick(0, false);
                break;
            }
        };
    }

    @EventLink(value = -100)
    public final Listener<TickEvent> onTick = event -> {
       attackThisNigger();
    };
}