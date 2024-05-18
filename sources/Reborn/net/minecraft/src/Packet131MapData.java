package net.minecraft.src;

import java.io.*;

public class Packet131MapData extends Packet
{
    public short itemID;
    public short uniqueID;
    public byte[] itemData;
    
    public Packet131MapData() {
        this.isChunkDataPacket = true;
    }
    
    public Packet131MapData(final short par1, final short par2, final byte[] par3ArrayOfByte) {
        this.isChunkDataPacket = true;
        this.itemID = par1;
        this.uniqueID = par2;
        this.itemData = par3ArrayOfByte;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.itemID = par1DataInputStream.readShort();
        this.uniqueID = par1DataInputStream.readShort();
        par1DataInputStream.readFully(this.itemData = new byte[par1DataInputStream.readUnsignedShort()]);
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeShort(this.itemID);
        par1DataOutputStream.writeShort(this.uniqueID);
        par1DataOutputStream.writeShort(this.itemData.length);
        par1DataOutputStream.write(this.itemData);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleMapData(this);
    }
    
    @Override
    public int getPacketSize() {
        return 4 + this.itemData.length;
    }
}
