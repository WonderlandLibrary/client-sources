package net.minecraft.network.status.client;

import java.io.*;
import net.minecraft.network.status.*;
import net.minecraft.network.*;

public class C00PacketServerQuery implements Packet
{
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
    }
    
    public void func_180775_a(final INetHandlerStatusServer p_180775_1_) {
        p_180775_1_.processServerQuery(this);
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.func_180775_a((INetHandlerStatusServer)handler);
    }
}
