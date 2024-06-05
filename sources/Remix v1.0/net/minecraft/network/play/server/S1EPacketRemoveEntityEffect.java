package net.minecraft.network.play.server;

import net.minecraft.potion.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S1EPacketRemoveEntityEffect implements Packet
{
    private int field_149079_a;
    private int field_149078_b;
    
    public S1EPacketRemoveEntityEffect() {
    }
    
    public S1EPacketRemoveEntityEffect(final int p_i45212_1_, final PotionEffect p_i45212_2_) {
        this.field_149079_a = p_i45212_1_;
        this.field_149078_b = p_i45212_2_.getPotionID();
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.field_149079_a = data.readVarIntFromBuffer();
        this.field_149078_b = data.readUnsignedByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeVarIntToBuffer(this.field_149079_a);
        data.writeByte(this.field_149078_b);
    }
    
    public void processPacket(final INetHandlerPlayClient handler) {
        handler.handleRemoveEntityEffect(this);
    }
    
    public int func_149076_c() {
        return this.field_149079_a;
    }
    
    public int func_149075_d() {
        return this.field_149078_b;
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.processPacket((INetHandlerPlayClient)handler);
    }
}
