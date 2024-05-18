package net.minecraft.src;

import java.io.*;

public class Packet41EntityEffect extends Packet
{
    public int entityId;
    public byte effectId;
    public byte effectAmplifier;
    public short duration;
    
    public Packet41EntityEffect() {
    }
    
    public Packet41EntityEffect(final int par1, final PotionEffect par2PotionEffect) {
        this.entityId = par1;
        this.effectId = (byte)(par2PotionEffect.getPotionID() & 0xFF);
        this.effectAmplifier = (byte)(par2PotionEffect.getAmplifier() & 0xFF);
        if (par2PotionEffect.getDuration() > 32767) {
            this.duration = 32767;
        }
        else {
            this.duration = (short)par2PotionEffect.getDuration();
        }
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.entityId = par1DataInputStream.readInt();
        this.effectId = par1DataInputStream.readByte();
        this.effectAmplifier = par1DataInputStream.readByte();
        this.duration = par1DataInputStream.readShort();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.entityId);
        par1DataOutputStream.writeByte(this.effectId);
        par1DataOutputStream.writeByte(this.effectAmplifier);
        par1DataOutputStream.writeShort(this.duration);
    }
    
    public boolean isDurationMax() {
        return this.duration == 32767;
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleEntityEffect(this);
    }
    
    @Override
    public int getPacketSize() {
        return 8;
    }
    
    @Override
    public boolean isRealPacket() {
        return true;
    }
    
    @Override
    public boolean containsSameEntityIDAs(final Packet par1Packet) {
        final Packet41EntityEffect var2 = (Packet41EntityEffect)par1Packet;
        return var2.entityId == this.entityId && var2.effectId == this.effectId;
    }
}
