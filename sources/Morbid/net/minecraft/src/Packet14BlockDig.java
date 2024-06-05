package net.minecraft.src;

import java.io.*;

public class Packet14BlockDig extends Packet
{
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public int face;
    public int status;
    
    public Packet14BlockDig() {
    }
    
    public Packet14BlockDig(final int par1, final int par2, final int par3, final int par4, final int par5) {
        this.status = par1;
        this.xPosition = par2;
        this.yPosition = par3;
        this.zPosition = par4;
        this.face = par5;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.status = par1DataInputStream.read();
        this.xPosition = par1DataInputStream.readInt();
        this.yPosition = par1DataInputStream.read();
        this.zPosition = par1DataInputStream.readInt();
        this.face = par1DataInputStream.read();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.write(this.status);
        par1DataOutputStream.writeInt(this.xPosition);
        par1DataOutputStream.write(this.yPosition);
        par1DataOutputStream.writeInt(this.zPosition);
        par1DataOutputStream.write(this.face);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleBlockDig(this);
    }
    
    @Override
    public int getPacketSize() {
        return 11;
    }
}
