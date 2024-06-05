package net;

import net.minecraft.util.StringUtils;

public class Ej {
   public static String b(String var0) {
      return StringUtils.capitalize(var0);
   }

   public static boolean a(String var0) {
      return StringUtils.isNullOrEmpty(var0);
   }

   public static String a(int var0) {
      return StringUtils.ticksToElapsedTime(var0);
   }

   public static String c(String var0) {
      return StringUtils.stripControlCodes(var0);
   }
}
