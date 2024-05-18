package net.minecraft.src;

import java.io.*;

public class Packet201PlayerInfo extends Packet
{
    public String playerName;
    public boolean isConnected;
    public int ping;
    
    public Packet201PlayerInfo() {
    }
    
    public Packet201PlayerInfo(final String par1Str, final boolean par2, final int par3) {
        this.playerName = par1Str;
        this.isConnected = par2;
        this.ping = par3;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.playerName = Packet.readString(par1DataInputStream, 16);
        this.isConnected = (par1DataInputStream.readByte() != 0);
        this.ping = par1DataInputStream.readShort();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        Packet.writeString(this.playerName, par1DataOutputStream);
        par1DataOutputStream.writeByte(this.isConnected ? 1 : 0);
        par1DataOutputStream.writeShort(this.ping);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handlePlayerInfo(this);
    }
    
    @Override
    public int getPacketSize() {
        return this.playerName.length() + 2 + 1 + 2;
    }
}
