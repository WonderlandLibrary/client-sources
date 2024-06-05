package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;

public static class S16PacketEntityLook extends S14PacketEntity
{
    public S16PacketEntityLook() {
        this.field_149069_g = true;
    }
    
    public S16PacketEntityLook(final int p_i45972_1_, final byte p_i45972_2_, final byte p_i45972_3_, final boolean p_i45972_4_) {
        super(p_i45972_1_);
        this.field_149071_e = p_i45972_2_;
        this.field_149068_f = p_i45972_3_;
        this.field_149069_g = true;
        this.field_179743_g = p_i45972_4_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        super.readPacketData(data);
        this.field_149071_e = data.readByte();
        this.field_149068_f = data.readByte();
        this.field_179743_g = data.readBoolean();
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        super.writePacketData(data);
        data.writeByte(this.field_149071_e);
        data.writeByte(this.field_149068_f);
        data.writeBoolean(this.field_179743_g);
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        super.processPacket((INetHandlerPlayClient)handler);
    }
}
