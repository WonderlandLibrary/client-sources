package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S13PacketDestroyEntities implements Packet
{
    private int[] field_149100_a;
    
    public S13PacketDestroyEntities() {
    }
    
    public S13PacketDestroyEntities(final int... p_i45211_1_) {
        this.field_149100_a = p_i45211_1_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.field_149100_a = new int[data.readVarIntFromBuffer()];
        for (int var2 = 0; var2 < this.field_149100_a.length; ++var2) {
            this.field_149100_a[var2] = data.readVarIntFromBuffer();
        }
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeVarIntToBuffer(this.field_149100_a.length);
        for (int var2 = 0; var2 < this.field_149100_a.length; ++var2) {
            data.writeVarIntToBuffer(this.field_149100_a[var2]);
        }
    }
    
    public void processPacket(final INetHandlerPlayClient handler) {
        handler.handleDestroyEntities(this);
    }
    
    public int[] func_149098_c() {
        return this.field_149100_a;
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.processPacket((INetHandlerPlayClient)handler);
    }
}
