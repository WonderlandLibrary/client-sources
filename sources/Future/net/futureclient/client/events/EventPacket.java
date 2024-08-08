package net.futureclient.client.events;

import net.minecraft.network.Packet;

public class EventPacket extends Event
{
    private Packet<?> k;
    
    public EventPacket(final Packet<?> k) {
        super();
        this.k = k;
    }
    
    public void M(final Packet<?> k) {
        this.k = k;
    }
    
    public Packet<?> M() {
        return this.k;
    }
}
