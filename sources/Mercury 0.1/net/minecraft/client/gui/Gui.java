/*
 * Decompiled with CFR 0.145.
 */
package net.minecraft.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

public class Gui {
    public static final ResourceLocation optionsBackground = new ResourceLocation("textures/gui/options_background.png");
    public static final ResourceLocation statIcons = new ResourceLocation("textures/gui/container/stats_icons.png");
    public static final ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");
    protected static float zLevel;
    private static final String __OBFID = "CL_00000662";

    protected void drawHorizontalLine(int startX, int endX, int y2, int color) {
        if (endX < startX) {
            int var5 = startX;
            startX = endX;
            endX = var5;
        }
        Gui.drawRect(startX, y2, endX + 1, y2 + 1, color);
    }

    protected void drawVerticalLine(int x2, int startY, int endY, int color) {
        if (endY < startY) {
            int var5 = startY;
            startY = endY;
            endY = var5;
        }
        Gui.drawRect(x2, startY + 1, x2 + 1, endY, color);
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
        float var11 = (float)(color >> 24 & 255) / 255.0f;
        float var6 = (float)(color >> 16 & 255) / 255.0f;
        float var7 = (float)(color >> 8 & 255) / 255.0f;
        float var8 = (float)(color & 255) / 255.0f;
        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer var10 = var9.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.func_179090_x();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(var6, var7, var8, var11);
        var10.startDrawingQuads();
        var10.addVertex(left, bottom, 0.0);
        var10.addVertex(right, bottom, 0.0);
        var10.addVertex(right, top, 0.0);
        var10.addVertex(left, top, 0.0);
        var9.draw();
        GlStateManager.func_179098_w();
        GlStateManager.disableBlend();
    }

    protected void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
        float var7 = (float)(startColor >> 24 & 255) / 255.0f;
        float var8 = (float)(startColor >> 16 & 255) / 255.0f;
        float var9 = (float)(startColor >> 8 & 255) / 255.0f;
        float var10 = (float)(startColor & 255) / 255.0f;
        float var11 = (float)(endColor >> 24 & 255) / 255.0f;
        float var12 = (float)(endColor >> 16 & 255) / 255.0f;
        float var13 = (float)(endColor >> 8 & 255) / 255.0f;
        float var14 = (float)(endColor & 255) / 255.0f;
        GlStateManager.func_179090_x();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator var15 = Tessellator.getInstance();
        WorldRenderer var16 = var15.getWorldRenderer();
        var16.startDrawingQuads();
        var16.func_178960_a(var8, var9, var10, var7);
        var16.addVertex(right, top, zLevel);
        var16.addVertex(left, top, zLevel);
        var16.func_178960_a(var12, var13, var14, var11);
        var16.addVertex(left, bottom, zLevel);
        var16.addVertex(right, bottom, zLevel);
        var15.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.func_179098_w();
    }

    public static void drawGradientRectPublic(int left, int top, int right, int bottom, int startColor, int endColor) {
        float var7 = (float)(startColor >> 24 & 255) / 255.0f;
        float var8 = (float)(startColor >> 16 & 255) / 255.0f;
        float var9 = (float)(startColor >> 8 & 255) / 255.0f;
        float var10 = (float)(startColor & 255) / 255.0f;
        float var11 = (float)(endColor >> 24 & 255) / 255.0f;
        float var12 = (float)(endColor >> 16 & 255) / 255.0f;
        float var13 = (float)(endColor >> 8 & 255) / 255.0f;
        float var14 = (float)(endColor & 255) / 255.0f;
        GlStateManager.func_179090_x();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator var15 = Tessellator.getInstance();
        WorldRenderer var16 = var15.getWorldRenderer();
        var16.startDrawingQuads();
        var16.func_178960_a(var8, var9, var10, var7);
        var16.addVertex(right, top, zLevel);
        var16.addVertex(left, top, zLevel);
        var16.func_178960_a(var12, var13, var14, var11);
        var16.addVertex(left, bottom, zLevel);
        var16.addVertex(right, bottom, zLevel);
        var15.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.func_179098_w();
    }

    public void drawCenteredString(FontRenderer fontRendererIn, String text, int x2, int y2, int color) {
        fontRendererIn.func_175063_a(text, x2 - fontRendererIn.getStringWidth(text) / 2, y2, color);
    }

    public static void drawString(FontRenderer fontRendererIn, String text, int x2, int y2, int color) {
        fontRendererIn.func_175063_a(text, x2, y2, color);
    }

    public void drawTexturedModalRect(int x2, int y2, int textureX, int textureY, int width, int height) {
        float var7 = 0.00390625f;
        float var8 = 0.00390625f;
        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer var10 = var9.getWorldRenderer();
        var10.startDrawingQuads();
        var10.addVertexWithUV(x2 + 0, y2 + height, zLevel, (float)(textureX + 0) * var7, (float)(textureY + height) * var8);
        var10.addVertexWithUV(x2 + width, y2 + height, zLevel, (float)(textureX + width) * var7, (float)(textureY + height) * var8);
        var10.addVertexWithUV(x2 + width, y2 + 0, zLevel, (float)(textureX + width) * var7, (float)(textureY + 0) * var8);
        var10.addVertexWithUV(x2 + 0, y2 + 0, zLevel, (float)(textureX + 0) * var7, (float)(textureY + 0) * var8);
        var9.draw();
    }

    public void func_175174_a(float p_175174_1_, float p_175174_2_, int p_175174_3_, int p_175174_4_, int p_175174_5_, int p_175174_6_) {
        float var7 = 0.00390625f;
        float var8 = 0.00390625f;
        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer var10 = var9.getWorldRenderer();
        var10.startDrawingQuads();
        var10.addVertexWithUV(p_175174_1_ + 0.0f, p_175174_2_ + (float)p_175174_6_, zLevel, (float)(p_175174_3_ + 0) * var7, (float)(p_175174_4_ + p_175174_6_) * var8);
        var10.addVertexWithUV(p_175174_1_ + (float)p_175174_5_, p_175174_2_ + (float)p_175174_6_, zLevel, (float)(p_175174_3_ + p_175174_5_) * var7, (float)(p_175174_4_ + p_175174_6_) * var8);
        var10.addVertexWithUV(p_175174_1_ + (float)p_175174_5_, p_175174_2_ + 0.0f, zLevel, (float)(p_175174_3_ + p_175174_5_) * var7, (float)(p_175174_4_ + 0) * var8);
        var10.addVertexWithUV(p_175174_1_ + 0.0f, p_175174_2_ + 0.0f, zLevel, (float)(p_175174_3_ + 0) * var7, (float)(p_175174_4_ + 0) * var8);
        var9.draw();
    }

    public void func_175175_a(int p_175175_1_, int p_175175_2_, TextureAtlasSprite p_175175_3_, int p_175175_4_, int p_175175_5_) {
        Tessellator var6 = Tessellator.getInstance();
        WorldRenderer var7 = var6.getWorldRenderer();
        var7.startDrawingQuads();
        var7.addVertexWithUV(p_175175_1_ + 0, p_175175_2_ + p_175175_5_, zLevel, p_175175_3_.getMinU(), p_175175_3_.getMaxV());
        var7.addVertexWithUV(p_175175_1_ + p_175175_4_, p_175175_2_ + p_175175_5_, zLevel, p_175175_3_.getMaxU(), p_175175_3_.getMaxV());
        var7.addVertexWithUV(p_175175_1_ + p_175175_4_, p_175175_2_ + 0, zLevel, p_175175_3_.getMaxU(), p_175175_3_.getMinV());
        var7.addVertexWithUV(p_175175_1_ + 0, p_175175_2_ + 0, zLevel, p_175175_3_.getMinU(), p_175175_3_.getMinV());
        var6.draw();
    }

    public static void drawModalRectWithCustomSizedTexture(int x2, int y2, float u2, float v2, int width, int height, float textureWidth, float textureHeight) {
        float var8 = 1.0f / textureWidth;
        float var9 = 1.0f / textureHeight;
        Tessellator var10 = Tessellator.getInstance();
        WorldRenderer var11 = var10.getWorldRenderer();
        var11.startDrawingQuads();
        var11.addVertexWithUV(x2, y2 + height, 0.0, u2 * var8, (v2 + (float)height) * var9);
        var11.addVertexWithUV(x2 + width, y2 + height, 0.0, (u2 + (float)width) * var8, (v2 + (float)height) * var9);
        var11.addVertexWithUV(x2 + width, y2, 0.0, (u2 + (float)width) * var8, v2 * var9);
        var11.addVertexWithUV(x2, y2, 0.0, u2 * var8, v2 * var9);
        var10.draw();
    }

    public static void drawScaledCustomSizeModalRect(int x2, int y2, float u2, float v2, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
        float var10 = 1.0f / tileWidth;
        float var11 = 1.0f / tileHeight;
        Tessellator var12 = Tessellator.getInstance();
        WorldRenderer var13 = var12.getWorldRenderer();
        var13.startDrawingQuads();
        var13.addVertexWithUV(x2, y2 + height, 0.0, u2 * var10, (v2 + (float)vHeight) * var11);
        var13.addVertexWithUV(x2 + width, y2 + height, 0.0, (u2 + (float)uWidth) * var10, (v2 + (float)vHeight) * var11);
        var13.addVertexWithUV(x2 + width, y2, 0.0, (u2 + (float)uWidth) * var10, v2 * var11);
        var13.addVertexWithUV(x2, y2, 0.0, u2 * var10, v2 * var11);
        var12.draw();
    }

    public static void drawRect(double x2, double y2, double d2, double e2, int color) {
        double var5;
        if (x2 < d2) {
            var5 = x2;
            x2 = d2;
            d2 = var5;
        }
        if (y2 < e2) {
            var5 = y2;
            y2 = e2;
            e2 = var5;
        }
        float var11 = (float)(color >> 24 & 255) / 255.0f;
        float var6 = (float)(color >> 16 & 255) / 255.0f;
        float var7 = (float)(color >> 8 & 255) / 255.0f;
        float var8 = (float)(color & 255) / 255.0f;
        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer var10 = var9.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.func_179090_x();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(var6, var7, var8, var11);
        var10.startDrawingQuads();
        var10.addVertex(x2, e2, 0.0);
        var10.addVertex(d2, e2, 0.0);
        var10.addVertex(d2, y2, 0.0);
        var10.addVertex(x2, y2, 0.0);
        var9.draw();
        GlStateManager.func_179098_w();
        GlStateManager.disableBlend();
    }
}

