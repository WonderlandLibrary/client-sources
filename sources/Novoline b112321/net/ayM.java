package net;

import net.acE;
import net.c1;
import viaversion.viaversion.api.entities.EntityType;

public class ayM {
   private static acE[] b;

   public static void a(c1 var0, int var1, EntityType var2) {
      var0.addEntity(var1, var2);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new acE[5]);
      }

   }
}
