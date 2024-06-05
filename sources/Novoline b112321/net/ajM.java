package net;

import net.minecraft.entity.monster.EntityEndermite;

public class ajM {
   public static void a(EntityEndermite var0, boolean var1) {
      var0.setSpawnedByPlayer(var1);
   }

   public static boolean a(EntityEndermite var0) {
      return var0.isSpawnedByPlayer();
   }
}
