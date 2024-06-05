package net;

import cc.novoline.utils.java.Checks;
import java.util.Collection;

public class Dt {
   private static int[] b;

   public static void a(Object var0, String var1) {
      Checks.notNull(var0, var1);
   }

   public static void a(boolean var0, String var1) {
      Checks.check(var0, var1);
   }

   public static String a(String var0, String var1) {
      return Checks.notBlank(var0, var1);
   }

   public static void a(int var0, String var1) {
      Checks.notNegative(var0, var1);
   }

   public static void a(long var0, String var2) {
      Checks.notNegative(var0, var2);
   }

   public static void a(Collection var0, String var1) {
      Checks.notEmpty(var0, var1);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new int[1]);
      }

   }
}
