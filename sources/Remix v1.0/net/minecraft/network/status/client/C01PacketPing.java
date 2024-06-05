package net.minecraft.network.status.client;

import java.io.*;
import net.minecraft.network.status.*;
import net.minecraft.network.*;

public class C01PacketPing implements Packet
{
    private long clientTime;
    
    public C01PacketPing() {
    }
    
    public C01PacketPing(final long p_i45276_1_) {
        this.clientTime = p_i45276_1_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.clientTime = data.readLong();
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeLong(this.clientTime);
    }
    
    public void func_180774_a(final INetHandlerStatusServer p_180774_1_) {
        p_180774_1_.processPing(this);
    }
    
    public long getClientTime() {
        return this.clientTime;
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.func_180774_a((INetHandlerStatusServer)handler);
    }
}
