package net.minecraft.client.gui;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

public class Gui {
   protected float zLevel;
   public static final ResourceLocation optionsBackground = new ResourceLocation("textures/gui/options_background.png");
   public static final ResourceLocation statIcons = new ResourceLocation("textures/gui/container/stats_icons.png");
   public static final ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");
   private static final String __OBFID = "CL_00000662";

   protected void drawHorizontalLine(int var1, int var2, int var3, int var4) {
      if (var2 < var1) {
         int var5 = var1;
         var1 = var2;
         var2 = var5;
      }

      drawRect((double)var1, (double)var3, (double)(var2 + 1), (double)(var3 + 1), var4);
   }

   protected void drawGradientRect(int var1, int var2, int var3, int var4, int var5, int var6) {
      float var7 = (float)(var5 >> 24 & 255) / 255.0F;
      float var8 = (float)(var5 >> 16 & 255) / 255.0F;
      float var9 = (float)(var5 >> 8 & 255) / 255.0F;
      float var10 = (float)(var5 & 255) / 255.0F;
      float var11 = (float)(var6 >> 24 & 255) / 255.0F;
      float var12 = (float)(var6 >> 16 & 255) / 255.0F;
      float var13 = (float)(var6 >> 8 & 255) / 255.0F;
      float var14 = (float)(var6 & 255) / 255.0F;
      GlStateManager.func_179090_x();
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.shadeModel(7425);
      Tessellator var15 = Tessellator.getInstance();
      WorldRenderer var16 = var15.getWorldRenderer();
      var16.startDrawingQuads();
      var16.func_178960_a(var8, var9, var10, var7);
      var16.addVertex((double)var3, (double)var2, (double)this.zLevel);
      var16.addVertex((double)var1, (double)var2, (double)this.zLevel);
      var16.func_178960_a(var12, var13, var14, var11);
      var16.addVertex((double)var1, (double)var4, (double)this.zLevel);
      var16.addVertex((double)var3, (double)var4, (double)this.zLevel);
      var15.draw();
      GlStateManager.shadeModel(7424);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.func_179098_w();
   }

   public static void drawScaledCustomSizeModalRect(int var0, int var1, float var2, float var3, int var4, int var5, int var6, int var7, float var8, float var9) {
      float var10 = 1.0F / var8;
      float var11 = 1.0F / var9;
      Tessellator var12 = Tessellator.getInstance();
      WorldRenderer var13 = var12.getWorldRenderer();
      var13.startDrawingQuads();
      var13.addVertexWithUV((double)var0, (double)(var1 + var7), 0.0D, (double)(var2 * var10), (double)((var3 + (float)var5) * var11));
      var13.addVertexWithUV((double)(var0 + var6), (double)(var1 + var7), 0.0D, (double)((var2 + (float)var4) * var10), (double)((var3 + (float)var5) * var11));
      var13.addVertexWithUV((double)(var0 + var6), (double)var1, 0.0D, (double)((var2 + (float)var4) * var10), (double)(var3 * var11));
      var13.addVertexWithUV((double)var0, (double)var1, 0.0D, (double)(var2 * var10), (double)(var3 * var11));
      var12.draw();
   }

   public static void drawRect(double var0, double var2, double var4, double var6, int var8) {
      double var9;
      if (var0 < var4) {
         var9 = var0;
         var0 = var4;
         var4 = var9;
      }

      if (var2 < var6) {
         var9 = var2;
         var2 = var6;
         var6 = var9;
      }

      float var11 = (float)(var8 >> 24 & 255) / 255.0F;
      float var12 = (float)(var8 >> 16 & 255) / 255.0F;
      float var13 = (float)(var8 >> 8 & 255) / 255.0F;
      float var14 = (float)(var8 & 255) / 255.0F;
      Tessellator var15 = Tessellator.getInstance();
      WorldRenderer var16 = var15.getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.func_179090_x();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(var12, var13, var14, var11);
      var16.startDrawingQuads();
      var16.addVertex(var0, var6, 0.0D);
      var16.addVertex(var4, var6, 0.0D);
      var16.addVertex(var4, var2, 0.0D);
      var16.addVertex(var0, var2, 0.0D);
      var15.draw();
      GlStateManager.func_179098_w();
      GlStateManager.disableBlend();
   }

   public static void drawModalRectWithCustomSizedTexture(int var0, int var1, float var2, float var3, int var4, int var5, float var6, float var7) {
      float var8 = 1.0F / var6;
      float var9 = 1.0F / var7;
      Tessellator var10 = Tessellator.getInstance();
      WorldRenderer var11 = var10.getWorldRenderer();
      var11.startDrawingQuads();
      var11.addVertexWithUV((double)var0, (double)(var1 + var5), 0.0D, (double)(var2 * var8), (double)((var3 + (float)var5) * var9));
      var11.addVertexWithUV((double)(var0 + var4), (double)(var1 + var5), 0.0D, (double)((var2 + (float)var4) * var8), (double)((var3 + (float)var5) * var9));
      var11.addVertexWithUV((double)(var0 + var4), (double)var1, 0.0D, (double)((var2 + (float)var4) * var8), (double)(var3 * var9));
      var11.addVertexWithUV((double)var0, (double)var1, 0.0D, (double)(var2 * var8), (double)(var3 * var9));
      var10.draw();
   }

   protected void drawVerticalLine(int var1, int var2, int var3, int var4) {
      if (var3 < var2) {
         int var5 = var2;
         var2 = var3;
         var3 = var5;
      }

      drawRect((double)var1, (double)(var2 + 1), (double)(var1 + 1), (double)var3, var4);
   }

   public void drawTexturedModalRect(int var1, int var2, int var3, int var4, int var5, int var6) {
      float var7 = 0.00390625F;
      float var8 = 0.00390625F;
      Tessellator var9 = Tessellator.getInstance();
      WorldRenderer var10 = var9.getWorldRenderer();
      var10.startDrawingQuads();
      var10.addVertexWithUV((double)var1, (double)(var2 + var6), (double)this.zLevel, (double)((float)var3 * var7), (double)((float)(var4 + var6) * var8));
      var10.addVertexWithUV((double)(var1 + var5), (double)(var2 + var6), (double)this.zLevel, (double)((float)(var3 + var5) * var7), (double)((float)(var4 + var6) * var8));
      var10.addVertexWithUV((double)(var1 + var5), (double)var2, (double)this.zLevel, (double)((float)(var3 + var5) * var7), (double)((float)var4 * var8));
      var10.addVertexWithUV((double)var1, (double)var2, (double)this.zLevel, (double)((float)var3 * var7), (double)((float)var4 * var8));
      var9.draw();
   }

   public void func_175174_a(float var1, float var2, int var3, int var4, int var5, int var6) {
      float var7 = 0.00390625F;
      float var8 = 0.00390625F;
      Tessellator var9 = Tessellator.getInstance();
      WorldRenderer var10 = var9.getWorldRenderer();
      var10.startDrawingQuads();
      var10.addVertexWithUV((double)(var1 + 0.0F), (double)(var2 + (float)var6), (double)this.zLevel, (double)((float)var3 * var7), (double)((float)(var4 + var6) * var8));
      var10.addVertexWithUV((double)(var1 + (float)var5), (double)(var2 + (float)var6), (double)this.zLevel, (double)((float)(var3 + var5) * var7), (double)((float)(var4 + var6) * var8));
      var10.addVertexWithUV((double)(var1 + (float)var5), (double)(var2 + 0.0F), (double)this.zLevel, (double)((float)(var3 + var5) * var7), (double)((float)var4 * var8));
      var10.addVertexWithUV((double)(var1 + 0.0F), (double)(var2 + 0.0F), (double)this.zLevel, (double)((float)var3 * var7), (double)((float)var4 * var8));
      var9.draw();
   }

   public void drawCenteredString(FontRenderer var1, String var2, int var3, int var4, int var5) {
      var1.drawStringWithShadow(var2, (double)((float)(var3 - var1.getStringWidth(var2) / 2)), (double)((float)var4), var5);
   }

   public void drawString(FontRenderer var1, String var2, int var3, int var4, int var5) {
      var1.drawStringWithShadow(var2, (double)((float)var3), (double)((float)var4), var5);
   }

   public void func_175175_a(int var1, int var2, TextureAtlasSprite var3, int var4, int var5) {
      Tessellator var6 = Tessellator.getInstance();
      WorldRenderer var7 = var6.getWorldRenderer();
      var7.startDrawingQuads();
      var7.addVertexWithUV((double)var1, (double)(var2 + var5), (double)this.zLevel, (double)var3.getMinU(), (double)var3.getMaxV());
      var7.addVertexWithUV((double)(var1 + var4), (double)(var2 + var5), (double)this.zLevel, (double)var3.getMaxU(), (double)var3.getMaxV());
      var7.addVertexWithUV((double)(var1 + var4), (double)var2, (double)this.zLevel, (double)var3.getMaxU(), (double)var3.getMinV());
      var7.addVertexWithUV((double)var1, (double)var2, (double)this.zLevel, (double)var3.getMinU(), (double)var3.getMinV());
      var6.draw();
   }
}
