package net;

import net.minecraft.entity.ai.EntityAIControlledByPlayer;

public class hd {
   public static boolean a(EntityAIControlledByPlayer var0) {
      return var0.isControlledByPlayer();
   }

   public static void b(EntityAIControlledByPlayer var0) {
      var0.boostSpeed();
   }
}
