package net.minecraft.network.play.server;

import net.minecraft.network.play.*;
import java.util.*;
import net.minecraft.entity.*;
import java.io.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.network.*;

public class S0CPacketSpawnPlayer implements Packet<INetHandlerPlayClient>
{
    private UUID playerId;
    private byte pitch;
    private int x;
    private List<DataWatcher.WatchableObject> field_148958_j;
    private DataWatcher watcher;
    private int currentItem;
    private int z;
    private byte yaw;
    private int entityId;
    private int y;
    
    public List<DataWatcher.WatchableObject> func_148944_c() {
        if (this.field_148958_j == null) {
            this.field_148958_j = this.watcher.getAllWatched();
        }
        return this.field_148958_j;
    }
    
    @Override
    public void processPacket(final INetHandlerPlayClient netHandlerPlayClient) {
        netHandlerPlayClient.handleSpawnPlayer(this);
    }
    
    @Override
    public void writePacketData(final PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeUuid(this.playerId);
        packetBuffer.writeInt(this.x);
        packetBuffer.writeInt(this.y);
        packetBuffer.writeInt(this.z);
        packetBuffer.writeByte(this.yaw);
        packetBuffer.writeByte(this.pitch);
        packetBuffer.writeShort(this.currentItem);
        this.watcher.writeTo(packetBuffer);
    }
    
    public int getEntityID() {
        return this.entityId;
    }
    
    public int getY() {
        return this.y;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public S0CPacketSpawnPlayer() {
    }
    
    public byte getYaw() {
        return this.yaw;
    }
    
    @Override
    public void readPacketData(final PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarIntFromBuffer();
        this.playerId = packetBuffer.readUuid();
        this.x = packetBuffer.readInt();
        this.y = packetBuffer.readInt();
        this.z = packetBuffer.readInt();
        this.yaw = packetBuffer.readByte();
        this.pitch = packetBuffer.readByte();
        this.currentItem = packetBuffer.readShort();
        this.field_148958_j = DataWatcher.readWatchedListFromPacketBuffer(packetBuffer);
    }
    
    public int getCurrentItemID() {
        return this.currentItem;
    }
    
    public int getX() {
        return this.x;
    }
    
    public S0CPacketSpawnPlayer(final EntityPlayer entityPlayer) {
        this.entityId = entityPlayer.getEntityId();
        this.playerId = entityPlayer.getGameProfile().getId();
        this.x = MathHelper.floor_double(entityPlayer.posX * 32.0);
        this.y = MathHelper.floor_double(entityPlayer.posY * 32.0);
        this.z = MathHelper.floor_double(entityPlayer.posZ * 32.0);
        this.yaw = (byte)(entityPlayer.rotationYaw * 256.0f / 360.0f);
        this.pitch = (byte)(entityPlayer.rotationPitch * 256.0f / 360.0f);
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        int currentItem2;
        if (currentItem == null) {
            currentItem2 = "".length();
            "".length();
            if (2 < 0) {
                throw null;
            }
        }
        else {
            currentItem2 = Item.getIdFromItem(currentItem.getItem());
        }
        this.currentItem = currentItem2;
        this.watcher = entityPlayer.getDataWatcher();
    }
    
    public int getZ() {
        return this.z;
    }
    
    @Override
    public void processPacket(final INetHandler netHandler) {
        this.processPacket((INetHandlerPlayClient)netHandler);
    }
    
    public UUID getPlayer() {
        return this.playerId;
    }
    
    public byte getPitch() {
        return this.pitch;
    }
}
