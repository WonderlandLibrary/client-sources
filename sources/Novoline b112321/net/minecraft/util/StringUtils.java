package net.minecraft.util;

import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtils {
   private static final Pattern patternControlCode = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");

   public static String ticksToElapsedTime(int var0) {
      int var1 = var0 / 20;
      int var2 = var1 / 60;
      var1 = var1 % 60;
      return var1 < 10?var2 + ":0" + var1:var2 + ":" + var1;
   }

   public static String stripControlCodes(String var0) {
      return patternControlCode.matcher(var0).replaceAll("");
   }

   public static String digitString(String var0) {
      return (String)var0.chars().mapToObj(StringUtils::lambda$digitString$0).filter(Character::isDigit).map(String::valueOf).collect(Collectors.joining());
   }

   public static String capitalize(String var0) {
      return var0.substring(0, 1).toUpperCase() + var0.substring(1).toLowerCase();
   }

   public static boolean containsDigit(String var0) {
      for(int var1 = 0; var1 < 10; ++var1) {
         if(var0.contains(String.valueOf(var1))) {
            return true;
         }
      }

      return false;
   }

   public static boolean isNullOrEmpty(String var0) {
      return org.apache.commons.lang3.StringUtils.isEmpty(var0);
   }

   private static Character lambda$digitString$0(int var0) {
      return Character.valueOf((char)var0);
   }
}
