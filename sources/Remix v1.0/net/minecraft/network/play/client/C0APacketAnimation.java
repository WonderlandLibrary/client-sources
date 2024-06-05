package net.minecraft.network.play.client;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class C0APacketAnimation implements Packet
{
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
    }
    
    public void func_179721_a(final INetHandlerPlayServer p_179721_1_) {
        p_179721_1_.func_175087_a(this);
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.func_179721_a((INetHandlerPlayServer)handler);
    }
}
