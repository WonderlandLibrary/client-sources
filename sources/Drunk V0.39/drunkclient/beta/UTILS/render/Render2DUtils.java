/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package drunkclient.beta.UTILS.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class Render2DUtils {
    public static void drawRect(float x, float y, float x1, float y1, int color) {
        Render2DUtils.enableGL2D();
        Render2DUtils.glColor(color);
        Render2DUtils.drawRect(x, y, x1, y1);
        Render2DUtils.disableGL2D();
    }

    public static void prepareScissorBox(double x, double y, double x2, double y2) {
        int factor = new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor();
        GL11.glScissor((int)((int)(x * (double)factor)), (int)((int)(((double)new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight() - y2) * (double)factor)), (int)((int)((x2 - x) * (double)factor)), (int)((int)((y2 - y) * (double)factor)));
    }

    public static void drawRect2(double left, double top, double right, double bottom, int color) {
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
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f4 = (float)(color >> 16 & 0xFF) / 255.0f;
        float f5 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f6 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f4, f5, f6, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0).endVertex();
        worldrenderer.pos(right, bottom, 0.0).endVertex();
        worldrenderer.pos(right, top, 0.0).endVertex();
        worldrenderer.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void enableGL2D() {
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDepthMask((boolean)true);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glHint((int)3155, (int)4354);
    }

    public static void disableGL2D() {
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)2848);
        GL11.glHint((int)3154, (int)4352);
        GL11.glHint((int)3155, (int)4352);
    }

    public static double interpolate(double old, double now, float partialTicks) {
        return old + (now - old) * (double)partialTicks;
    }

    public static void drawFace(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight, AbstractClientPlayer target) {
        try {
            ResourceLocation skin = target.getLocationSkin();
            Minecraft.getMinecraft().getTextureManager().bindTexture(skin);
            GL11.glEnable((int)3042);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            Gui.drawScaledCustomSizeModalRect(x, y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
            GL11.glDisable((int)3042);
            return;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void drawCircle(double d, double e, float r, int c) {
        double y2;
        double x2;
        int i;
        float f = (float)(c >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(c >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(c >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(c & 0xFF) / 255.0f;
        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GL11.glEnable((int)2848);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glColor4f((float)f2, (float)f3, (float)f4, (float)f);
        GL11.glBegin((int)6);
        for (i = 0; i <= 360; ++i) {
            x2 = Math.sin((double)i * Math.PI / 180.0) * (double)(r / 2.0f);
            y2 = Math.cos((double)i * Math.PI / 180.0) * (double)(r / 2.0f);
            GL11.glVertex2d((double)(d + (double)(r / 2.0f) + x2), (double)(e + (double)(r / 2.0f) + y2));
        }
        GL11.glEnd();
        GL11.glBegin((int)2);
        i = 0;
        while (true) {
            if (i > 360) {
                GL11.glEnd();
                GL11.glDisable((int)2848);
                GlStateManager.enableTexture2D();
                GlStateManager.disableBlend();
                GL11.glPopMatrix();
                return;
            }
            x2 = Math.sin((double)i * Math.PI / 180.0) * (double)(r / 2.0f);
            y2 = Math.cos((double)i * Math.PI / 180.0) * (double)(r / 2.0f);
            GL11.glVertex2d((double)(d + (double)(r / 2.0f) + x2), (double)(e + (double)(r / 2.0f) + y2));
            ++i;
        }
    }

    public static void glColor(int hex) {
        float alpha = (float)(hex >> 24 & 0xFF) / 255.0f;
        float red = (float)(hex >> 16 & 0xFF) / 255.0f;
        float green = (float)(hex >> 8 & 0xFF) / 255.0f;
        float blue = (float)(hex & 0xFF) / 255.0f;
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
    }

    public static void drawRect(float x, float y, float x1, float y1) {
        GL11.glBegin((int)7);
        GL11.glVertex2f((float)x, (float)y1);
        GL11.glVertex2f((float)x1, (float)y1);
        GL11.glVertex2f((float)x1, (float)y);
        GL11.glVertex2f((float)x, (float)y);
        GL11.glEnd();
    }
}

