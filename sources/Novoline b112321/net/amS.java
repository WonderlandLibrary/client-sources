package net;

import net.acE;

public class amS {
   private static acE[] b;

   public static String c(long var0) {
      return Long.toString(var0);
   }

   public static int a(long var0) {
      return Long.hashCode(var0);
   }

   public static Long b(long var0) {
      return Long.valueOf(var0);
   }

   public static long a(String var0) {
      return Long.parseLong(var0);
   }

   public static long a(Long var0) {
      return var0.longValue();
   }

   public static int b(Long var0) {
      return var0.intValue();
   }

   public static Long b(String var0) {
      return Long.valueOf(var0);
   }

   public static String a(long var0, int var2) {
      return Long.toString(var0, var2);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new acE[4]);
      }

   }
}
