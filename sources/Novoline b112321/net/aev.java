package net;

import net.shadersmod.common.SMCLog;

public class aev {
   private static String b;

   public static void a(String var0) {
      SMCLog.info(var0);
   }

   public static void b(String var0) {
      SMCLog.severe(var0);
   }

   public static void d(String var0) {
      SMCLog.warning(var0);
   }

   public static void b(String var0, Object[] var1) {
      SMCLog.info(var0, var1);
   }

   public static void a(String var0, Object[] var1) {
      SMCLog.warning(var0, var1);
   }

   public static void c(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() != null) {
         c("k29bs");
      }

   }
}
