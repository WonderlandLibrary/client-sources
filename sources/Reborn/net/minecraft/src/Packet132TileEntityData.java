package net.minecraft.src;

import java.io.*;

public class Packet132TileEntityData extends Packet
{
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public int actionType;
    public NBTTagCompound customParam1;
    
    public Packet132TileEntityData() {
        this.isChunkDataPacket = true;
    }
    
    public Packet132TileEntityData(final int par1, final int par2, final int par3, final int par4, final NBTTagCompound par5NBTTagCompound) {
        this.isChunkDataPacket = true;
        this.xPosition = par1;
        this.yPosition = par2;
        this.zPosition = par3;
        this.actionType = par4;
        this.customParam1 = par5NBTTagCompound;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.xPosition = par1DataInputStream.readInt();
        this.yPosition = par1DataInputStream.readShort();
        this.zPosition = par1DataInputStream.readInt();
        this.actionType = par1DataInputStream.readByte();
        this.customParam1 = Packet.readNBTTagCompound(par1DataInputStream);
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.xPosition);
        par1DataOutputStream.writeShort(this.yPosition);
        par1DataOutputStream.writeInt(this.zPosition);
        par1DataOutputStream.writeByte((byte)this.actionType);
        Packet.writeNBTTagCompound(this.customParam1, par1DataOutputStream);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleTileEntityData(this);
    }
    
    @Override
    public int getPacketSize() {
        return 25;
    }
}
