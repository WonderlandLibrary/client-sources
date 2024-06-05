package net.minecraft.src;

import java.io.*;

public class Packet43Experience extends Packet
{
    public float experience;
    public int experienceTotal;
    public int experienceLevel;
    
    public Packet43Experience() {
    }
    
    public Packet43Experience(final float par1, final int par2, final int par3) {
        this.experience = par1;
        this.experienceTotal = par2;
        this.experienceLevel = par3;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.experience = par1DataInputStream.readFloat();
        this.experienceLevel = par1DataInputStream.readShort();
        this.experienceTotal = par1DataInputStream.readShort();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeFloat(this.experience);
        par1DataOutputStream.writeShort(this.experienceLevel);
        par1DataOutputStream.writeShort(this.experienceTotal);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleExperience(this);
    }
    
    @Override
    public int getPacketSize() {
        return 4;
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
