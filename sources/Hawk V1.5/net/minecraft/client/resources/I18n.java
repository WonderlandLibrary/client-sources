package net.minecraft.client.resources;

import java.util.Map;

public class I18n {
   private static Locale i18nLocale;

   public static String format(String var0, Object... var1) {
      return i18nLocale.formatMessage(var0, var1);
   }

   static void setLocale(Locale var0) {
      i18nLocale = var0;
   }

   public static Map getLocaleProperties() {
      return i18nLocale.field_135032_a;
   }
}
