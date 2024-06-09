// 
// Decompiled by Procyon v0.5.36
// 

package events.listeners;

import net.minecraft.network.Packet;
import events.EventCancellable;

public class EventReceivedPacket extends EventCancellable
{
    public static EventReceivedPacket INSTANCE;
    private Packet packet;
    
    public EventReceivedPacket(final Packet packet) {
        EventReceivedPacket.INSTANCE = this;
        this.packet = packet;
    }
    
    @Override
    public Packet getPacket() {
        return this.packet;
    }
    
    public void setPacket(final Packet packet) {
        this.packet = packet;
    }
}
