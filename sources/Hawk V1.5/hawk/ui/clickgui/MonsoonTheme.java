package hawk.ui.clickgui;

import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.theme.ColorScheme;
import com.lukflug.panelstudio.theme.Renderer;
import com.lukflug.panelstudio.theme.RendererBase;
import com.lukflug.panelstudio.theme.Theme;
import hawk.util.JColor;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

public class MonsoonTheme implements Theme {
   protected ColorScheme scheme;
   protected final boolean gradient;
   protected Renderer componentRenderer;
   protected Renderer panelRenderer;

   public Renderer getPanelRenderer() {
      return this.panelRenderer;
   }

   public Renderer getContainerRenderer() {
      return this.componentRenderer;
   }

   public MonsoonTheme(ColorScheme var1, boolean var2, int var3, int var4) {
      this.scheme = var1;
      this.gradient = false;
      this.panelRenderer = new MonsoonTheme.ComponentRenderer(this, true, var3, var4);
      this.componentRenderer = new MonsoonTheme.ComponentRenderer(this, false, var3, var4);
   }

   public Renderer getComponentRenderer() {
      return this.componentRenderer;
   }

   protected class ComponentRenderer extends RendererBase {
      final MonsoonTheme this$0;
      protected final boolean panel;

      public void renderTitle(Context var1, String var2, boolean var3, boolean var4, boolean var5) {
         super.renderTitle(var1, var2, var3, var4, var5);
         if (!this.panel) {
            Color var6 = this.getFontColor(var4);
            Point var7;
            Point var8;
            Point var9;
            if (var5) {
               var9 = new Point(var1.getPos().x + var1.getSize().width - 2, var1.getPos().y + var1.getSize().height / 4);
               var8 = new Point(var1.getPos().x + var1.getSize().width - var1.getSize().height / 2, var1.getPos().y + var1.getSize().height * 3 / 4);
               var7 = new Point(var1.getPos().x + var1.getSize().width - var1.getSize().height + 2, var1.getPos().y + var1.getSize().height / 4);
            } else {
               var9 = new Point(var1.getPos().x + var1.getSize().width - var1.getSize().height * 3 / 4, var1.getPos().y + 2);
               var8 = new Point(var1.getPos().x + var1.getSize().width - var1.getSize().height / 4, var1.getPos().y + var1.getSize().height / 2);
               var7 = new Point(var1.getPos().x + var1.getSize().width - var1.getSize().height * 3 / 4, var1.getPos().y + var1.getSize().height - 2);
            }

            var1.getInterface().fillTriangle(var7, var8, var9, var6, var6, var6);
         }

      }

      public void renderBackground(Context var1, boolean var2) {
         if (this.panel) {
            Color var3 = this.getBackgroundColor(var2);
            var1.getInterface().fillRect(var1.getRect(), var3, var3, var3, var3);
         }

      }

      public void renderRect(Context var1, String var2, boolean var3, boolean var4, Rectangle var5, boolean var6) {
         if (this.panel || var4) {
            Color var7 = this.getMainColor(var3, true);
            Color var8 = this.getBackgroundColor(var3);
            if (this.this$0.gradient && this.panel) {
               var1.getInterface().fillRect(var5, var7, var7, var8, var8);
            } else {
               var1.getInterface().fillRect(var5, var7, var7, var7, var7);
            }
         }

         if (!this.panel && var6) {
            Object var9;
            if (var1.isHovered()) {
               var9 = new JColor(114, 137, 218, 100);
            } else {
               var9 = new Color(255, 255, 255, 0);
            }

            var1.getInterface().fillRect(var1.getRect(), (Color)var9, (Color)var9, (Color)var9, (Color)var9);
         }

         Point var10 = new Point(var5.getLocation());
         var10.translate(0, this.getOffset());
         var1.getInterface().drawString(var10, var2, this.getFontColor(var3));
      }

      public Color getBackgroundColor(boolean var1) {
         Color var2 = this.getColorScheme().getBackgroundColor();
         return new Color(var2.getRed(), var2.getGreen(), var2.getBlue(), this.getColorScheme().getOpacity());
      }

      public void renderBorder(Context var1, boolean var2, boolean var3, boolean var4) {
      }

      public Color getMainColor(boolean var1, boolean var2) {
         return var2 ? this.getColorScheme().getActiveColor() : new Color(0, 0, 0, 0);
      }

      public ColorScheme getDefaultColorScheme() {
         return this.this$0.scheme;
      }

      public ComponentRenderer(MonsoonTheme var1, boolean var2, int var3, int var4) {
         super(var3 + 2 * var4, var4, 0, 0, 0);
         this.this$0 = var1;
         this.panel = var2;
      }

      public void renderTitle(Context var1, String var2, boolean var3, boolean var4) {
         if (this.panel) {
            super.renderTitle(var1, var2, var3, var4);
         } else {
            Color var5;
            if (var1.isHovered()) {
               var5 = new Color(0, 0, 0, 64);
            } else {
               var5 = new Color(0, 0, 0, 0);
            }

            var1.getInterface().fillRect(var1.getRect(), var5, var5, var5, var5);
            Color var6 = this.getFontColor(var3);
            if (var4) {
               var6 = this.getMainColor(var3, true);
            }

            Point var7 = new Point(var1.getPos());
            var7.translate(0, this.getOffset());
            var1.getInterface().drawString(var7, var2, var6);
         }

      }
   }
}
