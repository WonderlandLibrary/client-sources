package net.minecraft.src;

import java.io.*;

public class Packet29DestroyEntity extends Packet
{
    public int[] entityId;
    
    public Packet29DestroyEntity() {
    }
    
    public Packet29DestroyEntity(final int... par1ArrayOfInteger) {
        this.entityId = par1ArrayOfInteger;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.entityId = new int[par1DataInputStream.readByte()];
        for (int var2 = 0; var2 < this.entityId.length; ++var2) {
            this.entityId[var2] = par1DataInputStream.readInt();
        }
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeByte(this.entityId.length);
        for (int var2 = 0; var2 < this.entityId.length; ++var2) {
            par1DataOutputStream.writeInt(this.entityId[var2]);
        }
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleDestroyEntity(this);
    }
    
    @Override
    public int getPacketSize() {
        return 1 + this.entityId.length * 4;
    }
}
