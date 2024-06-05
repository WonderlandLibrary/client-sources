package net;

import net.minecraft.util.IProgressUpdate;

public class kn {
   public static void a(IProgressUpdate var0, int var1) {
      var0.setLoadingProgress(var1);
   }

   public static void b(IProgressUpdate var0, String var1) {
      var0.displaySavingString(var1);
   }

   public static void a(IProgressUpdate var0, String var1) {
      var0.displayLoadingString(var1);
   }

   public static void c(IProgressUpdate var0, String var1) {
      var0.resetProgressAndMessage(var1);
   }

   public static void a(IProgressUpdate var0) {
      var0.setDoneWorking();
   }
}
