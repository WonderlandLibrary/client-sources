package net.minecraft.src;

import java.io.*;

public class Packet42RemoveEntityEffect extends Packet
{
    public int entityId;
    public byte effectId;
    
    public Packet42RemoveEntityEffect() {
    }
    
    public Packet42RemoveEntityEffect(final int par1, final PotionEffect par2PotionEffect) {
        this.entityId = par1;
        this.effectId = (byte)(par2PotionEffect.getPotionID() & 0xFF);
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.entityId = par1DataInputStream.readInt();
        this.effectId = par1DataInputStream.readByte();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.entityId);
        par1DataOutputStream.writeByte(this.effectId);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleRemoveEntityEffect(this);
    }
    
    @Override
    public int getPacketSize() {
        return 5;
    }
}
