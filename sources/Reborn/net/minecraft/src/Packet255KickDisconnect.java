package net.minecraft.src;

import java.io.*;

public class Packet255KickDisconnect extends Packet
{
    public String reason;
    
    public Packet255KickDisconnect() {
    }
    
    public Packet255KickDisconnect(final String par1Str) {
        this.reason = par1Str;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.reason = Packet.readString(par1DataInputStream, 256);
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        Packet.writeString(this.reason, par1DataOutputStream);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleKickDisconnect(this);
    }
    
    @Override
    public int getPacketSize() {
        return this.reason.length();
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
