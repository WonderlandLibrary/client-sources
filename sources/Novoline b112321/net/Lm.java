package net;

import cc.novoline.utils.java.FilteredArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class Lm {
   public static void c(FilteredArrayList var0) {
      var0.update();
   }

   public static int d(FilteredArrayList var0) {
      return var0.size();
   }

   public static boolean f(FilteredArrayList var0) {
      return var0.isEmpty();
   }

   public static List a(FilteredArrayList var0, int var1, int var2) {
      return var0.subList(var1, var2);
   }

   public static Stream a(FilteredArrayList var0) {
      return var0.stream();
   }

   public static Object a(FilteredArrayList var0, int var1) {
      return var0.get(var1);
   }

   public static int b(FilteredArrayList var0, Object var1) {
      return var0.indexOf(var1);
   }

   public static Iterator b(FilteredArrayList var0) {
      return var0.iterator();
   }

   public static Collection e(FilteredArrayList var0) {
      return var0.getUnfiltered();
   }

   public static void c(FilteredArrayList var0, Object var1) {
      var0.add(var1);
   }

   public static boolean a(FilteredArrayList var0, Object var1) {
      return var0.remove(var1);
   }

   public static void g(FilteredArrayList var0) {
      var0.clear();
   }
}
