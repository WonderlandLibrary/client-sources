package net;

import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntityRabbit$EnumMoveType;

public class aw6 {
   public static EntityLookHelper a(EntityRabbit var0) {
      return var0.getLookHelper();
   }

   public static int d(EntityRabbit var0) {
      return var0.getVerticalFaceSpeed();
   }

   public static void a(EntityRabbit var0, double var1) {
      var0.setMovementSpeed(var1);
   }

   public static float a(EntityRabbit var0, float var1) {
      return var0.func_175521_o(var1);
   }

   public static String b(EntityRabbit var0) {
      return var0.getName();
   }

   public static int e(EntityRabbit var0) {
      return var0.getRabbitType();
   }

   public static boolean c(EntityRabbit var0) {
      return var0.func_175523_cj();
   }

   public static void a(EntityRabbit var0, EntityRabbit$EnumMoveType var1) {
      var0.doMovementAction(var1);
   }
}
