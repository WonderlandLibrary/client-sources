package net;

import tv.twitch.ErrorCode;

public class qe {
   private static boolean b;

   public static boolean c(ErrorCode var0) {
      return ErrorCode.failed(var0);
   }

   public static boolean b(ErrorCode var0) {
      return ErrorCode.succeeded(var0);
   }

   public static String a(ErrorCode var0) {
      return ErrorCode.getString(var0);
   }

   public static int d(ErrorCode var0) {
      return var0.getValue();
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
      if(!a()) {
         b(true);
      }

   }
}
