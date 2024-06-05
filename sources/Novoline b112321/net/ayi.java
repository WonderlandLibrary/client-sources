package net;

import net.minecraft.client.LoadingScreenRenderer;

public class ayi {
   public static void a(LoadingScreenRenderer var0, String var1) {
      var0.displaySavingString(var1);
   }

   public static void b(LoadingScreenRenderer var0, String var1) {
      var0.displayLoadingString(var1);
   }

   public static void c(LoadingScreenRenderer var0, String var1) {
      var0.resetProgressAndMessage(var1);
   }
}
