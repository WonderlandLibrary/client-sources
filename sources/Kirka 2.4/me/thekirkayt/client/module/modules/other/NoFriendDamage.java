/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.other;

import me.thekirkayt.client.friend.FriendManager;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.PacketSendEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.MovingObjectPosition;

@Module.Mod
public class NoFriendDamage
extends Module {
    @EventTarget
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacket() instanceof C02PacketUseEntity) {
            C02PacketUseEntity packet = (C02PacketUseEntity)event.getPacket();
            Entity target = ClientUtils.mc().objectMouseOver.entityHit;
            boolean isFriend = FriendManager.isFriend(target.getName());
            if (packet.getAction() == C02PacketUseEntity.Action.ATTACK && isFriend) {
                event.setCancelled(true);
            }
        }
    }
}

