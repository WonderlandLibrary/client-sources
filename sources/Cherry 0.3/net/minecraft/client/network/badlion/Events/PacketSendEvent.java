// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Events;

import net.minecraft.network.Packet;
import net.minecraft.client.network.badlion.memes.events.EventCancelable;

public class PacketSendEvent implements EventCancelable
{
    private Packet packet;
    
    public PacketSendEvent(final Packet packet) {
        this.packet = packet;
    }
    
    public Packet getPacket() {
        return this.packet;
    }
    
    public void setPacket(final Packet packet) {
        this.packet = packet;
    }
    
    @Override
    public boolean isCancelled() {
        return false;
    }
    
    @Override
    public void setCancelled(final boolean state) {
    }
}
