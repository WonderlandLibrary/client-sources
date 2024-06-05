package net.minecraft.src;

import java.io.*;

public class Packet39AttachEntity extends Packet
{
    public int entityId;
    public int vehicleEntityId;
    
    public Packet39AttachEntity() {
    }
    
    public Packet39AttachEntity(final Entity par1Entity, final Entity par2Entity) {
        this.entityId = par1Entity.entityId;
        this.vehicleEntityId = ((par2Entity != null) ? par2Entity.entityId : -1);
    }
    
    @Override
    public int getPacketSize() {
        return 8;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.entityId = par1DataInputStream.readInt();
        this.vehicleEntityId = par1DataInputStream.readInt();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.entityId);
        par1DataOutputStream.writeInt(this.vehicleEntityId);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleAttachEntity(this);
    }
    
    @Override
    public boolean isRealPacket() {
        return true;
    }
    
    @Override
    public boolean containsSameEntityIDAs(final Packet par1Packet) {
        final Packet39AttachEntity var2 = (Packet39AttachEntity)par1Packet;
        return var2.entityId == this.entityId;
    }
}
