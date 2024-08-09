package ez.h.event.events;

import ez.h.event.*;

public class EventPacketReceive extends Event
{
    public hb handler;
    ht<?> packet;
    
    public void setPacket(final ht<?> packet) {
        this.packet = packet;
    }
    
    public EventPacketReceive(final ht<?> packet, final hb handler) {
        this.handler = handler;
        this.packet = packet;
    }
    
    public ht<?> getPacket() {
        return this.packet;
    }
}
