package net.minecraft.src;

import java.io.*;

public class Packet16BlockItemSwitch extends Packet
{
    public int id;
    
    public Packet16BlockItemSwitch() {
    }
    
    public Packet16BlockItemSwitch(final int par1) {
        this.id = par1;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.id = par1DataInputStream.readShort();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeShort(this.id);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleBlockItemSwitch(this);
    }
    
    @Override
    public int getPacketSize() {
        return 2;
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
