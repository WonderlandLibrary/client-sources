// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.events;

import net.minecraft.network.Packet;
import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class EventPacketSend extends EventCancellable
{
    private Packet packet;
    
    public EventPacketSend(final Packet packet) {
        this.packet = packet;
    }
    
    public Packet getPacket() {
        return this.packet;
    }
    
    public void setPacket(final Packet packet) {
        this.packet = packet;
    }
}
