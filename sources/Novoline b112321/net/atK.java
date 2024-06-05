package net;

public class atK {
   public static Runtime a() {
      return Runtime.getRuntime();
   }

   public static Process a(Runtime var0, String var1) {
      return var0.exec(var1);
   }

   public static int a(Runtime var0) {
      return var0.availableProcessors();
   }

   public static Process a(Runtime var0, String[] var1) {
      return var0.exec(var1);
   }

   public static void a(Runtime var0, Thread var1) {
      var0.addShutdownHook(var1);
   }

   public static long d(Runtime var0) {
      return var0.totalMemory();
   }

   public static long c(Runtime var0) {
      return var0.freeMemory();
   }

   public static long b(Runtime var0) {
      return var0.maxMemory();
   }
}
