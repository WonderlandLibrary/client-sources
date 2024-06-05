package net;

import net.optifine.MatchBlock;

public class ahh {
   private int a = 18;
   private int g = 128;
   private int c = 18;
   private int h = 0;
   private int e = 0;
   private int b = 0;
   private byte[][][] f = (byte[][][])((byte[][][])null);
   private byte[] d = null;
   private int i = 0;

   public ahh(int var1, int var2, int var3) {
      this.a = var1;
      this.g = var2;
      this.c = var3;
      this.f = new byte[var1][var2][var3];
      this.a();
   }

   public void a() {
      MatchBlock.b();
      int var2 = 0;
      if(var2 < this.a) {
         byte[][] var3 = this.f[var2];
         int var4 = 0;
         if(var4 < this.g) {
            byte[] var5 = var3[var4];
            int var6 = 0;
            if(var6 < this.c) {
               var5[var6] = -1;
               ++var6;
            }

            ++var4;
         }

         ++var2;
      }

   }

   public void b(int var1, int var2, int var3) {
      this.h = var1;
      this.e = var2;
      this.b = var3;
      this.a();
   }

   public byte a(int var1, int var2, int var3) {
      try {
         this.d = this.f[var1 - this.h][var2 - this.e];
         this.i = var3 - this.b;
         return this.d[this.i];
      } catch (ArrayIndexOutOfBoundsException var5) {
         var5.printStackTrace();
         return (byte)-1;
      }
   }

   public void a(byte var1) {
      try {
         this.d[this.i] = var1;
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   private static ArrayIndexOutOfBoundsException a(ArrayIndexOutOfBoundsException var0) {
      return var0;
   }
}
