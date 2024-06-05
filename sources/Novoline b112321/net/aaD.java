package net;

import net.optifine.CloudRenderer;

public class aaD {
   public static void a(CloudRenderer var0, boolean var1, int var2, float var3) {
      var0.a(var1, var2, var3);
   }

   public static boolean c(CloudRenderer var0) {
      return var0.shouldUpdateGlList();
   }

   public static void d(CloudRenderer var0) {
      var0.startUpdateGlList();
   }

   public static void e(CloudRenderer var0) {
      var0.endUpdateGlList();
   }

   public static void b(CloudRenderer var0) {
      var0.renderGlList();
   }

   public static void a(CloudRenderer var0) {
      var0.reset();
   }
}
