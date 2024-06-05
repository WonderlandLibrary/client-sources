package net;

import java.util.Objects;

public class ajP {
   private static String[] b;

   public static int a(Object[] var0) {
      return Objects.hash(var0);
   }

   public static boolean a(Object var0, Object var1) {
      return Objects.equals(var0, var1);
   }

   public static Object a(Object var0, String var1) {
      return Objects.requireNonNull(var0, var1);
   }

   public static Object a(Object var0) {
      return Objects.requireNonNull(var0);
   }

   public static int b(Object var0) {
      return Objects.hashCode(var0);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new String[2]);
      }

   }
}
