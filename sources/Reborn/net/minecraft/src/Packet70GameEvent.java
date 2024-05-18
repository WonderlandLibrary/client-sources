package net.minecraft.src;

import java.io.*;

public class Packet70GameEvent extends Packet
{
    public static final String[] clientMessage;
    public int eventType;
    public int gameMode;
    
    static {
        clientMessage = new String[] { "tile.bed.notValid", null, null, "gameMode.changed" };
    }
    
    public Packet70GameEvent() {
    }
    
    public Packet70GameEvent(final int par1, final int par2) {
        this.eventType = par1;
        this.gameMode = par2;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.eventType = par1DataInputStream.readByte();
        this.gameMode = par1DataInputStream.readByte();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeByte(this.eventType);
        par1DataOutputStream.writeByte(this.gameMode);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleGameEvent(this);
    }
    
    @Override
    public int getPacketSize() {
        return 2;
    }
}
