package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.io.*;
import net.minecraft.network.*;

public class S2EPacketCloseWindow implements Packet
{
    private int field_148896_a;
    
    public S2EPacketCloseWindow() {
    }
    
    public S2EPacketCloseWindow(final int p_i45183_1_) {
        this.field_148896_a = p_i45183_1_;
    }
    
    public void func_180731_a(final INetHandlerPlayClient p_180731_1_) {
        p_180731_1_.handleCloseWindow(this);
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.field_148896_a = data.readUnsignedByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeByte(this.field_148896_a);
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.func_180731_a((INetHandlerPlayClient)handler);
    }
}
