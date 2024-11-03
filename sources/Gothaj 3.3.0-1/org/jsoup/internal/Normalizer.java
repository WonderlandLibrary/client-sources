package org.jsoup.internal;

import java.util.Locale;

public final class Normalizer {
   public static String lowerCase(String input) {
      return input != null ? input.toLowerCase(Locale.ENGLISH) : "";
   }

   public static String normalize(String input) {
      return lowerCase(input).trim();
   }

   public static String normalize(String input, boolean isStringLiteral) {
      return isStringLiteral ? lowerCase(input) : normalize(input);
   }
}
