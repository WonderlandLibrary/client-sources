package net;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntitySenses;

public class Wz {
   public static boolean a(EntitySenses var0, Entity var1) {
      return var0.canSee(var1);
   }

   public static void a(EntitySenses var0) {
      var0.clearSensingCache();
   }
}
