package net.minecraft.network.play.client;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class C0DPacketCloseWindow implements Packet
{
    private int windowId;
    
    public C0DPacketCloseWindow() {
    }
    
    public C0DPacketCloseWindow(final int p_i45247_1_) {
        this.windowId = p_i45247_1_;
    }
    
    public void func_180759_a(final INetHandlerPlayServer p_180759_1_) {
        p_180759_1_.processCloseWindow(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.windowId = data.readByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeByte(this.windowId);
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.func_180759_a((INetHandlerPlayServer)handler);
    }
}
