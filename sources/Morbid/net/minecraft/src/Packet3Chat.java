package net.minecraft.src;

import java.io.*;

public class Packet3Chat extends Packet
{
    public static int maxChatLength;
    public String message;
    private boolean isServer;
    
    static {
        Packet3Chat.maxChatLength = 119;
    }
    
    public Packet3Chat() {
        this.isServer = true;
    }
    
    public Packet3Chat(final String par1Str) {
        this(par1Str, true);
    }
    
    public Packet3Chat(String par1Str, final boolean par2) {
        this.isServer = true;
        if (par1Str.length() > Packet3Chat.maxChatLength) {
            par1Str = par1Str.substring(0, Packet3Chat.maxChatLength);
        }
        this.message = par1Str;
        this.isServer = par2;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.message = Packet.readString(par1DataInputStream, Packet3Chat.maxChatLength);
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        Packet.writeString(this.message, par1DataOutputStream);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleChat(this);
    }
    
    @Override
    public int getPacketSize() {
        return 2 + this.message.length() * 2;
    }
    
    public boolean getIsServer() {
        return this.isServer;
    }
    
    @Override
    public boolean canProcessAsync() {
        return !this.message.startsWith("/");
    }
}
