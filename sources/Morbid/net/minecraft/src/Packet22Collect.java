package net.minecraft.src;

import java.io.*;

public class Packet22Collect extends Packet
{
    public int collectedEntityId;
    public int collectorEntityId;
    
    public Packet22Collect() {
    }
    
    public Packet22Collect(final int par1, final int par2) {
        this.collectedEntityId = par1;
        this.collectorEntityId = par2;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.collectedEntityId = par1DataInputStream.readInt();
        this.collectorEntityId = par1DataInputStream.readInt();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.collectedEntityId);
        par1DataOutputStream.writeInt(this.collectorEntityId);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleCollect(this);
    }
    
    @Override
    public int getPacketSize() {
        return 8;
    }
}
