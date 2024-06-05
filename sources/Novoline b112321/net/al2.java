package net;

import net.minecraft.world.gen.layer.IntCache;

public class al2 {
   private static int b;

   public static int[] a(int var0) {
      return IntCache.getIntCache(var0);
   }

   public static String a() {
      return IntCache.getCacheSizes();
   }

   public static void c() {
      IntCache.resetIntCache();
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int d() {
      int var0 = b();
      return 8;
   }

   static {
      if(d() == 0) {
         b(86);
      }

   }
}
