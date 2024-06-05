package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.Context;
import java.awt.Color;
import java.awt.Rectangle;

public abstract class RendererProxy implements Renderer {
   public void renderBorder(Context var1, boolean var2, boolean var3, boolean var4) {
      this.getRenderer().renderBorder(var1, var2, var3, var4);
   }

   public int getBottomBorder() {
      return this.getRenderer().getBottomBorder();
   }

   public Color getMainColor(boolean var1, boolean var2) {
      return this.getRenderer().getMainColor(var1, var2);
   }

   public int getBorder() {
      return this.getRenderer().getBorder();
   }

   public Color getFontColor(boolean var1) {
      return this.getRenderer().getFontColor(var1);
   }

   public Color getBackgroundColor(boolean var1) {
      return this.getRenderer().getBackgroundColor(var1);
   }

   public void renderTitle(Context var1, String var2, boolean var3, boolean var4) {
      this.getRenderer().renderTitle(var1, var2, var3, var4);
   }

   public void renderRect(Context var1, String var2, boolean var3, boolean var4, Rectangle var5, boolean var6) {
      this.getRenderer().renderRect(var1, var2, var3, var4, var5, var6);
   }

   public int getHeight(boolean var1) {
      return this.getRenderer().getHeight(var1);
   }

   public ColorScheme getDefaultColorScheme() {
      return this.getRenderer().getDefaultColorScheme();
   }

   public void renderTitle(Context var1, String var2, boolean var3) {
      this.getRenderer().renderTitle(var1, var2, var3);
   }

   protected abstract Renderer getRenderer();

   public void restoreColorScheme() {
      this.getRenderer().restoreColorScheme();
   }

   public int getOffset() {
      return this.getRenderer().getOffset();
   }

   public int getLeftBorder(boolean var1) {
      return this.getRenderer().getLeftBorder(var1);
   }

   public void overrideColorScheme(ColorScheme var1) {
      this.getRenderer().overrideColorScheme(var1);
   }

   public void renderTitle(Context var1, String var2, boolean var3, boolean var4, boolean var5) {
      this.getRenderer().renderTitle(var1, var2, var3, var4, var5);
   }

   public int getRightBorder(boolean var1) {
      return this.getRenderer().getRightBorder(var1);
   }

   public int renderScrollBar(Context var1, boolean var2, boolean var3, boolean var4, int var5, int var6) {
      return this.getRenderer().renderScrollBar(var1, var2, var3, var4, var5, var6);
   }

   public void renderBackground(Context var1, boolean var2) {
      this.getRenderer().renderBackground(var1, var2);
   }
}
