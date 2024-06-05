package net;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class aOp {
   private static String[] b;

   public static boolean b(List var0, Object var1) {
      return var0.add(var1);
   }

   public static Object a(List var0, int var1) {
      return var0.get(var1);
   }

   public static int e(List var0) {
      return var0.size();
   }

   public static void f(List var0) {
      var0.clear();
   }

   public static Iterator d(List var0) {
      return var0.iterator();
   }

   public static boolean e(List var0, Object var1) {
      return var0.contains(var1);
   }

   public static Stream c(List var0) {
      return var0.stream();
   }

   public static boolean a(List var0) {
      return var0.isEmpty();
   }

   public static void a(List var0, Consumer var1) {
      var0.forEach(var1);
   }

   public static Object[] a(List var0, Object[] var1) {
      return var0.toArray(var1);
   }

   public static int c(List var0, Object var1) {
      return var0.indexOf(var1);
   }

   public static boolean a(List var0, Object var1) {
      return var0.remove(var1);
   }

   public static void b(List var0, int var1, Object var2) {
      var0.add(var1, var2);
   }

   public static boolean b(List var0, Collection var1) {
      return var0.addAll(var1);
   }

   public static Object a(List var0, int var1, Object var2) {
      return var0.set(var1, var2);
   }

   public static boolean a(List var0, Predicate var1) {
      return var0.removeIf(var1);
   }

   public static Object b(List var0, int var1) {
      return var0.remove(var1);
   }

   public static List a(List var0, int var1, int var2) {
      return var0.subList(var1, var2);
   }

   public static void a(List var0, Comparator var1) {
      var0.sort(var1);
   }

   public static boolean a(List var0, Collection var1) {
      return var0.removeAll(var1);
   }

   public static int d(List var0, Object var1) {
      return var0.lastIndexOf(var1);
   }

   public static Object[] b(List var0) {
      return var0.toArray();
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new String[3]);
      }

   }
}
