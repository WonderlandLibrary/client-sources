package net.minecraft.src;

import java.io.*;

public class Packet106Transaction extends Packet
{
    public int windowId;
    public short shortWindowId;
    public boolean accepted;
    
    public Packet106Transaction() {
    }
    
    public Packet106Transaction(final int par1, final short par2, final boolean par3) {
        this.windowId = par1;
        this.shortWindowId = par2;
        this.accepted = par3;
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleTransaction(this);
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.windowId = par1DataInputStream.readByte();
        this.shortWindowId = par1DataInputStream.readShort();
        this.accepted = (par1DataInputStream.readByte() != 0);
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeByte(this.windowId);
        par1DataOutputStream.writeShort(this.shortWindowId);
        par1DataOutputStream.writeByte(this.accepted ? 1 : 0);
    }
    
    @Override
    public int getPacketSize() {
        return 4;
    }
}
