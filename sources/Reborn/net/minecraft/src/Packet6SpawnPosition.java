package net.minecraft.src;

import java.io.*;

public class Packet6SpawnPosition extends Packet
{
    public int xPosition;
    public int yPosition;
    public int zPosition;
    
    public Packet6SpawnPosition() {
    }
    
    public Packet6SpawnPosition(final int par1, final int par2, final int par3) {
        this.xPosition = par1;
        this.yPosition = par2;
        this.zPosition = par3;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.xPosition = par1DataInputStream.readInt();
        this.yPosition = par1DataInputStream.readInt();
        this.zPosition = par1DataInputStream.readInt();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.xPosition);
        par1DataOutputStream.writeInt(this.yPosition);
        par1DataOutputStream.writeInt(this.zPosition);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleSpawnPosition(this);
    }
    
    @Override
    public int getPacketSize() {
        return 12;
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
        return false;
    }
}
