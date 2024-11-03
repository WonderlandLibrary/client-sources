package net.minecraft.client.gui;

import net.augustus.utils.skid.tenacity.GLUtil;
import net.augustus.utils.skid.tenacity.RenderUtilTenacity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Gui {
   public static final ResourceLocation optionsBackground = new ResourceLocation("textures/gui/options_background.png");
   public static final ResourceLocation statIcons = new ResourceLocation("textures/gui/container/stats_icons.png");
   public static final ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");
   public static float zLevel;

   protected static void drawFloatRect(float v, float i, float v1, float i1, float i2) {
      drawRect((int)v, (int)i, (int)v1, (int)i1, (int)i2);
   }

   public static void drawRect(double posX, double v, double v1, double v2, int color) {
      drawRect((int)posX, (int)v, (int)v1, (int)v2, color);
   }

   public static void drawRect2(double x, double y, double width, double height, int color) {
      RenderUtilTenacity.resetColor();
      RenderUtilTenacity.setAlphaLimit(0);
      GLUtil.setup2DRendering(true);

      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();

      worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
      worldrenderer.pos(x, y, 0.0D).color(color).endVertex();
      worldrenderer.pos(x, y + height, 0.0D).color(color).endVertex();
      worldrenderer.pos(x + width, y + height, 0.0D).color(color).endVertex();
      worldrenderer.pos(x + width, y, 0.0D).color(color).endVertex();
      tessellator.draw();

      GLUtil.end2DRendering();
   }

   public static void drawRect2Uwe(double x, double y, double x2, double y2, int color) {
      RenderUtilTenacity.resetColor();
      RenderUtilTenacity.setAlphaLimit(0);
      GLUtil.setup2DRendering(true);

      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();

      worldrenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
      worldrenderer.pos(x, y, 0.0D).color(color).endVertex();
      worldrenderer.pos(x, y2, 0.0D).color(color).endVertex();
      worldrenderer.pos(x2, y2, 0.0D).color(color).endVertex();
      worldrenderer.pos(x2, y, 0.0D).color(color).endVertex();
      tessellator.draw();

      GLUtil.end2DRendering();
   }

    protected void drawHorizontalLine(int startX, int endX, int y, int color) {
      if (endX < startX) {
         int i = startX;
         startX = endX;
         endX = i;
      }

      drawRect(startX, y, endX + 1, y + 1, color);
   }

   protected void drawVerticalLine(int x, int startY, int endY, int color) {
      if (endY < startY) {
         int i = startY;
         startY = endY;
         endY = i;
      }

      drawRect(x, startY + 1, x + 1, endY, color);
   }

   public static void drawRect(int left, int top, int right, int bottom, int color) {
      if (left < right) {
         int i = left;
         left = right;
         right = i;
      }

      if (top < bottom) {
         int j = top;
         top = bottom;
         bottom = j;
      }

      float f3 = (float)(color >> 24 & 0xFF) / 255.0F;
      float f = (float)(color >> 16 & 0xFF) / 255.0F;
      float f1 = (float)(color >> 8 & 0xFF) / 255.0F;
      float f2 = (float)(color & 0xFF) / 255.0F;
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(f, f1, f2, f3);
      worldrenderer.begin(7, DefaultVertexFormats.POSITION);
      worldrenderer.pos(left, bottom, 0.0).endVertex();
      worldrenderer.pos(right, bottom, 0.0).endVertex();
      worldrenderer.pos(right, top, 0.0).endVertex();
      worldrenderer.pos(left, top, 0.0).endVertex();
      tessellator.draw();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
   }

   protected void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
      float f = (float)(startColor >> 24 & 0xFF) / 255.0F;
      float f1 = (float)(startColor >> 16 & 0xFF) / 255.0F;
      float f2 = (float)(startColor >> 8 & 0xFF) / 255.0F;
      float f3 = (float)(startColor & 0xFF) / 255.0F;
      float f4 = (float)(endColor >> 24 & 0xFF) / 255.0F;
      float f5 = (float)(endColor >> 16 & 0xFF) / 255.0F;
      float f6 = (float)(endColor >> 8 & 0xFF) / 255.0F;
      float f7 = (float)(endColor & 0xFF) / 255.0F;
      GlStateManager.disableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.shadeModel(7425);
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
      worldrenderer.pos(right, top, zLevel).color(f1, f2, f3, f).endVertex();
      worldrenderer.pos(left, top, zLevel).color(f1, f2, f3, f).endVertex();
      worldrenderer.pos(left, bottom, zLevel).color(f5, f6, f7, f4).endVertex();
      worldrenderer.pos(right, bottom, zLevel).color(f5, f6, f7, f4).endVertex();
      tessellator.draw();
      GlStateManager.shadeModel(7424);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.enableTexture2D();
   }

   public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
      fontRendererIn.drawStringWithShadow(text, (float)(x - fontRendererIn.getStringWidth(text) / 2), (float)y, color);
   }

   public void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
      fontRendererIn.drawStringWithShadow(text, (float)x, (float)y, color);
   }

   public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
      float f = 0.00390625F;
      float f1 = 0.00390625F;
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
      worldrenderer.pos(x, y + height, zLevel)
         .tex((float)(textureX) * f, (float)(textureY + height) * f1)
         .endVertex();
      worldrenderer.pos(x + width, y + height, zLevel)
         .tex((float)(textureX + width) * f, (float)(textureY + height) * f1)
         .endVertex();
      worldrenderer.pos(x + width, y, zLevel)
         .tex((float)(textureX + width) * f, (float)(textureY) * f1)
         .endVertex();
      worldrenderer.pos(x, y, zLevel)
         .tex((float)(textureX) * f, (float)(textureY) * f1)
         .endVertex();
      tessellator.draw();
   }

   public void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV) {
      float f = 0.00390625F;
      float f1 = 0.00390625F;
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
      worldrenderer.pos(xCoord + 0.0F, yCoord + (float)maxV, zLevel)
         .tex((float)(minU) * f, (float)(minV + maxV) * f1)
         .endVertex();
      worldrenderer.pos(xCoord + (float)maxU, yCoord + (float)maxV, zLevel)
         .tex((float)(minU + maxU) * f, (float)(minV + maxV) * f1)
         .endVertex();
      worldrenderer.pos(xCoord + (float)maxU, yCoord + 0.0F, zLevel)
         .tex((float)(minU + maxU) * f, (float)(minV) * f1)
         .endVertex();
      worldrenderer.pos(xCoord + 0.0F, yCoord + 0.0F, zLevel)
         .tex((float)(minU) * f, (float)(minV) * f1)
         .endVertex();
      tessellator.draw();
   }

   public void drawTexturedModalRect(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn) {
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
      worldrenderer.pos(xCoord, yCoord + heightIn, zLevel)
         .tex(textureSprite.getMinU(), textureSprite.getMaxV())
         .endVertex();
      worldrenderer.pos(xCoord + widthIn, yCoord + heightIn, zLevel)
         .tex(textureSprite.getMaxU(), textureSprite.getMaxV())
         .endVertex();
      worldrenderer.pos(xCoord + widthIn, yCoord, zLevel)
         .tex(textureSprite.getMaxU(), textureSprite.getMinV())
         .endVertex();
      worldrenderer.pos(xCoord, yCoord, zLevel)
         .tex(textureSprite.getMinU(), textureSprite.getMinV())
         .endVertex();
      tessellator.draw();
   }

   public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
      float f = 1.0F / textureWidth;
      float f1 = 1.0F / textureHeight;
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
      worldrenderer.pos(x, y + height, 0.0).tex(u * f, (v + (float)height) * f1).endVertex();
      worldrenderer.pos(x + width, y + height, 0.0).tex((u + (float)width) * f, (v + (float)height) * f1).endVertex();
      worldrenderer.pos(x + width, y, 0.0).tex((u + (float)width) * f, v * f1).endVertex();
      worldrenderer.pos(x, y, 0.0).tex(u * f, v * f1).endVertex();
      tessellator.draw();
   }

   public static void drawScaledCustomSizeModalRect(
      int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight
   ) {
      float f = 1.0F / tileWidth;
      float f1 = 1.0F / tileHeight;
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
      worldrenderer.pos(x, y + height, 0.0).tex(u * f, (v + (float)vHeight) * f1).endVertex();
      worldrenderer.pos(x + width, y + height, 0.0).tex((u + (float)uWidth) * f, (v + (float)vHeight) * f1).endVertex();
      worldrenderer.pos(x + width, y, 0.0).tex((u + (float)uWidth) * f, v * f1).endVertex();
      worldrenderer.pos(x, y, 0.0).tex(u * f, v * f1).endVertex();
      tessellator.draw();
   }
}
