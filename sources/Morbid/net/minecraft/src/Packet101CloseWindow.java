package net.minecraft.src;

import java.io.*;

public class Packet101CloseWindow extends Packet
{
    public int windowId;
    
    public Packet101CloseWindow() {
    }
    
    public Packet101CloseWindow(final int par1) {
        this.windowId = par1;
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleCloseWindow(this);
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.windowId = par1DataInputStream.readByte();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeByte(this.windowId);
    }
    
    @Override
    public int getPacketSize() {
        return 1;
    }
}
