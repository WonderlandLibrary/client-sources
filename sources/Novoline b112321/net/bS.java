package net;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;

public class bS {
   public static int b(Int2IntFunction var0, int var1) {
      return var0.get(var1);
   }

   public static void a(Int2IntFunction var0, int var1) {
      var0.defaultReturnValue(var1);
   }

   public static int a(Int2IntFunction var0, int var1, int var2) {
      return var0.put(var1, var2);
   }
}
