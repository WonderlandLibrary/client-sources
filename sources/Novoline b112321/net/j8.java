package net;

import net.minecraft.entity.projectile.EntityPotion;

public class j8 {
   private static String[] b;

   public static void a(EntityPotion var0, int var1) {
      var0.setPotionDamage(var1);
   }

   public static void a(EntityPotion var0, double var1, double var3, double var5, float var7, float var8) {
      var0.setThrowableHeading(var1, var3, var5, var7, var8);
   }

   public static int a(EntityPotion var0) {
      return var0.getPotionDamage();
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new String[1]);
      }

   }
}
