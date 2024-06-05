package net.minecraft.client.resources;

import java.util.Map;
import net.minecraft.client.resources.Locale;

public class I18n {
   private static Locale i18nLocale;
   private static final String b = "CL_00001094";

   static void setLocale(Locale var0) {
      i18nLocale = var0;
   }

   public static String format(String var0, Object... var1) {
      return i18nLocale.formatMessage(var0, var1);
   }

   public static Map getLocaleProperties() {
      return i18nLocale.properties;
   }
}
