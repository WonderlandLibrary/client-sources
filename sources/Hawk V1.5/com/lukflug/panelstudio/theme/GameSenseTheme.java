package com.lukflug.panelstudio.theme;

import com.lukflug.panelstudio.Context;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class GameSenseTheme implements Theme {
   protected Renderer panelRenderer;
   protected ColorScheme scheme;
   protected Renderer containerRenderer;
   protected Renderer componentRenderer;

   public GameSenseTheme(ColorScheme var1, int var2, int var3, int var4) {
      this.scheme = var1;
      this.panelRenderer = new GameSenseTheme.ComponentRenderer(this, 0, var2, var3, var4);
      this.containerRenderer = new GameSenseTheme.ComponentRenderer(this, 1, var2, var3, var4);
      this.componentRenderer = new GameSenseTheme.ComponentRenderer(this, 2, var2, var3, var4);
   }

   public Renderer getPanelRenderer() {
      return this.panelRenderer;
   }

   public Renderer getContainerRenderer() {
      return this.containerRenderer;
   }

   public Renderer getComponentRenderer() {
      return this.componentRenderer;
   }

   protected class ComponentRenderer extends RendererBase {
      final GameSenseTheme this$0;
      protected final int level;
      protected final int border;

      public void renderBackground(Context var1, boolean var2) {
      }

      public Color getBackgroundColor(boolean var1) {
         return new Color(0, 0, 0, 0);
      }

      public void renderBorder(Context var1, boolean var2, boolean var3, boolean var4) {
         Color var5 = this.getDefaultColorScheme().getOutlineColor();
         if (this.level == 0) {
            var1.getInterface().fillRect(new Rectangle(var1.getPos(), new Dimension(var1.getSize().width, 1)), var5, var5, var5, var5);
            var1.getInterface().fillRect(new Rectangle(var1.getPos(), new Dimension(1, var1.getSize().height)), var5, var5, var5, var5);
            var1.getInterface().fillRect(new Rectangle(new Point(var1.getPos().x + var1.getSize().width - 1, var1.getPos().y), new Dimension(1, var1.getSize().height)), var5, var5, var5, var5);
         }

         if (this.level == 0 || var4) {
            var1.getInterface().fillRect(new Rectangle(new Point(var1.getPos().x, var1.getPos().y + var1.getSize().height - 1), new Dimension(var1.getSize().width, 1)), var5, var5, var5, var5);
            var1.getInterface().fillRect(new Rectangle(new Point(var1.getPos().x, var1.getPos().y + this.getHeight(var4) - 1), new Dimension(var1.getSize().width, 1)), var5, var5, var5, var5);
         }

      }

      public ComponentRenderer(GameSenseTheme var1, int var2, int var3, int var4, int var5) {
         super(var3 + 2 * var4, 0, 0, 0, var5);
         this.this$0 = var1;
         this.level = var2;
         this.border = var4;
      }

      public int renderScrollBar(Context var1, boolean var2, boolean var3, boolean var4, int var5, int var6) {
         if (var4) {
            int var7 = var1.getSize().height - this.getHeight(true);
            int var8 = (int)((double)var6 / (double)var5 * (double)var7);
            int var9 = (int)((double)(var6 + var7) / (double)var5 * (double)var7);
            Color var10 = this.getMainColor(var2, false);
            Color var11 = this.getMainColor(var2, true);
            var1.getInterface().fillRect(new Rectangle(new Point(var1.getPos().x + var1.getSize().width - this.getRightBorder(true), var1.getPos().y + this.getHeight(true)), new Dimension(this.getRightBorder(true), var8)), var10, var10, var10, var10);
            var1.getInterface().fillRect(new Rectangle(new Point(var1.getPos().x + var1.getSize().width - this.getRightBorder(true), var1.getPos().y + this.getHeight(true) + var8), new Dimension(this.getRightBorder(true), var9 - var8)), var11, var11, var11, var11);
            var1.getInterface().fillRect(new Rectangle(new Point(var1.getPos().x + var1.getSize().width - this.getRightBorder(true), var1.getPos().y + this.getHeight(true) + var9), new Dimension(this.getRightBorder(true), var1.getSize().height - this.getHeight(true) - var9)), var10, var10, var10, var10);
            Color var12 = this.getDefaultColorScheme().getOutlineColor();
            var1.getInterface().fillRect(new Rectangle(new Point(var1.getPos().x + var1.getSize().width - this.getRightBorder(true) - 1, var1.getPos().y + this.getHeight(true)), new Dimension(1, var1.getSize().height - this.getHeight(true))), var12, var12, var12, var12);
            if (var1.isClicked() && var1.getInterface().getMouse().x >= var1.getPos().x + var1.getSize().width - this.getRightBorder(true)) {
               return (int)((double)((var1.getInterface().getMouse().y - var1.getPos().y - this.getHeight(true)) * var5) / (double)var7 - (double)var7 / 2.0D);
            }
         }

         return var6;
      }

      public ColorScheme getDefaultColorScheme() {
         return this.this$0.scheme;
      }

      public Color getMainColor(boolean var1, boolean var2) {
         Color var3;
         if (var2) {
            var3 = this.getColorScheme().getActiveColor();
         } else {
            var3 = this.getColorScheme().getBackgroundColor();
         }

         if (!var2 && this.level < 2) {
            var3 = this.getColorScheme().getInactiveColor();
         }

         var3 = new Color(var3.getRed(), var3.getGreen(), var3.getBlue(), this.getColorScheme().getOpacity());
         return var3;
      }

      public void renderRect(Context var1, String var2, boolean var3, boolean var4, Rectangle var5, boolean var6) {
         Color var7 = this.getMainColor(var3, var4);
         var1.getInterface().fillRect(var5, var7, var7, var7, var7);
         if (var6) {
            Color var8;
            if (var1.isHovered()) {
               var8 = new Color(255, 255, 255, 64);
            } else {
               var8 = new Color(255, 255, 255, 0);
            }

            var1.getInterface().fillRect(var1.getRect(), var8, var8, var8, var8);
         }

         Point var9 = new Point(var5.getLocation());
         var9.translate(0, this.border);
         var1.getInterface().drawString(var9, var2, this.getFontColor(var3));
      }
   }
}
