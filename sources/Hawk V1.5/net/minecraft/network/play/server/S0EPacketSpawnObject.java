package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

public class S0EPacketSpawnObject implements Packet {
   private int field_149015_e;
   private int field_149016_b;
   private int field_149018_a;
   private int field_149013_g;
   private int field_149021_h;
   private int field_149022_i;
   private int field_149019_j;
   private int field_149012_f;
   private int field_149020_k;
   private static final String __OBFID = "CL_00001276";
   private int field_149017_c;
   private int field_149014_d;

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeVarIntToBuffer(this.field_149018_a);
      var1.writeByte(this.field_149019_j);
      var1.writeInt(this.field_149016_b);
      var1.writeInt(this.field_149017_c);
      var1.writeInt(this.field_149014_d);
      var1.writeByte(this.field_149021_h);
      var1.writeByte(this.field_149022_i);
      var1.writeInt(this.field_149020_k);
      if (this.field_149020_k > 0) {
         var1.writeShort(this.field_149015_e);
         var1.writeShort(this.field_149012_f);
         var1.writeShort(this.field_149013_g);
      }

   }

   public void func_149003_d(int var1) {
      this.field_149015_e = var1;
   }

   public int func_149001_c() {
      return this.field_149018_a;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_149018_a = var1.readVarIntFromBuffer();
      this.field_149019_j = var1.readByte();
      this.field_149016_b = var1.readInt();
      this.field_149017_c = var1.readInt();
      this.field_149014_d = var1.readInt();
      this.field_149021_h = var1.readByte();
      this.field_149022_i = var1.readByte();
      this.field_149020_k = var1.readInt();
      if (this.field_149020_k > 0) {
         this.field_149015_e = var1.readShort();
         this.field_149012_f = var1.readShort();
         this.field_149013_g = var1.readShort();
      }

   }

   public int func_149010_g() {
      return this.field_149015_e;
   }

   public void func_148996_a(int var1) {
      this.field_149016_b = var1;
   }

   public int func_149008_j() {
      return this.field_149021_h;
   }

   public void func_149002_g(int var1) {
      this.field_149020_k = var1;
   }

   public S0EPacketSpawnObject(Entity var1, int var2) {
      this(var1, var2, 0);
   }

   public int func_148994_f() {
      return this.field_149014_d;
   }

   public void processPacket(INetHandler var1) {
      this.processPacket((INetHandlerPlayClient)var1);
   }

   public int func_149009_m() {
      return this.field_149020_k;
   }

   public int func_148993_l() {
      return this.field_149019_j;
   }

   public int func_148998_e() {
      return this.field_149017_c;
   }

   public int func_149004_h() {
      return this.field_149012_f;
   }

   public void func_149005_c(int var1) {
      this.field_149014_d = var1;
   }

   public int func_149006_k() {
      return this.field_149022_i;
   }

   public void func_149007_f(int var1) {
      this.field_149013_g = var1;
   }

   public S0EPacketSpawnObject(Entity var1, int var2, int var3) {
      this.field_149018_a = var1.getEntityId();
      this.field_149016_b = MathHelper.floor_double(var1.posX * 32.0D);
      this.field_149017_c = MathHelper.floor_double(var1.posY * 32.0D);
      this.field_149014_d = MathHelper.floor_double(var1.posZ * 32.0D);
      this.field_149021_h = MathHelper.floor_float(var1.rotationPitch * 256.0F / 360.0F);
      this.field_149022_i = MathHelper.floor_float(var1.rotationYaw * 256.0F / 360.0F);
      this.field_149019_j = var2;
      this.field_149020_k = var3;
      if (var3 > 0) {
         double var4 = var1.motionX;
         double var6 = var1.motionY;
         double var8 = var1.motionZ;
         double var10 = 3.9D;
         if (var4 < -var10) {
            var4 = -var10;
         }

         if (var6 < -var10) {
            var6 = -var10;
         }

         if (var8 < -var10) {
            var8 = -var10;
         }

         if (var4 > var10) {
            var4 = var10;
         }

         if (var6 > var10) {
            var6 = var10;
         }

         if (var8 > var10) {
            var8 = var10;
         }

         this.field_149015_e = (int)(var4 * 8000.0D);
         this.field_149012_f = (int)(var6 * 8000.0D);
         this.field_149013_g = (int)(var8 * 8000.0D);
      }

   }

   public int func_148997_d() {
      return this.field_149016_b;
   }

   public void processPacket(INetHandlerPlayClient var1) {
      var1.handleSpawnObject(this);
   }

   public void func_148995_b(int var1) {
      this.field_149017_c = var1;
   }

   public void func_149000_e(int var1) {
      this.field_149012_f = var1;
   }

   public S0EPacketSpawnObject() {
   }

   public int func_148999_i() {
      return this.field_149013_g;
   }
}
