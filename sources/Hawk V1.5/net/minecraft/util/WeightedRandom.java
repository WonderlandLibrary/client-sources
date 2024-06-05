package net.minecraft.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class WeightedRandom {
   private static final String __OBFID = "CL_00001503";

   public static WeightedRandom.Item getRandomItem(Random var0, Collection var1, int var2) {
      if (var2 <= 0) {
         throw new IllegalArgumentException();
      } else {
         int var3 = var0.nextInt(var2);
         return func_180166_a(var1, var3);
      }
   }

   public static WeightedRandom.Item func_180166_a(Collection var0, int var1) {
      Iterator var2 = var0.iterator();

      while(var2.hasNext()) {
         WeightedRandom.Item var3 = (WeightedRandom.Item)var2.next();
         var1 -= var3.itemWeight;
         if (var1 < 0) {
            return var3;
         }
      }

      return null;
   }

   public static WeightedRandom.Item getRandomItem(Random var0, Collection var1) {
      return getRandomItem(var0, var1, getTotalWeight(var1));
   }

   public static int getTotalWeight(Collection var0) {
      int var1 = 0;

      WeightedRandom.Item var2;
      for(Iterator var3 = var0.iterator(); var3.hasNext(); var1 += var2.itemWeight) {
         var2 = (WeightedRandom.Item)var3.next();
      }

      return var1;
   }

   public static class Item {
      protected int itemWeight;
      private static final String __OBFID = "CL_00001504";

      public Item(int var1) {
         this.itemWeight = var1;
      }
   }
}
