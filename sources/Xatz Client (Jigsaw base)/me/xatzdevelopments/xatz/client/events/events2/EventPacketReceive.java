package me.xatzdevelopments.xatz.client.events.events2;

import me.xatzdevelopments.xatz.client.darkmagician6.Cancellable;
import me.xatzdevelopments.xatz.client.events.Event;
import net.minecraft.network.Packet;

public final class EventPacketReceive extends Event implements Cancellable
{
    private boolean cancel;
    private Packet packet;
    
    public EventPacketReceive(final Packet packet) {
        this.packet = packet;
    }
    
    public Packet getPacket() {
        return this.packet;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public void setPacket(final Packet packet) {
        this.packet = packet;
    }
}
