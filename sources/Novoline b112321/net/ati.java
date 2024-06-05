package net;

import com.google.common.collect.Multimap;
import java.util.Collection;

public class ati {
   public static boolean b(Multimap var0, Object var1, Object var2) {
      return var0.put(var1, var2);
   }

   public static Collection b(Multimap var0) {
      return var0.entries();
   }

   public static Collection a(Multimap var0, Object var1) {
      return var0.get(var1);
   }

   public static void a(Multimap var0) {
      var0.clear();
   }

   public static boolean a(Multimap var0, Object var1, Object var2) {
      return var0.remove(var1, var2);
   }

   public static boolean c(Multimap var0) {
      return var0.isEmpty();
   }
}
