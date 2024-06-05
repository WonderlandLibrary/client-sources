package net;

import net.minecraft.util.LongHashMap;

public class agx {
   public static boolean b(LongHashMap var0, long var1) {
      return var0.containsItem(var1);
   }

   public static Object a(LongHashMap var0, long var1) {
      return var0.getValueByKey(var1);
   }

   public static void a(LongHashMap var0, long var1, Object var3) {
      var0.add(var1, var3);
   }

   public static Object c(LongHashMap var0, long var1) {
      return var0.remove(var1);
   }

   public static int a(LongHashMap var0) {
      return var0.getNumHashElements();
   }
}
