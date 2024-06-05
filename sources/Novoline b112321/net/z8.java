package net;

import net.acE;
import net.alg;
import viaversion.viabackwards.api.entities.storage.EntityPositionHandler;
import viaversion.viaversion.api.PacketWrapper;

public class z8 {
   public static void a(EntityPositionHandler var0, PacketWrapper var1, boolean var2, boolean var3) {
      var0.cacheEntityPosition(var1, var2, var3);
   }

   public static void a(PacketWrapper var0, double var1, double var3, double var5, double var7, double var9, double var11) {
      boolean var13 = alg.c();
      EntityPositionHandler.writeFacingDegrees(var0, var1, var3, var5, var7, var9, var11);
      if(acE.b() == null) {
         alg.b(false);
      }

   }

   public static void a(EntityPositionHandler var0, PacketWrapper var1, double var2, double var4, double var6, boolean var8, boolean var9) {
      boolean var10 = alg.c();
      var0.cacheEntityPosition(var1, var2, var4, var6, var8, var9);
   }
}
