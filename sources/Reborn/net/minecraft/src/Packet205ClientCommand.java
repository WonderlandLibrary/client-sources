package net.minecraft.src;

import java.io.*;

public class Packet205ClientCommand extends Packet
{
    public int forceRespawn;
    
    public Packet205ClientCommand() {
    }
    
    public Packet205ClientCommand(final int par1) {
        this.forceRespawn = par1;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.forceRespawn = par1DataInputStream.readByte();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeByte(this.forceRespawn & 0xFF);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleClientCommand(this);
    }
    
    @Override
    public int getPacketSize() {
        return 1;
    }
}
