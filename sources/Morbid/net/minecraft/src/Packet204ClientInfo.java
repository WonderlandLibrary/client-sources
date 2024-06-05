package net.minecraft.src;

import java.io.*;

public class Packet204ClientInfo extends Packet
{
    private String language;
    private int renderDistance;
    private int chatVisisble;
    private boolean chatColours;
    private int gameDifficulty;
    private boolean showCape;
    
    public Packet204ClientInfo() {
    }
    
    public Packet204ClientInfo(final String par1Str, final int par2, final int par3, final boolean par4, final int par5, final boolean par6) {
        this.language = par1Str;
        this.renderDistance = par2;
        this.chatVisisble = par3;
        this.chatColours = par4;
        this.gameDifficulty = par5;
        this.showCape = par6;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.language = Packet.readString(par1DataInputStream, 7);
        this.renderDistance = par1DataInputStream.readByte();
        final byte var2 = par1DataInputStream.readByte();
        this.chatVisisble = (var2 & 0x7);
        this.chatColours = ((var2 & 0x8) == 0x8);
        this.gameDifficulty = par1DataInputStream.readByte();
        this.showCape = par1DataInputStream.readBoolean();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        Packet.writeString(this.language, par1DataOutputStream);
        par1DataOutputStream.writeByte(this.renderDistance);
        par1DataOutputStream.writeByte(this.chatVisisble | (this.chatColours ? 1 : 0) << 3);
        par1DataOutputStream.writeByte(this.gameDifficulty);
        par1DataOutputStream.writeBoolean(this.showCape);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleClientInfo(this);
    }
    
    @Override
    public int getPacketSize() {
        return 7;
    }
    
    public String getLanguage() {
        return this.language;
    }
    
    public int getRenderDistance() {
        return this.renderDistance;
    }
    
    public int getChatVisibility() {
        return this.chatVisisble;
    }
    
    public boolean getChatColours() {
        return this.chatColours;
    }
    
    public int getDifficulty() {
        return this.gameDifficulty;
    }
    
    public boolean getShowCape() {
        return this.showCape;
    }
    
    @Override
    public boolean isRealPacket() {
        return true;
    }
    
    @Override
    public boolean containsSameEntityIDAs(final Packet par1Packet) {
        return true;
    }
}
