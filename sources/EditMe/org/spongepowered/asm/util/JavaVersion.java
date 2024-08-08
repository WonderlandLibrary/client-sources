package org.spongepowered.asm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class JavaVersion {
   private static double current = 0.0D;

   private JavaVersion() {
   }

   public static double current() {
      if (current == 0.0D) {
         current = resolveCurrentVersion();
      }

      return current;
   }

   private static double resolveCurrentVersion() {
      String var0 = System.getProperty("java.version");
      Matcher var1 = Pattern.compile("[0-9]+\\.[0-9]+").matcher(var0);
      return var1.find() ? Double.parseDouble(var1.group()) : 1.6D;
   }
}
