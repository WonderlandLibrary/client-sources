package net.minecraft.src;

import java.io.*;

public class Packet250CustomPayload extends Packet
{
    public String channel;
    public int length;
    public byte[] data;
    
    public Packet250CustomPayload() {
    }
    
    public Packet250CustomPayload(final String par1Str, final byte[] par2ArrayOfByte) {
        this.channel = par1Str;
        this.data = par2ArrayOfByte;
        if (par2ArrayOfByte != null) {
            this.length = par2ArrayOfByte.length;
            if (this.length > 32767) {
                throw new IllegalArgumentException("Payload may not be larger than 32k");
            }
        }
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.channel = Packet.readString(par1DataInputStream, 20);
        this.length = par1DataInputStream.readShort();
        if (this.length > 0 && this.length < 32767) {
            par1DataInputStream.readFully(this.data = new byte[this.length]);
        }
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        Packet.writeString(this.channel, par1DataOutputStream);
        par1DataOutputStream.writeShort((short)this.length);
        if (this.data != null) {
            par1DataOutputStream.write(this.data);
        }
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleCustomPayload(this);
    }
    
    @Override
    public int getPacketSize() {
        return 2 + this.channel.length() * 2 + 2 + this.length;
    }
}
