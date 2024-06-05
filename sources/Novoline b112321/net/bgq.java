package net;

import com.google.common.collect.BiMap;
import java.util.Set;

public class bgq {
   public static BiMap c(BiMap var0) {
      return var0.inverse();
   }

   public static Object c(BiMap var0, Object var1) {
      return var0.get(var1);
   }

   public static Object a(BiMap var0, Object var1, Object var2) {
      return var0.put(var1, var2);
   }

   public static Set a(BiMap var0) {
      return var0.entrySet();
   }

   public static int b(BiMap var0) {
      return var0.size();
   }

   public static boolean a(BiMap var0, Object var1) {
      return var0.containsKey(var1);
   }

   public static boolean b(BiMap var0, Object var1) {
      return var0.containsValue(var1);
   }
}
