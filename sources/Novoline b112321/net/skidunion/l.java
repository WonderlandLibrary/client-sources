package net.skidunion;

import net.skidunion.i;

public class l extends i {
   long p;
   final i q;

   public long a() {
      return this.p;
   }

   public void c(byte[] var1) {
      a(this.p, var1, 0);
   }

   public void c(long var1) {
      this.p = var1;
   }

   public void d(byte[] var1) {
      this.p = a(var1, 0);
   }

   public l(i var1) {
      this.q = var1;
      this.c(0L);
   }

   public l(i var1, long var2) {
      this.q = var1;
      this.c(var2);
   }

   public l(i var1, byte[] var2) {
      this.q = var1;
      this.d(var2);
   }

   public void a() {
      this.p = 0L;
      super.a();
   }

   private long b(long var1) {
      var1 = var1 ^ this.p;
      var1 = super.c(var1);
      return this.p = var1;
   }

   private long d(long var1) {
      long var3 = var1;
      var1 = super.a(var1);
      var1 = var1 ^ this.p;
      this.p = var3;
      return var1;
   }

   public void a(byte[] var1, byte[] var2) {
      int var4 = var1.length;
      i.b();
      int var7 = 0;
      if(var7 < var4) {
         long var5 = a(var1, var7);
         var5 = this.b(var5);
         a(var5, var2, var7);
         var7 = var7 + 8;
      }

   }

   public void b(byte[] var1) {
      i.b();
      int var3 = var1.length;
      int var6 = 0;
      if(var6 < var3) {
         long var4 = a(var1, var6);
         var4 = this.b(var4);
         a(var4, var1, var6);
         var6 = var6 + 8;
      }

   }

   public void a(int[] var1, int[] var2) {
      int var4 = var1.length;
      i.b();
      int var7 = 0;
      if(var7 < var4) {
         long var5 = a(var1, var7);
         var5 = this.b(var5);
         a(var5, var2, var7);
         var7 = var7 + 2;
      }

   }

   public void a(int[] var1) {
      i.b();
      int var3 = var1.length;
      int var6 = 0;
      if(var6 < var3) {
         long var4 = a(var1, var6);
         var4 = this.b(var4);
         a(var4, var1, var6);
         var6 = var6 + 2;
      }

   }

   public void a(long[] var1, long[] var2) {
      i.b();
      int var4 = var1.length;
      int var5 = 0;
      if(var5 < var4) {
         var2[var5] = this.b(var1[var5]);
         ++var5;
      }

   }

   public void a(long[] var1) {
      int var3 = var1.length;
      i.b();
      int var4 = 0;
      if(var4 < var3) {
         var1[var4] = this.b(var1[var4]);
         ++var4;
      }

   }

   public void b(byte[] var1, byte[] var2) {
      i.b();
      int var4 = var1.length;
      int var7 = 0;
      if(var7 < var4) {
         long var5 = a(var1, var7);
         var5 = this.d(var5);
         a(var5, var2, var7);
         var7 = var7 + 8;
      }

   }

   public void a(byte[] var1) {
      int var3 = var1.length;
      i.b();
      int var6 = 0;
      if(var6 < var3) {
         long var4 = a(var1, var6);
         var4 = this.d(var4);
         a(var4, var1, var6);
         var6 = var6 + 8;
      }

   }

   public void b(int[] var1, int[] var2) {
      i.b();
      int var4 = var1.length;
      int var7 = 0;
      if(var7 < var4) {
         long var5 = a(var1, var7);
         var5 = this.d(var5);
         a(var5, var2, var7);
         var7 = var7 + 2;
      }

   }

   public void b(int[] var1) {
      i.b();
      int var3 = var1.length;
      int var6 = 0;
      if(var6 < var3) {
         long var4 = a(var1, var6);
         var4 = this.d(var4);
         a(var4, var1, var6);
         var6 = var6 + 2;
      }

   }

   public void b(long[] var1, long[] var2) {
      i.b();
      int var4 = var1.length;
      int var5 = 0;
      if(var5 < var4) {
         var2[var5] = this.d(var1[var5]);
         ++var5;
      }

   }

   public void b(long[] var1) {
      i.b();
      int var3 = var1.length;
      int var4 = 0;
      if(var4 < var3) {
         var1[var4] = this.d(var1[var4]);
         ++var4;
      }

   }
}
