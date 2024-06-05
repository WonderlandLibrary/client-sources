package net;

import java.util.List;
import viaversion.viarewind.replacement.EntityReplacement;

public class bg2 {
   private static String[] b;

   public static void a(EntityReplacement var0, List var1) {
      var0.updateMetadata(var1);
   }

   public static void b(EntityReplacement var0) {
      var0.despawn();
   }

   public static int a(EntityReplacement var0) {
      return var0.getEntityId();
   }

   public static void a(EntityReplacement var0, double var1, double var3, double var5) {
      var0.relMove(var1, var3, var5);
   }

   public static void a(EntityReplacement var0, float var1, float var2) {
      var0.setYawPitch(var1, var2);
   }

   public static void a(EntityReplacement var0, float var1) {
      var0.setHeadYaw(var1);
   }

   public static void b(EntityReplacement var0, double var1, double var3, double var5) {
      var0.setLocation(var1, var3, var5);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[2]);
      }

   }
}
