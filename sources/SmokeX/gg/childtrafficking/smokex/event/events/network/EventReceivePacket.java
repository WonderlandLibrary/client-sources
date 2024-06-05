// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.event.events.network;

import net.minecraft.network.Packet;
import gg.childtrafficking.smokex.event.Event;

public class EventReceivePacket extends Event
{
    private Packet<?> packet;
    
    public EventReceivePacket(final Packet<?> packet) {
        this.packet = packet;
    }
    
    public Packet<?> getPacket() {
        return this.packet;
    }
    
    public void setPacket(final Packet<?> packet) {
        this.packet = packet;
    }
}
