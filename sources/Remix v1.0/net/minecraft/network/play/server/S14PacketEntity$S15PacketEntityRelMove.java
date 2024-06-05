package net.minecraft.network.play.server;

import java.io.*;
import net.minecraft.network.*;
import net.minecraft.network.play.*;

public static class S15PacketEntityRelMove extends S14PacketEntity
{
    public S15PacketEntityRelMove() {
    }
    
    public S15PacketEntityRelMove(final int p_i45974_1_, final byte p_i45974_2_, final byte p_i45974_3_, final byte p_i45974_4_, final boolean p_i45974_5_) {
        super(p_i45974_1_);
        this.field_149072_b = p_i45974_2_;
        this.field_149073_c = p_i45974_3_;
        this.field_149070_d = p_i45974_4_;
        this.field_179743_g = p_i45974_5_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        super.readPacketData(data);
        this.field_149072_b = data.readByte();
        this.field_149073_c = data.readByte();
        this.field_149070_d = data.readByte();
        this.field_179743_g = data.readBoolean();
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        super.writePacketData(data);
        data.writeByte(this.field_149072_b);
        data.writeByte(this.field_149073_c);
        data.writeByte(this.field_149070_d);
        data.writeBoolean(this.field_179743_g);
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        super.processPacket((INetHandlerPlayClient)handler);
    }
}
