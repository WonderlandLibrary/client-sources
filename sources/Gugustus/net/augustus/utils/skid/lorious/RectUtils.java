package net.augustus.utils.skid.lorious;

import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class RectUtils {
    public static void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
        float f = (startColor >> 24 & 0xFF) / 255.0F;
        float f1 = (startColor >> 16 & 0xFF) / 255.0F;
        float f2 = (startColor >> 8 & 0xFF) / 255.0F;
        float f3 = (startColor & 0xFF) / 255.0F;
        float f4 = (endColor >> 24 & 0xFF) / 255.0F;
        float f5 = (endColor >> 16 & 0xFF) / 255.0F;
        float f6 = (endColor >> 8 & 0xFF) / 255.0F;
        float f7 = (endColor & 0xFF) / 255.0F;
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
        float f3 = (color >> 24 & 0xFF) / 255.0F;
        float f = (color >> 16 & 0xFF) / 255.0F;
        float f1 = (color >> 8 & 0xFF) / 255.0F;
        float f2 = (color & 0xFF) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
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
    }

    public static void drawRect(float left, float top, float right, float bottom, int color) {
        if (left < right) {
            float i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            float j = top;
            top = bottom;
            bottom = j;
        }
        float f3 = (color >> 24 & 0xFF) / 255.0F;
        float f = (color >> 16 & 0xFF) / 255.0F;
        float f1 = (color >> 8 & 0xFF) / 255.0F;
        float f2 = (color & 0xFF) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
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
    }

    public static void drawRect(double left, double top, double right, double bottom, int color) {
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
        float f3 = (color >> 24 & 0xFF) / 255.0F;
        float f = (color >> 16 & 0xFF) / 255.0F;
        float f1 = (color >> 8 & 0xFF) / 255.0F;
        float f2 = (color & 0xFF) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
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
    }

    public static void drawRoundedRect(int left, int top, int right, int bottom, int radius, int color) {
        left += radius;
        top += radius;
        bottom -= radius;
        right -= radius;
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
        float f3 = (color >> 24 & 0xFF) / 255.0F;
        float f = (color >> 16 & 0xFF) / 255.0F;
        float f1 = (color >> 8 & 0xFF) / 255.0F;
        float f2 = (color & 0xFF) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(6, DefaultVertexFormats.POSITION);
        for (int cornerId = 0; cornerId < 4; cornerId++) {
            int ky = (cornerId + 1) / 2 % 2;
            int kx = cornerId / 2;
            double x = (kx != 0) ? right : left;
            double y = (ky != 0) ? bottom : top;
            for (int a = 0; a <= 8; a++)
                worldrenderer.pos(x + Math.sin(0.19634954084936207D * a + Math.PI * cornerId / 2.0D) * radius, y + Math.cos(0.19634954084936207D * a + Math.PI * cornerId / 2.0D) * radius, 0.0D).endVertex();
        }
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRoundedRect(float left, float top, float right, float bottom, int radius, int color) {
        left += radius;
        top += radius;
        bottom -= radius;
        right -= radius;
        if (left < right) {
            float i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            float j = top;
            top = bottom;
            bottom = j;
        }
        float f3 = (color >> 24 & 0xFF) / 255.0F;
        float f = (color >> 16 & 0xFF) / 255.0F;
        float f1 = (color >> 8 & 0xFF) / 255.0F;
        float f2 = (color & 0xFF) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(6, DefaultVertexFormats.POSITION);
        for (int cornerId = 0; cornerId < 4; cornerId++) {
            int ky = (cornerId + 1) / 2 % 2;
            int kx = cornerId / 2;
            double x = (kx != 0) ? right : left;
            double y = (ky != 0) ? bottom : top;
            for (int a = 0; a <= 8; a++)
                worldrenderer.pos(x + Math.sin(0.19634954084936207D * a + Math.PI * cornerId / 2.0D) * radius, y + Math.cos(0.19634954084936207D * a + Math.PI * cornerId / 2.0D) * radius, 0.0D).endVertex();
        }
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawNovoRoundedRect(float x, float y, float width, float height, float radius, int color) {
        float x1 = x + width;
        float y1 = y + height;
        float f = (color >> 24 & 0xFF) / 255.0F;
        float f1 = (color >> 16 & 0xFF) / 255.0F;
        float f2 = (color >> 8 & 0xFF) / 255.0F;
        float f3 = (color & 0xFF) / 255.0F;
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        x *= 2.0F;
        y *= 2.0F;
        x1 *= 2.0F;
        y1 *= 2.0F;
        GL11.glDisable(3553);
        GL11.glColor4f(f1, f2, f3, f);
        GlStateManager.enableBlend();
        GL11.glEnable(2848);
        GL11.glBegin(9);
        double v = 0.017453292519943295D;
        int i;
        for (i = 0; i <= 90; i += 3)
            GL11.glVertex2d((x + radius + MathHelper.sin((float)(i * 0.017453292519943295D)) * radius * -1.0F), (y + radius + MathHelper.cos((float)(i * 0.017453292519943295D)) * radius * -1.0F));
        for (i = 90; i <= 180; i += 3)
            GL11.glVertex2d((x + radius + MathHelper.sin((float)(i * 0.017453292519943295D)) * radius * -1.0F), (y1 - radius + MathHelper.cos((float)(i * 0.017453292519943295D)) * radius * -1.0F));
        for (i = 0; i <= 90; i += 3)
            GL11.glVertex2d((x1 - radius + MathHelper.sin((float)(i * 0.017453292519943295D)) * radius), (y1 - radius + MathHelper.cos((float)(i * 0.017453292519943295D)) * radius));
        for (i = 90; i <= 180; i += 3)
            GL11.glVertex2d((x1 - radius + MathHelper.sin((float)(i * 0.017453292519943295D)) * radius), (y + radius + MathHelper.cos((float)(i * 0.017453292519943295D)) * radius));
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glScaled(2.0D, 2.0D, 2.0D);
        GL11.glPopAttrib();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void drawRoundedRect(double left, double top, double right, double bottom, double radius, int color) {
        left += radius;
        top += radius;
        bottom -= radius;
        right -= radius;
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
        float f3 = (color >> 24 & 0xFF) / 255.0F;
        float f = (color >> 16 & 0xFF) / 255.0F;
        float f1 = (color >> 8 & 0xFF) / 255.0F;
        float f2 = (color & 0xFF) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(6, DefaultVertexFormats.POSITION);
        for (int cornerId = 0; cornerId < 4; cornerId++) {
            int ky = (cornerId + 1) / 2 % 2;
            int kx = cornerId / 2;
            double x = (kx != 0) ? right : left;
            double y = (ky != 0) ? bottom : top;
            for (int a = 0; a <= 8; a++)
                worldrenderer.pos(x + Math.sin(0.19634954084936207D * a + Math.PI * cornerId / 2.0D) * radius, y + Math.cos(0.19634954084936207D * a + Math.PI * cornerId / 2.0D) * radius, 0.0D).endVertex();
        }
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    protected void drawGradientRect(float left, float top, float right, float bottom, int startColor, int endColor) {
        float f = (startColor >> 24 & 0xFF) / 255.0F;
        float f1 = (startColor >> 16 & 0xFF) / 255.0F;
        float f2 = (startColor >> 8 & 0xFF) / 255.0F;
        float f3 = (startColor & 0xFF) / 255.0F;
        float f4 = (endColor >> 24 & 0xFF) / 255.0F;
        float f5 = (endColor >> 16 & 0xFF) / 255.0F;
        float f6 = (endColor >> 8 & 0xFF) / 255.0F;
        float f7 = (endColor & 0xFF) / 255.0F;
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

    public static void drawGradientRect(double left, double top, double right, double bottom, int startColor, int endColor) {
        float f = (startColor >> 24 & 0xFF) / 255.0F;
        float f1 = (startColor >> 16 & 0xFF) / 255.0F;
        float f2 = (startColor >> 8 & 0xFF) / 255.0F;
        float f3 = (startColor & 0xFF) / 255.0F;
        float f4 = (endColor >> 24 & 0xFF) / 255.0F;
        float f5 = (endColor >> 16 & 0xFF) / 255.0F;
        float f6 = (endColor >> 8 & 0xFF) / 255.0F;
        float f7 = (endColor & 0xFF) / 255.0F;
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

    public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
        float f = (col1 >> 24 & 0xFF) / 255.0F;
        float f1 = (col1 >> 16 & 0xFF) / 255.0F;
        float f2 = (col1 >> 8 & 0xFF) / 255.0F;
        float f3 = (col1 & 0xFF) / 255.0F;
        float f4 = (col2 >> 24 & 0xFF) / 255.0F;
        float f5 = (col2 >> 16 & 0xFF) / 255.0F;
        float f6 = (col2 >> 8 & 0xFF) / 255.0F;
        float f7 = (col2 & 0xFF) / 255.0F;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBegin(7);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glVertex2d(left, top);
        GL11.glVertex2d(left, bottom);
        GL11.glColor4f(f5, f6, f7, f4);
        GL11.glVertex2d(right, bottom);
        GL11.glVertex2d(right, top);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }

    public static void drawCircle(float cx, float cy, float r, int c) {
        GL11.glPushMatrix();
        cx *= 2.0F;
        cy *= 2.0F;
        int num_segments = (int)(r * 3.0F);
        float f = (c >> 24 & 0xFF) / 255.0F;
        float f1 = (c >> 16 & 0xFF) / 255.0F;
        float f2 = (c >> 8 & 0xFF) / 255.0F;
        float f3 = (c & 0xFF) / 255.0F;
        float theta = (float)(6.2831852D / num_segments);
        float p = (float)Math.cos(theta);
        float s = (float)Math.sin(theta);
        float x = r *= 2.0F;
        float y = 0.0F;
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glBegin(2);
        int ii = 0;
        while (ii < num_segments) {
            GL11.glVertex2f(x + cx, y + cy);
            float t = x;
            x = p * x - s * y;
            y = s * t + p * y;
            ii++;
        }
        GL11.glEnd();
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }

    public static void drawGradientSide(double left, double top, double right, double bottom, int startColor, int endColor) {
        float f = (startColor >> 24 & 0xFF) / 255.0F;
        float f1 = (startColor >> 16 & 0xFF) / 255.0F;
        float f2 = (startColor >> 8 & 0xFF) / 255.0F;
        float f3 = (startColor & 0xFF) / 255.0F;
        float f4 = (endColor >> 24 & 0xFF) / 255.0F;
        float f5 = (endColor >> 16 & 0xFF) / 255.0F;
        float f6 = (endColor >> 8 & 0xFF) / 255.0F;
        float f7 = (endColor & 0xFF) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos(right, top, 0.0D).color(f5, f6, f7, f4).endVertex();
        worldrenderer.pos(left, top, 0.0D).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos(left, bottom, 0.0D).color(f1, f2, f3, f).endVertex();
        worldrenderer.pos(right, bottom, 0.0D).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawGradient(double left, double top, double right, double bottom, int col1, int col2, int col3, int col4) {
        float alphaCol1 = (col1 >> 24 & 0xFF) / 255.0F;
        float redCol1 = (col1 >> 16 & 0xFF) / 255.0F;
        float greenCol1 = (col1 >> 8 & 0xFF) / 255.0F;
        float blueCol1 = (col1 & 0xFF) / 255.0F;
        float alphaCol2 = (col2 >> 24 & 0xFF) / 255.0F;
        float redCol2 = (col2 >> 16 & 0xFF) / 255.0F;
        float greenCol2 = (col2 >> 8 & 0xFF) / 255.0F;
        float blueCol2 = (col2 & 0xFF) / 255.0F;
        float alphaCol3 = (col3 >> 24 & 0xFF) / 255.0F;
        float redCol3 = (col3 >> 16 & 0xFF) / 255.0F;
        float greenCol3 = (col3 >> 8 & 0xFF) / 255.0F;
        float blueCol3 = (col3 & 0xFF) / 255.0F;
        float alphaCol4 = (col4 >> 24 & 0xFF) / 255.0F;
        float redCol4 = (col4 >> 16 & 0xFF) / 255.0F;
        float greenCol4 = (col4 >> 8 & 0xFF) / 255.0F;
        float blueCol4 = (col4 & 0xFF) / 255.0F;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(redCol1, greenCol1, blueCol1, alphaCol1);
        GL11.glVertex2d(left, top);
        GL11.glColor4f(redCol2, greenCol2, blueCol2, alphaCol2);
        GL11.glVertex2d(left, bottom);
        GL11.glColor4f(redCol4, greenCol4, blueCol4, alphaCol4);
        GL11.glVertex2d(right, bottom);
        GL11.glColor4f(redCol3, greenCol3, blueCol3, alphaCol3);
        GL11.glVertex2d(right, top);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }

    public static void drawCustomRoundedRect(float x, float y, float x2, float y2, float round, int color) {
        x += (float)((round / 2.0F) + 0.5D);
        y += (float)((round / 2.0F) + 0.5D);
        x2 -= (float)((round / 2.0F) + 0.5D);
        y2 -= (float)((round / 2.0F) + 0.5D);
        drawRect(x, y, x2, y2, color);
        circle(x2 - round / 2.0F, y + round / 2.0F, round, color);
        circle(x + round / 2.0F, y2 - round / 2.0F, round, color);
        circle(x + round / 2.0F, y + round / 2.0F, round, color);
        circle(x2 - round / 2.0F, y2 - round / 2.0F, round, color);
        drawRect(x - round / 2.0F - 0.5F, y + round / 2.0F, x2, y2 - round / 2.0F, color);
        drawRect(x, y + round / 2.0F, x2 + round / 2.0F + 0.5F, y2 - round / 2.0F, color);
        drawRect(x + round / 2.0F, y - round / 2.0F - 0.5F, x2 - round / 2.0F, y2 - round / 2.0F, color);
        drawRect(x + round / 2.0F, y, x2 - round / 2.0F, y2 + round / 2.0F + 0.5F, color);
    }

    public static void circleThin(float x, float y, float radius, Color fill) {
        arc1(x, y, 0.0F, 360.0F, radius, fill);
    }

    public static void circle(float x, float y, float radius, int fill) {
        arc(x, y, 0.0F, 360.0F, radius, fill);
    }

    public static void circle(float x, float y, float radius, Color fill) {
        arc(x, y, 0.0F, 90.0F, radius, fill);
    }

    public static void arc(float x, float y, float start, float end, float radius, int color) {
        arcEllipse(x, y, start, end, radius, radius, color);
    }

    public static void arc1(float x, float y, float start, float end, float radius, Color color) {
        ar(x, y, start, end, radius, radius, color.getRGB());
    }

    public static void arc(float x, float y, float start, float end, float radius, Color color) {
        arcEllipse(x, y, start, end, radius, radius, color);
    }

    public static void ar(float x, float y, float start, float end, float w, float h, int color) {
        GlStateManager.color(0.0F, 0.0F, 0.0F);
        GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);
        float temp = 0.0F;
        if (start > end) {
            temp = end;
            end = start;
            start = temp;
        }
        float var11 = (color >> 24 & 0xFF) / 255.0F;
        float var12 = (color >> 16 & 0xFF) / 255.0F;
        float var13 = (color >> 8 & 0xFF) / 255.0F;
        float var14 = (color & 0xFF) / 255.0F;
        Tessellator var15 = Tessellator.getInstance();
        WorldRenderer var16 = var15.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(var12, var13, var14, var11);
        if (var11 > 0.5F) {
            GL11.glEnable(2848);
            GL11.glLineWidth(2.0F);
            GL11.glBegin(3);
            float f;
            for (f = end; f >= start; f -= 4.0F) {
                float ldx = (float)Math.cos(f * Math.PI / 180.0D) * w * 1.001F;
                float ldy = (float)Math.sin(f * Math.PI / 180.0D) * h * 1.001F;
                GL11.glVertex2f(x + ldx, y + ldy);
            }
            GL11.glEnd();
            GL11.glDisable(2848);
        }
        GL11.glBegin(6);
        float i;
        for (i = end; i >= start; i -= 4.0F) {
            float ldx = (float)Math.cos(i * Math.PI / 180.0D) * w;
            float ldy = (float)Math.sin(i * Math.PI / 180.0D) * h;
            GL11.glVertex2f(x + ldx, y + ldy);
        }
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void arcEllipse(float x, float y, float start, float end, float w, float h, int color) {
        GlStateManager.color(0.0F, 0.0F, 0.0F);
        GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);
        float temp = 0.0F;
        if (start > end) {
            temp = end;
            end = start;
            start = temp;
        }
        float var11 = (color >> 24 & 0xFF) / 255.0F;
        float var12 = (color >> 16 & 0xFF) / 255.0F;
        float var13 = (color >> 8 & 0xFF) / 255.0F;
        float var14 = (color & 0xFF) / 255.0F;
        Tessellator var15 = Tessellator.getInstance();
        WorldRenderer var16 = var15.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(var12, var13, var14, var11);
        if (var11 > 0.5F) {
            GL11.glEnable(2848);
            GL11.glLineWidth(2.0F);
            GL11.glBegin(3);
            float f;
            for (f = end; f >= start; f -= 4.0F) {
                float ldx = (float)Math.cos(f * Math.PI / 180.0D) * w * 1.001F;
                float ldy = (float)Math.sin(f * Math.PI / 180.0D) * h * 1.001F;
                GL11.glVertex2f(x + ldx, y + ldy);
            }
            GL11.glEnd();
            GL11.glDisable(2848);
        }
        GL11.glBegin(6);
        float i;
        for (i = end; i >= start; i -= 4.0F) {
            float ldx = (float)Math.cos(i * Math.PI / 180.0D) * w;
            float ldy = (float)Math.sin(i * Math.PI / 180.0D) * h;
            GL11.glVertex2f(x + ldx, y + ldy);
        }
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawARoundedRect(double x, double y, double width, double height, double radius, Color color) {
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        x *= 2.0D;
        y *= 2.0D;
        width *= 2.0D;
        height *= 2.0D;
        width += x;
        height += y;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
        GL11.glEnable(2848);
        GL11.glBegin(9);
        int i;
        for (i = 0; i <= 90; i++)
            GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D);
        for (i = 90; i <= 180; i++)
            GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, height - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D);
        for (i = 0; i <= 90; i++)
            GL11.glVertex2d(width - radius + Math.sin(i * Math.PI / 180.0D) * radius, height - radius + Math.cos(i * Math.PI / 180.0D) * radius);
        for (i = 90; i <= 180; i++)
            GL11.glVertex2d(width - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + radius + Math.cos(i * Math.PI / 180.0D) * radius);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glScaled(2.0D, 2.0D, 2.0D);
        GL11.glPopAttrib();
    }

    public static void arcEllipse(float x, float y, float start, float end, float w, float h, Color color) {
        GlStateManager.color(0.0F, 0.0F, 0.0F);
        GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);
        float temp = 0.0F;
        if (start > end) {
            temp = end;
            end = start;
            start = temp;
        }
        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer var10 = var9.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color
                .getAlpha() / 255.0F);
        if (color.getAlpha() > 0.5F) {
            GL11.glEnable(2848);
            GL11.glLineWidth(2.0F);
            GL11.glBegin(3);
            float f;
            for (f = end; f >= start; f -= 4.0F) {
                float ldx = (float)Math.cos(f * Math.PI / 180.0D) * w * 1.001F;
                float ldy = (float)Math.sin(f * Math.PI / 180.0D) * h * 1.001F;
                GL11.glVertex2f(x + ldx, y + ldy);
            }
            GL11.glEnd();
            GL11.glDisable(2848);
        }
        GL11.glBegin(6);
        float i;
        for (i = end; i >= start; i -= 4.0F) {
            float ldx = (float)Math.cos(i * Math.PI / 180.0D) * w;
            float ldy = (float)Math.sin(i * Math.PI / 180.0D) * h;
            GL11.glVertex2f(x + ldx, y + ldy);
        }
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawGradientSideways(float left, float top, float right, float bottom, int col1, int col2) {
        float f = (col1 >> 24 & 0xFF) / 255.0F;
        float f1 = (col1 >> 16 & 0xFF) / 255.0F;
        float f2 = (col1 >> 8 & 0xFF) / 255.0F;
        float f3 = (col1 & 0xFF) / 255.0F;
        float f4 = (col2 >> 24 & 0xFF) / 255.0F;
        float f5 = (col2 >> 16 & 0xFF) / 255.0F;
        float f6 = (col2 >> 8 & 0xFF) / 255.0F;
        float f7 = (col2 & 0xFF) / 255.0F;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glVertex2d(left, top);
        GL11.glVertex2d(left, bottom);
        GL11.glColor4f(f5, f6, f7, f4);
        GL11.glVertex2d(right, bottom);
        GL11.glVertex2d(right, top);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }

    public static void drawRoundedRect(double x, double y, double width, double height, float cornerRadius) {
        int slices = 10;
        drawFillRectangle(x + cornerRadius, y, width - (2.0F * cornerRadius), height);
        drawFillRectangle(x, y + cornerRadius, cornerRadius, height - (2.0F * cornerRadius));
        drawFillRectangle(x + width - cornerRadius, y + cornerRadius, cornerRadius, height - (2.0F * cornerRadius));
        drawCirclePart(x + cornerRadius, y + cornerRadius, -MathHelper.PI, -MathHelper.PId2, cornerRadius, 10);
        drawCirclePart(x + cornerRadius, y + height - cornerRadius, -MathHelper.PId2, 0.0F, cornerRadius, 10);
        drawCirclePart(x + width - cornerRadius, y + cornerRadius, MathHelper.PId2, MathHelper.PI, cornerRadius, 10);
        drawCirclePart(x + width - cornerRadius, y + height - cornerRadius, 0.0F, MathHelper.PId2, cornerRadius, 10);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GlStateManager.disableBlend();
    }

    public static void drawCirclePart(double x, double y, float fromAngle, float toAngle, float radius, int slices) {
        GlStateManager.enableBlend();
        GL11.glBegin(6);
        GL11.glVertex2d(x, y);
        float increment = (toAngle - fromAngle) / slices;
        for (int i = 0; i <= slices; i++) {
            float angle = fromAngle + i * increment;
            float dX = MathHelper.sin(angle);
            float dY = MathHelper.cos(angle);
            GL11.glVertex2d(x + (dX * radius), y + (dY * radius));
        }
        GL11.glEnd();
    }

    public static void color(int color) {
        float red = (color & 0xFF) / 255.0F;
        float green = (color >> 8 & 0xFF) / 255.0F;
        float blue = (color >> 16 & 0xFF) / 255.0F;
        float alpha = (color >> 24 & 0xFF) / 255.0F;
        GlStateManager.color(red, green, blue, alpha);
    }

    public static void colorRGBA(int color) {
        float a = (color >> 24 & 0xFF) / 255.0F;
        float r = (color >> 16 & 0xFF) / 255.0F;
        float g = (color >> 8 & 0xFF) / 255.0F;
        float b = (color & 0xFF) / 255.0F;
        GlStateManager.color(r, g, b, a);
    }

    public static void drawFillRectangle(double x, double y, double width, double height) {
        GlStateManager.enableBlend();
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glBegin(7);
        GL11.glVertex2d(x, y + height);
        GL11.glVertex2d(x + width, y + height);
        GL11.glVertex2d(x + width, y);
        GL11.glVertex2d(x, y);
        GL11.glEnd();
    }

    public static void drawGradientSideways(int left, int top, int right, int bottom, int col1, int col2) {
        float f = (col1 >> 24 & 0xFF) / 255.0F;
        float f1 = (col1 >> 16 & 0xFF) / 255.0F;
        float f2 = (col1 >> 8 & 0xFF) / 255.0F;
        float f3 = (col1 & 0xFF) / 255.0F;
        float f4 = (col2 >> 24 & 0xFF) / 255.0F;
        float f5 = (col2 >> 16 & 0xFF) / 255.0F;
        float f6 = (col2 >> 8 & 0xFF) / 255.0F;
        float f7 = (col2 & 0xFF) / 255.0F;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glVertex2d(left, top);
        GL11.glVertex2d(left, bottom);
        GL11.glColor4f(f5, f6, f7, f4);
        GL11.glVertex2d(right, bottom);
        GL11.glVertex2d(right, top);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }
}
