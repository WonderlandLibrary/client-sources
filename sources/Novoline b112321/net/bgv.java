package net;

import net.minecraft.util.IntHashMap;

public class bgv {
   public static void a(IntHashMap var0, int var1, Object var2) {
      var0.addKey(var1, var2);
   }

   public static Object c(IntHashMap var0, int var1) {
      return var0.lookup(var1);
   }

   public static Object a(IntHashMap var0, int var1) {
      return var0.removeObject(var1);
   }

   public static boolean b(IntHashMap var0, int var1) {
      return var0.containsItem(var1);
   }

   public static void a(IntHashMap var0) {
      var0.clearMap();
   }
}
