package net;

import viaversion.viaversion.util.ConcurrentList;

public class a8x {
   public static Object a(ConcurrentList var0, int var1) {
      return var0.get(var1);
   }

   public static Object b(ConcurrentList var0, int var1) {
      return var0.remove(var1);
   }

   public static Object b(ConcurrentList var0, int var1, Object var2) {
      return var0.set(var1, var2);
   }

   public static void a(ConcurrentList var0, int var1, Object var2) {
      var0.add(var1, var2);
   }
}
