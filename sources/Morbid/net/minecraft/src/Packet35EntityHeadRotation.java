package net.minecraft.src;

import java.io.*;

public class Packet35EntityHeadRotation extends Packet
{
    public int entityId;
    public byte headRotationYaw;
    
    public Packet35EntityHeadRotation() {
    }
    
    public Packet35EntityHeadRotation(final int par1, final byte par2) {
        this.entityId = par1;
        this.headRotationYaw = par2;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.entityId = par1DataInputStream.readInt();
        this.headRotationYaw = par1DataInputStream.readByte();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.entityId);
        par1DataOutputStream.writeByte(this.headRotationYaw);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleEntityHeadRotation(this);
    }
    
    @Override
    public int getPacketSize() {
        return 5;
    }
    
    @Override
    public boolean isRealPacket() {
        return true;
    }
    
    @Override
    public boolean containsSameEntityIDAs(final Packet par1Packet) {
        final Packet35EntityHeadRotation var2 = (Packet35EntityHeadRotation)par1Packet;
        return var2.entityId == this.entityId;
    }
    
    @Override
    public boolean canProcessAsync() {
        return true;
    }
}
