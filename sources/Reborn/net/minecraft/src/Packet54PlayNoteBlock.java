package net.minecraft.src;

import java.io.*;

public class Packet54PlayNoteBlock extends Packet
{
    public int xLocation;
    public int yLocation;
    public int zLocation;
    public int instrumentType;
    public int pitch;
    public int blockId;
    
    public Packet54PlayNoteBlock() {
    }
    
    public Packet54PlayNoteBlock(final int par1, final int par2, final int par3, final int par4, final int par5, final int par6) {
        this.xLocation = par1;
        this.yLocation = par2;
        this.zLocation = par3;
        this.instrumentType = par5;
        this.pitch = par6;
        this.blockId = par4;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.xLocation = par1DataInputStream.readInt();
        this.yLocation = par1DataInputStream.readShort();
        this.zLocation = par1DataInputStream.readInt();
        this.instrumentType = par1DataInputStream.read();
        this.pitch = par1DataInputStream.read();
        this.blockId = (par1DataInputStream.readShort() & 0xFFF);
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.xLocation);
        par1DataOutputStream.writeShort(this.yLocation);
        par1DataOutputStream.writeInt(this.zLocation);
        par1DataOutputStream.write(this.instrumentType);
        par1DataOutputStream.write(this.pitch);
        par1DataOutputStream.writeShort(this.blockId & 0xFFF);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleBlockEvent(this);
    }
    
    @Override
    public int getPacketSize() {
        return 14;
    }
}
