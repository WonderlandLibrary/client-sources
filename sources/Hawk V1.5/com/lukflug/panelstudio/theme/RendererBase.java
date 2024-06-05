package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.Context;
import java.awt.Color;

public abstract class RendererBase implements Renderer {
   protected final int right;
   protected final int offset;
   protected final int height;
   protected ColorScheme scheme = null;
   protected final int border;
   protected final int left;

   public void renderTitle(Context var1, String var2, boolean var3, boolean var4, boolean var5) {
      this.renderTitle(var1, var2, var3, var4);
   }

   public int getRightBorder(boolean var1) {
      return var1 ? this.right : 0;
   }

   public void renderTitle(Context var1, String var2, boolean var3, boolean var4) {
      this.renderRect(var1, var2, var3, var4, var1.getRect(), true);
   }

   public int getLeftBorder(boolean var1) {
      return var1 ? this.left : 0;
   }

   public void restoreColorScheme() {
      this.scheme = null;
   }

   public void renderTitle(Context var1, String var2, boolean var3) {
      this.renderTitle(var1, var2, var3, false);
   }

   public Color getFontColor(boolean var1) {
      return this.getColorScheme().getFontColor();
   }

   protected ColorScheme getColorScheme() {
      return this.scheme == null ? this.getDefaultColorScheme() : this.scheme;
   }

   public int getBottomBorder() {
      return 0;
   }

   public int getBorder() {
      return this.border;
   }

   public int getOffset() {
      return this.offset;
   }

   public int renderScrollBar(Context var1, boolean var2, boolean var3, boolean var4, int var5, int var6) {
      return var6;
   }

   public int getHeight(boolean var1) {
      return this.height;
   }

   public RendererBase(int var1, int var2, int var3, int var4, int var5) {
      this.height = var1;
      this.offset = var2;
      this.border = var3;
      this.left = var4;
      this.right = var5;
   }

   public void overrideColorScheme(ColorScheme var1) {
      this.scheme = var1;
   }
}
