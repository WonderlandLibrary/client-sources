package net.minecraft.src;

import java.io.*;

public class Packet203AutoComplete extends Packet
{
    private String text;
    
    public Packet203AutoComplete() {
    }
    
    public Packet203AutoComplete(final String par1Str) {
        this.text = par1Str;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.text = Packet.readString(par1DataInputStream, Packet3Chat.maxChatLength);
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        Packet.writeString(this.text, par1DataOutputStream);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleAutoComplete(this);
    }
    
    @Override
    public int getPacketSize() {
        return 2 + this.text.length() * 2;
    }
    
    public String getText() {
        return this.text;
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
