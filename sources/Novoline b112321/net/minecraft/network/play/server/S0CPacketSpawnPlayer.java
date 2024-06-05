package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import net.rF;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

public class S0CPacketSpawnPlayer implements Packet {
   private int entityId;
   private UUID playerId;
   private int x;
   private int y;
   private int z;
   private byte yaw;
   private byte pitch;
   private int currentItem;
   private rF d;
   private List field_148958_j;

   public S0CPacketSpawnPlayer() {
   }

   public S0CPacketSpawnPlayer(EntityPlayer var1) {
      this.entityId = var1.getEntityID();
      this.playerId = var1.getGameProfile().getId();
      this.x = MathHelper.floor_double(var1.posX * 32.0D);
      this.y = MathHelper.floor_double(var1.posY * 32.0D);
      this.z = MathHelper.floor_double(var1.posZ * 32.0D);
      this.yaw = (byte)((int)(var1.rotationYaw * 256.0F / 360.0F));
      this.pitch = (byte)((int)(var1.rotationPitch * 256.0F / 360.0F));
      ItemStack var2 = var1.inventory.getCurrentItem();
      this.currentItem = 0;
      this.d = var1.k();
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.entityId = var1.readVarIntFromBuffer();
      this.playerId = var1.readUuid();
      this.x = var1.readInt();
      this.y = var1.readInt();
      this.z = var1.readInt();
      this.yaw = var1.readByte();
      this.pitch = var1.readByte();
      this.currentItem = var1.readShort();
      this.field_148958_j = rF.a(var1);
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.entityId);
      var1.writeUuid(this.playerId);
      var1.writeInt(this.x);
      var1.writeInt(this.y);
      var1.writeInt(this.z);
      var1.writeByte(this.yaw);
      var1.writeByte(this.pitch);
      var1.writeShort(this.currentItem);
      this.d.b(var1);
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleSpawnPlayer(this);
   }

   public List func_148944_c() {
      if(this.field_148958_j == null) {
         this.field_148958_j = this.d.c();
      }

      return this.field_148958_j;
   }

   public int getEntityID() {
      return this.entityId;
   }

   public UUID getPlayer() {
      return this.playerId;
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public int getZ() {
      return this.z;
   }

   public byte getYaw() {
      return this.yaw;
   }

   public byte getPitch() {
      return this.pitch;
   }

   public int getCurrentItemID() {
      return this.currentItem;
   }
}
