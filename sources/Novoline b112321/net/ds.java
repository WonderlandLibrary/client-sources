package net;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ds {
   private static int[] b;

   public static boolean e(Set var0) {
      return var0.isEmpty();
   }

   public static boolean a(Set var0, Object var1) {
      return var0.contains(var1);
   }

   public static int d(Set var0) {
      return var0.size();
   }

   public static boolean c(Set var0, Object var1) {
      return var0.add(var1);
   }

   public static Iterator a(Set var0) {
      return var0.iterator();
   }

   public static boolean c(Set var0, Collection var1) {
      return var0.retainAll(var1);
   }

   public static void c(Set var0) {
      var0.clear();
   }

   public static boolean a(Set var0, Collection var1) {
      return var0.addAll(var1);
   }

   public static boolean b(Set var0, Object var1) {
      return var0.remove(var1);
   }

   public static Object[] a(Set var0, Object[] var1) {
      return var0.toArray(var1);
   }

   public static boolean a(Set var0, Predicate var1) {
      return var0.removeIf(var1);
   }

   public static Stream f(Set var0) {
      return var0.stream();
   }

   public static Object[] b(Set var0) {
      return var0.toArray();
   }

   public static boolean b(Set var0, Collection var1) {
      return var0.removeAll(var1);
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
