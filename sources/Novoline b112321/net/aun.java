package net;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityLookHelper;

public class aun {
   public static void a(EntityLookHelper var0, double var1, double var3, double var5, float var7, float var8) {
      var0.setLookPosition(var1, var3, var5, var7, var8);
   }

   public static void a(EntityLookHelper var0, Entity var1, float var2, float var3) {
      var0.setLookPositionWithEntity(var1, var2, var3);
   }

   public static void b(EntityLookHelper var0) {
      var0.onUpdateLook();
   }

   public static double a(EntityLookHelper var0) {
      return var0.getLookPosX();
   }

   public static double e(EntityLookHelper var0) {
      return var0.getLookPosY();
   }

   public static double c(EntityLookHelper var0) {
      return var0.getLookPosZ();
   }

   public static boolean d(EntityLookHelper var0) {
      return var0.getIsLooking();
   }
}
