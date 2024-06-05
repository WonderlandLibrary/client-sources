package net;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class xy {
   private static boolean b;

   public static Throwable a(Throwable var0) {
      return ExceptionUtils.getRootCause(var0);
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
      if(a()) {
         b(true);
      }

   }
}
