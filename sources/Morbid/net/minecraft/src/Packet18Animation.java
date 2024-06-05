package net.minecraft.src;

import java.io.*;

public class Packet18Animation extends Packet
{
    public int entityId;
    public int animate;
    
    public Packet18Animation() {
    }
    
    public Packet18Animation(final Entity par1Entity, final int par2) {
        this.entityId = par1Entity.entityId;
        this.animate = par2;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.entityId = par1DataInputStream.readInt();
        this.animate = par1DataInputStream.readByte();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.entityId);
        par1DataOutputStream.writeByte(this.animate);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleAnimation(this);
    }
    
    @Override
    public int getPacketSize() {
        return 5;
    }
}
