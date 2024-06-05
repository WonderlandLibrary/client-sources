package net.minecraft.src;

import java.util.*;
import java.io.*;

public class Packet24MobSpawn extends Packet
{
    public int entityId;
    public int type;
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public int velocityX;
    public int velocityY;
    public int velocityZ;
    public byte yaw;
    public byte pitch;
    public byte headYaw;
    private DataWatcher metaData;
    private List metadata;
    
    public Packet24MobSpawn() {
    }
    
    public Packet24MobSpawn(final EntityLiving par1EntityLiving) {
        this.entityId = par1EntityLiving.entityId;
        this.type = (byte)EntityList.getEntityID(par1EntityLiving);
        this.xPosition = par1EntityLiving.myEntitySize.multiplyBy32AndRound(par1EntityLiving.posX);
        this.yPosition = MathHelper.floor_double(par1EntityLiving.posY * 32.0);
        this.zPosition = par1EntityLiving.myEntitySize.multiplyBy32AndRound(par1EntityLiving.posZ);
        this.yaw = (byte)(par1EntityLiving.rotationYaw * 256.0f / 360.0f);
        this.pitch = (byte)(par1EntityLiving.rotationPitch * 256.0f / 360.0f);
        this.headYaw = (byte)(par1EntityLiving.rotationYawHead * 256.0f / 360.0f);
        final double var2 = 3.9;
        double var3 = par1EntityLiving.motionX;
        double var4 = par1EntityLiving.motionY;
        double var5 = par1EntityLiving.motionZ;
        if (var3 < -var2) {
            var3 = -var2;
        }
        if (var4 < -var2) {
            var4 = -var2;
        }
        if (var5 < -var2) {
            var5 = -var2;
        }
        if (var3 > var2) {
            var3 = var2;
        }
        if (var4 > var2) {
            var4 = var2;
        }
        if (var5 > var2) {
            var5 = var2;
        }
        this.velocityX = (int)(var3 * 8000.0);
        this.velocityY = (int)(var4 * 8000.0);
        this.velocityZ = (int)(var5 * 8000.0);
        this.metaData = par1EntityLiving.getDataWatcher();
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.entityId = par1DataInputStream.readInt();
        this.type = (par1DataInputStream.readByte() & 0xFF);
        this.xPosition = par1DataInputStream.readInt();
        this.yPosition = par1DataInputStream.readInt();
        this.zPosition = par1DataInputStream.readInt();
        this.yaw = par1DataInputStream.readByte();
        this.pitch = par1DataInputStream.readByte();
        this.headYaw = par1DataInputStream.readByte();
        this.velocityX = par1DataInputStream.readShort();
        this.velocityY = par1DataInputStream.readShort();
        this.velocityZ = par1DataInputStream.readShort();
        this.metadata = DataWatcher.readWatchableObjects(par1DataInputStream);
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.entityId);
        par1DataOutputStream.writeByte(this.type & 0xFF);
        par1DataOutputStream.writeInt(this.xPosition);
        par1DataOutputStream.writeInt(this.yPosition);
        par1DataOutputStream.writeInt(this.zPosition);
        par1DataOutputStream.writeByte(this.yaw);
        par1DataOutputStream.writeByte(this.pitch);
        par1DataOutputStream.writeByte(this.headYaw);
        par1DataOutputStream.writeShort(this.velocityX);
        par1DataOutputStream.writeShort(this.velocityY);
        par1DataOutputStream.writeShort(this.velocityZ);
        this.metaData.writeWatchableObjects(par1DataOutputStream);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleMobSpawn(this);
    }
    
    @Override
    public int getPacketSize() {
        return 26;
    }
    
    public List getMetadata() {
        if (this.metadata == null) {
            this.metadata = this.metaData.getAllWatched();
        }
        return this.metadata;
    }
}
