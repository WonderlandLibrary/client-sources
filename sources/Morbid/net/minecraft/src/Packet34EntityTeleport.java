package net.minecraft.src;

import java.io.*;

public class Packet34EntityTeleport extends Packet
{
    public int entityId;
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public byte yaw;
    public byte pitch;
    
    public Packet34EntityTeleport() {
    }
    
    public Packet34EntityTeleport(final Entity par1Entity) {
        this.entityId = par1Entity.entityId;
        this.xPosition = MathHelper.floor_double(par1Entity.posX * 32.0);
        this.yPosition = MathHelper.floor_double(par1Entity.posY * 32.0);
        this.zPosition = MathHelper.floor_double(par1Entity.posZ * 32.0);
        this.yaw = (byte)(par1Entity.rotationYaw * 256.0f / 360.0f);
        this.pitch = (byte)(par1Entity.rotationPitch * 256.0f / 360.0f);
    }
    
    public Packet34EntityTeleport(final int par1, final int par2, final int par3, final int par4, final byte par5, final byte par6) {
        this.entityId = par1;
        this.xPosition = par2;
        this.yPosition = par3;
        this.zPosition = par4;
        this.yaw = par5;
        this.pitch = par6;
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.entityId = par1DataInputStream.readInt();
        this.xPosition = par1DataInputStream.readInt();
        this.yPosition = par1DataInputStream.readInt();
        this.zPosition = par1DataInputStream.readInt();
        this.yaw = (byte)par1DataInputStream.read();
        this.pitch = (byte)par1DataInputStream.read();
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.entityId);
        par1DataOutputStream.writeInt(this.xPosition);
        par1DataOutputStream.writeInt(this.yPosition);
        par1DataOutputStream.writeInt(this.zPosition);
        par1DataOutputStream.write(this.yaw);
        par1DataOutputStream.write(this.pitch);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleEntityTeleport(this);
    }
    
    @Override
    public int getPacketSize() {
        return 34;
    }
    
    @Override
    public boolean isRealPacket() {
        return true;
    }
    
    @Override
    public boolean containsSameEntityIDAs(final Packet par1Packet) {
        final Packet34EntityTeleport var2 = (Packet34EntityTeleport)par1Packet;
        return var2.entityId == this.entityId;
    }
}
