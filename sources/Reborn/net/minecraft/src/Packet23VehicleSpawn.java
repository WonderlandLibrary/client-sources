package net.minecraft.src;

import java.io.*;

public class Packet23VehicleSpawn extends Packet
{
    public int entityId;
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public int speedX;
    public int speedY;
    public int speedZ;
    public int pitch;
    public int yaw;
    public int type;
    public int throwerEntityId;
    
    public Packet23VehicleSpawn() {
    }
    
    public Packet23VehicleSpawn(final Entity par1Entity, final int par2) {
        this(par1Entity, par2, 0);
    }
    
    public Packet23VehicleSpawn(final Entity par1Entity, final int par2, final int par3) {
        this.entityId = par1Entity.entityId;
        this.xPosition = MathHelper.floor_double(par1Entity.posX * 32.0);
        this.yPosition = MathHelper.floor_double(par1Entity.posY * 32.0);
        this.zPosition = MathHelper.floor_double(par1Entity.posZ * 32.0);
        this.pitch = MathHelper.floor_float(par1Entity.rotationPitch * 256.0f / 360.0f);
        this.yaw = MathHelper.floor_float(par1Entity.rotationYaw * 256.0f / 360.0f);
        this.type = par2;
        this.throwerEntityId = par3;
        if (par3 > 0) {
            double var4 = par1Entity.motionX;
            double var5 = par1Entity.motionY;
            double var6 = par1Entity.motionZ;
            final double var7 = 3.9;
            if (var4 < -var7) {
                var4 = -var7;
            }
            if (var5 < -var7) {
                var5 = -var7;
            }
            if (var6 < -var7) {
                var6 = -var7;
            }
            if (var4 > var7) {
                var4 = var7;
            }
            if (var5 > var7) {
                var5 = var7;
            }
            if (var6 > var7) {
                var6 = var7;
            }
            this.speedX = (int)(var4 * 8000.0);
            this.speedY = (int)(var5 * 8000.0);
            this.speedZ = (int)(var6 * 8000.0);
        }
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.entityId = par1DataInputStream.readInt();
        this.type = par1DataInputStream.readByte();
        this.xPosition = par1DataInputStream.readInt();
        this.yPosition = par1DataInputStream.readInt();
        this.zPosition = par1DataInputStream.readInt();
        this.pitch = par1DataInputStream.readByte();
        this.yaw = par1DataInputStream.readByte();
        this.throwerEntityId = par1DataInputStream.readInt();
        if (this.throwerEntityId > 0) {
            this.speedX = par1DataInputStream.readShort();
            this.speedY = par1DataInputStream.readShort();
            this.speedZ = par1DataInputStream.readShort();
        }
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.entityId);
        par1DataOutputStream.writeByte(this.type);
        par1DataOutputStream.writeInt(this.xPosition);
        par1DataOutputStream.writeInt(this.yPosition);
        par1DataOutputStream.writeInt(this.zPosition);
        par1DataOutputStream.writeByte(this.pitch);
        par1DataOutputStream.writeByte(this.yaw);
        par1DataOutputStream.writeInt(this.throwerEntityId);
        if (this.throwerEntityId > 0) {
            par1DataOutputStream.writeShort(this.speedX);
            par1DataOutputStream.writeShort(this.speedY);
            par1DataOutputStream.writeShort(this.speedZ);
        }
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleVehicleSpawn(this);
    }
    
    @Override
    public int getPacketSize() {
        return (21 + this.throwerEntityId > 0) ? 6 : 0;
    }
}
