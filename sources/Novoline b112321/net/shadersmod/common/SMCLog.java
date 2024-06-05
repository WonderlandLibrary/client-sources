package net.shadersmod.common;

import net.acE;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class SMCLog {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final String b = "[Shaders] ";
   private static int c;

   public static void severe(String var0) {
      LOGGER.error("[Shaders] " + var0);
   }

   public static void warning(String var0) {
      LOGGER.warn("[Shaders] " + var0);
   }

   public static void info(String var0) {
      LOGGER.info("[Shaders] " + var0);
   }

   public static void fine(String var0) {
      LOGGER.debug("[Shaders] " + var0);
   }

   public static void severe(String var0, Object... var1) {
      String var2 = String.format(var0, var1);
      LOGGER.error("[Shaders] " + var2);
   }

   public static void warning(String var0, Object... var1) {
      String var2 = String.format(var0, var1);
      LOGGER.warn("[Shaders] " + var2);
   }

   public static void info(String var0, Object... var1) {
      String var3 = String.format(var0, var1);
      int var10000 = a();
      LOGGER.info("[Shaders] " + var3);
      int var2 = var10000;
      if(acE.b() == null) {
         ++var2;
         b(var2);
      }

   }

   public static void fine(String var0, Object... var1) {
      b();
      String var3 = String.format(var0, var1);
      LOGGER.debug("[Shaders] " + var3);
   }

   static {
      b(49);
   }

   public static void b(int var0) {
      c = var0;
   }

   public static int b() {
      return c;
   }

   public static int a() {
      int var0 = b();
      return 28;
   }
}
