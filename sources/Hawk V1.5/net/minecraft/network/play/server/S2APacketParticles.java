package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.EnumParticleTypes;

public class S2APacketParticles implements Packet {
   private float field_149230_f;
   private float field_149235_c;
   private float field_149233_e;
   private float field_149237_h;
   private EnumParticleTypes field_179751_a;
   private float field_149234_b;
   private boolean field_179752_j;
   private float field_149231_g;
   private static final String __OBFID = "CL_00001308";
   private int field_149238_i;
   private int[] field_179753_k;
   private float field_149232_d;

   public void func_180740_a(INetHandlerPlayClient var1) {
      var1.handleParticles(this);
   }

   public float func_149224_h() {
      return this.field_149230_f;
   }

   public boolean func_179750_b() {
      return this.field_179752_j;
   }

   public EnumParticleTypes func_179749_a() {
      return this.field_179751_a;
   }

   public void processPacket(INetHandler var1) {
      this.func_180740_a((INetHandlerPlayClient)var1);
   }

   public double func_149226_e() {
      return (double)this.field_149235_c;
   }

   public float func_149221_g() {
      return this.field_149233_e;
   }

   public int func_149222_k() {
      return this.field_149238_i;
   }

   public float func_149227_j() {
      return this.field_149237_h;
   }

   public double func_149225_f() {
      return (double)this.field_149232_d;
   }

   public void readPacketData(PacketBuffer var1) throws IOException {
      this.field_179751_a = EnumParticleTypes.func_179342_a(var1.readInt());
      if (this.field_179751_a == null) {
         this.field_179751_a = EnumParticleTypes.BARRIER;
      }

      this.field_179752_j = var1.readBoolean();
      this.field_149234_b = var1.readFloat();
      this.field_149235_c = var1.readFloat();
      this.field_149232_d = var1.readFloat();
      this.field_149233_e = var1.readFloat();
      this.field_149230_f = var1.readFloat();
      this.field_149231_g = var1.readFloat();
      this.field_149237_h = var1.readFloat();
      this.field_149238_i = var1.readInt();
      int var2 = this.field_179751_a.func_179345_d();
      this.field_179753_k = new int[var2];

      for(int var3 = 0; var3 < var2; ++var3) {
         this.field_179753_k[var3] = var1.readVarIntFromBuffer();
      }

   }

   public float func_149223_i() {
      return this.field_149231_g;
   }

   public S2APacketParticles() {
   }

   public double func_149220_d() {
      return (double)this.field_149234_b;
   }

   public int[] func_179748_k() {
      return this.field_179753_k;
   }

   public void writePacketData(PacketBuffer var1) throws IOException {
      var1.writeInt(this.field_179751_a.func_179348_c());
      var1.writeBoolean(this.field_179752_j);
      var1.writeFloat(this.field_149234_b);
      var1.writeFloat(this.field_149235_c);
      var1.writeFloat(this.field_149232_d);
      var1.writeFloat(this.field_149233_e);
      var1.writeFloat(this.field_149230_f);
      var1.writeFloat(this.field_149231_g);
      var1.writeFloat(this.field_149237_h);
      var1.writeInt(this.field_149238_i);
      int var2 = this.field_179751_a.func_179345_d();

      for(int var3 = 0; var3 < var2; ++var3) {
         var1.writeVarIntToBuffer(this.field_179753_k[var3]);
      }

   }

   public S2APacketParticles(EnumParticleTypes var1, boolean var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10, int... var11) {
      this.field_179751_a = var1;
      this.field_179752_j = var2;
      this.field_149234_b = var3;
      this.field_149235_c = var4;
      this.field_149232_d = var5;
      this.field_149233_e = var6;
      this.field_149230_f = var7;
      this.field_149231_g = var8;
      this.field_149237_h = var9;
      this.field_149238_i = var10;
      this.field_179753_k = var11;
   }
}
