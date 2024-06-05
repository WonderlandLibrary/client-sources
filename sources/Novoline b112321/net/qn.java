package net;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.objects.ObjectSet;

public class qn {
   public static int a(Int2IntMap var0, int var1) {
      return var0.get(var1);
   }

   public static boolean b(Int2IntMap var0, int var1) {
      return var0.containsKey(var1);
   }

   public static int a(Int2IntMap var0, int var1, int var2) {
      return var0.put(var1, var2);
   }

   public static int b(Int2IntMap var0, int var1, int var2) {
      return var0.getOrDefault(var1, var2);
   }

   public static void c(Int2IntMap var0, int var1) {
      var0.defaultReturnValue(var1);
   }

   public static ObjectSet b(Int2IntMap var0) {
      return var0.int2IntEntrySet();
   }

   public static int d(Int2IntMap var0, int var1) {
      return var0.remove(var1);
   }

   public static void a(Int2IntMap var0) {
      var0.clear();
   }
}
