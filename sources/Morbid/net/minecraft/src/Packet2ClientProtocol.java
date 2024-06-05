package net.minecraft.src;

import java.io.*;

public class Packet2ClientProtocol extends Packet
{
    private int protocolVersion;
    private String username;
    private String serverHost;
    private int serverPort;
    
    public Packet2ClientProtocol() {
    }
    
    public Packet2ClientProtocol(final int par1, final String par2Str, final String par3Str, final int par4) {
        this.protocolVersion = par1;
        this.username = par2Str;
        this.serverHost = par3Str;
        this.serverPort = par4;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.protocolVersion = par1DataInputStream.readByte();
        this.username = Packet.readString(par1DataInputStream, 16);
        this.serverHost = Packet.readString(par1DataInputStream, 255);
        this.serverPort = par1DataInputStream.readInt();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeByte(this.protocolVersion);
        Packet.writeString(this.username, par1DataOutputStream);
        Packet.writeString(this.serverHost, par1DataOutputStream);
        par1DataOutputStream.writeInt(this.serverPort);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleClientProtocol(this);
    }
    
    @Override
    public int getPacketSize() {
        return 3 + 2 * this.username.length();
    }
    
    public int getProtocolVersion() {
        return this.protocolVersion;
    }
    
    public String getUsername() {
        return this.username;
    }
}
