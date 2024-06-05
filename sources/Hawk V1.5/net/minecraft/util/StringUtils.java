package net.minecraft.util;

import java.util.regex.Pattern;

public class StringUtils {
   private static final String __OBFID = "CL_00001501";
   private static final Pattern patternControlCode = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");

   public static String stripControlCodes(String var0) {
      return patternControlCode.matcher(var0).replaceAll("");
   }

   public static boolean isNullOrEmpty(String var0) {
      return org.apache.commons.lang3.StringUtils.isEmpty(var0);
   }

   public static String ticksToElapsedTime(int var0) {
      int var1 = var0 / 20;
      int var2 = var1 / 60;
      var1 %= 60;
      return var1 < 10 ? String.valueOf((new StringBuilder(String.valueOf(var2))).append(":0").append(var1)) : String.valueOf((new StringBuilder(String.valueOf(var2))).append(":").append(var1));
   }
}
