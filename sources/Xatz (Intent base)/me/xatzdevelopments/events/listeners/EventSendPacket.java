package me.xatzdevelopments.events.listeners;

import me.xatzdevelopments.events.Event;
import net.minecraft.network.Packet;

public class EventSendPacket extends Event<EventSendPacket> {
    private Packet packet;
    private boolean isCancelled;
    
    public EventSendPacket(final Packet packet) {
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
