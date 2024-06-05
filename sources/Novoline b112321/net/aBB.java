package net;

import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.AxisAlignedBB;

public class aBB {
   public static void a(EntityBoat var0, boolean var1) {
      var0.setIsBoatEmpty(var1);
   }

   public static AxisAlignedBB d(EntityBoat var0) {
      return var0.getEntityBoundingBox();
   }

   public static int a(EntityBoat var0) {
      return var0.getTimeSinceHit();
   }

   public static float c(EntityBoat var0) {
      return var0.getDamageTaken();
   }

   public static int b(EntityBoat var0) {
      return var0.getForwardDirection();
   }
}
