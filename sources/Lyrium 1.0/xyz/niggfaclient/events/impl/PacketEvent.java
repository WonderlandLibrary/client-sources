// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.events.impl;

import net.minecraft.network.Packet;
import xyz.niggfaclient.events.CancellableEvent;

public class PacketEvent extends CancellableEvent
{
    private Packet packet;
    private State state;
    
    public PacketEvent(final Packet packet, final State state) {
        this.packet = packet;
        this.state = state;
    }
    
    public Packet getPacket() {
        return this.packet;
    }
    
    public void setPacket(final Packet packet) {
        this.packet = packet;
    }
    
    public State getState() {
        return this.state;
    }
    
    public enum State
    {
        RECEIVE, 
        SEND;
    }
}
