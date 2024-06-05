package net;

import it.unimi.dsi.fastutil.objects.Object2IntMap;

public class r0 {
   public static int a(Object2IntMap var0, Object var1) {
      return var0.getInt(var1);
   }

   public static void a(Object2IntMap var0, int var1) {
      var0.defaultReturnValue(var1);
   }

   public static int a(Object2IntMap var0, Object var1, int var2) {
      return var0.put(var1, var2);
   }
}
