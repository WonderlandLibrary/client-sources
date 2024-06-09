/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.utils;

import java.awt.Color;
import me.thekirkayt.client.module.modules.misc.CustomColor;
import net.minecraft.client.Minecraft;
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
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;

public final class RenderUtils {
    public static final ScaledResolution getScaledRes() {
        ScaledResolution scaledRes = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        return scaledRes;
    }

    public static String getLogInfo(int obj) {
        return ARBShaderObjects.glGetInfoLogARB((int)obj, (int)ARBShaderObjects.glGetObjectParameteriARB((int)obj, (int)35716));
    }

    public static int createShader(String shaderCode, int shaderType) throws Exception {
        int shader;
        block4 : {
            shader = 0;
            try {
                shader = ARBShaderObjects.glCreateShaderObjectARB((int)shaderType);
                if (shader != 0) break block4;
                return 0;
            }
            catch (Exception exc) {
                ARBShaderObjects.glDeleteObjectARB((int)shader);
                throw exc;
            }
        }
        ARBShaderObjects.glShaderSourceARB((int)shader, (CharSequence)shaderCode);
        ARBShaderObjects.glCompileShaderARB((int)shader);
        if (ARBShaderObjects.glGetObjectParameteriARB((int)shader, (int)35713) == 0) {
            throw new RuntimeException("Error creating shader: " + RenderUtils.getLogInfo(shader));
        }
        return shader;
    }

    public static void setupLineSmooth() {
        GL11.glEnable((int)3042);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)3553);
        GL11.glHint((int)3154, (int)4354);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)32925);
        GL11.glEnable((int)32926);
        GL11.glShadeModel((int)7425);
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

    public static void drawRect(float g, float h, float i, float j, int col1) {
        float f = (float)(col1 >> 24 & 255) / 255.0f;
        float f2 = (float)(col1 >> 16 & 255) / 255.0f;
        float f3 = (float)(col1 >> 8 & 255) / 255.0f;
        float f4 = (float)(col1 & 255) / 255.0f;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glPushMatrix();
        GL11.glColor4f((float)f2, (float)f3, (float)f4, (float)f);
        GL11.glBegin((int)7);
        GL11.glVertex2d((double)i, (double)h);
        GL11.glVertex2d((double)g, (double)h);
        GL11.glVertex2d((double)g, (double)j);
        GL11.glVertex2d((double)i, (double)j);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
    }

    public static void drawRectNoBlend(float g, float h, float i, float j, int col1) {
        float f = (float)(col1 >> 24 & 255) / 255.0f;
        float f1 = (float)(col1 >> 16 & 255) / 255.0f;
        float f2 = (float)(col1 >> 8 & 255) / 255.0f;
        float f3 = (float)(col1 & 255) / 255.0f;
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glPushMatrix();
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
        GL11.glBegin((int)7);
        GL11.glVertex2d((double)i, (double)h);
        GL11.glVertex2d((double)g, (double)h);
        GL11.glVertex2d((double)g, (double)j);
        GL11.glVertex2d((double)i, (double)j);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2848);
    }

    public static void drawEsp(EntityLivingBase ent, float pTicks, int hexColor, int hexColorIn) {
        if (!ent.isEntityAlive()) {
            return;
        }
        double x = RenderUtils.getDiff(ent.lastTickPosX, ent.posX, pTicks, RenderManager.renderPosX);
        double y = RenderUtils.getDiff(ent.lastTickPosY, ent.posY, pTicks, RenderManager.renderPosY);
        double z = RenderUtils.getDiff(ent.lastTickPosZ, ent.posZ, pTicks, RenderManager.renderPosZ);
        RenderUtils.boundingBox(ent, x, y, z, hexColor, hexColorIn);
    }

    public static void drawFilledBox(AxisAlignedBB mask) {
        WorldRenderer worldRenderer = Tessellator.instance.getWorldRenderer();
        Tessellator tessellator = Tessellator.instance;
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(mask.minX, mask.minY, mask.minZ);
        worldRenderer.addVertex(mask.minX, mask.maxY, mask.minZ);
        worldRenderer.addVertex(mask.maxX, mask.minY, mask.minZ);
        worldRenderer.addVertex(mask.maxX, mask.maxY, mask.minZ);
        worldRenderer.addVertex(mask.maxX, mask.minY, mask.maxZ);
        worldRenderer.addVertex(mask.maxX, mask.maxY, mask.maxZ);
        worldRenderer.addVertex(mask.minX, mask.minY, mask.maxZ);
        worldRenderer.addVertex(mask.minX, mask.maxY, mask.maxZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(mask.maxX, mask.maxY, mask.minZ);
        worldRenderer.addVertex(mask.maxX, mask.minY, mask.minZ);
        worldRenderer.addVertex(mask.minX, mask.maxY, mask.minZ);
        worldRenderer.addVertex(mask.minX, mask.minY, mask.minZ);
        worldRenderer.addVertex(mask.minX, mask.maxY, mask.maxZ);
        worldRenderer.addVertex(mask.minX, mask.minY, mask.maxZ);
        worldRenderer.addVertex(mask.maxX, mask.maxY, mask.maxZ);
        worldRenderer.addVertex(mask.maxX, mask.minY, mask.maxZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(mask.minX, mask.maxY, mask.minZ);
        worldRenderer.addVertex(mask.maxX, mask.maxY, mask.minZ);
        worldRenderer.addVertex(mask.maxX, mask.maxY, mask.maxZ);
        worldRenderer.addVertex(mask.minX, mask.maxY, mask.maxZ);
        worldRenderer.addVertex(mask.minX, mask.maxY, mask.minZ);
        worldRenderer.addVertex(mask.minX, mask.maxY, mask.maxZ);
        worldRenderer.addVertex(mask.maxX, mask.maxY, mask.maxZ);
        worldRenderer.addVertex(mask.maxX, mask.maxY, mask.minZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(mask.minX, mask.minY, mask.minZ);
        worldRenderer.addVertex(mask.maxX, mask.minY, mask.minZ);
        worldRenderer.addVertex(mask.maxX, mask.minY, mask.maxZ);
        worldRenderer.addVertex(mask.minX, mask.minY, mask.maxZ);
        worldRenderer.addVertex(mask.minX, mask.minY, mask.minZ);
        worldRenderer.addVertex(mask.minX, mask.minY, mask.maxZ);
        worldRenderer.addVertex(mask.maxX, mask.minY, mask.maxZ);
        worldRenderer.addVertex(mask.maxX, mask.minY, mask.minZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(mask.minX, mask.minY, mask.minZ);
        worldRenderer.addVertex(mask.minX, mask.maxY, mask.minZ);
        worldRenderer.addVertex(mask.minX, mask.minY, mask.maxZ);
        worldRenderer.addVertex(mask.minX, mask.maxY, mask.maxZ);
        worldRenderer.addVertex(mask.maxX, mask.minY, mask.maxZ);
        worldRenderer.addVertex(mask.maxX, mask.maxY, mask.maxZ);
        worldRenderer.addVertex(mask.maxX, mask.minY, mask.minZ);
        worldRenderer.addVertex(mask.maxX, mask.maxY, mask.minZ);
        tessellator.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(mask.minX, mask.maxY, mask.maxZ);
        worldRenderer.addVertex(mask.minX, mask.minY, mask.maxZ);
        worldRenderer.addVertex(mask.minX, mask.maxY, mask.minZ);
        worldRenderer.addVertex(mask.minX, mask.minY, mask.minZ);
        worldRenderer.addVertex(mask.maxX, mask.maxY, mask.minZ);
        worldRenderer.addVertex(mask.maxX, mask.minY, mask.minZ);
        worldRenderer.addVertex(mask.maxX, mask.maxY, mask.maxZ);
        worldRenderer.addVertex(mask.maxX, mask.minY, mask.maxZ);
        tessellator.draw();
    }

    public static void boundingBox(Entity entity, double x, double y, double z, int color, int colorIn) {
        GlStateManager.pushMatrix();
        GL11.glLineWidth((float)1.0f);
        AxisAlignedBB var11 = entity.getEntityBoundingBox();
        AxisAlignedBB var12 = new AxisAlignedBB(var11.minX - entity.posX + x, var11.minY - entity.posY + y, var11.minZ - entity.posZ + z, var11.maxX - entity.posX + x, var11.maxY - entity.posY + y, var11.maxZ - entity.posZ + z);
        if (color != 0) {
            GlStateManager.disableDepth();
            RenderUtils.filledBox(var12, colorIn, true);
            GlStateManager.disableLighting();
            RenderGlobal.drawOutlinedBoundingBox(var12, color);
        }
        GlStateManager.popMatrix();
    }

    public static void filledBox(AxisAlignedBB boundingBox, int color, boolean shouldColor) {
        GlStateManager.pushMatrix();
        float var11 = (float)(color >> 24 & 255) / 255.0f;
        float var12 = (float)(color >> 16 & 255) / 255.0f;
        float var13 = (float)(color >> 8 & 255) / 255.0f;
        float var14 = (float)(color & 255) / 255.0f;
        WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        if (shouldColor) {
            GlStateManager.color(var12, var13, var14, var11);
        }
        int draw = 7;
        worldRenderer.startDrawing(7);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        Tessellator.getInstance().draw();
        worldRenderer.startDrawing(7);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        Tessellator.getInstance().draw();
        worldRenderer.startDrawing(7);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        Tessellator.getInstance().draw();
        worldRenderer.startDrawing(7);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        Tessellator.getInstance().draw();
        worldRenderer.startDrawing(7);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        Tessellator.getInstance().draw();
        worldRenderer.startDrawing(7);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        Tessellator.getInstance().draw();
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();
    }

    public static void rectangle(double left, double top, double right, double bottom, int color) {
        double var5;
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
        float var6 = (float)(color >> 24 & 255) / 255.0f;
        float var7 = (float)(color >> 16 & 255) / 255.0f;
        float var8 = (float)(color >> 8 & 255) / 255.0f;
        float var9 = (float)(color & 255) / 255.0f;
        WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(var7, var8, var9, var6);
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(left, bottom, 0.0);
        worldRenderer.addVertex(right, bottom, 0.0);
        worldRenderer.addVertex(right, top, 0.0);
        worldRenderer.addVertex(left, top, 0.0);
        Tessellator.getInstance().draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void rectangleBordered(double x, double y, double x1, double y1, double width, int internalColor, int borderColor, Side side) {
        RenderUtils.rectangle(x + width, y + width, x1 - width, y1 - width, internalColor);
        if (!side.equals((Object)Side.Top)) {
            RenderUtils.rectangle(x + width, y, x1 - width, y + width, borderColor);
        }
        if (!side.equals((Object)Side.Left)) {
            RenderUtils.rectangle(x, y, x + width, y1, borderColor);
        }
        if (!side.equals((Object)Side.Right)) {
            RenderUtils.rectangle(x1 - width, y, x1, y1, borderColor);
        }
        if (!side.equals((Object)Side.Bottom)) {
            RenderUtils.rectangle(x + width, y1 - width, x1 - width, y1, borderColor);
        }
    }

    public static void rectangleBordered(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
        RenderUtils.rectangle(x + width, y + width, x1 - width, y1 - width, internalColor);
        RenderUtils.rectangle(x + width, y, x1 - width, y + width, borderColor);
        RenderUtils.rectangle(x, y, x + width, y1, borderColor);
        RenderUtils.rectangle(x1 - width, y, x1, y1, borderColor);
        RenderUtils.rectangle(x + width, y1 - width, x1 - width, y1, borderColor);
    }

    public static void rectangleGradient(double x1, double y1, double x2, double y2, int[] color) {
        float[] r = new float[color.length];
        float[] g = new float[color.length];
        float[] b = new float[color.length];
        float[] a = new float[color.length];
        for (int i = 0; i < color.length; ++i) {
            r[i] = (float)(color[i] >> 16 & 255) / 255.0f;
            g[i] = (float)(color[i] >> 8 & 255) / 255.0f;
            b[i] = (float)(color[i] & 255) / 255.0f;
            a[i] = (float)(color[i] >> 24 & 255) / 255.0f;
        }
        GlStateManager.disableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GlStateManager.blendFunc(770, 771);
        GlStateManager.shadeModel(7425);
        WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.setColorRGBA_F(r[0], g[0], b[0], a[0]);
        worldRenderer.addVertex(x2, y1, 0.0);
        worldRenderer.setColorRGBA_F(r[1], g[1], b[1], a[1]);
        worldRenderer.addVertex(x1, y1, 0.0);
        worldRenderer.setColorRGBA_F(r[2], g[2], b[2], a[2]);
        worldRenderer.addVertex(x1, y2, 0.0);
        worldRenderer.setColorRGBA_F(r[3], g[3], b[3], a[3]);
        worldRenderer.addVertex(x2, y2, 0.0);
        Tessellator.getInstance().draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void rectangleOutlinedGradient(double x1, double y1, double x2, double y2, int[] color, double width) {
        RenderUtils.rectangleGradient(x1, y1, x2, y1 + width, new int[]{color[0], color[1], color[0], color[1]});
        RenderUtils.rectangleGradient(x1, y2 - width, x2, y2, new int[]{color[2], color[3], color[2], color[3]});
        RenderUtils.rectangleGradient(x1, y1 + width, x1 + width, y2 - width, color);
        RenderUtils.rectangleGradient(x2 - width, y1 + width, x2, y2 - width, color);
    }

    public static void rectangleBorderedGradient(double x1, double y1, double x2, double y2, int[] fill, int[] outline, double width) {
        RenderUtils.rectangleOutlinedGradient(x1, y1, x2, y2, outline, width);
        RenderUtils.rectangleGradient(x1 + width, y1 + width, x2 - width, y2 - width, fill);
    }

    public static int blend(int color1, int color2, float perc) {
        Color blended;
        Color x = new Color(color1);
        Color y = new Color(color2);
        float inverse_blending = 1.0f - perc;
        float red = (float)x.getRed() * perc + (float)y.getRed() * inverse_blending;
        float green = (float)x.getGreen() * perc + (float)y.getGreen() * inverse_blending;
        float blue = (float)x.getBlue() * perc + (float)y.getBlue() * inverse_blending;
        try {
            blended = new Color(red / 255.0f, green / 255.0f, blue / 255.0f);
        }
        catch (Exception e) {
            blended = new Color(-1);
        }
        return blended.getRGB();
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
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3008);
        GL11.glDepthMask((boolean)true);
        GL11.glCullFace((int)1029);
        GL11.glDisable((int)2848);
        GL11.glHint((int)3154, (int)4352);
        GL11.glHint((int)3155, (int)4352);
    }

    public static double getDiff(double lastI, double i, float ticks, double ownI) {
        return lastI + (i - lastI) * (double)ticks - ownI;
    }

    public static void filledBoxRainbow(AxisAlignedBB boundingBox, float red, float green, float blue, boolean shouldColor) {
        GlStateManager.pushMatrix();
        WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        if (shouldColor) {
            GlStateManager.color(CustomColor.red, CustomColor.green, CustomColor.blue, 0.2f);
        }
        int draw = 7;
        worldRenderer.startDrawing(7);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        Tessellator.getInstance().draw();
        worldRenderer.startDrawing(7);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        Tessellator.getInstance().draw();
        worldRenderer.startDrawing(7);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        Tessellator.getInstance().draw();
        worldRenderer.startDrawing(7);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        Tessellator.getInstance().draw();
        worldRenderer.startDrawing(7);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        Tessellator.getInstance().draw();
        worldRenderer.startDrawing(7);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        Tessellator.getInstance().draw();
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();
    }

    public static enum Side {
        Top("Top", 0),
        Right("Right", 1),
        Bottom("Bottom", 2),
        Left("Left", 3),
        None("None", 4);
        

        private Side(String s, int n2) {
        }
    }

}

