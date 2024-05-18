package net.minecraft.src;

import java.io.*;

public class Packet130UpdateSign extends Packet
{
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public String[] signLines;
    
    public Packet130UpdateSign() {
        this.isChunkDataPacket = true;
    }
    
    public Packet130UpdateSign(final int par1, final int par2, final int par3, final String[] par4ArrayOfStr) {
        this.isChunkDataPacket = true;
        this.xPosition = par1;
        this.yPosition = par2;
        this.zPosition = par3;
        this.signLines = new String[] { par4ArrayOfStr[0], par4ArrayOfStr[1], par4ArrayOfStr[2], par4ArrayOfStr[3] };
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.xPosition = par1DataInputStream.readInt();
        this.yPosition = par1DataInputStream.readShort();
        this.zPosition = par1DataInputStream.readInt();
        this.signLines = new String[4];
        for (int var2 = 0; var2 < 4; ++var2) {
            this.signLines[var2] = Packet.readString(par1DataInputStream, 15);
        }
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.xPosition);
        par1DataOutputStream.writeShort(this.yPosition);
        par1DataOutputStream.writeInt(this.zPosition);
        for (int var2 = 0; var2 < 4; ++var2) {
            Packet.writeString(this.signLines[var2], par1DataOutputStream);
        }
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleUpdateSign(this);
    }
    
    @Override
    public int getPacketSize() {
        int var1 = 0;
        for (int var2 = 0; var2 < 4; ++var2) {
            var1 += this.signLines[var2].length();
        }
        return var1;
    }
}
