package net;

import net.minecraft.util.MathHelper;

public final class d3 {
   private long d = 0L;
   private long c = -1L;
   private static String[] b;

   public boolean a(long var1) {
      String[] var3 = e();
      if(this.a() >= var1) {
         this.b();
         return true;
      } else {
         return false;
      }
   }

   public boolean a(float var1) {
      String[] var2 = e();
      return (float)(System.currentTimeMillis() - this.c) >= var1;
   }

   public boolean a(double var1) {
      String[] var3 = e();
      return (double)MathHelper.a((float)(this.d() - this.d), 0.0F, (float)var1) >= var1;
   }

   public void b() {
      this.c = System.currentTimeMillis();
      this.d = this.d();
   }

   public long a() {
      return System.nanoTime() / 1000000L - this.d;
   }

   public long d() {
      return System.nanoTime() / 1000000L;
   }

   public double f() {
      return (double)(this.d() - this.c());
   }

   public long c() {
      return this.d;
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] e() {
      return b;
   }

   static {
      if(e() != null) {
         b(new String[4]);
      }

   }
}
