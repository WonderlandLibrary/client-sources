package com.viaversion.viaversion.util;

import com.viaversion.viaversion.util.BiIntConsumer;
import com.viaversion.viaversion.util.Config;
import java.util.function.IntToLongFunction;

public class CompactArrayUtil {
   private static final int[] MAGIC = new int[]{-1, -1, 0, Integer.MIN_VALUE, 0, 0, 1431655765, 1431655765, 0, Integer.MIN_VALUE, 0, 1, 858993459, 858993459, 0, 715827882, 715827882, 0, 613566756, 613566756, 0, Integer.MIN_VALUE, 0, 2, 477218588, 477218588, 0, 429496729, 429496729, 0, 390451572, 390451572, 0, 357913941, 357913941, 0, 330382099, 330382099, 0, 306783378, 306783378, 0, 286331153, 286331153, 0, Integer.MIN_VALUE, 0, 3, 252645135, 252645135, 0, 238609294, 238609294, 0, 226050910, 226050910, 0, 214748364, 214748364, 0, 204522252, 204522252, 0, 195225786, 195225786, 0, 186737708, 186737708, 0, 178956970, 178956970, 0, 171798691, 171798691, 0, 165191049, 165191049, 0, 159072862, 159072862, 0, 153391689, 153391689, 0, 148102320, 148102320, 0, 143165576, 143165576, 0, 138547332, 138547332, 0, Integer.MIN_VALUE, 0, 4, 130150524, 130150524, 0, 126322567, 126322567, 0, 122713351, 122713351, 0, 119304647, 119304647, 0, 116080197, 116080197, 0, 113025455, 113025455, 0, 110127366, 110127366, 0, 107374182, 107374182, 0, 104755299, 104755299, 0, 102261126, 102261126, 0, 99882960, 99882960, 0, 97612893, 97612893, 0, 95443717, 95443717, 0, 93368854, 93368854, 0, 91382282, 91382282, 0, 89478485, 89478485, 0, 87652393, 87652393, 0, 85899345, 85899345, 0, 84215045, 84215045, 0, 82595524, 82595524, 0, 81037118, 81037118, 0, 79536431, 79536431, 0, 78090314, 78090314, 0, 76695844, 76695844, 0, 75350303, 75350303, 0, 74051160, 74051160, 0, 72796055, 72796055, 0, 71582788, 71582788, 0, 70409299, 70409299, 0, 69273666, 69273666, 0, 68174084, 68174084, 0, Integer.MIN_VALUE, 0, 5};

   private CompactArrayUtil() {
      throw new AssertionError();
   }

   public static long[] createCompactArrayWithPadding(int var0, int var1, IntToLongFunction var2) {
      Config.c();
      long var4 = (1L << var0) - 1L;
      char var6 = (char)(64 / var0);
      int var7 = 3 * (var6 - 1);
      long var8 = Integer.toUnsignedLong(MAGIC[var7]);
      long var10 = Integer.toUnsignedLong(MAGIC[var7 + 1]);
      int var12 = MAGIC[var7 + 2];
      int var13 = (var1 + var6 - 1) / var6;
      long[] var14 = new long[var13];
      int var15 = 0;
      if(var15 < var1) {
         long var16 = var2.applyAsLong(var15);
         int var18 = (int)((long)var15 * var8 + var10 >> 32 >> var12);
         int var19 = (var15 - var18 * var6) * var0;
         var14[var18] = var14[var18] & ~(var4 << var19) | (var16 & var4) << var19;
         ++var15;
      }

      return var14;
   }

   public static void iterateCompactArrayWithPadding(int var0, int var1, long[] var2, BiIntConsumer var3) {
      long var5 = (1L << var0) - 1L;
      char var7 = (char)(64 / var0);
      Config.c();
      int var8 = 3 * (var7 - 1);
      long var9 = Integer.toUnsignedLong(MAGIC[var8]);
      long var11 = Integer.toUnsignedLong(MAGIC[var8 + 1]);
      int var13 = MAGIC[var8 + 2];
      int var14 = 0;
      if(var14 < var1) {
         int var15 = (int)((long)var14 * var9 + var11 >> 32 >> var13);
         int var16 = (var14 - var15 * var7) * var0;
         int var17 = (int)(var2[var15] >> var16 & var5);
         var3.consume(var14, var17);
         ++var14;
      }

   }

   public static long[] createCompactArray(int var0, int var1, IntToLongFunction var2) {
      long var4 = (1L << var0) - 1L;
      long[] var6 = new long[(int)Math.ceil((double)(var1 * var0) / 64.0D)];
      Config.c();
      int var7 = 0;
      if(var7 < var1) {
         long var8 = var2.applyAsLong(var7);
         int var10 = var7 * var0;
         int var11 = var10 / 64;
         int var12 = ((var7 + 1) * var0 - 1) / 64;
         int var13 = var10 % 64;
         var6[var11] = var6[var11] & ~(var4 << var13) | (var8 & var4) << var13;
         if(var11 != var12) {
            int var14 = 64 - var13;
            var6[var12] = var6[var12] >>> var14 << var14 | (var8 & var4) >> var14;
         }

         ++var7;
      }

      return var6;
   }

   public static void iterateCompactArray(int var0, int var1, long[] var2, BiIntConsumer var3) {
      Config.c();
      long var5 = (1L << var0) - 1L;
      int var7 = 0;
      if(var7 < var1) {
         int var8 = var7 * var0;
         int var9 = var8 / 64;
         int var10 = ((var7 + 1) * var0 - 1) / 64;
         int var11 = var8 % 64;
         if(var9 == var10) {
            int var10000 = (int)(var2[var9] >>> var11 & var5);
         }

         int var13 = 64 - var11;
         int var12 = (int)((var2[var9] >>> var11 | var2[var10] << var13) & var5);
         var3.consume(var7, var12);
         ++var7;
      }

   }
}
