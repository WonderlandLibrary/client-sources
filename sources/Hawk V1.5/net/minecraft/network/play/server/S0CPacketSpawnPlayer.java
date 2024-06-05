package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

public class S0CPacketSpawnPlayer implements Packet {
   private int field_148957_a;
   private static final String __OBFID = "CL_00001281";
   private int field_148956_c;
   private List field_148958_j;
   private DataWatcher field_148960_i;
   private UUID field_179820_b;
   private int field_148953_d;
   private int field_148954_e;
   private int field_148959_h;
   private byte field_148951_f;
   private byte field_148952_g;

   public int func_148947_k() {
      return this.field_148959_h;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.field_148957_a);
      var1.writeUuid(this.field_179820_b);
      var1.writeInt(this.field_148956_c);
      var1.writeInt(this.field_148953_d);
      var1.writeInt(this.field_148954_e);
      var1.writeByte(this.field_148951_f);
      var1.writeByte(this.field_148952_g);
      var1.writeShort(this.field_148959_h);
      this.field_148960_i.writeTo(var1);
   }

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayClient)var1);
   }

   public int func_148949_g() {
      return this.field_148953_d;
   }

   public int func_148943_d() {
      return this.field_148957_a;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_148957_a = var1.readVarIntFromBuffer();
      this.field_179820_b = var1.readUuid();
      this.field_148956_c = var1.readInt();
      this.field_148953_d = var1.readInt();
      this.field_148954_e = var1.readInt();
      this.field_148951_f = var1.readByte();
      this.field_148952_g = var1.readByte();
      this.field_148959_h = var1.readShort();
      this.field_148958_j = DataWatcher.readWatchedListFromPacketBuffer(var1);
   }

   public int func_148946_h() {
      return this.field_148954_e;
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleSpawnPlayer(this);
   }

   public List func_148944_c() {
      if (this.field_148958_j == null) {
         this.field_148958_j = this.field_148960_i.getAllWatched();
      }

      return this.field_148958_j;
   }

   public byte func_148945_j() {
      return this.field_148952_g;
   }

   public byte func_148941_i() {
      return this.field_148951_f;
   }

   public S0CPacketSpawnPlayer(EntityPlayer var1) {
      this.field_148957_a = var1.getEntityId();
      this.field_179820_b = var1.getGameProfile().getId();
      this.field_148956_c = MathHelper.floor_double(var1.posX * 32.0D);
      this.field_148953_d = MathHelper.floor_double(var1.posY * 32.0D);
      this.field_148954_e = MathHelper.floor_double(var1.posZ * 32.0D);
      this.field_148951_f = (byte)((int)(var1.rotationYaw * 256.0F / 360.0F));
      this.field_148952_g = (byte)((int)(var1.rotationPitch * 256.0F / 360.0F));
      ItemStack var2 = var1.inventory.getCurrentItem();
      this.field_148959_h = var2 == null ? 0 : Item.getIdFromItem(var2.getItem());
      this.field_148960_i = var1.getDataWatcher();
   }

   public int func_148942_f() {
      return this.field_148956_c;
   }

   public S0CPacketSpawnPlayer() {
   }

   public UUID func_179819_c() {
      return this.field_179820_b;
   }
}
