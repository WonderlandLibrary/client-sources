package cc.slack.utils.render;

import cc.slack.utils.client.mc;
import java.awt.Color;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public final class Render2DUtil extends mc {
   public static boolean mouseInArea(int mouseX, int mouseY, double x, double y, double width, double height) {
      return (double)mouseX >= x && (double)mouseX <= x + width && (double)mouseY >= y && (double)mouseY <= y + height;
   }

   public static void drawImage(ResourceLocation image, float x, float y, float width, float height, Color color) {
      mc.getMinecraft().getTextureManager().bindTexture(image);
      float f = 1.0F / width;
      float f2 = 1.0F / height;
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4f((float)color.getRed(), (float)color.getGreen(), (float)color.getBlue(), (float)color.getAlpha());
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
      worldrenderer.pos((double)x, (double)(y + height), 0.0D).tex(0.0D, (double)(height * f2)).endVertex();
      worldrenderer.pos((double)(x + width), (double)(y + height), 0.0D).tex((double)(width * f), (double)(height * f2)).endVertex();
      worldrenderer.pos((double)(x + width), (double)y, 0.0D).tex((double)(width * f), 0.0D).endVertex();
      worldrenderer.pos((double)x, (double)y, 0.0D).tex(0.0D, 0.0D).endVertex();
      tessellator.draw();
      GL11.glDisable(3042);
   }

   public void drawRect(int x, int y, int width, int height, int color) {
      Gui.drawRect(x, y, x + width, y + height, color);
   }

   public static void drawRoundedCornerRect(float x, float y, float x1, float y1, float radius, Color color) {
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      boolean hasCull = GL11.glIsEnabled(2884);
      GL11.glDisable(2884);
      glColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
      GL11.glBegin(9);
      float xRadius = (float)Math.min((double)(x1 - x) * 0.5D, (double)radius);
      float yRadius = (float)Math.min((double)(y1 - y) * 0.5D, (double)radius);
      polygonCircle(x + xRadius, y + yRadius, xRadius, yRadius, 180, 270);
      polygonCircle(x1 - xRadius, y + yRadius, xRadius, yRadius, 90, 180);
      polygonCircle(x1 - xRadius, y1 - yRadius, xRadius, yRadius, 0, 90);
      polygonCircle(x + xRadius, y1 - yRadius, xRadius, yRadius, 270, 360);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      setGlState(2884, hasCull);
   }

   public static void polygonCircle(float x, float y, float xRadius, float yRadius, int start, int end) {
      for(int i = end; i >= start; i -= 4) {
         GL11.glVertex2d((double)x + Math.sin((double)i * 3.141592653589793D / 180.0D) * (double)xRadius, (double)y + Math.cos((double)i * 3.141592653589793D / 180.0D) * (double)yRadius);
      }

   }

   public static void glColor(int red, int green, int blue, int alpha) {
      GlStateManager.color((float)red / 255.0F, (float)green / 255.0F, (float)blue / 255.0F, (float)alpha / 255.0F);
   }

   public static void setGlState(int cap, boolean state) {
      if (state) {
         GL11.glEnable(cap);
      } else {
         GL11.glDisable(cap);
      }

   }
}
