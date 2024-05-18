package net.minecraft.src;

import java.io.*;

public class Packet100OpenWindow extends Packet
{
    public int windowId;
    public int inventoryType;
    public String windowTitle;
    public int slotsCount;
    public boolean useProvidedWindowTitle;
    
    public Packet100OpenWindow() {
    }
    
    public Packet100OpenWindow(final int par1, final int par2, final String par3Str, final int par4, final boolean par5) {
        this.windowId = par1;
        this.inventoryType = par2;
        this.windowTitle = par3Str;
        this.slotsCount = par4;
        this.useProvidedWindowTitle = par5;
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleOpenWindow(this);
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.windowId = (par1DataInputStream.readByte() & 0xFF);
        this.inventoryType = (par1DataInputStream.readByte() & 0xFF);
        this.windowTitle = Packet.readString(par1DataInputStream, 32);
        this.slotsCount = (par1DataInputStream.readByte() & 0xFF);
        this.useProvidedWindowTitle = par1DataInputStream.readBoolean();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeByte(this.windowId & 0xFF);
        par1DataOutputStream.writeByte(this.inventoryType & 0xFF);
        Packet.writeString(this.windowTitle, par1DataOutputStream);
        par1DataOutputStream.writeByte(this.slotsCount & 0xFF);
        par1DataOutputStream.writeBoolean(this.useProvidedWindowTitle);
    }
    
    @Override
    public int getPacketSize() {
        return 4 + this.windowTitle.length();
    }
}
