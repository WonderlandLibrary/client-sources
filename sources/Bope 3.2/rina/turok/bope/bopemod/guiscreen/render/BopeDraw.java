package rina.turok.bope.bopemod.guiscreen.render;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.guiscreen.render.font.CFontRenderer;
import rina.turok.turok.Turok;
import rina.turok.turok.draw.TurokRenderHelp;
import rina.turok.turok.task.TurokRect;

public class BopeDraw {
   public static FontRenderer font_renderer;
   public static FontRenderer custom_font;
   public static CFontRenderer cfont_renderer;
   private float size;

   public BopeDraw(float size) {
      this.size = size;
   }

   public static void draw_rect(int x, int y, int w, int h, int r, int g, int b, int a) {
      Gui.drawRect(x, y, w, h, (new BopeDraw.TurokColor(r, g, b, a)).hex());
   }

   public static void draw_rect(int x, int y, int w, int h, int r, int g, int b, int a, int size, String type) {
      if (Arrays.asList(type.split("-")).contains("up")) {
         draw_rect(x, y, x + w, y + size, r, g, b, a);
      }

      if (Arrays.asList(type.split("-")).contains("down")) {
         draw_rect(x, y + h - size, x + w, y + h, r, g, b, a);
      }

      if (Arrays.asList(type.split("-")).contains("left")) {
         draw_rect(x, y, x + size, y + h, r, g, b, a);
      }

      if (Arrays.asList(type.split("-")).contains("right")) {
         draw_rect(x + w - size, y, x + w, y + h, r, g, b, a);
      }

   }

   public static void draw_rect(TurokRect rect, int r, int g, int b, int a) {
      Gui.drawRect(rect.get_x(), rect.get_y(), rect.get_screen_width(), rect.get_screen_height(), (new BopeDraw.TurokColor(r, g, b, a)).hex());
   }

   public static CFontRenderer get_cfont_renderer() {
      return cfont_renderer;
   }

   public static FontRenderer get_font_renderer(boolean smooth) {
      return smooth ? custom_font : font_renderer;
   }

   public static void draw_string(String string, int x, int y, int r, int g, int b, boolean smoth) {
      if (smoth) {
         cfont_renderer.drawStringWithShadow(string, (double)x, (double)y, (new BopeDraw.TurokColor(r, g, b)).hex());
      } else {
         font_renderer.drawStringWithShadow(string, (float)x, (float)y, (new BopeDraw.TurokColor(r, g, b)).hex());
      }

   }

   public static void draw_string(String string, int x, int y, int r, int g, int b, boolean shadow, boolean smoth) {
      if (smoth) {
         if (shadow) {
            cfont_renderer.drawStringWithShadow(string, (double)x, (double)y, (new BopeDraw.TurokColor(r, g, b)).hex());
         } else {
            cfont_renderer.drawString(string, (float)x, (float)y, (new BopeDraw.TurokColor(r, g, b)).hex());
         }
      } else if (shadow) {
         font_renderer.drawStringWithShadow(string, (float)x, (float)y, (new BopeDraw.TurokColor(r, g, b)).hex());
      } else {
         font_renderer.drawString(string, x, y, (new BopeDraw.TurokColor(r, g, b)).hex());
      }

   }

   public void draw_string_gl(String string, int x, int y, int r, int g, int b, int size) {
      Turok resize_gl = new Turok("Resize");
      resize_gl.resize(x, y, (float)size);
      font_renderer.drawString(string, x, y, (new BopeDraw.TurokColor(r, g, b)).hex());
      resize_gl.resize(x, y, (float)size, "end");
      GL11.glPushMatrix();
      GL11.glEnable(3553);
      GL11.glEnable(3042);
      GlStateManager.enableBlend();
      GL11.glPopMatrix();
      TurokRenderHelp.release_gl();
   }

   public int get_string_width(String string, boolean smoth) {
      FontRenderer fontRenderer = font_renderer;
      return smoth ? cfont_renderer.getStringWidth(string) : (int)((float)fontRenderer.getStringWidth(string) * this.size);
   }

   public int get_string_height(String string, boolean smoth) {
      FontRenderer fontRenderer = font_renderer;
      return smoth ? cfont_renderer.getStringHeight(string) : (int)((float)fontRenderer.FONT_HEIGHT * this.size);
   }

   public static int get_width() {
      ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
      return resolution.getScaledWidth();
   }

   public static int get_height() {
      ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
      return resolution.getScaledHeight();
   }

   static {
      font_renderer = Minecraft.getMinecraft().fontRenderer;
      custom_font = new BopeString(true);
      cfont_renderer = new CFontRenderer(new Font(Bope.font_name, 0, 16), true, false);
   }

   public static class TurokColor extends Color {
      public TurokColor(int r, int g, int b, int a) {
         super(r, g, b, a);
      }

      public TurokColor(int r, int g, int b) {
         super(r, g, b);
      }

      public int hex() {
         return this.getRGB();
      }
   }
}
