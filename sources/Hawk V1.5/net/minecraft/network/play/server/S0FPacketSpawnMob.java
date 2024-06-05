package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

public class S0FPacketSpawnMob implements Packet {
   private int field_149036_f;
   private static final String __OBFID = "CL_00001279";
   private byte field_149045_j;
   private byte field_149046_k;
   private int field_149040_b;
   private DataWatcher field_149043_l;
   private int field_149038_d;
   private int field_149037_g;
   private byte field_149048_i;
   private List field_149044_m;
   private int field_149039_e;
   private int field_149041_c;
   private int field_149047_h;
   private int field_149042_a;

   public int func_149029_h() {
      return this.field_149039_e;
   }

   public int func_149026_i() {
      return this.field_149036_f;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149042_a = var1.readVarIntFromBuffer();
      this.field_149040_b = var1.readByte() & 255;
      this.field_149041_c = var1.readInt();
      this.field_149038_d = var1.readInt();
      this.field_149039_e = var1.readInt();
      this.field_149048_i = var1.readByte();
      this.field_149045_j = var1.readByte();
      this.field_149046_k = var1.readByte();
      this.field_149036_f = var1.readShort();
      this.field_149037_g = var1.readShort();
      this.field_149047_h = var1.readShort();
      this.field_149044_m = DataWatcher.readWatchedListFromPacketBuffer(var1);
   }

   public void processPacket(INetHandler var1) {
      this.func_180721_a((INetHandlerPlayClient)var1);
   }

   public void func_180721_a(INetHandlerPlayClient var1) {
      var1.handleSpawnMob(this);
   }

   public byte func_149028_l() {
      return this.field_149048_i;
   }

   public byte func_149032_n() {
      return this.field_149046_k;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.field_149042_a);
      var1.writeByte(this.field_149040_b & 255);
      var1.writeInt(this.field_149041_c);
      var1.writeInt(this.field_149038_d);
      var1.writeInt(this.field_149039_e);
      var1.writeByte(this.field_149048_i);
      var1.writeByte(this.field_149045_j);
      var1.writeByte(this.field_149046_k);
      var1.writeShort(this.field_149036_f);
      var1.writeShort(this.field_149037_g);
      var1.writeShort(this.field_149047_h);
      this.field_149043_l.writeTo(var1);
   }

   public int func_149034_g() {
      return this.field_149038_d;
   }

   public byte func_149030_m() {
      return this.field_149045_j;
   }

   public int func_149033_j() {
      return this.field_149037_g;
   }

   public int func_149031_k() {
      return this.field_149047_h;
   }

   public S0FPacketSpawnMob() {
   }

   public int func_149025_e() {
      return this.field_149040_b;
   }

   public List func_149027_c() {
      if (this.field_149044_m == null) {
         this.field_149044_m = this.field_149043_l.getAllWatched();
      }

      return this.field_149044_m;
   }

   public int func_149024_d() {
      return this.field_149042_a;
   }

   public S0FPacketSpawnMob(EntityLivingBase var1) {
      this.field_149042_a = var1.getEntityId();
      this.field_149040_b = (byte)EntityList.getEntityID(var1);
      this.field_149041_c = MathHelper.floor_double(var1.posX * 32.0D);
      this.field_149038_d = MathHelper.floor_double(var1.posY * 32.0D);
      this.field_149039_e = MathHelper.floor_double(var1.posZ * 32.0D);
      this.field_149048_i = (byte)((int)(var1.rotationYaw * 256.0F / 360.0F));
      this.field_149045_j = (byte)((int)(var1.rotationPitch * 256.0F / 360.0F));
      this.field_149046_k = (byte)((int)(var1.rotationYawHead * 256.0F / 360.0F));
      double var2 = 3.9D;
      double var4 = var1.motionX;
      double var6 = var1.motionY;
      double var8 = var1.motionZ;
      if (var4 < -var2) {
         var4 = -var2;
      }

      if (var6 < -var2) {
         var6 = -var2;
      }

      if (var8 < -var2) {
         var8 = -var2;
      }

      if (var4 > var2) {
         var4 = var2;
      }

      if (var6 > var2) {
         var6 = var2;
      }

      if (var8 > var2) {
         var8 = var2;
      }

      this.field_149036_f = (int)(var4 * 8000.0D);
      this.field_149037_g = (int)(var6 * 8000.0D);
      this.field_149047_h = (int)(var8 * 8000.0D);
      this.field_149043_l = var1.getDataWatcher();
   }

   public int func_149023_f() {
      return this.field_149041_c;
   }
}
