package me.satisfactory.base.events;

import me.satisfactory.base.events.event.callables.*;
import me.satisfactory.base.events.event.*;
import net.minecraft.network.*;

public class EventPacketReceive extends EventCancellable implements Event
{
    public Packet packet;
    
    public EventPacketReceive(final Packet packet) {
        this.packet = packet;
    }
    
    public Packet getPacket() {
        return this.packet;
    }
    
    public void setPacket(final Packet packet) {
        this.packet = packet;
    }
}
