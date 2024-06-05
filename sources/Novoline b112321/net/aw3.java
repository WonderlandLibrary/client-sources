package net;

import java.util.concurrent.atomic.AtomicInteger;
import net.acE;

public class aw3 {
   private static acE[] b;

   public static int b(AtomicInteger var0) {
      return var0.get();
   }

   public static void a(AtomicInteger var0, int var1) {
      var0.set(var1);
   }

   public static int a(AtomicInteger var0) {
      return var0.incrementAndGet();
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new acE[1]);
      }

   }
}
