package net;

import java.util.List;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.Locale;

public class aI_ {
   public static void a(Locale var0, IResourceManager var1, List var2) {
      var0.loadLocaleDataFiles(var1, var2);
   }

   public static boolean a(Locale var0) {
      return var0.isUnicode();
   }

   public static String a(Locale var0, String var1, Object[] var2) {
      return var0.formatMessage(var1, var2);
   }
}
