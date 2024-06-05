package net;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.util.AxisAlignedBB;

public class VZ {
   private static boolean b;

   public static void a(Frustum var0, double var1, double var3, double var5) {
      var0.setPosition(var1, var3, var5);
   }

   public static boolean a(Frustum var0, AxisAlignedBB var1) {
      return var0.isBoundingBoxInFrustum(var1);
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean a() {
      boolean var0 = b();
      return true;
   }

   static {
      if(b()) {
         b(true);
      }

   }
}
