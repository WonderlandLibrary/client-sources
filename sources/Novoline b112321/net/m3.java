package net;

import java.util.LinkedHashMap;
import java.util.Set;

public class m3 {
   public static Object a(LinkedHashMap var0, Object var1, Object var2) {
      return var0.put(var1, var2);
   }

   public static Set a(LinkedHashMap var0) {
      return var0.entrySet();
   }

   public static Object a(LinkedHashMap var0, Object var1) {
      return var0.get(var1);
   }

   public static boolean b(LinkedHashMap var0, Object var1) {
      return var0.containsKey(var1);
   }
}
