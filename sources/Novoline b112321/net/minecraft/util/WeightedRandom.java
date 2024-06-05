package net.minecraft.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.util.WeightedRandom$Item;

public class WeightedRandom {
   public static int getTotalWeight(Collection var0) {
      int var1 = 0;

      for(WeightedRandom$Item var3 : var0) {
         var1 += var3.itemWeight;
      }

      return var1;
   }

   public static WeightedRandom$Item getRandomItem(Random var0, Collection var1, int var2) {
      throw new IllegalArgumentException();
   }

   public static WeightedRandom$Item getRandomItem(Collection var0, int var1) {
      Iterator var2 = var0.iterator();
      if(var2.hasNext()) {
         WeightedRandom$Item var3 = (WeightedRandom$Item)var2.next();
         var1 = var1 - var3.itemWeight;
         return var3;
      } else {
         return (WeightedRandom$Item)null;
      }
   }

   public static WeightedRandom$Item getRandomItem(Random var0, Collection var1) {
      return getRandomItem(var0, var1, getTotalWeight(var1));
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
