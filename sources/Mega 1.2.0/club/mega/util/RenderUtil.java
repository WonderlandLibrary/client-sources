package club.mega.util;

import club.mega.Mega;
import club.mega.interfaces.MinecraftInterface;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.util.glu.GLU;

import javax.vecmath.Vector3d;
import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public final class RenderUtil implements MinecraftInterface {

    private static final Frustum frustum = new Frustum();

    private static final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
    private static final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
    private static final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
    private static final FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);

    public static void drawRect(final double x, final double y, final double width, final double height, final Color color) {
        Gui.drawRect(x, y, x + width, y + height, color.getRGB());
    }

    public static void drawRect(final double x, final double y, final double width, final double height, final int color) {
        Gui.drawRect(x, y, x + width, y + height, color);
    }

    public static void drawGradientRect(final double x, final double y, final double width, final double height, final Color startColor, final Color endColor) {
        Gui.drawGradientRect(x, y, x + width, y + height, startColor.getRGB(), endColor.getRGB());
    }

    public static void drawGradientRect(final double x, final double y, final double width, final double height, final int startColor, final int endColor) {
        Gui.drawGradientRect(x, y, x + width, y + height, startColor, endColor);
    }

    public static void drawRoundedRect(final double x, final double y, final double width, final double height, final double cr, final Color color) {
        Stencil stencil = Stencil.getInstance();
        stencil.clear();
        stencil.startLayer();
        stencil.setBuffer(true);
        final double x1 = x + width;
        final double y1 = y + height;
        drawFullCircle((x + cr), (y + cr), cr, color.getRGB());
        drawFullCircle((x + cr), (y1 - cr), cr, color.getRGB());
        drawFullCircle((x1 - cr), (y + cr), cr, color.getRGB());
        drawFullCircle((x1 - cr), (y1 - cr), cr, color.getRGB());
        Gui.drawRect(x, y + cr, x1, y1 - cr, color.getRGB());
        Gui.drawRect(x + cr, y, x1 - cr, y1, color.getRGB());
        stencil.cropInside();
        Gui.drawRect(x, y, x1, y1, color.getRGB());
        stencil.stopLayer();
        stencil.clear();
    }

    public static void drawBlurredRect(final double x, final double y, final double width, final double height, final Color color) {
        drawBlurredRect((float) x, (float) y, (float) width, (float) height, color);
    }

    public static void drawBlurredRect(final float x, final float y, final float width, final float height, final Color color) {
        GlStateManager.pushMatrix();
        BlurUtil.blurBackground();
        BlurUtil.renderBlurredBackground(getScaledResolution().getScaledWidth(), getScaledResolution().getScaledHeight(), x, y, width, height, color);
        GlStateManager.popMatrix();
    }

    public static void drawFullCircle(double cx, double cy, double r, final int c) {
        GL11.glPushMatrix();
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        r *= 2.0D;
        cx *= 2.0D;
        cy *= 2.0D;
        float f = (c >> 24 & 0xFF) / 255.0F;
        float f1 = (c >> 16 & 0xFF) / 255.0F;
        float f2 = (c >> 8 & 0xFF) / 255.0F;
        float f3 = (c & 0xFF) / 255.0F;
        boolean blend = GL11.glIsEnabled(3042);
        boolean texture2d = GL11.glIsEnabled(3553);
        boolean line = GL11.glIsEnabled(2848);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glBegin(6);
        for (int i = 0; i <= 360; i++) {
            double x = Math.sin(i * Math.PI / 180.0D) * r;
            double y = Math.cos(i * Math.PI / 180.0D) * r;
            GL11.glVertex2d(cx + x, cy + y);
        }
        GL11.glEnd();
        f = (c >> 24 & 0xFF) / 255.0F;
        f1 = (c >> 16 & 0xFF) / 255.0F;
        f2 = (c >> 8 & 0xFF) / 255.0F;
        f3 = (c & 0xFF) / 255.0F;
        GL11.glColor4f(f1, f2, f3, f);
        if (!line)
            GL11.glDisable(2848);
        if (texture2d)
            GL11.glEnable(3553);
        if (!blend)
            GL11.glDisable(3042);
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        GL11.glPopMatrix();
    }

    public static void drawTexturedRect(final float x, final float y, final float width, final float height, final float uMin, final float uMax, final float vMin, final float vMax) {
        drawTexturedRect(x, y, width, height, uMin, uMax, vMin, vMax, GL11.GL_NEAREST);
    }

    public static void drawTexturedRect(final float x, final float y, final float width, final float height, final float uMin, final float uMax, final float vMin, final float vMax, final int filter) {
        GlStateManager.enableBlend();
        GL14.glBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);

        drawTexturedRectNoBlend(x, y, width, height, uMin, uMax, vMin, vMax, filter);

        GlStateManager.disableBlend();
    }

    public static void drawTexturedRectNoBlend(final float x, final float y, final float width, final float height, final float uMin, final float uMax, final float vMin, final float vMax, final int filter) {
        GlStateManager.enableTexture2D();

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filter);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filter);

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + height, 0.0D).tex(uMin, vMax).endVertex();
        worldrenderer.pos(x + width, y + height, 0.0D).tex(uMax, vMax).endVertex();
        worldrenderer.pos(x + width, y, 0.0D).tex(uMax, vMin).endVertex();
        worldrenderer.pos(x, y, 0.0D).tex(uMin, vMin).endVertex();
        tessellator.draw();

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
    }

    public static void drawOutlinedRect(final double x, final double y, final double width, final double height, final double size, final Color color, final Color outlineColor) {
        drawRect(x - size, y - size, width + size, height + size, color);
        drawRect(x - size, y - size, size, height + size, outlineColor);
        drawRect(x + width, y - size, size, height + size * 2, outlineColor);
        drawRect(x, y - size, width, size, outlineColor);
        drawRect(x - size, y + height, width + size * 2, size, outlineColor);
    }

    public static void prepareScissorBox(final double x, final double y, final double width, final double height) {
        int factor = getScaledResolution().getScaleFactor();
        GL11.glScissor((int) (x * (float) factor), (int) (((float) getScaledResolution().getScaledHeight() - (y + height)) * (float) factor), (int) (((x + width) - x) * (float) factor), (int) (((y + height) - y) * (float) factor));
    }

    public static void prepareScissorBox(final float x, final float y, final float width, final float height) {
        prepareScissorBox((double) x, (double) y, (double) width, (double) height);
    }

    public static double executeInterpolate(final double currentPosition, final double lastTick, final double partialTicks) {
        return lastTick + (currentPosition - lastTick) * partialTicks;
    }

    public static javax.vecmath.Vector3d executeProjection2D(ScaledResolution scaledResolution, double x, double y, double z) {
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelview);
        GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
        GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);
        if (GLU.gluProject((float) x, (float) y, (float) z, modelview, projection, viewport, vector)) {
            return new Vector3d(vector.get(0) / scaledResolution.getScaleFactor(), (Display.getHeight() - vector.get(1)) / scaledResolution.getScaleFactor(), vector.get(2));
        }
        return null;
    }

    public static boolean isInViewFrustrum(Entity entity) {
        return isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
    }

    public static boolean isInViewFrustrum(AxisAlignedBB bb) {
        final Entity current = MC.getRenderViewEntity();
        frustum.setPosition(current.posX, current.posY, current.posZ);
        return frustum.isBoundingBoxInFrustum(bb);
    }


    public static ScaledResolution getScaledResolution() {
        return new ScaledResolution(MC);
    }

    public static void drawImage(final ResourceLocation resourceLocation, final int x, final int y, final int u, final int v, final int uWidth, final int vHeight, final int width, final int height, final int titleWidth, final int titleHeight) {
        GlStateManager.pushMatrix();
        GL11.glColor4f(1, 1, 1, 1);
        MC.getTextureManager().bindTexture(resourceLocation);
        Gui.drawScaledCustomSizeModalRect(x, y, u, v, uWidth, vHeight, width, height, titleWidth, titleHeight);
        GlStateManager.popMatrix();
    }

    public static void drawImage(final ResourceLocation resourceLocation, final int x, final int y, final int width, final int height) {
        drawImage(resourceLocation, x, y, 0, 0, width, height, width, height, width, height);
    }

    public static void drawScaledCustomSizeModalRect(final double x, final double y, final double width, final double height) {
        Gui.drawScaledCustomSizeModalRect((int) x, (int) y, 0, 0, (int) width, (int) height, (int) width, (int) height, (float) width, (float) height);
    }

}
