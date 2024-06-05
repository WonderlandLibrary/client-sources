package net.minecraft.src;

import java.util.concurrent.*;

class CallablePacketClass implements Callable
{
    final Packet thePacket;
    final NetServerHandler theNetServerHandler;
    
    CallablePacketClass(final NetServerHandler par1NetServerHandler, final Packet par2Packet) {
        this.theNetServerHandler = par1NetServerHandler;
        this.thePacket = par2Packet;
    }
    
    public String getPacketClass() {
        return this.thePacket.getClass().getCanonicalName();
    }
    
    @Override
    public Object call() {
        return this.getPacketClass();
    }
}
