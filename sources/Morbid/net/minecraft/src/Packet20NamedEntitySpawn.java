package net.minecraft.src;

import java.util.*;
import java.io.*;

public class Packet20NamedEntitySpawn extends Packet
{
    public int entityId;
    public String name;
    public int xPosition;
    public int yPosition;
    public int zPosition;
    public byte rotation;
    public byte pitch;
    public int currentItem;
    private DataWatcher metadata;
    private List metadataWatchableObjects;
    
    public Packet20NamedEntitySpawn() {
    }
    
    public Packet20NamedEntitySpawn(final EntityPlayer par1EntityPlayer) {
        this.entityId = par1EntityPlayer.entityId;
        this.name = par1EntityPlayer.username;
        this.xPosition = MathHelper.floor_double(par1EntityPlayer.posX * 32.0);
        this.yPosition = MathHelper.floor_double(par1EntityPlayer.posY * 32.0);
        this.zPosition = MathHelper.floor_double(par1EntityPlayer.posZ * 32.0);
        this.rotation = (byte)(par1EntityPlayer.rotationYaw * 256.0f / 360.0f);
        this.pitch = (byte)(par1EntityPlayer.rotationPitch * 256.0f / 360.0f);
        final ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        this.currentItem = ((var2 == null) ? 0 : var2.itemID);
        this.metadata = par1EntityPlayer.getDataWatcher();
    }
    
    @Override
    public void readPacketData(final DataInputStream par1DataInputStream) throws IOException {
        this.entityId = par1DataInputStream.readInt();
        this.name = Packet.readString(par1DataInputStream, 16);
        this.xPosition = par1DataInputStream.readInt();
        this.yPosition = par1DataInputStream.readInt();
        this.zPosition = par1DataInputStream.readInt();
        this.rotation = par1DataInputStream.readByte();
        this.pitch = par1DataInputStream.readByte();
        this.currentItem = par1DataInputStream.readShort();
        this.metadataWatchableObjects = DataWatcher.readWatchableObjects(par1DataInputStream);
    }
    
    @Override
    public void writePacketData(final DataOutputStream par1DataOutputStream) throws IOException {
        par1DataOutputStream.writeInt(this.entityId);
        Packet.writeString(this.name, par1DataOutputStream);
        par1DataOutputStream.writeInt(this.xPosition);
        par1DataOutputStream.writeInt(this.yPosition);
        par1DataOutputStream.writeInt(this.zPosition);
        par1DataOutputStream.writeByte(this.rotation);
        par1DataOutputStream.writeByte(this.pitch);
        par1DataOutputStream.writeShort(this.currentItem);
        this.metadata.writeWatchableObjects(par1DataOutputStream);
    }
    
    @Override
    public void processPacket(final NetHandler par1NetHandler) {
        par1NetHandler.handleNamedEntitySpawn(this);
    }
    
    @Override
    public int getPacketSize() {
        return 28;
    }
    
    public List getWatchedMetadata() {
        if (this.metadataWatchableObjects == null) {
            this.metadataWatchableObjects = this.metadata.getAllWatched();
        }
        return this.metadataWatchableObjects;
    }
}
