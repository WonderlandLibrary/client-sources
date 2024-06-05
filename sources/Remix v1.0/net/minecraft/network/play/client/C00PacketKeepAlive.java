package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class C00PacketKeepAlive implements Packet
{
    private int key;
    
    public C00PacketKeepAlive() {
    }
    
    public C00PacketKeepAlive(final int p_i45252_1_) {
        this.key = p_i45252_1_;
    }
    
    public void processPacket(final INetHandlerPlayServer handler) {
        handler.processKeepAlive(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.key = data.readVarIntFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeVarIntToBuffer(this.key);
    }
    
    public int getKey() {
        return this.key;
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.processPacket((INetHandlerPlayServer)handler);
    }
}
