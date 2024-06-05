package net;

import java.util.concurrent.atomic.AtomicReference;

public class m0 {
   public static Object a(AtomicReference var0) {
      return var0.get();
   }

   public static Object a(AtomicReference var0, Object var1) {
      return var0.getAndSet(var1);
   }

   public static boolean a(AtomicReference var0, Object var1, Object var2) {
      return var0.compareAndSet(var1, var2);
   }

   public static void b(AtomicReference var0, Object var1) {
      var0.set(var1);
   }
}
