package net.minecraft.src;

import java.io.*;

public class Packet4UpdateTime extends Packet
{
    public long worldAge;
    public long time;
    
    public Packet4UpdateTime() {
    }
    
    public Packet4UpdateTime(final long par1, final long par3) {
        this.worldAge = par1;
        this.time = par3;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.worldAge = par1DataInputStream.readLong();
        this.time = par1DataInputStream.readLong();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeLong(this.worldAge);
        par1DataOutputStream.writeLong(this.time);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleUpdateTime(this);
    }
    
    @Override
    public int getPacketSize() {
        return 16;
    }
    
    @Override
    public boolean isRealPacket() {
        return true;
    }
    
    @Override
    public boolean containsSameEntityIDAs(final Packet par1Packet) {
        return true;
    }
    
    @Override
    public boolean canProcessAsync() {
        return true;
    }
}
