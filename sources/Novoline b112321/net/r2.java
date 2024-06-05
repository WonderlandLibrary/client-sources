package net;

import net.minecraft.util.EntityDamageSource;

public class r2 {
   public static boolean a(EntityDamageSource var0) {
      return var0.getIsThornsDamage();
   }

   public static EntityDamageSource b(EntityDamageSource var0) {
      return var0.setIsThornsDamage();
   }
}
