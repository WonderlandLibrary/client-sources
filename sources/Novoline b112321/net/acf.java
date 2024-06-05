package net;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;

public class acf {
   private double e;
   private double d;
   private double a;
   private float c;
   private float b;

   public acf(double var1, double var3, double var5, float var7, float var8) {
      this.e = var1;
      this.d = var3;
      this.a = var5;
      this.c = var7;
      this.b = var8;
   }

   public acf(double var1, double var3, double var5) {
      this.e = var1;
      this.d = var3;
      this.a = var5;
      this.c = 0.0F;
      this.b = 0.0F;
   }

   public acf(BlockPos var1) {
      this.e = (double)var1.getX();
      this.d = (double)var1.getY();
      this.a = (double)var1.getZ();
      this.c = 0.0F;
      this.b = 0.0F;
   }

   public acf(int var1, int var2, int var3) {
      this.e = (double)var1;
      this.d = (double)var2;
      this.a = (double)var3;
      this.c = 0.0F;
      this.b = 0.0F;
   }

   public acf(EntityLivingBase var1) {
      this.e = var1.posX;
      this.d = var1.posY;
      this.a = var1.posZ;
      this.c = 0.0F;
      this.b = 0.0F;
   }

   public acf b(int var1, int var2, int var3) {
      this.e += (double)var1;
      this.d += (double)var2;
      this.a += (double)var3;
      return this;
   }

   public acf a(double var1, double var3, double var5) {
      this.e += var1;
      this.d += var3;
      this.a += var5;
      return this;
   }

   public acf a(int var1, int var2, int var3) {
      this.e -= (double)var1;
      this.d -= (double)var2;
      this.a -= (double)var3;
      return this;
   }

   public acf b(double var1, double var3, double var5) {
      this.e -= var1;
      this.d -= var3;
      this.a -= var5;
      return this;
   }

   public Block c() {
      return Minecraft.getMinecraft().theWorld.getBlockState(this.d()).getBlock();
   }

   public double f() {
      return this.e;
   }

   public acf c(double var1) {
      this.e = var1;
      return this;
   }

   public double g() {
      return this.d;
   }

   public acf b(double var1) {
      this.d = var1;
      return this;
   }

   public double e() {
      return this.a;
   }

   public acf a(double var1) {
      this.a = var1;
      return this;
   }

   public float a() {
      return this.c;
   }

   public acf a(float var1) {
      this.c = var1;
      return this;
   }

   public float b() {
      return this.b;
   }

   public acf b(float var1) {
      this.b = var1;
      return this;
   }

   public static acf a(BlockPos var0) {
      return new acf(var0.getX(), var0.getY(), var0.getZ());
   }

   public BlockPos d() {
      return new BlockPos(this.f(), this.g(), this.e());
   }

   public double a(acf var1) {
      double var2 = var1.e - this.e;
      double var4 = var1.a - this.a;
      double var6 = var1.d - this.d;
      return Math.sqrt(var2 * var2 + var6 * var6 + var4 * var4);
   }

   public double b(acf var1) {
      double var2 = var1.d - this.d;
      return Math.sqrt(var2 * var2);
   }
}
