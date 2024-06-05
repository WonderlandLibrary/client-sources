package net.minecraft.client.gui;

import java.awt.Color;


import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class Gui
{
    public static final ResourceLocation OPTIONS_BACKGROUND = new ResourceLocation("textures/gui/options_background.png");
    public static final ResourceLocation STAT_ICONS = new ResourceLocation("textures/gui/container/stats_icons.png");
    public static final ResourceLocation ICONS = new ResourceLocation("textures/gui/icons.png");
    public static float zLevel;   
    public static Gui guipyk = new Gui();
    public void drawBorderedRect(int x, int y, int x1, int y1, int size, int borderC, int insideC)
    {
    drawRect(x + size, y + size, x1 - size, y1 - size, insideC);
    drawRect(x + size, y + size, x1, y, borderC);
    drawRect(x, y, x + size, y1 - 1, borderC);
    drawRect(x1, y1 - 1, x1 - size, y + size, borderC);
    drawRect(x, y1 - size, x1, y1, borderC);
    }
    
    /**
     * 
     * Draws a thin horizontal line between two points.
     */
    protected void drawHorizontalLine(int startX, int endX, int y, int color)
    {
        if (endX < startX)
        {
            int i = startX;
            startX = endX;
            endX = i;
        }

        drawRect(startX, y, endX + 1, y + 1, color);
    }

    /**
     * Draw a 1 pixel wide vertical line. Args : x, y1, y2, color
     */
    protected void drawVerticalLine(int x, int startY, int endY, int color)
    {
        if (endY < startY)
        {
            int i = startY;
            startY = endY;
            endY = i;
        }

        drawRect(x, startY + 1, x + 1, endY, color);
    }

    /**
     * Draws a solid color rectangle with the specified coordinates and color.
     */
    public static void drawRect(double x, double y, double d, double e, int color)
    {
      if (x < d)
      {
        double var5 = x;
        x = d;
        d = var5;
      }
      if (y < e)
      {
        double var5 = y;
        y = e;
        e = var5;
      }

        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f, f1, f2, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos((double)x, (double)e, 0.0D).endVertex();
        bufferbuilder.pos((double)d, (double)e, 0.0D).endVertex();
        bufferbuilder.pos((double)d, (double)y, 0.0D).endVertex();
        bufferbuilder.pos((double)x, (double)y, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    /**
     * Draws a rectangle with a vertical gradient between the specified colors (ARGB format). Args : x1, y1, x2, y2,
     * topColor, bottomColor
     */
    public void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor)
    {

        float f = (float)(startColor >> 24 & 255) / 255.0F;
        float f1 = (float)(startColor >> 16 & 255) / 255.0F;
        float f2 = (float)(startColor >> 8 & 255) / 255.0F;
        float f3 = (float)(startColor & 255) / 255.0F;
        float f4 = (float)(endColor >> 24 & 255) / 255.0F;
        float f5 = (float)(endColor >> 16 & 255) / 255.0F;
        float f6 = (float)(endColor >> 8 & 255) / 255.0F;
        float f7 = (float)(endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)right, (double)top, (double)zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double)left, (double)top, (double)zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double)left, (double)bottom, (double)zLevel).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos((double)right, (double)bottom, (double)zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    /**
     * Renders the specified text to the screen, center-aligned. Args : renderer, string, x, y, color
     */
    public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color)
    {
        fontRendererIn.drawStringWithShadow(text, (float)(x - fontRendererIn.getStringWidth(text) / 2), (float)y, color);
    }

    /**
     * Renders the specified text to the screen. Args : renderer, string, x, y, color
     */
    public static void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color)
    {
        fontRendererIn.drawStringWithShadow(text, (float)x, (float)y, color);
    }

    /**
     * Draws a textured rectangle at the current z-value.
     */
    public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)(x + 0), (double)(y + height), (double)zLevel).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + height), (double)zLevel).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + 0), (double)zLevel).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(x + 0), (double)(y + 0), (double)zLevel).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
        tessellator.draw();
    }

    /**
     * Draws a textured rectangle using the texture currently bound to the TextureManager
     */
    public static void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)(xCoord + 0.0F), (double)(yCoord + (float)maxV), (double)zLevel).tex((double)((float)(minU + 0) * 0.00390625F), (double)((float)(minV + maxV) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(xCoord + (float)maxU), (double)(yCoord + (float)maxV), (double)zLevel).tex((double)((float)(minU + maxU) * 0.00390625F), (double)((float)(minV + maxV) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(xCoord + (float)maxU), (double)(yCoord + 0.0F), (double)zLevel).tex((double)((float)(minU + maxU) * 0.00390625F), (double)((float)(minV + 0) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(xCoord + 0.0F), (double)(yCoord + 0.0F), (double)zLevel).tex((double)((float)(minU + 0) * 0.00390625F), (double)((float)(minV + 0) * 0.00390625F)).endVertex();
        tessellator.draw();
    }

    /**
     * Draws a texture rectangle using the texture currently bound to the TextureManager
     */
    public void drawTexturedModalRect(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)(xCoord + 0), (double)(yCoord + heightIn), (double)this.zLevel).tex((double)textureSprite.getMinU(), (double)textureSprite.getMaxV()).endVertex();
        bufferbuilder.pos((double)(xCoord + widthIn), (double)(yCoord + heightIn), (double)this.zLevel).tex((double)textureSprite.getMaxU(), (double)textureSprite.getMaxV()).endVertex();
        bufferbuilder.pos((double)(xCoord + widthIn), (double)(yCoord + 0), (double)this.zLevel).tex((double)textureSprite.getMaxU(), (double)textureSprite.getMinV()).endVertex();
        bufferbuilder.pos((double)(xCoord + 0), (double)(yCoord + 0), (double)this.zLevel).tex((double)textureSprite.getMinU(), (double)textureSprite.getMinV()).endVertex();
        tessellator.draw();
    }

    /**
     * Draws a textured rectangle at z = 0. Args: x, y, u, v, width, height, textureWidth, textureHeight
     */
    public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight)
    {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)x, (double)(y + height), 0.0D).tex((double)(u * f), (double)((v + (float)height) * f1)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + height), 0.0D).tex((double)((u + (float)width) * f), (double)((v + (float)height) * f1)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)y, 0.0D).tex((double)((u + (float)width) * f), (double)(v * f1)).endVertex();
        bufferbuilder.pos((double)x, (double)y, 0.0D).tex((double)(u * f), (double)(v * f1)).endVertex();
        tessellator.draw();
    }

    /**
     * Draws a scaled, textured, tiled modal rect at z = 0. This method isn't used anywhere in vanilla code.
     */
    public static void drawScaledCustomSizeModalRect(float g, float h, float u, float v, int uWidth, int vHeight, double e, double d, float tileWidth, float tileHeight)
    {
        float f = 1.0F / tileWidth;
        float f1 = 1.0F / tileHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)g, (double)(h + d), 0.0D).tex((double)(u * f), (double)((v + (float)vHeight) * f1)).endVertex();
        bufferbuilder.pos((double)(g + e), (double)(h + d), 0.0D).tex((double)((u + (float)uWidth) * f), (double)((v + (float)vHeight) * f1)).endVertex();
        bufferbuilder.pos((double)(g + e), (double)h, 0.0D).tex((double)((u + (float)uWidth) * f), (double)(v * f1)).endVertex();
        bufferbuilder.pos((double)g, (double)h, 0.0D).tex((double)(u * f), (double)(v * f1)).endVertex();
        tessellator.draw();
    }

    public static void drawCircle(int xx, int yy, int radius, Color col) {
        int sections = 70;
        double dAngle = 6.283185307179586 / (double)sections;
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)2);
        int i = 0;
        while (i < sections) {
            float x = (float)((double)radius * Math.cos((double)((double)i * dAngle)));
            float y = (float)((double)radius * Math.sin((double)((double)i * dAngle)));
            GL11.glColor4f((float)((float)col.getRed() / 255.0f), (float)((float)col.getGreen() / 255.0f), (float)((float)col.getBlue() / 255.0f), (float)((float)col.getAlpha() / 255.0f));
            GL11.glVertex2f((float)((float)xx + x), (float)((float)yy + y));
            ++i;
        }
        GlStateManager.color((float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glPopMatrix();
    }

    public static void drawCircle(float xx, float yy, float radius, Color col) {
        int sections = 70;
        double dAngle = 6.283185307179586 / (double)sections;
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)1.0f);
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)2);
        int i = 0;
        while (i < sections) {
            float x = (float)((double)radius * Math.cos((double)((double)i * dAngle)));
            float y = (float)((double)radius * Math.sin((double)((double)i * dAngle)));
            GL11.glColor4f((float)((float)col.getRed() / 255.0f), (float)((float)col.getGreen() / 255.0f), (float)((float)col.getBlue() / 255.0f), (float)((float)col.getAlpha() / 255.0f));
            GL11.glVertex2f((float)(xx + x), (float)(yy + y));
            ++i;
        }
        GlStateManager.color((float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glPopMatrix();
    }

    public static void drawCircle(float xx, float yy, float radius, Color col, float width, float position, float round) {
        int sections = 100;
        double dAngle = (double)(round * 2.0f) * 3.141592653589793 / (double)sections;
        float x = 0.0f;
        float y = 0.0f;
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)width);
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)2);
        int i = (int)position;
        while ((float)i < position + (float)sections) {
            x = (float)((double)radius * Math.cos((double)((double)i * dAngle)));
            y = (float)((double)radius * Math.sin((double)((double)i * dAngle)));
            GL11.glColor4f((float)((float)col.getRed() / 255.0f), (float)((float)col.getGreen() / 255.0f), (float)((float)col.getBlue() / 255.0f), (float)((float)col.getAlpha() / 255.0f));
            GL11.glVertex2f((float)(xx + x), (float)(yy + y));
            ++i;
        }
        i = (int)(position + (float)sections);
        while (i > (int)position) {
            x = (float)((double)radius * Math.cos((double)((double)i * dAngle)));
            y = (float)((double)radius * Math.sin((double)((double)i * dAngle)));
            GL11.glColor4f((float)((float)col.getRed() / 255.0f), (float)((float)col.getGreen() / 255.0f), (float)((float)col.getBlue() / 255.0f), (float)((float)col.getAlpha() / 255.0f));
            GL11.glVertex2f((float)(xx + x), (float)(yy + y));
            --i;
        }
        GlStateManager.color((float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glPopMatrix();
    }

    public static void circle(float x, float y, float radius, int fill) {
        Gui.arc(x, y, 0.0f, 360.0f, radius, fill);
    }

    public static void circle(float x, float y, float radius, Color fill) {
        Gui.arc(x, y, 0.0f, 360.0f, radius, fill);
    }
    
    public static void arc(float x, float y, float start, float end, float radius, int color) {
        Gui.arcEllipse(x, y, start, end, radius, radius, color);
    }

    public static void arc(float x, float y, float start, float end, float radius, Color color) {
        Gui.arcEllipse(x, y, start, end, radius, radius, color);
    }
    
    public static void arcEllipse(float x, float y, float start, float end, float w, float h, int color) {
        float ldy;
        float ldx;
        float i;
        GlStateManager.color((float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)0.0f);
        float temp = 0.0f;
        if (start > end) {
            temp = end;
            end = start;
            start = temp;
        }
        float var11 = (float)(color >> 24 & 255) / 255.0f;
        float var6 = (float)(color >> 16 & 255) / 255.0f;
        float var7 = (float)(color >> 8 & 255) / 255.0f;
        float var8 = (float)(color & 255) / 255.0f;
        Tessellator var9 = Tessellator.getInstance();
        BufferBuilder var10 = var9.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.color((float)var6, (float)var7, (float)var8, (float)var11);
        if (var11 > 0.5f) {
            GL11.glEnable((int)2848);
            GL11.glLineWidth((float)2.0f);
            GL11.glBegin((int)3);
            i = end;
            while (i >= start) {
                ldx = (float)Math.cos((double)((double)i * 3.141592653589793 / 180.0)) * (w * 1.001f);
                ldy = (float)Math.sin((double)((double)i * 3.141592653589793 / 180.0)) * (h * 1.001f);
                GL11.glVertex2f((float)(x + ldx), (float)(y + ldy));
                i -= 4.0f;
            }
            GL11.glEnd();
            GL11.glDisable((int)2848);
        }
        GL11.glBegin((int)6);
        i = end;
        while (i >= start) {
            ldx = (float)Math.cos((double)((double)i * 3.141592653589793 / 180.0)) * w;
            ldy = (float)Math.sin((double)((double)i * 3.141592653589793 / 180.0)) * h;
            GL11.glVertex2f((float)(x + ldx), (float)(y + ldy));
            i -= 4.0f;
        }
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void arcEllipse(float x, float y, float start, float end, float w, float h, Color color) {
        float ldy;
        float ldx;
        float i;
        GlStateManager.color((float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)0.0f);
        float temp = 0.0f;
        if (start > end) {
            temp = end;
            end = start;
            start = temp;
        }
        Tessellator var9 = Tessellator.getInstance();
        BufferBuilder var10 = var9.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.color((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        if ((float)color.getAlpha() > 0.5f) {
            GL11.glEnable((int)2848);
            GL11.glLineWidth((float)2.0f);
            GL11.glBegin((int)3);
            i = end;
            while (i >= start) {
                ldx = (float)Math.cos((double)((double)i * 3.141592653589793 / 180.0)) * (w * 1.001f);
                ldy = (float)Math.sin((double)((double)i * 3.141592653589793 / 180.0)) * (h * 1.001f);
                GL11.glVertex2f((float)(x + ldx), (float)(y + ldy));
                i -= 4.0f;
            }
            GL11.glEnd();
            GL11.glDisable((int)2848);
        }
        GL11.glBegin((int)6);
        i = end;
        while (i >= start) {
            ldx = (float)Math.cos((double)((double)i * 3.141592653589793 / 180.0)) * w;
            ldy = (float)Math.sin((double)((double)i * 3.141592653589793 / 180.0)) * h;
            GL11.glVertex2f((float)(x + ldx), (float)(y + ldy));
            i -= 4.0f;
        }
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}