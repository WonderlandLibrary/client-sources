package net;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class afv {
   private static boolean b;

   public static int c(ArrayList var0) {
      return var0.size();
   }

   public static Object b(ArrayList var0, int var1) {
      return var0.get(var1);
   }

   public static Iterator d(ArrayList var0) {
      return var0.iterator();
   }

   public static boolean c(ArrayList var0, Object var1) {
      return var0.add(var1);
   }

   public static boolean a(ArrayList var0, Predicate var1) {
      return var0.removeIf(var1);
   }

   public static void b(ArrayList var0) {
      var0.clear();
   }

   public static boolean b(ArrayList var0, Object var1) {
      return var0.remove(var1);
   }

   public static boolean a(ArrayList var0) {
      return var0.isEmpty();
   }

   public static void a(ArrayList var0, Consumer var1) {
      var0.forEach(var1);
   }

   public static void a(ArrayList var0, int var1, Object var2) {
      var0.add(var1, var2);
   }

   public static Object b(ArrayList var0, int var1, Object var2) {
      return var0.set(var1, var2);
   }

   public static Object[] a(ArrayList var0, Object[] var1) {
      return var0.toArray(var1);
   }

   public static boolean a(ArrayList var0, Collection var1) {
      return var0.addAll(var1);
   }

   public static int a(ArrayList var0, Object var1) {
      return var0.indexOf(var1);
   }

   public static Object a(ArrayList var0, int var1) {
      return var0.remove(var1);
   }

   public static void b(boolean var0) {
      b = var0;
   }

   public static boolean b() {
      return b;
   }

   public static boolean a() {
      boolean var0 = b();
      return true;
   }

   static {
      if(!a()) {
         b(true);
      }

   }
}
