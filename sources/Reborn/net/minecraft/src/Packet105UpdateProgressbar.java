package net.minecraft.src;

import java.io.*;

public class Packet105UpdateProgressbar extends Packet
{
    public int windowId;
    public int progressBar;
    public int progressBarValue;
    
    public Packet105UpdateProgressbar() {
    }
    
    public Packet105UpdateProgressbar(final int par1, final int par2, final int par3) {
        this.windowId = par1;
        this.progressBar = par2;
        this.progressBarValue = par3;
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleUpdateProgressbar(this);
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.windowId = par1DataInputStream.readByte();
        this.progressBar = par1DataInputStream.readShort();
        this.progressBarValue = par1DataInputStream.readShort();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeByte(this.windowId);
        par1DataOutputStream.writeShort(this.progressBar);
        par1DataOutputStream.writeShort(this.progressBarValue);
    }
    
    @Override
    public int getPacketSize() {
        return 5;
    }
}
