package net;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class aJi {
   private static int[] b;

   public static boolean a(Map var0, Object var1) {
      return var0.containsKey(var1);
   }

   public static Object c(Map var0, Object var1) {
      return var0.remove(var1);
   }

   public static Object c(Map var0, Object var1, Object var2) {
      return var0.put(var1, var2);
   }

   public static Object b(Map var0, Object var1) {
      return var0.get(var1);
   }

   public static void e(Map var0) {
      var0.clear();
   }

   public static int b(Map var0) {
      return var0.size();
   }

   public static Set d(Map var0) {
      return var0.entrySet();
   }

   public static Set c(Map var0) {
      return var0.keySet();
   }

   public static void a(Map var0, Map var1) {
      var0.putAll(var1);
   }

   public static Object a(Map var0, Object var1, Function var2) {
      return var0.computeIfAbsent(var1, var2);
   }

   public static Object a(Map var0, Object var1, Object var2) {
      return var0.getOrDefault(var1, var2);
   }

   public static void a(Map var0, BiConsumer var1) {
      var0.forEach(var1);
   }

   public static Object a(Map var0, Object var1, BiFunction var2) {
      return var0.compute(var1, var2);
   }

   public static boolean a(Map var0) {
      return var0.isEmpty();
   }

   public static Object b(Map var0, Object var1, Object var2) {
      return var0.putIfAbsent(var1, var2);
   }

   public static void a(Map var0, BiFunction var1) {
      var0.replaceAll(var1);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new int[1]);
      }

   }
}
