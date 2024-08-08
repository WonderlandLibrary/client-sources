// 
// Decompiled by Procyon v0.5.36
// 

package me.xatzdevelopments.xatz.client.Unused.inEvents.Listeners;

import me.xatzdevelopments.xatz.client.Unused.inEvents.inEvent;
import net.minecraft.network.Packet;

public class inEventSendPacket extends inEvent<inEventSendPacket>
{
    private Packet packet;
    private boolean isCancelled;
    
    public inEventSendPacket(final Packet packet) {
        this.packet = packet;
    }
    
    public Packet getPacket() {
        return this.packet;
    }
    
    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }
    
    @Override
    public void setCancelled(final boolean cancelled) {
        this.isCancelled = cancelled;
    }
}
