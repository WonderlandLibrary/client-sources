package net.minecraft.src;

import java.io.*;

public class Packet31RelEntityMove extends Packet30Entity
{
    public Packet31RelEntityMove() {
    }
    
    public Packet31RelEntityMove(final int par1, final byte par2, final byte par3, final byte par4) {
        super(par1);
        this.xPosition = par2;
        this.yPosition = par3;
        this.zPosition = par4;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        super.readPacketData(par1DataInputStream);
        this.xPosition = par1DataInputStream.readByte();
        this.yPosition = par1DataInputStream.readByte();
        this.zPosition = par1DataInputStream.readByte();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        super.writePacketData(par1DataOutputStream);
        par1DataOutputStream.writeByte(this.xPosition);
        par1DataOutputStream.writeByte(this.yPosition);
        par1DataOutputStream.writeByte(this.zPosition);
    }
    
    @Override
    public int getPacketSize() {
        return 7;
    }
}
