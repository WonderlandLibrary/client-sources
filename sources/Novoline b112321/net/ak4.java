package net;

import java.io.PrintWriter;

public class ak4 {
   private static boolean b;

   public static void d(Throwable var0) {
      var0.printStackTrace();
   }

   public static void a(Throwable var0, Throwable var1) {
      var0.addSuppressed(var1);
   }

   public static StackTraceElement[] a(Throwable var0) {
      return var0.getStackTrace();
   }

   public static String b(Throwable var0) {
      return var0.getMessage();
   }

   public static Throwable c(Throwable var0) {
      return var0.getCause();
   }

   public static void a(Throwable var0, StackTraceElement[] var1) {
      var0.setStackTrace(var1);
   }

   public static void a(Throwable var0, PrintWriter var1) {
      var0.printStackTrace(var1);
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
