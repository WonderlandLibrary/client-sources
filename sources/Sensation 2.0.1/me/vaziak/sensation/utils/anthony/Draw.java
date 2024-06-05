package me.vaziak.sensation.utils.anthony;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;

/**
 * @author antja03
 */
public class Draw {

    /**
     * Draws a solid color rectangle with the specified coordinates and color (ARGB format). Args: x1, y1, x2, y2, color
     */
    public static void drawRectangle(double left, double top, double right, double bottom, int color) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }

        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }

        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).endVertex();
        worldrenderer.pos(right, top, 0.0D).endVertex();
        worldrenderer.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawGradientRect(double left, double top, double right, double bottom, int startColor, int endColor) {
        float f = (float) (startColor >> 24 & 255) / 255.0F;
        float f1 = (float) (startColor >> 16 & 255) / 255.0F;
        float f2 = (float) (startColor >> 8 & 255) / 255.0F;
        float f3 = (float) (startColor & 255) / 255.0F;
        float f4 = (float) (endColor >> 24 & 255) / 255.0F;
        float f5 = (float) (endColor >> 16 & 255) / 255.0F;
        float f6 = (float) (endColor >> 8 & 255) / 255.0F;
        float f7 = (float) (endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(right, top, 0.0D).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos(left, top, 0.0D).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos(left, bottom, 0.0D).color(f5, f6, f7, f4).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawBorderedRectangle(double left, double top, double right, double bottom, double borderWidth, int insideColor, int borderColor, boolean borderIncludedInBounds) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }

        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }

        float insideAlpha = (float) (insideColor >> 24 & 255) / 255.0F;
        float insideRed = (float) (insideColor >> 16 & 255) / 255.0F;
        float insideGreen = (float) (insideColor >> 8 & 255) / 255.0F;
        float insieBlue = (float) (insideColor & 255) / 255.0F;

        float borderAlpha = (float) (borderColor >> 24 & 255) / 255.0F;
        float borderRed = (float) (borderColor >> 16 & 255) / 255.0F;
        float borderGreen = (float) (borderColor >> 8 & 255) / 255.0F;
        float borderBlue = (float) (borderColor & 255) / 255.0F;

        if (insideAlpha > 0.0f) {
            GlStateManager.color(insideRed, insideGreen, insieBlue, insideAlpha);
            worldrenderer.begin(7, DefaultVertexFormats.POSITION);
            worldrenderer.pos(left, bottom, 0.0D).endVertex();
            worldrenderer.pos(right, bottom, 0.0D).endVertex();
            worldrenderer.pos(right, top, 0.0D).endVertex();
            worldrenderer.pos(left, top, 0.0D).endVertex();
            tessellator.draw();
        }

        if (borderAlpha > 0.0f) {
            GlStateManager.color(borderRed, borderGreen, borderBlue, borderAlpha);

            worldrenderer.begin(7, DefaultVertexFormats.POSITION);
            worldrenderer.pos(left - (borderIncludedInBounds ? 0 : borderWidth), bottom, 0.0D).endVertex();
            worldrenderer.pos(left - (borderIncludedInBounds ? borderWidth : 0), bottom, 0.0D).endVertex();
            worldrenderer.pos(left - (borderIncludedInBounds ? borderWidth : 0), top, 0.0D).endVertex();
            worldrenderer.pos(left - (borderIncludedInBounds ? 0 : borderWidth), top, 0.0D).endVertex();
            tessellator.draw();

            worldrenderer.begin(7, DefaultVertexFormats.POSITION);
            worldrenderer.pos(left, top - (borderIncludedInBounds ? borderWidth : 0), 0.0D).endVertex();
            worldrenderer.pos(right, top - (borderIncludedInBounds ? borderWidth : 0), 0.0D).endVertex();
            worldrenderer.pos(right, top - (borderIncludedInBounds ? 0 : borderWidth), 0.0D).endVertex();
            worldrenderer.pos(left, top - (borderIncludedInBounds ? 0 : borderWidth), 0.0D).endVertex();
            tessellator.draw();

            worldrenderer.begin(7, DefaultVertexFormats.POSITION);
            worldrenderer.pos(right + (borderIncludedInBounds ? borderWidth : 0), bottom, 0.0D).endVertex();
            worldrenderer.pos(right + (borderIncludedInBounds ? 0 : borderWidth), bottom, 0.0D).endVertex();
            worldrenderer.pos(right + (borderIncludedInBounds ? 0 : borderWidth), top, 0.0D).endVertex();
            worldrenderer.pos(right + (borderIncludedInBounds ? borderWidth : 0), top, 0.0D).endVertex();
            tessellator.draw();

            worldrenderer.begin(7, DefaultVertexFormats.POSITION);
            worldrenderer.pos(left, bottom - +(borderIncludedInBounds ? 0 : borderWidth), 0.0D).endVertex();
            worldrenderer.pos(right, bottom + (borderIncludedInBounds ? 0 : borderWidth), 0.0D).endVertex();
            worldrenderer.pos(right, bottom + (borderIncludedInBounds ? borderWidth : 0), 0.0D).endVertex();
            worldrenderer.pos(left, bottom + (borderIncludedInBounds ? borderWidth : 0), 0.0D).endVertex();
            tessellator.draw();
        }

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawImg(ResourceLocation loc, double posX, double posY, double width, double height) {

        GlStateManager.pushMatrix();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        Minecraft.getMinecraft().getTextureManager().bindTexture(loc);
        float f = 1.0F / (float) width;
        float f1 = 1.0F / (float) height;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(posX, (posY + height), 0.0D).tex((double) (0 * f), (double) ((0 + (float) height) * f1)).endVertex();
        worldrenderer.pos((posX + width), (posY + height), 0.0D).tex((double) ((0 + (float) width) * f), (double) ((0 + (float) height) * f1)).endVertex();
        worldrenderer.pos((posX + width), posY, 0.0D).tex((double) ((0 + (float) width) * f), (double) (0 * f1)).endVertex();
        worldrenderer.pos(posX, posY, 0.0D).tex((double) (0 * f), (double) (0 * f1)).endVertex();
        tessellator.draw();
        GlStateManager.popMatrix();
    }


    public static void glColor(float alpha, int redRGB, int greenRGB, int blueRGB) {
        float red = 0.003921569F * redRGB;
        float green = 0.003921569F * greenRGB;
        float blue = 0.003921569F * blueRGB;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void glColor(int hex) {
        float alpha = (hex >> 24 & 0xFF) / 255.0F;
        float red = (hex >> 16 & 0xFF) / 255.0F;
        float green = (hex >> 8 & 0xFF) / 255.0F;
        float blue = (hex & 0xFF) / 255.0F;
        GL11.glColor4f(red, green, blue, alpha);
    }
}
