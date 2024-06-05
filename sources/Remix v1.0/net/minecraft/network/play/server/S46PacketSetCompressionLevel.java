package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S46PacketSetCompressionLevel implements Packet
{
    private int field_179761_a;
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.field_179761_a = data.readVarIntFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeVarIntToBuffer(this.field_179761_a);
    }
    
    public void func_179759_a(final INetHandlerPlayClient p_179759_1_) {
        p_179759_1_.func_175100_a(this);
    }
    
    public int func_179760_a() {
        return this.field_179761_a;
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.func_179759_a((INetHandlerPlayClient)handler);
    }
}
