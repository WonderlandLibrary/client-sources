package net;

import cc.novoline.utils.fonts.api.FontFamily;
import cc.novoline.utils.fonts.api.FontType;
import cc.novoline.utils.fonts.impl.SimpleFontFamily;
import java.awt.Font;
import net.acE;

public class tu {
   private static acE[] b;

   public static FontFamily a(FontType var0, Font var1) {
      return SimpleFontFamily.create(var0, var1);
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new acE[2]);
      }

   }
}
