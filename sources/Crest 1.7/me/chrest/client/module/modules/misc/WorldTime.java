// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.misc;

import me.chrest.utils.ClientUtils;
import me.chrest.event.Event;
import me.chrest.event.events.UpdateEvent;
import me.chrest.event.EventTarget;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import me.chrest.event.events.PacketReceiveEvent;
import me.chrest.client.option.Option;
import me.chrest.client.module.Module;

@Mod(displayName = "World Time")
public class WorldTime extends Module
{
    @Option.Op(min = 0.0, max = 24000.0, increment = 250.0)
    private double time;
    
    public WorldTime() {
        this.time = 9000.0;
    }
    
    @EventTarget
    private void onPacketRecieve(final PacketReceiveEvent event) {
        if (event.getPacket() instanceof S03PacketTimeUpdate) {
            event.setCancelled(true);
        }
    }
    
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        if (event.getState() == Event.State.POST) {
            ClientUtils.world().setWorldTime((long)this.time);
        }
    }
}
