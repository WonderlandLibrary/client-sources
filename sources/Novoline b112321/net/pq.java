package net;

import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatBasic;

public class pq {
   public static StatBase b(StatBasic var0) {
      return var0.initIndependentStat();
   }

   public static StatBase a(StatBasic var0) {
      return var0.registerStat();
   }
}
