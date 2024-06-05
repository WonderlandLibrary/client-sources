package net.minecraft.util;

import net.minecraft.util.StringTranslate;

public class StatCollector {
   private static StringTranslate localizedName = StringTranslate.getInstance();
   private static StringTranslate fallbackTranslator = new StringTranslate();

   public static String translateToLocal(String var0) {
      return localizedName.translateKey(var0);
   }

   public static String translateToLocalFormatted(String var0, Object... var1) {
      return localizedName.translateKeyFormat(var0, var1);
   }

   public static String translateToFallback(String var0) {
      return fallbackTranslator.translateKey(var0);
   }

   public static boolean canTranslate(String var0) {
      return localizedName.isKeyTranslated(var0);
   }

   public static long getLastTranslationUpdateTimeInMilliseconds() {
      return localizedName.getLastUpdateTimeInMilliseconds();
   }
}
