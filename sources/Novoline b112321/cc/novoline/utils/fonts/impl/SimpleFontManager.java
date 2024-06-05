package cc.novoline.utils.fonts.impl;

import cc.novoline.utils.fonts.api.FontFamily;
import cc.novoline.utils.fonts.api.FontManager;
import cc.novoline.utils.fonts.api.FontType;
import cc.novoline.utils.fonts.impl.SimpleFontManager$FontRegistry;

public final class SimpleFontManager implements FontManager {
   private static final String b;
   private final SimpleFontManager$FontRegistry fonts = new SimpleFontManager$FontRegistry();
   private static int[] c;

   public static FontManager create() {
      return new SimpleFontManager();
   }

   public FontFamily fontFamily(FontType var1) {
      return SimpleFontManager$FontRegistry.access$100(this.fonts, var1);
   }

   public static void b(int[] var0) {
      c = var0;
   }

   public static int[] b() {
      return c;
   }

   static {
      if(b() != null) {
         b(new int[2]);
      }

      b = "novoline/fonts/";
   }
}
