/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils;

import java.awt.Color;
import java.awt.Rectangle;
import me.thekirkayt.utils.MCStencil;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public class RenderUtil {
    public static Minecraft mc;

    public static void setColor(Color c) {
        GL11.glColor4d((double)((float)c.getRed() / 255.0f), (double)((float)c.getGreen() / 255.0f), (double)((float)c.getBlue() / 255.0f), (double)((float)c.getAlpha() / 255.0f));
    }

    public static void drawRect(int x, int y, int x1, int y1, Color color) {
        Gui.drawRect(x, y, x1, y1, color.getRGB());
    }

    public static final ScaledResolution getScaledRes() {
        ScaledResolution scaledRes = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        return scaledRes;
    }

    public static void drawEsp(EntityLivingBase ent, float pTicks, int hexColor, int hexColorIn) {
        if (!ent.isEntityAlive()) {
            return;
        }
        double x = RenderUtil.getDiff(ent.lastTickPosX, ent.posX, pTicks, RenderManager.renderPosX);
        double y = RenderUtil.getDiff(ent.lastTickPosY, ent.posY, pTicks, RenderManager.renderPosY);
        double z = RenderUtil.getDiff(ent.lastTickPosZ, ent.posZ, pTicks, RenderManager.renderPosZ);
        RenderUtil.boundingBox(ent, x, y, z, hexColor, hexColorIn);
    }

    public static void renderOne() {
        MCStencil.checkSetupFBO();
        GL11.glPushAttrib((int)1048575);
        GL11.glDisable((int)3008);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2896);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glLineWidth((float)3.0f);
        GL11.glEnable((int)2848);
        GL11.glEnable((int)2960);
        GL11.glClear((int)1024);
        GL11.glClearStencil((int)15);
        GL11.glStencilFunc((int)512, (int)1, (int)15);
        GL11.glStencilOp((int)7681, (int)7681, (int)7681);
        GL11.glPolygonMode((int)1032, (int)6913);
    }

    public static void renderTwo() {
        GL11.glStencilFunc((int)512, (int)0, (int)15);
        GL11.glStencilOp((int)7681, (int)7681, (int)7681);
        GL11.glPolygonMode((int)1032, (int)6914);
    }

    public static void renderThree() {
        GL11.glStencilFunc((int)514, (int)1, (int)15);
        GL11.glStencilOp((int)7680, (int)7680, (int)7680);
        GL11.glPolygonMode((int)1032, (int)6913);
    }

    public static void renderFive() {
        GL11.glPolygonOffset((float)1.0f, (float)2000000.0f);
        GL11.glDisable((int)10754);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)2960);
        GL11.glDisable((int)2848);
        GL11.glHint((int)3154, (int)4352);
        GL11.glEnable((int)3042);
        GL11.glEnable((int)2896);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)3008);
        GL11.glPopAttrib();
    }

    public static void boundingBox(Entity entity, double x, double y, double z, int color, int colorIn) {
        GlStateManager.pushMatrix();
        GL11.glLineWidth((float)1.0f);
        AxisAlignedBB var11 = entity.getEntityBoundingBox();
        AxisAlignedBB var12 = new AxisAlignedBB(var11.minX - entity.posX + x, var11.minY - entity.posY + y, var11.minZ - entity.posZ + z, var11.maxX - entity.posX + x, var11.maxY - entity.posY + y, var11.maxZ - entity.posZ + z);
        if (color != 0) {
            GlStateManager.disableDepth();
            RenderUtil.filledBox(var12, colorIn, true);
            RenderUtil.disableLighting();
            RenderGlobal.drawOutlinedBoundingBox(var12, color);
        }
        GlStateManager.popMatrix();
    }

    public static void disableLighting() {
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable((int)3553);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)3553);
    }

    public static void filledBox(AxisAlignedBB aa, int color, boolean shouldColor) {
        GlStateManager.pushMatrix();
        float var11 = (float)(color >> 24 & 255) / 255.0f;
        float var12 = (float)(color >> 16 & 255) / 255.0f;
        float var13 = (float)(color >> 8 & 255) / 255.0f;
        float var14 = (float)(color & 255) / 255.0f;
        Tessellator var15 = Tessellator.getInstance();
        WorldRenderer t = var15.getWorldRenderer();
        if (shouldColor) {
            GlStateManager.color(var12, var13, var14, var11);
        }
        int draw = 7;
        t.startDrawing(7);
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.minZ);
        t.addVertex(aa.maxX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.maxZ);
        t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        t.addVertex(aa.minX, aa.minY, aa.maxZ);
        t.addVertex(aa.minX, aa.maxY, aa.maxZ);
        var15.draw();
        t.startDrawing(7);
        t.addVertex(aa.maxX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.minZ);
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.addVertex(aa.minX, aa.maxY, aa.maxZ);
        t.addVertex(aa.minX, aa.minY, aa.maxZ);
        t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        t.addVertex(aa.maxX, aa.minY, aa.maxZ);
        var15.draw();
        t.startDrawing(7);
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        t.addVertex(aa.minX, aa.maxY, aa.maxZ);
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.addVertex(aa.minX, aa.maxY, aa.maxZ);
        t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        t.addVertex(aa.maxX, aa.maxY, aa.minZ);
        var15.draw();
        t.startDrawing(7);
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.maxZ);
        t.addVertex(aa.minX, aa.minY, aa.maxZ);
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.addVertex(aa.minX, aa.minY, aa.maxZ);
        t.addVertex(aa.maxX, aa.minY, aa.maxZ);
        t.addVertex(aa.maxX, aa.minY, aa.minZ);
        var15.draw();
        t.startDrawing(7);
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.addVertex(aa.minX, aa.minY, aa.maxZ);
        t.addVertex(aa.minX, aa.maxY, aa.maxZ);
        t.addVertex(aa.maxX, aa.minY, aa.maxZ);
        t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        t.addVertex(aa.maxX, aa.minY, aa.minZ);
        t.addVertex(aa.maxX, aa.maxY, aa.minZ);
        var15.draw();
        t.startDrawing(7);
        t.addVertex(aa.minX, aa.maxY, aa.maxZ);
        t.addVertex(aa.minX, aa.minY, aa.maxZ);
        t.addVertex(aa.minX, aa.maxY, aa.minZ);
        t.addVertex(aa.minX, aa.minY, aa.minZ);
        t.addVertex(aa.maxX, aa.maxY, aa.minZ);
        t.addVertex(aa.maxX, aa.minY, aa.minZ);
        t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
        t.addVertex(aa.maxX, aa.minY, aa.maxZ);
        var15.draw();
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();
    }

    private static double getDiff(double lastI, double i, float ticks, double ownI) {
        return lastI + (i - lastI) * (double)ticks - ownI;
    }

    public static void drawCubeOutline(double x, double y, double z, double x1, double y1, double z1) {
        Tessellator tes = Tessellator.getInstance();
        WorldRenderer wr = tes.getWorldRenderer();
        wr.startDrawing(3);
        wr.addVertex(x, y, z);
        wr.addVertex(x1, y, z);
        wr.addVertex(x1, y, z1);
        wr.addVertex(x, y, z1);
        wr.addVertex(x, y, z);
        tes.draw();
        wr.startDrawing(3);
        wr.addVertex(x, y1, z);
        wr.addVertex(x1, y1, z);
        wr.addVertex(x1, y1, z1);
        wr.addVertex(x, y1, z1);
        wr.addVertex(x, y1, z);
        tes.draw();
        wr.startDrawing(1);
        wr.addVertex(x, y, z);
        wr.addVertex(x, y1, z);
        wr.addVertex(x1, y, z);
        wr.addVertex(x1, y1, z);
        wr.addVertex(x1, y, z1);
        wr.addVertex(x1, y1, z1);
        wr.addVertex(x, y, z1);
        wr.addVertex(x, y1, z1);
        tes.draw();
    }

    public static void drawCube(double x, double y, double z, double x1, double y1, double z1) {
        Tessellator tes = Tessellator.getInstance();
        WorldRenderer wr = tes.getWorldRenderer();
        wr.startDrawingQuads();
        wr.addVertex(x, y, z);
        wr.addVertex(x, y1, z);
        wr.addVertex(x1, y, z);
        wr.addVertex(x1, y1, z);
        wr.addVertex(x1, y, z1);
        wr.addVertex(x1, y1, z1);
        wr.addVertex(x, y, z1);
        wr.addVertex(x, y1, z1);
        tes.draw();
        wr.startDrawingQuads();
        wr.addVertex(x1, y1, z);
        wr.addVertex(x1, y, z);
        wr.addVertex(x, y1, z);
        wr.addVertex(x, y, z);
        wr.addVertex(x, y1, z1);
        wr.addVertex(x, y, z1);
        wr.addVertex(x1, y1, z1);
        wr.addVertex(x1, y, z1);
        tes.draw();
        wr.startDrawingQuads();
        wr.addVertex(x, y1, z);
        wr.addVertex(x1, y1, z);
        wr.addVertex(x1, y1, z1);
        wr.addVertex(x, y1, z1);
        wr.addVertex(x, y1, z);
        wr.addVertex(x, y1, z1);
        wr.addVertex(x1, y1, z1);
        wr.addVertex(x1, y1, z);
        tes.draw();
        wr.startDrawingQuads();
        wr.addVertex(x, y, z);
        wr.addVertex(x1, y, z);
        wr.addVertex(x1, y, z1);
        wr.addVertex(x, y, z1);
        wr.addVertex(x, y, z);
        wr.addVertex(x, y, z1);
        wr.addVertex(x1, y, z1);
        wr.addVertex(x1, y, z);
        tes.draw();
        wr.startDrawingQuads();
        wr.addVertex(x, y, z);
        wr.addVertex(x, y1, z);
        wr.addVertex(x, y, z1);
        wr.addVertex(x, y1, z1);
        wr.addVertex(x1, y, z1);
        wr.addVertex(x1, y1, z1);
        wr.addVertex(x1, y, z);
        wr.addVertex(x1, y1, z);
        tes.draw();
        wr.startDrawingQuads();
        wr.addVertex(x, y1, z1);
        wr.addVertex(x, y, z1);
        wr.addVertex(x, y1, z);
        wr.addVertex(x, y, z);
        wr.addVertex(x1, y1, z);
        wr.addVertex(x1, y, z);
        wr.addVertex(x1, y1, z1);
        wr.addVertex(x1, y, z1);
        tes.draw();
    }

    public static void startDrawing() {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2896);
        GL11.glEnable((int)2848);
        GL11.glDepthMask((boolean)false);
        GL11.glDisable((int)2929);
    }

    public static void stopDrawing() {
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)2896);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }

    public static void drawRect(float x, float y, float x1, float y1, int color) {
        RenderUtil.enableGL2D();
        RenderUtil.glColor(color);
        RenderUtil.drawRect(x, y, x1, y1);
        RenderUtil.disableGL2D();
    }

    public static void drawBorderedRect(float x, float y, float x1, float y1, float width, int internalColor, int borderColor) {
        RenderUtil.enableGL2D();
        RenderUtil.glColor(internalColor);
        RenderUtil.drawRect(x + width, y + width, x1 - width, y1 - width);
        RenderUtil.glColor(borderColor);
        RenderUtil.drawRect(x + width, y, x1 - width, y + width);
        RenderUtil.drawRect(x, y, x + width, y1);
        RenderUtil.drawRect(x1 - width, y, x1, y1);
        RenderUtil.drawRect(x + width, y1 - width, x1 - width, y1);
        RenderUtil.disableGL2D();
    }

    public static void drawBorderedRectReliant(float x, float y, float x1, float y1, float lineWidth, int inside, int border) {
        RenderUtil.enableGL2D();
        RenderUtil.drawRect(x, y, x1, y1, inside);
        RenderUtil.glColor(border);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glLineWidth((float)lineWidth);
        GL11.glBegin((int)3);
        GL11.glVertex2f((float)x, (float)y);
        GL11.glVertex2f((float)x, (float)y1);
        GL11.glVertex2f((float)x1, (float)y1);
        GL11.glVertex2f((float)x1, (float)y);
        GL11.glVertex2f((float)x, (float)y);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        RenderUtil.disableGL2D();
    }

    public static void drawRect(Rectangle rectangle, int color) {
        RenderUtil.drawRect((float)rectangle.x, (float)rectangle.y, (float)(rectangle.x + rectangle.width), (float)(rectangle.y + rectangle.height), color);
    }

    public static void glColor(Color color) {
        GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
    }

    public static void glColor(int hex) {
        float alpha = (float)(hex >> 24 & 255) / 255.0f;
        float red = (float)(hex >> 16 & 255) / 255.0f;
        float green = (float)(hex >> 8 & 255) / 255.0f;
        float blue = (float)(hex & 255) / 255.0f;
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

    public static void glColor(float alpha, int redRGB, int greenRGB, int blueRGB) {
        float red = 0.003921569f * (float)redRGB;
        float green = 0.003921569f * (float)greenRGB;
        float blue = 0.003921569f * (float)blueRGB;
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
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

    public static void enableGL3D() {
        GL11.glDisable((int)3008);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glEnable((int)2884);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4353);
        GL11.glDisable((int)2896);
    }

    public static void enableGL3D(float lineWidth) {
        GL11.glDisable((int)3008);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glEnable((int)2884);
        Minecraft.getMinecraft().entityRenderer.disableLightmap();
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glHint((int)3155, (int)4354);
        GL11.glLineWidth((float)lineWidth);
    }

    public static void disableGL3D() {
        GL11.glEnable((int)2896);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3008);
        GL11.glDepthMask((boolean)true);
        GL11.glCullFace((int)1029);
    }

    public static void disableGL2D() {
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)2848);
        GL11.glHint((int)3154, (int)4352);
        GL11.glHint((int)3155, (int)4352);
    }

    public static double interp(double from, double to, double pct) {
        return from + (to - from) * pct;
    }

    public static void drawFineBorderedRect(int x, int y, int x1, int y1, int bord, int color) {
        GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        RenderUtil.drawRect((float)((x *= 2) + 1), (float)((y *= 2) + 1), (float)(x1 *= 2), (float)(y1 *= 2), color);
        RenderUtil.drawVerticalLine(x, y, y1, bord);
        RenderUtil.drawVerticalLine(x1, y, y1, bord);
        RenderUtil.drawHorizontalLine(x + 1, y, x1, bord);
        RenderUtil.drawHorizontalLine(x, y1, x1 + 1, bord);
        GL11.glScaled((double)2.0, (double)2.0, (double)2.0);
    }

    public static void drawVerticalLine(int x, int y, int height, int color) {
        RenderUtil.drawRect((float)x, (float)y, (float)(x + 1), (float)height, color);
    }

    public static void drawHorizontalLine(int x, int y, int width, int color) {
        RenderUtil.drawRect((float)x, (float)y, (float)width, (float)(y + 1), color);
    }

    public static void drawBorderedCircle(int x, int y, float radius, int outsideC, int insideC) {
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glPushMatrix();
        float scale = 0.1f;
        GL11.glScalef((float)0.1f, (float)0.1f, (float)0.1f);
        RenderUtil.drawCircle(x *= 10, y *= 10, radius *= 10.0f, insideC);
        RenderUtil.drawUnfilledCircle(x, y, radius, 1.0f, outsideC);
        GL11.glScalef((float)10.0f, (float)10.0f, (float)10.0f);
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
    }

    public static void drawUnfilledCircle(int x, int y, float radius, float lineWidth, int color) {
        float alpha = (float)(color >> 24 & 255) / 255.0f;
        float red = (float)(color >> 16 & 255) / 255.0f;
        float green = (float)(color >> 8 & 255) / 255.0f;
        float blue = (float)(color & 255) / 255.0f;
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        GL11.glLineWidth((float)lineWidth);
        GL11.glEnable((int)2848);
        GL11.glBegin((int)2);
        for (int i = 0; i <= 360; ++i) {
            GL11.glVertex2d((double)((double)x + Math.sin((double)i * 3.141526 / 180.0) * (double)radius), (double)((double)y + Math.cos((double)i * 3.141526 / 180.0) * (double)radius));
        }
        GL11.glEnd();
        GL11.glDisable((int)2848);
    }

    public static void drawCircle(int x, int y, float radius, int color) {
        float alpha = (float)(color >> 24 & 255) / 255.0f;
        float red = (float)(color >> 16 & 255) / 255.0f;
        float green = (float)(color >> 8 & 255) / 255.0f;
        float blue = (float)(color & 255) / 255.0f;
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        GL11.glBegin((int)9);
        for (int i = 0; i <= 360; ++i) {
            GL11.glVertex2d((double)((double)x + Math.sin((double)i * 3.141526 / 180.0) * (double)radius), (double)((double)y + Math.cos((double)i * 3.141526 / 180.0) * (double)radius));
        }
        GL11.glEnd();
    }

    public static void drawFilledBBESP(AxisAlignedBB axisalignedbb, int color) {
        GL11.glPushMatrix();
        float red = (float)(color >> 24 & 255) / 255.0f;
        float green = (float)(color >> 16 & 255) / 255.0f;
        float blue = (float)(color >> 8 & 255) / 255.0f;
        float alpha = (float)(color & 255) / 255.0f;
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        RenderUtil.drawFilledBox(axisalignedbb);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2896);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }

    public static void drawBoundingBoxESP(AxisAlignedBB axisalignedbb, float width, int color) {
        GL11.glPushMatrix();
        float red = (float)(color >> 24 & 255) / 255.0f;
        float green = (float)(color >> 16 & 255) / 255.0f;
        float blue = (float)(color >> 8 & 255) / 255.0f;
        float alpha = (float)(color & 255) / 255.0f;
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glLineWidth((float)width);
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        RenderUtil.drawOutlinedBox(axisalignedbb);
        GL11.glLineWidth((float)1.0f);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2896);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }

    public static void drawFilledBox(AxisAlignedBB boundingBox) {
        if (boundingBox == null) {
            return;
        }
        GL11.glBegin((int)7);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.minY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.maxY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.minY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.maxY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.minY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.maxY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.minY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.maxY, (double)boundingBox.maxZ);
        GL11.glEnd();
        GL11.glBegin((int)7);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.maxY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.minY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.maxY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.minY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.maxY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.minY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.maxY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.minY, (double)boundingBox.maxZ);
        GL11.glEnd();
        GL11.glBegin((int)7);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.minY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.maxY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.minY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.maxY, (double)boundingBox.minZ);
        GL11.glEnd();
        GL11.glBegin((int)7);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.maxY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.maxY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.maxY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.maxY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.maxY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.maxY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.maxY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.maxY, (double)boundingBox.minZ);
        GL11.glEnd();
        GL11.glBegin((int)7);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.minY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.minY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.minY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.minY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.minY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.minY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.minY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.minY, (double)boundingBox.minZ);
        GL11.glEnd();
        GL11.glBegin((int)7);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.minY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.maxY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.minY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.maxY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.minY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.maxY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.minY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.maxY, (double)boundingBox.minZ);
        GL11.glEnd();
        GL11.glBegin((int)7);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.maxY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.minY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.maxY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.minY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.maxY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.minY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.maxY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.minY, (double)boundingBox.maxZ);
        GL11.glEnd();
    }

    public static void drawOutlinedBox(AxisAlignedBB boundingBox) {
        if (boundingBox == null) {
            return;
        }
        GL11.glBegin((int)3);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.minY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.minY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.minY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.minY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.minY, (double)boundingBox.minZ);
        GL11.glEnd();
        GL11.glBegin((int)3);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.maxY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.maxY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.maxY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.maxY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.maxY, (double)boundingBox.minZ);
        GL11.glEnd();
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.minY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.maxY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.minY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.maxY, (double)boundingBox.minZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.minY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.maxX, (double)boundingBox.maxY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.minY, (double)boundingBox.maxZ);
        GL11.glVertex3d((double)boundingBox.minX, (double)boundingBox.maxY, (double)boundingBox.maxZ);
        GL11.glEnd();
    }

    public static void drawGradientBorderedRectReliant(float x, float y, float x1, float y1, float lineWidth, int border, int bottom, int top) {
        RenderUtil.enableGL2D();
        RenderUtil.drawGradientRect(x, y, x1, y1, top, bottom);
        RenderUtil.glColor(border);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glLineWidth((float)lineWidth);
        GL11.glBegin((int)3);
        GL11.glVertex2f((float)x, (float)y);
        GL11.glVertex2f((float)x, (float)y1);
        GL11.glVertex2f((float)x1, (float)y1);
        GL11.glVertex2f((float)x1, (float)y);
        GL11.glVertex2f((float)x, (float)y);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        RenderUtil.disableGL2D();
    }

    public static void drawGradientRect(float x, float y, float x1, float y1, int topColor, int bottomColor) {
        RenderUtil.enableGL2D();
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)7);
        RenderUtil.glColor(topColor);
        GL11.glVertex2f((float)x, (float)y1);
        GL11.glVertex2f((float)x1, (float)y1);
        RenderUtil.glColor(bottomColor);
        GL11.glVertex2f((float)x1, (float)y);
        GL11.glVertex2f((float)x, (float)y);
        GL11.glEnd();
        GL11.glShadeModel((int)7424);
        RenderUtil.disableGL2D();
    }

    public static void drawFilledCircle(int x, int y, double radius, Color c) {
        RenderUtil.setColor(c);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GL11.glDisable((int)3553);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.alphaFunc(516, 0.001f);
        Tessellator tess = Tessellator.getInstance();
        WorldRenderer render = tess.getWorldRenderer();
        for (double i = 0.0; i < 360.0; i += 1.0) {
            double cs = i * 3.141592653589793 / 180.0;
            double ps = (i - 1.0) * 3.141592653589793 / 180.0;
            double[] outer = new double[]{Math.cos(cs) * radius, -Math.sin(cs) * radius, Math.cos(ps) * radius, -Math.sin(ps) * radius};
            render.startDrawing(6);
            render.addVertex((double)x + outer[2], (double)y + outer[3], 0.0);
            render.addVertex((double)x + outer[0], (double)y + outer[1], 0.0);
            render.addVertex(x, y, 0.0);
            tess.draw();
        }
        GlStateManager.disableBlend();
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.disableAlpha();
        GL11.glEnable((int)3553);
    }

    public static void drawTri(double x1, double y1, double x2, double y2, double x3, double y3, double width, Color c) {
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glBlendFunc((int)770, (int)771);
        RenderUtil.setColor(c);
        GL11.glLineWidth((float)((float)width));
        GL11.glBegin((int)3);
        GL11.glVertex2d((double)x1, (double)y1);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glVertex2d((double)x3, (double)y3);
        GL11.glEnd();
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
    }

    public static void drawFineBorderedKKKRect(int x, int y, int x1, int y1, int bord, int color) {
        GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        RenderUtil.drawRect((float)((x *= 2) + 1), (float)((y *= 2) + 1), (float)(x1 *= 2), (float)(y1 *= 2), color);
        RenderUtil.drawVerticalLine(x, y, y1, bord);
        RenderUtil.drawVerticalLine(x1, y, y1, bord);
        RenderUtil.drawHorizontalLine(x + 1, y, x1, bord);
        RenderUtil.drawHorizontalLine(x, y1, x1 + 1, bord);
        GL11.glScaled((double)2.0, (double)2.0, (double)2.0);
    }

    public static void drawOutlinedBoundingBox(AxisAlignedBB aabb) {
        WorldRenderer worldRenderer = Tessellator.instance.getWorldRenderer();
        Tessellator tessellator = Tessellator.instance;
        worldRenderer.startDrawing(3);
        worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
        worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
        worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
        worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
        worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
        tessellator.draw();
        worldRenderer.startDrawing(3);
        worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
        worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
        worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
        worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
        worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
        tessellator.draw();
        worldRenderer.startDrawing(1);
        worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
        worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
        worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
        worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
        worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
        worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
        worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
        worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
        tessellator.draw();
    }

    public static void drawBoundingBox(AxisAlignedBB aabb) {
        WorldRenderer worldRenderer = Tessellator.instance.getWorldRenderer();
        Tessellator tessellator = Tessellator.instance;
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
        worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
        worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
        worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
        worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
        worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
        worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
        worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
        worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
        worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
        worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
        worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
        worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
        worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
        worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
        worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
        worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
        worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
        worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
        worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
        worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
        worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
        worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
        worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
        worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
        worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
        worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
        worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
        worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
        worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
        worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
        worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
        worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
        worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
        worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
        worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
        worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
        worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
        worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
        worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
        worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
        worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
        worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
        tessellator.draw();
    }

    public static int getBlockColor(Block block) {
        int color = block.getMaterial().getMaterialMapColor().colorValue;
        switch (Block.getIdFromBlock(block)) {
            case 14: 
            case 41: {
                color = -1711477173;
                break;
            }
            case 15: 
            case 42: {
                color = -1715420992;
                break;
            }
            case 16: 
            case 173: {
                color = -1724434633;
                break;
            }
            case 21: 
            case 22: {
                color = -1726527803;
                break;
            }
            case 49: {
                color = -1724108714;
                break;
            }
            case 54: 
            case 146: {
                color = -1711292672;
                break;
            }
            case 56: 
            case 57: 
            case 138: {
                color = -1721897739;
                break;
            }
            case 61: 
            case 62: {
                color = -1711395081;
                break;
            }
            case 73: 
            case 74: 
            case 152: {
                color = -1711341568;
                break;
            }
            case 89: {
                color = -1712594866;
                break;
            }
            case 129: 
            case 133: {
                color = -1726489246;
                break;
            }
            case 130: {
                color = -1713438249;
                break;
            }
            case 52: {
                color = 805728308;
                break;
            }
            default: {
                color = -1711276033;
            }
        }
        return color == 0 ? 806752583 : color;
    }

    public static void colour(int hex, float alpha) {
        float red = (float)(hex >> 16 & 255) / 255.0f;
        float green = (float)(hex >> 8 & 255) / 255.0f;
        float blue = (float)(hex & 255) / 255.0f;
        GlStateManager.color(red, green, blue, alpha);
    }
}

