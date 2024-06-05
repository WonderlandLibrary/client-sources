package net;

import cc.novoline.gui.label.Label;
import cc.novoline.utils.fonts.api.FontRenderer;

public class Bb {
   private static String[] b;

   public static FontRenderer a(Label var0) {
      return var0.getFontRenderer();
   }

   public static void a(Label var0, int var1, int var2) {
      var0.setPosition(var1, var2);
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new String[3]);
      }

   }
}
