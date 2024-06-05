package net;

import net.minecraft.entity.ai.EntityAIControlledByPlayer;
import net.minecraft.entity.passive.EntityPig;

public class agr {
   public static boolean c(EntityPig var0) {
      return var0.getSaddled();
   }

   public static boolean a(EntityPig var0) {
      return var0.isChild();
   }

   public static void a(EntityPig var0, boolean var1) {
      var0.setSaddled(var1);
   }

   public static EntityAIControlledByPlayer b(EntityPig var0) {
      return var0.getAIControlledByPlayer();
   }
}
