package net.minecraft.src;

import java.util.concurrent.*;

class CallablePacketID implements Callable
{
    final Packet thePacket;
    final NetServerHandler theNetServerHandler;
    
    CallablePacketID(final NetServerHandler par1NetServerHandler, final Packet par2Packet) {
        this.theNetServerHandler = par1NetServerHandler;
        this.thePacket = par2Packet;
    }
    
    public String callPacketID() {
        return String.valueOf(this.thePacket.getPacketId());
    }
    
    @Override
    public Object call() {
        return this.callPacketID();
    }
}
