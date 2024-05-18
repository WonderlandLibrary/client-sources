// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Events;

import net.minecraft.network.Packet;
import net.minecraft.client.network.badlion.memes.events.callables.MemeMeable;

public class EventPacketTake extends MemeMeable
{
    public Packet packet;
    
    public EventPacketTake(final Packet packet) {
        this.packet = packet;
    }
}
