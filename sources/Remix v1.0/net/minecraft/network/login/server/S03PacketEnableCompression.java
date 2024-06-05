package net.minecraft.network.login.server;

import java.io.*;
import net.minecraft.network.login.*;
import net.minecraft.network.*;

public class S03PacketEnableCompression implements Packet
{
    private int field_179733_a;
    
    public S03PacketEnableCompression() {
    }
    
    public S03PacketEnableCompression(final int p_i45929_1_) {
        this.field_179733_a = p_i45929_1_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.field_179733_a = data.readVarIntFromBuffer();
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeVarIntToBuffer(this.field_179733_a);
    }
    
    public void func_179732_a(final INetHandlerLoginClient p_179732_1_) {
        p_179732_1_.func_180464_a(this);
    }
    
    public int func_179731_a() {
        return this.field_179733_a;
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.func_179732_a((INetHandlerLoginClient)handler);
    }
}
