// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Events;

import net.minecraft.network.Packet;
import net.minecraft.client.network.badlion.memes.events.Event;

public class EventPacket implements Event
{
    private EventPacketType type;
    private Packet packet;
    
    public EventPacket(final EventPacketType type, final Packet packet) {
        this.type = type;
        this.packet = packet;
    }
    
    public Packet getPacket() {
        return this.packet;
    }
    
    public void setPacket(final Packet packet) {
        this.packet = packet;
    }
    
    public EventPacketType getType() {
        return this.type;
    }
    
    public enum EventPacketType
    {
        SEND("SEND", 0), 
        RECEIVE("RECEIVE", 1);
        
        private EventPacketType(final String s, final int n) {
        }
    }
}
