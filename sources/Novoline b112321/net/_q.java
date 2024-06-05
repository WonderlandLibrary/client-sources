package net;

import java.util.concurrent.ForkJoinPool;

public class _q {
   public static ForkJoinPool a() {
      return ForkJoinPool.commonPool();
   }

   public static void a(ForkJoinPool var0, Runnable var1) {
      var0.execute(var1);
   }
}
