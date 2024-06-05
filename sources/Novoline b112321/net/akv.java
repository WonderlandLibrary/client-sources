package net;

import java.util.HashMap;
import java.util.Set;
import java.util.function.Function;

public class akv {
   private static boolean b;

   public static Object b(HashMap var0, Object var1, Object var2) {
      return var0.put(var1, var2);
   }

   public static Object b(HashMap var0, Object var1) {
      return var0.get(var1);
   }

   public static boolean c(HashMap var0, Object var1) {
      return var0.containsKey(var1);
   }

   public static Object a(HashMap var0, Object var1, Object var2) {
      return var0.getOrDefault(var1, var2);
   }

   public static Object a(HashMap var0, Object var1) {
      return var0.remove(var1);
   }

   public static Set a(HashMap var0) {
      return var0.entrySet();
   }

   public static Object a(HashMap var0, Object var1, Function var2) {
      return var0.computeIfAbsent(var1, var2);
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean c() {
      boolean var0 = b();
      return true;
   }

   static {
      if(!c()) {
         b(true);
      }

   }
}
