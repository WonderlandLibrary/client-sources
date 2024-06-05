package net.minecraft.network.play.server;

import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import java.io.*;
import net.minecraft.network.play.*;
import net.minecraft.network.*;

public class S0CPacketSpawnPlayer implements Packet
{
    private int field_148957_a;
    private UUID field_179820_b;
    private int posX;
    private int posY;
    private int posZ;
    private byte field_148951_f;
    private byte field_148952_g;
    private int field_148959_h;
    private DataWatcher field_148960_i;
    private List field_148958_j;
    private EntityPlayer theEntity;
    
    public S0CPacketSpawnPlayer() {
    }
    
    public S0CPacketSpawnPlayer(final EntityPlayer p_i45171_1_) {
        this.field_148957_a = p_i45171_1_.getEntityId();
        this.field_179820_b = p_i45171_1_.getGameProfile().getId();
        this.posX = MathHelper.floor_double(p_i45171_1_.posX * 32.0);
        this.posY = MathHelper.floor_double(p_i45171_1_.posY * 32.0);
        this.posZ = MathHelper.floor_double(p_i45171_1_.posZ * 32.0);
        this.field_148951_f = (byte)(p_i45171_1_.rotationYaw * 256.0f / 360.0f);
        this.field_148952_g = (byte)(p_i45171_1_.rotationPitch * 256.0f / 360.0f);
        final ItemStack var2 = p_i45171_1_.inventory.getCurrentItem();
        this.field_148959_h = ((var2 == null) ? 0 : Item.getIdFromItem(var2.getItem()));
        this.field_148960_i = p_i45171_1_.getDataWatcher();
        this.theEntity = p_i45171_1_;
    }
    
    public EntityPlayer getEntity() {
        return this.theEntity;
    }
    
    public int getX() {
        return this.posX;
    }
    
    public int getY() {
        return this.posY;
    }
    
    public int getZ() {
        return this.posZ;
    }
    
    @Override
    public void readPacketData(final PacketBuffer data) throws IOException {
        this.field_148957_a = data.readVarIntFromBuffer();
        this.field_179820_b = data.readUuid();
        this.posX = data.readInt();
        this.posY = data.readInt();
        this.posZ = data.readInt();
        this.field_148951_f = data.readByte();
        this.field_148952_g = data.readByte();
        this.field_148959_h = data.readShort();
        this.field_148958_j = DataWatcher.readWatchedListFromPacketBuffer(data);
    }
    
    @Override
    public void writePacketData(final PacketBuffer data) throws IOException {
        data.writeVarIntToBuffer(this.field_148957_a);
        data.writeUuid(this.field_179820_b);
        data.writeInt(this.posX);
        data.writeInt(this.posY);
        data.writeInt(this.posZ);
        data.writeByte(this.field_148951_f);
        data.writeByte(this.field_148952_g);
        data.writeShort(this.field_148959_h);
        this.field_148960_i.writeTo(data);
    }
    
    public void processPacket(final INetHandlerPlayClient handler) {
        handler.handleSpawnPlayer(this);
    }
    
    public List func_148944_c() {
        if (this.field_148958_j == null) {
            this.field_148958_j = this.field_148960_i.getAllWatched();
        }
        return this.field_148958_j;
    }
    
    public int func_148943_d() {
        return this.field_148957_a;
    }
    
    public UUID func_179819_c() {
        return this.field_179820_b;
    }
    
    public int func_148942_f() {
        return this.posX;
    }
    
    public int func_148949_g() {
        return this.posY;
    }
    
    public int func_148946_h() {
        return this.posZ;
    }
    
    public byte func_148941_i() {
        return this.field_148951_f;
    }
    
    public byte func_148945_j() {
        return this.field_148952_g;
    }
    
    public int func_148947_k() {
        return this.field_148959_h;
    }
    
    @Override
    public void processPacket(final INetHandler handler) {
        this.processPacket((INetHandlerPlayClient)handler);
    }
}
