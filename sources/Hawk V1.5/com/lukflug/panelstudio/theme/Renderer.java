package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.Context;
import java.awt.Color;
import java.awt.Rectangle;

public interface Renderer {
   void renderTitle(Context var1, String var2, boolean var3);

   void renderBackground(Context var1, boolean var2);

   int getOffset();

   int getBottomBorder();

   int getBorder();

   Color getFontColor(boolean var1);

   static Color darker(Color var0) {
      int var1 = var0.getRed();
      int var2 = var0.getGreen();
      int var3 = var0.getBlue();
      var1 -= 64;
      var2 -= 64;
      var3 -= 64;
      if (var1 < 0) {
         var1 = 0;
      }

      if (var2 < 0) {
         var2 = 0;
      }

      if (var3 < 0) {
         var3 = 0;
      }

      return new Color(var1, var2, var3, var0.getAlpha());
   }

   Color getMainColor(boolean var1, boolean var2);

   void renderBorder(Context var1, boolean var2, boolean var3, boolean var4);

   void renderRect(Context var1, String var2, boolean var3, boolean var4, Rectangle var5, boolean var6);

   void overrideColorScheme(ColorScheme var1);

   int getRightBorder(boolean var1);

   int renderScrollBar(Context var1, boolean var2, boolean var3, boolean var4, int var5, int var6);

   int getLeftBorder(boolean var1);

   Color getBackgroundColor(boolean var1);

   static Color brighter(Color var0) {
      int var1 = var0.getRed();
      int var2 = var0.getGreen();
      int var3 = var0.getBlue();
      var1 += 64;
      var2 += 64;
      var3 += 64;
      if (var1 > 255) {
         var1 = 255;
      }

      if (var2 > 255) {
         var2 = 255;
      }

      if (var3 > 255) {
         var3 = 255;
      }

      return new Color(var1, var2, var3, var0.getAlpha());
   }

   void restoreColorScheme();

   int getHeight(boolean var1);

   void renderTitle(Context var1, String var2, boolean var3, boolean var4, boolean var5);

   ColorScheme getDefaultColorScheme();

   void renderTitle(Context var1, String var2, boolean var3, boolean var4);
}
