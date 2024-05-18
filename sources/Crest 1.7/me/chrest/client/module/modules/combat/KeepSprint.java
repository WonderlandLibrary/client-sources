// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.combat;

import me.chrest.event.EventTarget;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import me.chrest.event.events.PacketSendEvent;
import me.chrest.client.module.Module;

@Mod
public class KeepSprint extends Module
{
    @EventTarget
    public void onPacketSent(final PacketSendEvent e) {
        if (e.getPacket() instanceof C0BPacketEntityAction && e.getPacket() instanceof C02PacketUseEntity) {
            final C0BPacketEntityAction packet = (C0BPacketEntityAction)e.getPacket();
            final C02PacketUseEntity useEntity = (C02PacketUseEntity)e.getPacket();
            if (useEntity.getAction() == C02PacketUseEntity.Action.ATTACK) {
                packet.func_180764_b();
                if (packet.func_180764_b() == C0BPacketEntityAction.Action.STOP_SPRINTING) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
