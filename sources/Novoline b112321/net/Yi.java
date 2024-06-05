package net;

import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.passive.EntityOcelot;

public class Yi {
   public static boolean a(EntityOcelot var0) {
      return var0.isTamed();
   }

   public static boolean e(EntityOcelot var0) {
      return var0.isSitting();
   }

   public static EntityAISit f(EntityOcelot var0) {
      return var0.getAISit();
   }

   public static void a(EntityOcelot var0, boolean var1) {
      var0.setSitting(var1);
   }

   public static boolean b(EntityOcelot var0) {
      return var0.isSneaking();
   }

   public static boolean c(EntityOcelot var0) {
      return var0.isSprinting();
   }

   public static int d(EntityOcelot var0) {
      return var0.getTameSkin();
   }
}
