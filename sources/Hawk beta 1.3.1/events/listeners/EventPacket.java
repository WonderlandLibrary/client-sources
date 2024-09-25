package eze.events.listeners;

import eze.events.*;
import net.minecraft.network.*;

public class EventPacket<T> extends Event<EventPacket>
{
    public static Packet packet;
    public boolean cancelled;
    
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    @Override
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    public EventPacket(final Packet packet) {
        EventPacket.packet = packet;
    }
    
    public static Packet getPacket() {
        return EventPacket.packet;
    }
    
    public void setPacket(final Packet packet) {
        EventPacket.packet = packet;
    }
}
