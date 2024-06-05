package net;

import cc.novoline.utils.fonts.api.FontFamily;
import cc.novoline.utils.fonts.api.FontRenderer;

public class ax7 {
   private static int b;

   public static FontRenderer a(FontFamily var0, int var1) {
      return var0.ofSize(var1);
   }

   public static void b(int var0) {
      b = var0;
   }

   public static int b() {
      return b;
   }

   public static int c() {
      int var0 = b();
      return 26;
   }

   static {
      if(c() == 0) {
         b(89);
      }

   }
}
