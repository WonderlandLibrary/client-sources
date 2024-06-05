package net;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;

public class kx {
   public static KeySetView a() {
      return ConcurrentHashMap.newKeySet();
   }

   public static Object c(ConcurrentHashMap var0, Object var1) {
      return var0.remove(var1);
   }

   public static void b(ConcurrentHashMap var0) {
      var0.clear();
   }

   public static Object b(ConcurrentHashMap var0, Object var1) {
      return var0.get(var1);
   }

   public static boolean a(ConcurrentHashMap var0, Object var1) {
      return var0.containsKey(var1);
   }

   public static Set a(ConcurrentHashMap var0) {
      return var0.entrySet();
   }
}
