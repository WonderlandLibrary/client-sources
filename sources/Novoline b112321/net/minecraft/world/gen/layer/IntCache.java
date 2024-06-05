package net.minecraft.world.gen.layer;

import com.google.common.collect.Lists;
import java.util.List;

public class IntCache {
   private static int intCacheSize = 256;
   private static List freeSmallArrays = Lists.newArrayList();
   private static List inUseSmallArrays = Lists.newArrayList();
   private static List freeLargeArrays = Lists.newArrayList();
   private static List inUseLargeArrays = Lists.newArrayList();

   public static synchronized int[] getIntCache(int var0) {
      if(var0 <= 256) {
         if(freeSmallArrays.isEmpty()) {
            int[] var5 = new int[256];
            inUseSmallArrays.add(var5);
            return var5;
         } else {
            int[] var4 = (int[])((int[])freeSmallArrays.remove(freeSmallArrays.size() - 1));
            inUseSmallArrays.add(var4);
            return var4;
         }
      } else if(var0 > intCacheSize) {
         intCacheSize = var0;
         freeLargeArrays.clear();
         inUseLargeArrays.clear();
         int[] var3 = new int[intCacheSize];
         inUseLargeArrays.add(var3);
         return var3;
      } else if(freeLargeArrays.isEmpty()) {
         int[] var2 = new int[intCacheSize];
         inUseLargeArrays.add(var2);
         return var2;
      } else {
         int[] var1 = (int[])((int[])freeLargeArrays.remove(freeLargeArrays.size() - 1));
         inUseLargeArrays.add(var1);
         return var1;
      }
   }

   public static synchronized void resetIntCache() {
      if(!freeLargeArrays.isEmpty()) {
         freeLargeArrays.remove(freeLargeArrays.size() - 1);
      }

      if(!freeSmallArrays.isEmpty()) {
         freeSmallArrays.remove(freeSmallArrays.size() - 1);
      }

      freeLargeArrays.addAll(inUseLargeArrays);
      freeSmallArrays.addAll(inUseSmallArrays);
      inUseLargeArrays.clear();
      inUseSmallArrays.clear();
   }

   public static synchronized String getCacheSizes() {
      return "cache: " + freeLargeArrays.size() + ", tcache: " + freeSmallArrays.size() + ", allocated: " + inUseLargeArrays.size() + ", tallocated: " + inUseSmallArrays.size();
   }
}
