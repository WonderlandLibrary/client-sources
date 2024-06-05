package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;

public static class S17PacketEntityLookMove extends S14PacketEntity
{
    public S17PacketEntityLookMove() {
        this.field_149069_g = true;
    }
    
    public S17PacketEntityLookMove(final int p_i45973_1_, final byte p_i45973_2_, final byte p_i45973_3_, final byte p_i45973_4_, final byte p_i45973_5_, final byte p_i45973_6_, final boolean p_i45973_7_) {
        super(p_i45973_1_);
        this.field_149072_b = p_i45973_2_;
        this.field_149073_c = p_i45973_3_;
        this.field_149070_d = p_i45973_4_;
        this.field_149071_e = p_i45973_5_;
        this.field_149068_f = p_i45973_6_;
        this.field_179743_g = p_i45973_7_;
        this.field_149069_g = true;
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        super.readPacketData(data);
        this.field_149072_b = data.readByte();
        this.field_149073_c = data.readByte();
        this.field_149070_d = data.readByte();
        this.field_149071_e = data.readByte();
        this.field_149068_f = data.readByte();
        this.field_179743_g = data.readBoolean();
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        super.writePacketData(data);
        data.writeByte(this.field_149072_b);
        data.writeByte(this.field_149073_c);
        data.writeByte(this.field_149070_d);
        data.writeByte(this.field_149071_e);
        data.writeByte(this.field_149068_f);
        data.writeBoolean(this.field_179743_g);
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        super.processPacket((INetHandlerPlayClient)handler);
    }
}
