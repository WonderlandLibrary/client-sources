package net.minecraft.network.status.server;

import java.io.*;
import net.minecraft.network.status.*;
import net.minecraft.network.*;

public class S01PacketPong implements Packet
{
    private long clientTime;
    
    public S01PacketPong() {
    }
    
    public S01PacketPong(final long time) {
        this.clientTime = time;
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.clientTime = data.readLong();
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeLong(this.clientTime);
    }
    
    public void processPacket(final INetHandlerStatusClient handler) {
        handler.handlePong(this);
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.processPacket((INetHandlerStatusClient)handler);
    }
}
