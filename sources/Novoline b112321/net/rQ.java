package net;

import net.minecraft.entity.monster.EntitySnowman;

public class rQ {
   public static boolean a(EntitySnowman var0) {
      return var0.isInvisible();
   }

   public static void a(EntitySnowman var0, double var1, double var3, double var5, float var7, float var8) {
      var0.setLocationAndAngles(var1, var3, var5, var7, var8);
   }
}
