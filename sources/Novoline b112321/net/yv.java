package net;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class yv {
   private static int[] b;

   public static Collector a(Supplier var0) {
      return Collectors.toCollection(var0);
   }

   public static Collector a() {
      return Collectors.toList();
   }

   public static Collector a(Function var0, Function var1) {
      return Collectors.toMap(var0, var1);
   }

   public static Collector c() {
      return Collectors.toSet();
   }

   public static Collector a(CharSequence var0, CharSequence var1, CharSequence var2) {
      return Collectors.joining(var0, var1, var2);
   }

   public static Collector b() {
      return Collectors.joining();
   }

   public static Collector d() {
      return Collectors.counting();
   }

   public static Collector a(Function var0, Collector var1) {
      return Collectors.groupingBy(var0, var1);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] e() {
      return b;
   }

   static {
      if(e() != null) {
         b(new int[1]);
      }

   }
}
