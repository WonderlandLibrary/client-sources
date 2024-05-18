// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.player;

import me.chrest.event.EventTarget;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import me.chrest.event.events.PacketSendEvent;
import me.chrest.client.module.Module;

@Mod(displayName = "PingSpoof")
public class PingSpoof extends Module
{
    @EventTarget
    public void onPacketRecieve(final PacketSendEvent event) {
        if (event.getPacket() instanceof C00PacketKeepAlive || event.getPacket() instanceof C16PacketClientStatus) {
            event.setCancelled(true);
        }
    }
}
