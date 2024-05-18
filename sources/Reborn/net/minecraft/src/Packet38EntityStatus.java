package net.minecraft.src;

import java.io.*;

public class Packet38EntityStatus extends Packet
{
    public int entityId;
    public byte entityStatus;
    
    public Packet38EntityStatus() {
    }
    
    public Packet38EntityStatus(final int par1, final byte par2) {
        this.entityId = par1;
        this.entityStatus = par2;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.entityId = par1DataInputStream.readInt();
        this.entityStatus = par1DataInputStream.readByte();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.entityId);
        par1DataOutputStream.writeByte(this.entityStatus);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleEntityStatus(this);
    }
    
    @Override
    public int getPacketSize() {
        return 5;
    }
}
