package net.minecraft.src;

import java.io.*;

public class Packet19EntityAction extends Packet
{
    public int entityId;
    public int state;
    
    public Packet19EntityAction() {
    }
    
    public Packet19EntityAction(final Entity par1Entity, final int par2) {
        this.entityId = par1Entity.entityId;
        this.state = par2;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.entityId = par1DataInputStream.readInt();
        this.state = par1DataInputStream.readByte();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.entityId);
        par1DataOutputStream.writeByte(this.state);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleEntityAction(this);
    }
    
    @Override
    public int getPacketSize() {
        return 5;
    }
}
