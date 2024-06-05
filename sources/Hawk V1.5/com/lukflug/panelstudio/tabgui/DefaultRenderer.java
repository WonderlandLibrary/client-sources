package com.lukflug.panelstudio.tabgui;

import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.theme.ColorScheme;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class DefaultRenderer implements TabGUIRenderer {
   protected ColorScheme scheme;
   protected int border;
   protected int right;
   protected int height;
   protected int enter;
   protected int up;
   protected int down;
   protected int left;

   public boolean isUpKey(int var1) {
      return var1 == this.up;
   }

   public boolean isEscapeKey(int var1) {
      return var1 == this.left;
   }

   public boolean isDownKey(int var1) {
      return var1 == this.down;
   }

   public void renderBackground(Context var1, int var2, int var3) {
      Color var4 = this.scheme.getBackgroundColor();
      var4 = new Color(var4.getRed(), var4.getGreen(), var4.getBlue(), this.scheme.getOpacity());
      Color var5 = this.scheme.getOutlineColor();
      Color var6 = this.scheme.getActiveColor();
      var1.getInterface().fillRect(var1.getRect(), var4, var4, var4, var4);
      var1.getInterface().drawRect(var1.getRect(), var5, var5, var5, var5);
      Point var7 = var1.getPos();
      var7.translate(0, var2);
      Rectangle var8 = new Rectangle(var7, new Dimension(var1.getSize().width, var3));
      var1.getInterface().fillRect(var8, var6, var6, var6, var6);
      var1.getInterface().drawRect(var8, var5, var5, var5, var5);
   }

   public int getHeight() {
      return this.height;
   }

   public boolean isSelectKey(int var1) {
      return var1 == this.right || var1 == this.enter;
   }

   public void renderCaption(Context var1, String var2, int var3, int var4, boolean var5) {
      Color var6;
      if (var5) {
         var6 = this.scheme.getActiveColor();
      } else {
         var6 = this.scheme.getFontColor();
      }

      Point var7 = var1.getPos();
      var7.translate(0, var3 * var4);
      var1.getInterface().drawString(var7, var2, var6);
   }

   public DefaultRenderer(ColorScheme var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      this.scheme = var1;
      this.border = var3;
      this.height = var2;
      this.up = var4;
      this.down = var5;
      this.left = var6;
      this.right = var7;
      this.enter = var8;
   }

   public ColorScheme getColorScheme() {
      return this.scheme;
   }

   public int getBorder() {
      return this.border;
   }
}
