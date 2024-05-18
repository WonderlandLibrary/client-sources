package net.minecraft.src;

import java.io.*;

public class Packet254ServerPing extends Packet
{
    public int readSuccessfully;
    
    public Packet254ServerPing() {
        this.readSuccessfully = 0;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        try {
            this.readSuccessfully = par1DataInputStream.readByte();
        }
        catch (Throwable var3) {
            this.readSuccessfully = 0;
        }
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleServerPing(this);
    }
    
    @Override
    public int getPacketSize() {
        return 0;
    }
}
