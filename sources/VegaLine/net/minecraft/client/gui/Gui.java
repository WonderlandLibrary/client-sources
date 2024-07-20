/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import ru.govno.client.newfont.CFontRenderer;
import ru.govno.client.utils.Render.GLUtil;
import ru.govno.client.utils.Render.RenderUtils;

public class Gui {
    public static final ResourceLocation OPTIONS_BACKGROUND = new ResourceLocation("textures/gui/options_background.png");
    public static final ResourceLocation STAT_ICONS = new ResourceLocation("textures/gui/container/stats_icons.png");
    public static final ResourceLocation ICONS = new ResourceLocation("textures/gui/icons.png");
    public float zLevel;

    protected void drawHorizontalLine(int startX, int endX, int y, int color) {
        if (endX < startX) {
            int i = startX;
            startX = endX;
            endX = i;
        }
        Gui.drawRect(startX, (double)y, (double)(endX + 1), (double)(y + 1), color);
    }

    protected void drawVerticalLine(int x, int startY, int endY, int color) {
        if (endY < startY) {
            int i = startY;
            startY = endY;
            endY = i;
        }
        Gui.drawRect(x, (double)(startY + 1), (double)(x + 1), (double)endY, color);
    }

    public static void drawRect2(double x, double y, double width, double height, int color) {
        RenderUtils.resetColor();
        GLUtil.setup2DRendering(() -> GLUtil.render(7, () -> {
            RenderUtils.color(color);
            GL11.glVertex2d(x, y);
            GL11.glVertex2d(x, y + height);
            GL11.glVertex2d(x + width, y + height);
            GL11.glVertex2d(x + width, y);
        }));
    }

    public static void drawRect(int left, double g, double d, double e, int color) {
        if ((double)left < d) {
            int i = left;
            left = (int)d;
            d = i;
        }
        if (g < e) {
            double j = g;
            g = e;
            e = j;
        }
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f, f1, f2, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(left, e, 0.0).endVertex();
        bufferbuilder.pos(d, e, 0.0).endVertex();
        bufferbuilder.pos(d, g, 0.0).endVertex();
        bufferbuilder.pos(left, g, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
        float f = (float)(startColor >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(startColor >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(startColor >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(startColor & 0xFF) / 255.0f;
        float f4 = (float)(endColor >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(endColor >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(endColor >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(endColor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(right, top, this.zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos(left, top, this.zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos(left, bottom, this.zLevel).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos(right, bottom, this.zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawStringWithShadow(text, x - fontRendererIn.getStringWidth(text) / 2, y, color);
    }

    public void drawCenteredString(CFontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawStringWithShadow(text, x - fontRendererIn.getStringWidth(text) / 2, y, color);
    }

    public void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawStringWithShadow(text, x, y, color);
    }

    public void drawString(CFontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawStringWithShadow(text, x, y, color);
    }

    public static void drawCircledModalRect(ResourceLocation location, double x, double y, int textureX, int textureY, double width, double height) {
        boolean alpha_test = GL11.glIsEnabled(3008);
        GL11.glEnable(3008);
        Minecraft.getMinecraft().getTextureManager().bindTexture(location);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, y + height, 0.0).tex((float)textureX * 0.00390625f, (float)((double)textureY + height) * 0.00390625f).endVertex();
        bufferbuilder.pos(x + width, y + height, 0.0).tex((float)((double)textureX + width) * 0.00390625f, (float)((double)textureY + height) * 0.00390625f).endVertex();
        bufferbuilder.pos(x + width, y, 0.0).tex((float)((double)textureX + width) * 0.00390625f, (float)textureY * 0.00390625f).endVertex();
        bufferbuilder.pos(x, y, 0.0).tex((float)textureX * 0.00390625f, (float)textureY * 0.00390625f).endVertex();
        tessellator.draw();
        if (alpha_test) {
            GL11.glEnable(3008);
        } else {
            GL11.glDisable(3008);
        }
    }

    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
        float f = 0.00390625f;
        float f1 = 0.00390625f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x + 0, y + height, this.zLevel).tex((float)(textureX + 0) * 0.00390625f, (float)(textureY + height) * 0.00390625f).endVertex();
        bufferbuilder.pos(x + width, y + height, this.zLevel).tex((float)(textureX + width) * 0.00390625f, (float)(textureY + height) * 0.00390625f).endVertex();
        bufferbuilder.pos(x + width, y + 0, this.zLevel).tex((float)(textureX + width) * 0.00390625f, (float)(textureY + 0) * 0.00390625f).endVertex();
        bufferbuilder.pos(x + 0, y + 0, this.zLevel).tex((float)(textureX + 0) * 0.00390625f, (float)(textureY + 0) * 0.00390625f).endVertex();
        tessellator.draw();
    }

    void Bcircle(float x, float y, float radius) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator.getBuffer();
        x += radius / 2.0f;
        y += radius / 2.0f;
        for (int i = 0; i <= 180; ++i) {
            double xPosOffset = Math.sin((double)i * Math.PI / 90.0);
            double yPosOffset = Math.cos((double)i * Math.PI / 90.0);
            worldrenderer.pos((double)x + xPosOffset * (double)radius, (double)y + yPosOffset * (double)radius, 0.0).tex(0.0, 0.0).endVertex();
        }
    }

    public void drawSkin(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
        float f = 1.0f / tileWidth;
        float f1 = 1.0f / tileHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator.getBuffer();
        worldrenderer.begin(6, DefaultVertexFormats.POSITION_TEX);
        float x2 = (float)width / 2.0f;
        float y2 = (float)height / 2.0f;
        float u2 = ((u + (float)uWidth) * f - u * f) / 2.0f;
        float v2 = ((v + (float)vHeight) * f1 - v * f1) / 2.0f;
        for (int i = 0; i <= 180; ++i) {
            double xPosOffset = Math.sin((double)i * Math.PI / 90.0);
            double yPosOffset = Math.cos((double)i * Math.PI / 90.0);
            worldrenderer.pos((double)((float)x + x2) + xPosOffset * (double)x2, (double)((float)y + y2) + yPosOffset * (double)y2, 0.0).tex((double)(u * f + u2) + xPosOffset * (double)u2, (double)(v * f1 + v2) + yPosOffset * (double)v2).endVertex();
        }
        tessellator.draw();
    }

    public void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV) {
        float f = 0.00390625f;
        float f1 = 0.00390625f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(xCoord + 0.0f, yCoord + (float)maxV, this.zLevel).tex((float)(minU + 0) * 0.00390625f, (float)(minV + maxV) * 0.00390625f).endVertex();
        bufferbuilder.pos(xCoord + (float)maxU, yCoord + (float)maxV, this.zLevel).tex((float)(minU + maxU) * 0.00390625f, (float)(minV + maxV) * 0.00390625f).endVertex();
        bufferbuilder.pos(xCoord + (float)maxU, yCoord + 0.0f, this.zLevel).tex((float)(minU + maxU) * 0.00390625f, (float)(minV + 0) * 0.00390625f).endVertex();
        bufferbuilder.pos(xCoord + 0.0f, yCoord + 0.0f, this.zLevel).tex((float)(minU + 0) * 0.00390625f, (float)(minV + 0) * 0.00390625f).endVertex();
        tessellator.draw();
    }

    public void drawTexturedModalRect(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(xCoord + 0, yCoord + heightIn, this.zLevel).tex(textureSprite.getMinU(), textureSprite.getMaxV()).endVertex();
        bufferbuilder.pos(xCoord + widthIn, yCoord + heightIn, this.zLevel).tex(textureSprite.getMaxU(), textureSprite.getMaxV()).endVertex();
        bufferbuilder.pos(xCoord + widthIn, yCoord + 0, this.zLevel).tex(textureSprite.getMaxU(), textureSprite.getMinV()).endVertex();
        bufferbuilder.pos(xCoord + 0, yCoord + 0, this.zLevel).tex(textureSprite.getMinU(), textureSprite.getMinV()).endVertex();
        tessellator.draw();
    }

    public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
        float f = 1.0f / textureWidth;
        float f1 = 1.0f / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, y + height, 0.0).tex(u * f, (v + (float)height) * f1).endVertex();
        bufferbuilder.pos(x + width, y + height, 0.0).tex((u + (float)width) * f, (v + (float)height) * f1).endVertex();
        bufferbuilder.pos(x + width, y, 0.0).tex((u + (float)width) * f, v * f1).endVertex();
        bufferbuilder.pos(x, y, 0.0).tex(u * f, v * f1).endVertex();
        tessellator.draw();
    }

    public static void drawModalRectWithCustomSizedTexture(float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight) {
        float f = 1.0f / textureWidth;
        float f1 = 1.0f / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, y + height, 0.0).tex(u * f, (v + height) * f1).endVertex();
        bufferbuilder.pos(x + width, y + height, 0.0).tex((u + width) * f, (v + height) * f1).endVertex();
        bufferbuilder.pos(x + width, y, 0.0).tex((u + width) * f, v * f1).endVertex();
        bufferbuilder.pos(x, y, 0.0).tex(u * f, v * f1).endVertex();
        tessellator.draw();
    }

    public static void drawScaledCustomSizeModalRect(float width2, float height2, float u, float v, float uWidth, float vHeight, float width, float height, float tileWidth, float tileHeight) {
        float f = 1.0f / tileWidth;
        float f1 = 1.0f / tileHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(width2, height2 + height, 0.0).tex(u * f, (v + vHeight) * f1).endVertex();
        bufferbuilder.pos(width2 + width, height2 + height, 0.0).tex((u + uWidth) * f, (v + vHeight) * f1).endVertex();
        bufferbuilder.pos(width2 + width, height2, 0.0).tex((u + uWidth) * f, v * f1).endVertex();
        bufferbuilder.pos(width2, height2, 0.0).tex(u * f, v * f1).endVertex();
        tessellator.draw();
    }

    public static void drawScaledCustomSizeModalRect2(float width2, float height2, float u, float v, float uWidth, float vHeight, float width, float height, float tileWidth, float tileHeight) {
        float f = 1.0f / tileWidth;
        float f1 = 1.0f / tileHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(width2, height2 + height, 0.0).tex(u * f, (v + vHeight) * f1).endVertex();
        bufferbuilder.pos(width2 + width, height2 + height, 0.0).tex((u + uWidth) * f, (v + vHeight) * f1).endVertex();
        bufferbuilder.pos(width2 + width, height2, 0.0).tex((u + uWidth) * f, v * f1).endVertex();
        bufferbuilder.pos(width2, height2, 0.0).tex(u * f, v * f1).endVertex();
        tessellator.draw();
    }

    public static void drawScaledCustomSizeModalRect3(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
        float f = 1.0f / tileWidth;
        float f1 = 1.0f / tileHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator.getBuffer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + height, 0.0).tex(u * f, (v + (float)vHeight) * f1).endVertex();
        worldrenderer.pos(x + width, y + height, 0.0).tex((u + (float)uWidth) * f, (v + (float)vHeight) * f1).endVertex();
        worldrenderer.pos(x + width, y, 0.0).tex((u + (float)uWidth) * f, v * f1).endVertex();
        worldrenderer.pos(x, y, 0.0).tex(u * f, v * f1).endVertex();
        tessellator.draw();
    }

    public static void drawScaledHead(ResourceLocation skin, int x, int y, int width, int height) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(skin);
        Gui.drawScaledCustomSizeModalRect(x, y, 8.0f, 8.0f, 8.0f, 8.0f, width, height, 64.0f, 64.0f);
        Gui.drawScaledCustomSizeModalRect(x, y, 40.0f, 8.0f, 8.0f, 8.0f, width, height, 64.0f, 64.0f);
    }

    public static void drawGradientRect(int left, int top, int right, int bottom, Color color, Color color2) {
    }

    public static void drawRect(double x, double y, double d, double e, int color) {
    }

    public static void drawRect(float f, float g, float d, float e, org.lwjgl.util.Color color) {
    }
}

