package net.minecraft.src;

import java.io.*;

public class Packet30Entity extends Packet
{
    public int entityId;
    public byte xPosition;
    public byte yPosition;
    public byte zPosition;
    public byte yaw;
    public byte pitch;
    public boolean rotating;
    
    public Packet30Entity() {
        this.rotating = false;
    }
    
    public Packet30Entity(final int par1) {
        this.rotating = false;
        this.entityId = par1;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.entityId = par1DataInputStream.readInt();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.entityId);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleEntity(this);
    }
    
    @Override
    public int getPacketSize() {
        return 4;
    }
    
    @Override
    public String toString() {
        return "Entity_" + super.toString();
    }
    
    @Override
    public boolean isRealPacket() {
        return true;
    }
    
    @Override
    public boolean containsSameEntityIDAs(final Packet par1Packet) {
        final Packet30Entity var2 = (Packet30Entity)par1Packet;
        return var2.entityId == this.entityId;
    }
}
