package net;

import net.minecraft.entity.EntityList$EntityEggInfo;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;

public class a8w {
   public static StatBase a(String var0) {
      return StatList.getOneShotStat(var0);
   }

   public static void a() {
      StatList.init();
   }

   public static StatBase a(EntityList$EntityEggInfo var0) {
      return StatList.a(var0);
   }

   public static StatBase b(EntityList$EntityEggInfo var0) {
      return StatList.b(var0);
   }
}
