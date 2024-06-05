package net;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;

public class hc {
   private static int[] b;

   public static void a(Logger var0, String var1) {
      var0.warn(var1);
   }

   public static void a(Logger var0, Object var1) {
      var0.warn(var1);
   }

   public static void c(Logger var0, Object var1) {
      var0.error(var1);
   }

   public static void d(Logger var0, String var1) {
      var0.info(var1);
   }

   public static void a(Logger var0, String var1, Throwable var2) {
      var0.error(var1, var2);
   }

   public static void e(Logger var0, String var1, Throwable var2) {
      var0.warn(var1, var2);
   }

   public static void f(Logger var0, String var1) {
      var0.error(var1);
   }

   public static void a(Logger var0, String var1, Object[] var2) {
      var0.warn(var1, var2);
   }

   public static void a(Logger var0, Marker var1, String var2, Object[] var3) {
      var0.error(var1, var2, var3);
   }

   public static void d(Logger var0, Marker var1, String var2, Object[] var3) {
      var0.warn(var1, var2, var3);
   }

   public static void e(Logger var0, String var1, Object[] var2) {
      var0.debug(var1, var2);
   }

   public static void f(Logger var0, String var1, Throwable var2) {
      var0.fatal(var1, var2);
   }

   public static void b(Logger var0, String var1, Throwable var2) {
      var0.info(var1, var2);
   }

   public static boolean a(Logger var0) {
      return var0.isDebugEnabled();
   }

   public static void b(Logger var0, Marker var1, String var2, Object[] var3) {
      var0.debug(var1, var2, var3);
   }

   public static void e(Logger var0, String var1) {
      var0.debug(var1);
   }

   public static void b(Logger var0, String var1) {
      var0.trace(var1);
   }

   public static void d(Logger var0, String var1, Object[] var2) {
      var0.error(var1, var2);
   }

   public static void c(Logger var0, String var1, Object[] var2) {
      var0.info(var1, var2);
   }

   public static void b(Logger var0, String var1, Object[] var2) {
      var0.trace(var1, var2);
   }

   public static void d(Logger var0, String var1, Throwable var2) {
      var0.debug(var1, var2);
   }

   public static void c(Logger var0, String var1, Throwable var2) {
      var0.trace(var1, var2);
   }

   public static void b(Logger var0, Object var1) {
      var0.fatal(var1);
   }

   public static void a(Logger var0, Marker var1, String var2, Throwable var3) {
      var0.error(var1, var2, var3);
   }

   public static void b(Logger var0, Marker var1, String var2) {
      var0.info(var1, var2);
   }

   public static void d(Logger var0, Marker var1, String var2) {
      var0.error(var1, var2);
   }

   public static void c(Logger var0, String var1) {
      var0.fatal(var1);
   }

   public static void c(Logger var0, Marker var1, String var2) {
      var0.debug(var1, var2);
   }

   public static void a(Logger var0, Marker var1, String var2) {
      var0.warn(var1, var2);
   }

   public static void c(Logger var0, Marker var1, String var2, Object[] var3) {
      var0.info(var1, var2, var3);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new int[2]);
      }

   }
}
