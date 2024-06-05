package net;

import viaversion.viaversion.api.entities.EntityType;
import viaversion.viaversion.api.storage.EntityTracker;

public class ay0 {
   private static String b;

   public static EntityType b(EntityTracker var0, int var1) {
      return var0.getEntity(var1);
   }

   public static void a(EntityTracker var0, int var1, EntityType var2) {
      var0.addEntity(var1, var2);
   }

   public static void a(EntityTracker var0, int var1) {
      var0.removeEntity(var1);
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() != null) {
         b("CtLyNb");
      }

   }
}
