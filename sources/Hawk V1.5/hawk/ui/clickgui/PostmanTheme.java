package hawk.ui.clickgui;

import com.lukflug.panelstudio.Context;
import com.lukflug.panelstudio.theme.ColorScheme;
import com.lukflug.panelstudio.theme.Renderer;
import com.lukflug.panelstudio.theme.RendererBase;
import com.lukflug.panelstudio.theme.Theme;
import hawk.util.JColor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class PostmanTheme implements Theme {
   protected Renderer containerRenderer;
   protected Renderer panelRenderer;
   protected ColorScheme scheme;
   protected Renderer componentRenderer;

   public Renderer getComponentRenderer() {
      return this.componentRenderer;
   }

   public Renderer getPanelRenderer() {
      return this.panelRenderer;
   }

   public Renderer getContainerRenderer() {
      return this.containerRenderer;
   }

   public PostmanTheme(ColorScheme var1, int var2, int var3) {
      this.scheme = var1;
      this.panelRenderer = new PostmanTheme.ComponentRenderer(this, 0, var2, var3);
      this.containerRenderer = new PostmanTheme.ComponentRenderer(this, 1, var2, var3);
      this.componentRenderer = new PostmanTheme.ComponentRenderer(this, 2, var2, var3);
   }

   protected class ComponentRenderer extends RendererBase {
      final PostmanTheme this$0;
      protected final int level;
      protected final int border;

      public Color getMainColor(boolean var1, boolean var2) {
         Color var3;
         if (var2 && this.level > 0) {
            var3 = this.getColorScheme().getActiveColor();
         } else {
            var3 = this.getColorScheme().getBackgroundColor();
         }

         if (!var2 && this.level < 2) {
            var3 = this.getColorScheme().getInactiveColor();
         }

         if (var2 && this.level < 1) {
            var3 = this.getColorScheme().getOutlineColor();
         }

         var3 = new Color(var3.getRed(), var3.getGreen(), var3.getBlue(), this.getColorScheme().getOpacity());
         return var3;
      }

      public void renderBackground(Context var1, boolean var2) {
         Color var3 = this.getBackgroundColor(var2);
         var1.getInterface().fillRect(var1.getRect(), var3, var3, var3, var3);
      }

      public Color getBackgroundColor(boolean var1) {
         Color var2 = this.getColorScheme().getInactiveColor();
         var2 = new Color(var2.getRed(), var2.getGreen(), var2.getBlue(), this.getColorScheme().getOpacity());
         return var2;
      }

      public ColorScheme getDefaultColorScheme() {
         return this.this$0.scheme;
      }

      public void renderBorder(Context var1, boolean var2, boolean var3, boolean var4) {
         Color var5 = this.getDefaultColorScheme().getOutlineColor();
         if (this.level == 1 && var4) {
            var1.getInterface().fillRect(new Rectangle(var1.getPos(), new Dimension(1, var1.getSize().height)), var5, var5, var5, var5);
            var1.getInterface().fillRect(new Rectangle(new Point(var1.getPos().x + var1.getSize().width - 1, var1.getPos().y), new Dimension(1, var1.getSize().height)), var5, var5, var5, var5);
         }

      }

      public void renderRect(Context var1, String var2, boolean var3, boolean var4, Rectangle var5, boolean var6) {
         Color var7 = this.getMainColor(var3, var4);
         var1.getInterface().fillRect(var5, var7, var7, var7, var7);
         if (var6) {
            Object var8;
            if (var1.isHovered()) {
               var8 = new JColor(114, 137, 218, 100);
            } else {
               var8 = new Color(255, 255, 255, 0);
            }

            var1.getInterface().fillRect(var1.getRect(), (Color)var8, (Color)var8, (Color)var8, (Color)var8);
         }

         Point var9 = new Point(var5.getLocation());
         var9.translate(0, this.border);
         var1.getInterface().drawString(var9, var2, this.getFontColor(var3));
      }

      public ComponentRenderer(PostmanTheme var1, int var2, int var3, int var4) {
         super(var3 + 1, 1, 1, 0, 0);
         this.this$0 = var1;
         this.level = var2;
         this.border = var4;
      }
   }
}
