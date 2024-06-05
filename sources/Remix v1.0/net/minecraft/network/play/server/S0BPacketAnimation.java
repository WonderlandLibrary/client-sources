package net.minecraft.network.play.server;

import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S0BPacketAnimation implements Packet
{
    private int entityId;
    private int type;
    
    public S0BPacketAnimation() {
    }
    
    public S0BPacketAnimation(final Entity ent, final int animationType) {
        this.entityId = ent.getEntityId();
        this.type = animationType;
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.entityId = data.readVarIntFromBuffer();
        this.type = data.readUnsignedByte();
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeVarIntToBuffer(this.entityId);
        data.writeByte(this.type);
    }
    
    public void func_180723_a(final INetHandlerPlayClient p_180723_1_) {
        p_180723_1_.handleAnimation(this);
    }
    
    public int func_148978_c() {
        return this.entityId;
    }
    
    public int func_148977_d() {
        return this.type;
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.func_180723_a((INetHandlerPlayClient)handler);
    }
}
