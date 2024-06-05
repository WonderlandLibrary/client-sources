package net.skidunion;

import net.acE;

public class aA extends RuntimeException {
   private static acE[] b;

   public aA(String var1) {
      b();
      super(var1);
      this.setStackTrace(new StackTraceElement[0]);
      if(acE.b() == null) {
         b(new acE[5]);
      }

   }

   public aA(Throwable var1) {
      b();
      super(var1);
      this.setStackTrace(new StackTraceElement[0]);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   private static aA a(aA var0) {
      return var0;
   }

   static {
      if(b() == null) {
         b(new acE[5]);
      }

   }
}
