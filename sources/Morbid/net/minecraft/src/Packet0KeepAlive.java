package net.minecraft.src;

import java.io.*;

public class Packet0KeepAlive extends Packet
{
    public int randomId;
    
    public Packet0KeepAlive() {
    }
    
    public Packet0KeepAlive(final int par1) {
        this.randomId = par1;
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleKeepAlive(this);
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.randomId = par1DataInputStream.readInt();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.randomId);
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
    
    @Override
    public boolean canProcessAsync() {
        return true;
    }
}
