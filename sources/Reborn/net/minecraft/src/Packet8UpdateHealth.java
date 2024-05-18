package net.minecraft.src;

import java.io.*;

public class Packet8UpdateHealth extends Packet
{
    public int healthMP;
    public int food;
    public float foodSaturation;
    
    public Packet8UpdateHealth() {
    }
    
    public Packet8UpdateHealth(final int par1, final int par2, final float par3) {
        this.healthMP = par1;
        this.food = par2;
        this.foodSaturation = par3;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.healthMP = par1DataInputStream.readShort();
        this.food = par1DataInputStream.readShort();
        this.foodSaturation = par1DataInputStream.readFloat();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeShort(this.healthMP);
        par1DataOutputStream.writeShort(this.food);
        par1DataOutputStream.writeFloat(this.foodSaturation);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleUpdateHealth(this);
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
        return true;
    }
}
