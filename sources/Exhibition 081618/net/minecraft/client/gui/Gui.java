package net.minecraft.client.gui;

import exhibition.util.render.ColorContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class Gui {
   public static final ResourceLocation optionsBackground = new ResourceLocation("textures/gui/options_background.png");
   public static final ResourceLocation statIcons = new ResourceLocation("textures/gui/container/stats_icons.png");
   public static final ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");
   protected float zLevel;

   public static void drawBorderedRect(double x, double y, double x1, double y1, double width, int borderC) {
      drawRect(x, y + width, x + width, y1, borderC);
      drawRect(x1 - 1.0D, y + width, x1 + width - 1.0D, y1, borderC);
      drawRect(x, y, x1 + width - 1.0D, y + width, borderC);
      drawRect(x, y1, x1 + width - 1.0D, y1 + width, borderC);
   }

   protected void drawHorizontalLine(int startX, int endX, int y, int color) {
      if (endX < startX) {
         int var5 = startX;
         startX = endX;
         endX = var5;
      }

      drawRect(startX, y, endX + 1, y + 1, color);
   }

   public static void drawVerticalLine(int x, int startY, int endY, int color) {
      if (endY < startY) {
         int var5 = startY;
         startY = endY;
         endY = var5;
      }

      drawRect(x, startY + 1, x + 1, endY, color);
   }

   public static void drawScaledTexturedModalRect(double x, double y, double textureX, double textureY, double width, double height, float scale) {
      float f1 = 0.00390625F * scale;
      float f2 = 0.00390625F * scale;
      Tessellator tess = Tessellator.getInstance();
      WorldRenderer wr = tess.getWorldRenderer();
      wr.startDrawingQuads();
      wr.addVertexWithUV(x + 0.0D, y + height, 0.0D, (double)((float)(textureX + 0.0D) * f1), (double)((float)(textureY + height) * f2));
      wr.addVertexWithUV(x + width, y + height, 0.0D, (double)((float)(textureX + width) * f1), (double)((float)(textureY + height) * f2));
      wr.addVertexWithUV(x + width, y + 0.0D, 0.0D, (double)((float)(textureX + width) * f1), (double)((float)(textureY + 0.0D) * f2));
      wr.addVertexWithUV(x + 0.0D, y + 0.0D, 0.0D, (double)((float)(textureX + 0.0D) * f1), (double)((float)(textureY + 0.0D) * f2));
      tess.draw();
   }

   public static void fillHorizontalGrad(double x, double y, double x2, double y2, ColorContainer ColorContainer, ColorContainer c2) {
      float a1 = (float)c2.getAlpha() / 255.0F;
      float r1 = (float)c2.getRed() / 255.0F;
      float g1 = (float)c2.getGreen() / 255.0F;
      float b1 = (float)c2.getBlue() / 255.0F;
      float a2 = (float)ColorContainer.getAlpha() / 255.0F;
      float r2 = (float)ColorContainer.getRed() / 255.0F;
      float g2 = (float)ColorContainer.getGreen() / 255.0F;
      float b2 = (float)ColorContainer.getBlue() / 255.0F;
      Tessellator tess = Tessellator.getInstance();
      WorldRenderer wr = tess.getWorldRenderer();
      GlStateManager.disableTextures();
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.shadeModel(7425);
      wr.startDrawingQuads();
      wr.setColorRGBA(r1, g1, b1, a1);
      wr.addVertex(x + x2, y + y2, 0.0D);
      wr.addVertex(x + x2, y, 0.0D);
      wr.setColorRGBA(r2, g2, b2, a2);
      wr.addVertex(x, y, 0.0D);
      wr.addVertex(x, y + y2, 0.0D);
      tess.draw();
      GlStateManager.enableTextures();
   }

   public static void drawFilledCircle(double x, double y, double r, int c, int id) {
      float f = (float)(c >> 24 & 255) / 255.0F;
      float f1 = (float)(c >> 16 & 255) / 255.0F;
      float f2 = (float)(c >> 8 & 255) / 255.0F;
      float f3 = (float)(c & 255) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glColor4f(f1, f2, f3, f);
      GL11.glBegin(9);
      int i;
      double x2;
      double y2;
      if (id == 1) {
         GL11.glVertex2d(x, y);

         for(i = 0; i <= 90; ++i) {
            x2 = Math.sin((double)i * 3.141526D / 180.0D) * r;
            y2 = Math.cos((double)i * 3.141526D / 180.0D) * r;
            GL11.glVertex2d(x - x2, y - y2);
         }
      } else if (id == 2) {
         GL11.glVertex2d(x, y);

         for(i = 90; i <= 180; ++i) {
            x2 = Math.sin((double)i * 3.141526D / 180.0D) * r;
            y2 = Math.cos((double)i * 3.141526D / 180.0D) * r;
            GL11.glVertex2d(x - x2, y - y2);
         }
      } else if (id == 3) {
         GL11.glVertex2d(x, y);

         for(i = 270; i <= 360; ++i) {
            x2 = Math.sin((double)i * 3.141526D / 180.0D) * r;
            y2 = Math.cos((double)i * 3.141526D / 180.0D) * r;
            GL11.glVertex2d(x - x2, y - y2);
         }
      } else if (id == 4) {
         GL11.glVertex2d(x, y);

         for(i = 180; i <= 270; ++i) {
            x2 = Math.sin((double)i * 3.141526D / 180.0D) * r;
            y2 = Math.cos((double)i * 3.141526D / 180.0D) * r;
            GL11.glVertex2d(x - x2, y - y2);
         }
      } else {
         for(i = 0; i <= 360; ++i) {
            x2 = Math.sin((double)i * 3.141526D / 180.0D) * r;
            y2 = Math.cos((double)i * 3.141526D / 180.0D) * r;
            GL11.glVertex2f((float)(x - x2), (float)(y - y2));
         }
      }

      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
   }

   public static void drawRect(int left, int top, int right, int bottom, int color) {
      int var5;
      if (left < right) {
         var5 = left;
         left = right;
         right = var5;
      }

      if (top < bottom) {
         var5 = top;
         top = bottom;
         bottom = var5;
      }

      float var11 = (float)(color >> 24 & 255) / 255.0F;
      float var6 = (float)(color >> 16 & 255) / 255.0F;
      float var7 = (float)(color >> 8 & 255) / 255.0F;
      float var8 = (float)(color & 255) / 255.0F;
      Tessellator var9 = Tessellator.getInstance();
      WorldRenderer var10 = var9.getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTextures();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(var6, var7, var8, var11);
      var10.startDrawingQuads();
      var10.addVertex((double)left, (double)bottom, 0.0D);
      var10.addVertex((double)right, (double)bottom, 0.0D);
      var10.addVertex((double)right, (double)top, 0.0D);
      var10.addVertex((double)left, (double)top, 0.0D);
      var9.draw();
      GlStateManager.enableTextures();
      GlStateManager.disableBlend();
   }

   public void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
      float var7 = (float)(startColor >> 24 & 255) / 255.0F;
      float var8 = (float)(startColor >> 16 & 255) / 255.0F;
      float var9 = (float)(startColor >> 8 & 255) / 255.0F;
      float var10 = (float)(startColor & 255) / 255.0F;
      float var11 = (float)(endColor >> 24 & 255) / 255.0F;
      float var12 = (float)(endColor >> 16 & 255) / 255.0F;
      float var13 = (float)(endColor >> 8 & 255) / 255.0F;
      float var14 = (float)(endColor & 255) / 255.0F;
      GlStateManager.disableTextures();
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.shadeModel(7425);
      Tessellator var15 = Tessellator.getInstance();
      WorldRenderer var16 = var15.getWorldRenderer();
      var16.startDrawingQuads();
      var16.setColorRGBA(var8, var9, var10, var7);
      var16.addVertex((double)right, (double)top, (double)this.zLevel);
      var16.addVertex((double)left, (double)top, (double)this.zLevel);
      var16.setColorRGBA(var12, var13, var14, var11);
      var16.addVertex((double)left, (double)bottom, (double)this.zLevel);
      var16.addVertex((double)right, (double)bottom, (double)this.zLevel);
      var15.draw();
      GlStateManager.shadeModel(7424);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.enableTextures();
   }

   public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
      fontRendererIn.drawStringWithShadow(text, (float)(x - fontRendererIn.getStringWidth(text) / 2), (float)y, color);
   }

   public void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
      fontRendererIn.drawStringWithShadow(text, (float)x, (float)y, color);
   }

   public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
      float var7 = 0.00390625F;
      float var8 = 0.00390625F;
      Tessellator var9 = Tessellator.getInstance();
      WorldRenderer var10 = var9.getWorldRenderer();
      var10.startDrawingQuads();
      var10.addVertexWithUV((double)(x + 0), (double)(y + height), (double)this.zLevel, (double)((float)(textureX + 0) * var7), (double)((float)(textureY + height) * var8));
      var10.addVertexWithUV((double)(x + width), (double)(y + height), (double)this.zLevel, (double)((float)(textureX + width) * var7), (double)((float)(textureY + height) * var8));
      var10.addVertexWithUV((double)(x + width), (double)(y + 0), (double)this.zLevel, (double)((float)(textureX + width) * var7), (double)((float)(textureY + 0) * var8));
      var10.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)this.zLevel, (double)((float)(textureX + 0) * var7), (double)((float)(textureY + 0) * var8));
      var9.draw();
   }

   public void func_175174_a(float p_175174_1_, float p_175174_2_, int p_175174_3_, int p_175174_4_, int p_175174_5_, int p_175174_6_) {
      float var7 = 0.00390625F;
      float var8 = 0.00390625F;
      Tessellator var9 = Tessellator.getInstance();
      WorldRenderer var10 = var9.getWorldRenderer();
      var10.startDrawingQuads();
      var10.addVertexWithUV((double)(p_175174_1_ + 0.0F), (double)(p_175174_2_ + (float)p_175174_6_), (double)this.zLevel, (double)((float)(p_175174_3_ + 0) * var7), (double)((float)(p_175174_4_ + p_175174_6_) * var8));
      var10.addVertexWithUV((double)(p_175174_1_ + (float)p_175174_5_), (double)(p_175174_2_ + (float)p_175174_6_), (double)this.zLevel, (double)((float)(p_175174_3_ + p_175174_5_) * var7), (double)((float)(p_175174_4_ + p_175174_6_) * var8));
      var10.addVertexWithUV((double)(p_175174_1_ + (float)p_175174_5_), (double)(p_175174_2_ + 0.0F), (double)this.zLevel, (double)((float)(p_175174_3_ + p_175174_5_) * var7), (double)((float)(p_175174_4_ + 0) * var8));
      var10.addVertexWithUV((double)(p_175174_1_ + 0.0F), (double)(p_175174_2_ + 0.0F), (double)this.zLevel, (double)((float)(p_175174_3_ + 0) * var7), (double)((float)(p_175174_4_ + 0) * var8));
      var9.draw();
   }

   public void func_175175_a(int p_175175_1_, int p_175175_2_, TextureAtlasSprite p_175175_3_, int p_175175_4_, int p_175175_5_) {
      Tessellator var6 = Tessellator.getInstance();
      WorldRenderer var7 = var6.getWorldRenderer();
      var7.startDrawingQuads();
      var7.addVertexWithUV((double)(p_175175_1_ + 0), (double)(p_175175_2_ + p_175175_5_), (double)this.zLevel, (double)p_175175_3_.getMinU(), (double)p_175175_3_.getMaxV());
      var7.addVertexWithUV((double)(p_175175_1_ + p_175175_4_), (double)(p_175175_2_ + p_175175_5_), (double)this.zLevel, (double)p_175175_3_.getMaxU(), (double)p_175175_3_.getMaxV());
      var7.addVertexWithUV((double)(p_175175_1_ + p_175175_4_), (double)(p_175175_2_ + 0), (double)this.zLevel, (double)p_175175_3_.getMaxU(), (double)p_175175_3_.getMinV());
      var7.addVertexWithUV((double)(p_175175_1_ + 0), (double)(p_175175_2_ + 0), (double)this.zLevel, (double)p_175175_3_.getMinU(), (double)p_175175_3_.getMinV());
      var6.draw();
   }

   public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
      float var8 = 1.0F / textureWidth;
      float var9 = 1.0F / textureHeight;
      Tessellator var10 = Tessellator.getInstance();
      WorldRenderer var11 = var10.getWorldRenderer();
      var11.startDrawingQuads();
      var11.addVertexWithUV((double)x, (double)(y + height), 0.0D, (double)(u * var8), (double)((v + (float)height) * var9));
      var11.addVertexWithUV((double)(x + width), (double)(y + height), 0.0D, (double)((u + (float)width) * var8), (double)((v + (float)height) * var9));
      var11.addVertexWithUV((double)(x + width), (double)y, 0.0D, (double)((u + (float)width) * var8), (double)(v * var9));
      var11.addVertexWithUV((double)x, (double)y, 0.0D, (double)(u * var8), (double)(v * var9));
      var10.draw();
   }

   public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
      float var10 = 1.0F / tileWidth;
      float var11 = 1.0F / tileHeight;
      Tessellator var12 = Tessellator.getInstance();
      WorldRenderer var13 = var12.getWorldRenderer();
      var13.startDrawingQuads();
      var13.addVertexWithUV((double)x, (double)(y + height), 0.0D, (double)(u * var10), (double)((v + (float)vHeight) * var11));
      var13.addVertexWithUV((double)(x + width), (double)(y + height), 0.0D, (double)((u + (float)uWidth) * var10), (double)((v + (float)vHeight) * var11));
      var13.addVertexWithUV((double)(x + width), (double)y, 0.0D, (double)((u + (float)uWidth) * var10), (double)(v * var11));
      var13.addVertexWithUV((double)x, (double)y, 0.0D, (double)(u * var10), (double)(v * var11));
      var12.draw();
   }

   public static void drawRect(double x1, double y1, double x2, double y2, int color) {
      int x = (int)Math.round(x1);
      int y = (int)Math.round(y1);
      int w = (int)Math.round(x2);
      int h = (int)Math.round(y2);
      drawRect(x, y, w, h, color);
   }
}
