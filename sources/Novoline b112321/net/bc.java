package net;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.function.Function;

public class bc {
   public static Object a(Object2ObjectArrayMap var0, Object var1, Function var2) {
      return var0.computeIfAbsent(var1, var2);
   }

   public static Object a(Object2ObjectArrayMap var0, Object var1, Object var2) {
      return var0.put(var1, var2);
   }
}
