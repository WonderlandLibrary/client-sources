package net;

import net.minecraft.util.Vec3;

public class alT {
   private double d;
   private double c;
   private double e;
   private static String b;

   public alT(double var1, double var3, double var5) {
      this.d = var1;
      this.c = var3;
      this.e = var5;
   }

   public double f() {
      return this.d;
   }

   public double c() {
      return this.c;
   }

   public double g() {
      return this.e;
   }

   public void c(double var1) {
      this.d = var1;
   }

   public void b(double var1) {
      this.c = var1;
   }

   public void a(double var1) {
      this.e = var1;
   }

   public alT a(double var1, double var3, double var5) {
      return new alT(this.d + var1, this.c + var3, this.e + var5);
   }

   public alT d() {
      return new alT(Math.floor(this.d), Math.floor(this.c), Math.floor(this.e));
   }

   public double a(alT var1) {
      return Math.pow(var1.d - this.d, 2.0D) + Math.pow(var1.c - this.c, 2.0D) + Math.pow(var1.e - this.e, 2.0D);
   }

   public alT b(alT var1) {
      return this.a(var1.f(), var1.c(), var1.g());
   }

   public Vec3 e() {
      return new Vec3(this.d, this.c, this.e);
   }

   public String toString() {
      return "[" + this.d + ";" + this.c + ";" + this.e + "]";
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() != null) {
         b("kCiSec");
      }

   }
}
