package net;

import viaversion.viaversion.util.Int2IntBiMap;

public class ahl {
   public static int b(Int2IntBiMap var0, int var1) {
      return var0.get(var1);
   }

   public static int a(Int2IntBiMap var0, int var1, int var2) {
      return var0.put(var1, var2);
   }

   public static Int2IntBiMap a(Int2IntBiMap var0) {
      return var0.inverse();
   }

   public static boolean c(Int2IntBiMap var0, int var1) {
      return var0.containsKey(var1);
   }

   public static void a(Int2IntBiMap var0, int var1) {
      var0.defaultReturnValue(var1);
   }
}
