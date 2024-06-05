package net;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.util.function.IntFunction;
import net.acE;

public class A_ {
   private static acE[] b;

   public static Object a(Int2ObjectMap var0, int var1, IntFunction var2) {
      return var0.computeIfAbsent(var1, var2);
   }

   public static Object a(Int2ObjectMap var0, int var1, Object var2) {
      return var0.put(var1, var2);
   }

   public static Object a(Int2ObjectMap var0, int var1) {
      return var0.get(var1);
   }

   public static Object a(Int2ObjectMap var0, Object var1) {
      return var0.get(var1);
   }

   public static int b(Int2ObjectMap var0) {
      return var0.size();
   }

   public static Object b(Int2ObjectMap var0, int var1, Object var2) {
      return var0.getOrDefault(var1, var2);
   }

   public static boolean b(Int2ObjectMap var0, int var1) {
      return var0.containsKey(var1);
   }

   public static ObjectSet a(Int2ObjectMap var0) {
      return var0.int2ObjectEntrySet();
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new acE[5]);
      }

   }
}
