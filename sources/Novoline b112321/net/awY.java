package net;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;

public class awY {
   private static int b;

   public static Future a(ScheduledFuture var0, GenericFutureListener var1) {
      return var0.addListener(var1);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int a() {
      int var0 = b();
      return 123;
   }

   static {
      if(b() == 0) {
         b(50);
      }

   }
}
