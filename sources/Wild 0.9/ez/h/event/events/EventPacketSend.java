package ez.h.event.events;

import ez.h.event.*;

public class EventPacketSend extends Event
{
    public ht<?> packet;
    
    public EventPacketSend(final ht<?> packet) {
        this.packet = packet;
    }
    
    public void setPacket(final ht<?> packet) {
        this.packet = packet;
    }
    
    public ht<?> getPacket() {
        return this.packet;
    }
}
