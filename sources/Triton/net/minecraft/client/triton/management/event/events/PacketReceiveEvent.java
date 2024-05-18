// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.management.event.events;

import net.minecraft.client.triton.management.event.Event;
import net.minecraft.network.Packet;

public class PacketReceiveEvent extends Event
{
    private Packet packet;
    
    public Packet getPacket() {
        return this.packet;
    }
    
    public void setPacket(final Packet packet) {
        this.packet = packet;
    }
    
    public PacketReceiveEvent(final Packet packet) {
        this.packet = packet;
    }
}
