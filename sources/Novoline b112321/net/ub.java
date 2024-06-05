package net;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ub {
   public static void b(CountDownLatch var0) {
      var0.countDown();
   }

   public static void a(CountDownLatch var0) {
      var0.await();
   }

   public static boolean a(CountDownLatch var0, long var1, TimeUnit var3) {
      return var0.await(var1, var3);
   }
}
