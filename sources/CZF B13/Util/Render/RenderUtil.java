package gq.vapu.czfclient.Util.Render;

import gq.vapu.czfclient.Libraries.tessellate.Tessellation;
import gq.vapu.czfclient.Util.Helper;
import gq.vapu.czfclient.Util.Math.Vec2f;
import gq.vapu.czfclient.Util.Math.Vec3f;
import gq.vapu.czfclient.Util.Render.gl.GLClientState;
import gq.vapu.czfclient.Util.RenderUtil2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static gq.vapu.czfclient.Util.Render.RenderUtil.R2DUtils.drawHLine;

public class RenderUtil {
    public static final Tessellation tessellator;
    public static final Consumer<Integer> ENABLE_CLIENT_STATE;
    public static final Consumer<Integer> DISABLE_CLIENT_STATE;
    public static final List<Integer> csBuffer;

    static {
        tessellator = Tessellation.createExpanding(4, 1.0f, 2.0f);
        csBuffer = new ArrayList<Integer>();
        ENABLE_CLIENT_STATE = GL11::glEnableClientState;
        DISABLE_CLIENT_STATE = GL11::glEnableClientState;
    }

    public RenderUtil() {
        super();
    }

    public static void pre3D() {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDepthMask(false);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
    }

    public static void drawRoundedRect(float n, float n2, float n3, float n4, final int n5, final int n6) {
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        RenderUtil2.R2DUtils.drawVLine(n *= 2.0f, (n2 *= 2.0f) + 1.0f, (n4 *= 2.0f) - 2.0f, n5);
        RenderUtil2.R2DUtils.drawVLine((n3 *= 2.0f) - 1.0f, n2 + 1.0f, n4 - 2.0f, n5);
        drawHLine(n + 2.0f, n3 - 3.0f, n2, n5);
        drawHLine(n + 2.0f, n3 - 3.0f, n4 - 1.0f, n5);
        drawHLine(n + 1.0f, n + 1.0f, n2 + 1.0f, n5);
        drawHLine(n3 - 2.0f, n3 - 2.0f, n2 + 1.0f, n5);
        drawHLine(n3 - 2.0f, n3 - 2.0f, n4 - 2.0f, n5);
        drawHLine(n + 1.0f, n + 1.0f, n4 - 2.0f, n5);
        drawRect(n + 1.0f, n2 + 1.0f, n3 - 1.0f, n4 - 1.0f, n6);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static int width() {
        return new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth();
    }

    public static int height() {
        return new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight();
    }

    public static double interpolation(final double newPos, final double oldPos) {
        return oldPos + (newPos - oldPos) * Helper.mc.timer.renderPartialTicks;
    }

    public static boolean isInViewFrustrum(AxisAlignedBB bb) {
        Frustum frustrum = new Frustum();
        Entity current = Minecraft.getMinecraft().getRenderViewEntity();
        frustrum.setPosition(current.posX, current.posY, current.posZ);
        return frustrum.isBoundingBoxInFrustum(bb);
    }

    public static boolean isInViewFrustrum(Entity entity) {
        return RenderUtil.isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
    }

    public static void drawHorizontalLine(float x, float y, float x1, float thickness, int color) {
        RenderUtil.drawRect2(x, y, x1, y + thickness, color);
    }

    public static void drawRect2(float x, float y, float x2, float y2, int color) {
        RenderUtil.drawRect(x, y, x2, y2, color);
    }

    public static void drawVerticalLine(float x, float y, float y1, float thickness, int color) {
        RenderUtil.drawRect2(x, y, x + thickness, y1, color);
    }

    public static void drawBordered(double x2, double y2, double width, double height, double length, int innerColor, int outerColor) {
        Gui.drawRect(x2, y2, x2 + width, y2 + height, innerColor);
        Gui.drawRect(x2 - length, y2, x2, y2 + height, outerColor);
        Gui.drawRect(x2 - length, y2 - length, x2 + width, y2, outerColor);
        Gui.drawRect(x2 + width, y2 - length, x2 + width + length, y2 + height + length, outerColor);
        Gui.drawRect(x2 - length, y2 + height, x2 + width, y2 + height + length, outerColor);
    }

    public static int getHexRGB(final int hex) {
        return 0xFF000000 | hex;
    }

    public static void drawCustomImage(int x, int y, int width, int height, ResourceLocation image) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height,
                (float) width, (float) height);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        Gui.drawRect(0, 0, 0, 0, 0);
    }

    public static void drawBorderedRect(final float x, final double d, final float x2, final double e, final float l1,
                                        final int col1, final int col2) {
        Gui.drawRect(x, d, x2, e, col2);
        final float f = (col1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (col1 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glLineWidth(l1);
        GL11.glBegin(1);
        GL11.glVertex2d(x, d);
        GL11.glVertex2d(x, e);
        GL11.glVertex2d(x2, e);
        GL11.glVertex2d(x2, d);
        GL11.glVertex2d(x, d);
        GL11.glVertex2d(x2, d);
        GL11.glVertex2d(x, e);
        GL11.glVertex2d(x2, e);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }

    public static void pre() {
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
    }

    public static void post() {
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glColor3d(1.0, 1.0, 1.0);
    }

    public static void startDrawing() {
        GL11.glEnable(3042);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        Helper.mc.entityRenderer.setupCameraTransform(Helper.mc.timer.renderPartialTicks, 0);
    }

    public static void stopDrawing() {
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static Color blend(final Color color1, final Color color2, final double ratio) {
        final float r = (float) ratio;
        final float ir = 1.0f - r;
        final float[] rgb1 = new float[3];
        final float[] rgb2 = new float[3];
        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
        final Color color3 = new Color(rgb1[0] * r + rgb2[0] * ir, rgb1[1] * r + rgb2[1] * ir,
                rgb1[2] * r + rgb2[2] * ir);
        return color3;
    }

    public static void drawLine(final Vec2f start, final Vec2f end, final float width) {
        drawLine(start.getX(), start.getY(), end.getX(), end.getY(), width);
    }

    public static void drawLine(final Vec3f start, final Vec3f end, final float width) {
        drawLine((float) start.getX(), (float) start.getY(), (float) start.getZ(), (float) end.getX(),
                (float) end.getY(), (float) end.getZ(), width);
    }

    public static void drawLine(final float x, final float y, final float x1, final float y1, final float width) {
        drawLine(x, y, 0.0f, x1, y1, 0.0f, width);
    }

    public static void drawLine(final float x, final float y, final float z, final float x1, final float y1,
                                final float z1, final float width) {
        GL11.glLineWidth(width);
        setupRender(true);
        setupClientState(GLClientState.VERTEX, true);
        RenderUtil.tessellator.addVertex(x, y, z).addVertex(x1, y1, z1).draw(3);
        setupClientState(GLClientState.VERTEX, false);
        setupRender(false);
    }

    public static void setupClientState(final GLClientState state, final boolean enabled) {
        RenderUtil.csBuffer.clear();
        if (state.ordinal() > 0) {
            RenderUtil.csBuffer.add(state.getCap());
        }
        RenderUtil.csBuffer.add(32884);
        RenderUtil.csBuffer.forEach(enabled ? RenderUtil.ENABLE_CLIENT_STATE : RenderUtil.DISABLE_CLIENT_STATE);
    }

    public static void setupRender(final boolean start) {
        if (start) {
            GlStateManager.enableBlend();
            GL11.glEnable(2848);
            GlStateManager.disableDepth();
            GlStateManager.disableTexture2D();
            GlStateManager.blendFunc(770, 771);
            GL11.glHint(3154, 4354);
        } else {
            GlStateManager.disableBlend();
            GlStateManager.enableTexture2D();
            GL11.glDisable(2848);
            GlStateManager.enableDepth();
        }
        GlStateManager.depthMask(!start);
    }

    public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, width, height);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void layeredRect(double x1, double y1, double x2, double y2, int outline, int inline,
                                   int background) {
        R2DUtils.drawRect(x1, y1, x2, y2, outline);
        R2DUtils.drawRect(x1 + 1, y1 + 1, x2 - 1, y2 - 1, inline);
        R2DUtils.drawRect(x1 + 2, y1 + 2, x2 - 2, y2 - 2, background);
    }

    public static void drawEntityESP(double x, double y, double z, double width, double height, float red, float green,
                                     float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWdith) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        RenderUtil.drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
        GL11.glLineWidth(lineWdith);
        GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
        RenderUtil
                .drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public static void drawBoundingBox(AxisAlignedBB aa) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        tessellator.draw();
    }

    public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        tessellator.draw();
        worldRenderer.begin(1, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        tessellator.draw();
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

    public static void post3D() {
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        GL11.glColor4f(1, 1, 1, 1);
    }

    public static double getAnimationState(double animation, double finalState, double speed) {
        float add = (float) (0.01 * speed);
        if (animation < finalState) {
            if (animation + add < finalState)
                animation += add;
            else
                animation = finalState;
        } else {
            if (animation - add > finalState)
                animation -= add;
            else
                animation = finalState;
        }
        return animation;
    }

    public static void rectangleBordered(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
        RenderUtil.rectangle(x + width, y + width, x1 - width, y1 - width, internalColor);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtil.rectangle(x + width, y, x1 - width, y + width, borderColor);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtil.rectangle(x, y, x + width, y1, borderColor);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtil.rectangle(x1 - width, y, x1, y1, borderColor);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtil.rectangle(x + width, y1 - width, x1 - width, y1, borderColor);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void rectangle(double left, double top, double right, double bottom, int color) {
        if (left < right) {
            double var5 = left;
            left = right;
            right = var5;
        }
    }

    public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
        float f = (float) (col1 >> 24 & 255) / 255.0f;
        float f1 = (float) (col1 >> 16 & 255) / 255.0f;
        float f2 = (float) (col1 >> 8 & 255) / 255.0f;
        float f3 = (float) (col1 & 255) / 255.0f;
        float f4 = (float) (col2 >> 24 & 255) / 255.0f;
        float f5 = (float) (col2 >> 16 & 255) / 255.0f;
        float f6 = (float) (col2 >> 8 & 255) / 255.0f;
        float f7 = (float) (col2 & 255) / 255.0f;
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

    public static void drawRect(float g, float h, float i, float j, int col1) {
        float f2 = (float) (col1 >> 24 & 255) / 255.0f;
        float f22 = (float) (col1 >> 16 & 255) / 255.0f;
        float f3 = (float) (col1 >> 8 & 255) / 255.0f;
        float f4 = (float) (col1 & 255) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(f22, f3, f4, f2);
        GL11.glBegin(7);
        GL11.glVertex2d(i, h);
        GL11.glVertex2d(g, h);
        GL11.glVertex2d(g, j);
        GL11.glVertex2d(i, j);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }

    public static void drawIcon(float x, float y, int sizex, int sizey, ResourceLocation resourceLocation) {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GL11.glTranslatef(x, y, 0.0f);
        RenderUtil.drawScaledRect(0, 0, 0.0f, 0.0f, sizex, sizey, sizex, sizey, sizex, sizey);
        GlStateManager.disableAlpha();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();
        GlStateManager.disableRescaleNormal();
        GL11.glDisable(2848);
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }

    public static void drawScaledRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
        Gui.drawScaledCustomSizeModalRect(x, y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
    }

    public static class R3DUtils {
        public static void startDrawing() {
            GL11.glEnable(3042);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(2848);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            Minecraft.getMinecraft().entityRenderer
                    .setupCameraTransform(Minecraft.getMinecraft().timer.renderPartialTicks, 0);
        }

        public static void stopDrawing() {
            GL11.glDisable(3042);
            GL11.glEnable(3553);
            GL11.glDisable(2848);
            GL11.glDisable(3042);
            GL11.glEnable(2929);
        }

        public static void drawOutlinedBox(AxisAlignedBB box) {
            if (box == null) {
                return;
            }
            Minecraft.getMinecraft().entityRenderer
                    .setupCameraTransform(Minecraft.getMinecraft().timer.renderPartialTicks, 0);
            GL11.glBegin(3);
            GL11.glVertex3d(box.minX, box.minY, box.minZ);
            GL11.glVertex3d(box.maxX, box.minY, box.minZ);
            GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
            GL11.glVertex3d(box.minX, box.minY, box.maxZ);
            GL11.glVertex3d(box.minX, box.minY, box.minZ);
            GL11.glEnd();
            GL11.glBegin(3);
            GL11.glVertex3d(box.minX, box.maxY, box.minZ);
            GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
            GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
            GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
            GL11.glVertex3d(box.minX, box.maxY, box.minZ);
            GL11.glEnd();
            GL11.glBegin(1);
            GL11.glVertex3d(box.minX, box.minY, box.minZ);
            GL11.glVertex3d(box.minX, box.maxY, box.minZ);
            GL11.glVertex3d(box.maxX, box.minY, box.minZ);
            GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
            GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
            GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
            GL11.glVertex3d(box.minX, box.minY, box.maxZ);
            GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
            GL11.glEnd();
        }

        public static void drawBoundingBox(AxisAlignedBB aabb) {
            WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
            Tessellator tessellator = Tessellator.getInstance();
            Minecraft.getMinecraft().entityRenderer
                    .setupCameraTransform(Minecraft.getMinecraft().timer.renderPartialTicks, 0);
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

        public static void drawFilledBox(AxisAlignedBB mask) {
            GL11.glBegin(4);
            GL11.glVertex3d(mask.minX, mask.minY, mask.minZ);
            GL11.glVertex3d(mask.minX, mask.maxY, mask.minZ);
            GL11.glVertex3d(mask.maxX, mask.minY, mask.minZ);
            GL11.glVertex3d(mask.maxX, mask.maxY, mask.minZ);
            GL11.glVertex3d(mask.maxX, mask.minY, mask.maxZ);
            GL11.glVertex3d(mask.maxX, mask.maxY, mask.maxZ);
            GL11.glVertex3d(mask.minX, mask.minY, mask.maxZ);
            GL11.glVertex3d(mask.minX, mask.maxY, mask.maxZ);
            GL11.glEnd();
            GL11.glBegin(4);
            GL11.glVertex3d(mask.maxX, mask.maxY, mask.minZ);
            GL11.glVertex3d(mask.maxX, mask.minY, mask.minZ);
            GL11.glVertex3d(mask.minX, mask.maxY, mask.minZ);
            GL11.glVertex3d(mask.minX, mask.minY, mask.minZ);
            GL11.glVertex3d(mask.minX, mask.maxY, mask.maxZ);
            GL11.glVertex3d(mask.minX, mask.minY, mask.maxZ);
            GL11.glVertex3d(mask.maxX, mask.maxY, mask.maxZ);
            GL11.glVertex3d(mask.maxX, mask.minY, mask.maxZ);
            GL11.glEnd();
            GL11.glBegin(4);
            GL11.glVertex3d(mask.minX, mask.maxY, mask.minZ);
            GL11.glVertex3d(mask.maxX, mask.maxY, mask.minZ);
            GL11.glVertex3d(mask.maxX, mask.maxY, mask.maxZ);
            GL11.glVertex3d(mask.minX, mask.maxY, mask.maxZ);
            GL11.glVertex3d(mask.minX, mask.maxY, mask.minZ);
            GL11.glVertex3d(mask.minX, mask.maxY, mask.maxZ);
            GL11.glVertex3d(mask.maxX, mask.maxY, mask.maxZ);
            GL11.glVertex3d(mask.maxX, mask.maxY, mask.minZ);
            GL11.glEnd();
            GL11.glBegin(4);
            GL11.glVertex3d(mask.minX, mask.minY, mask.minZ);
            GL11.glVertex3d(mask.maxX, mask.minY, mask.minZ);
            GL11.glVertex3d(mask.maxX, mask.minY, mask.maxZ);
            GL11.glVertex3d(mask.minX, mask.minY, mask.maxZ);
            GL11.glVertex3d(mask.minX, mask.minY, mask.minZ);
            GL11.glVertex3d(mask.minX, mask.minY, mask.maxZ);
            GL11.glVertex3d(mask.maxX, mask.minY, mask.maxZ);
            GL11.glVertex3d(mask.maxX, mask.minY, mask.minZ);
            GL11.glEnd();
            GL11.glBegin(4);
            GL11.glVertex3d(mask.minX, mask.minY, mask.minZ);
            GL11.glVertex3d(mask.minX, mask.maxY, mask.minZ);
            GL11.glVertex3d(mask.minX, mask.minY, mask.maxZ);
            GL11.glVertex3d(mask.minX, mask.maxY, mask.maxZ);
            GL11.glVertex3d(mask.maxX, mask.minY, mask.maxZ);
            GL11.glVertex3d(mask.maxX, mask.maxY, mask.maxZ);
            GL11.glVertex3d(mask.maxX, mask.minY, mask.minZ);
            GL11.glVertex3d(mask.maxX, mask.maxY, mask.minZ);
            GL11.glEnd();
            GL11.glBegin(4);
            GL11.glVertex3d(mask.minX, mask.maxY, mask.maxZ);
            GL11.glVertex3d(mask.minX, mask.minY, mask.maxZ);
            GL11.glVertex3d(mask.minX, mask.maxY, mask.minZ);
            GL11.glVertex3d(mask.minX, mask.minY, mask.minZ);
            GL11.glVertex3d(mask.maxX, mask.maxY, mask.minZ);
            GL11.glVertex3d(mask.maxX, mask.minY, mask.minZ);
            GL11.glVertex3d(mask.maxX, mask.maxY, mask.maxZ);
            GL11.glVertex3d(mask.maxX, mask.minY, mask.maxZ);
            GL11.glEnd();
        }

        public static void drawOutlinedBoundingBox(AxisAlignedBB aabb) {
            GL11.glBegin(3);
            GL11.glVertex3d(aabb.minX, aabb.minY, aabb.minZ);
            GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.minZ);
            GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.maxZ);
            GL11.glVertex3d(aabb.minX, aabb.minY, aabb.maxZ);
            GL11.glVertex3d(aabb.minX, aabb.minY, aabb.minZ);
            GL11.glEnd();
            GL11.glBegin(3);
            GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.minZ);
            GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.minZ);
            GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.maxZ);
            GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.maxZ);
            GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.minZ);
            GL11.glEnd();
            GL11.glBegin(1);
            GL11.glVertex3d(aabb.minX, aabb.minY, aabb.minZ);
            GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.minZ);
            GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.minZ);
            GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.minZ);
            GL11.glVertex3d(aabb.maxX, aabb.minY, aabb.maxZ);
            GL11.glVertex3d(aabb.maxX, aabb.maxY, aabb.maxZ);
            GL11.glVertex3d(aabb.minX, aabb.minY, aabb.maxZ);
            GL11.glVertex3d(aabb.minX, aabb.maxY, aabb.maxZ);
            GL11.glEnd();
        }
    }

    public static class R2DUtils {
        public static void enableGL2D() {
            GL11.glDisable(2929);
            GL11.glEnable(3042);
            GL11.glDisable(3553);
            GL11.glBlendFunc(770, 771);
            GL11.glDepthMask(true);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glHint(3155, 4354);
        }

        public static void disableGL2D() {
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            GL11.glHint(3154, 4352);
            GL11.glHint(3155, 4352);
        }

        public static void draw2DCorner(Entity e, double posX, double posY, double posZ, int color) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(posX, posY, posZ);
            GL11.glNormal3f(0.0f, 0.0f, 0.0f);
            GlStateManager.rotate(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
            GlStateManager.scale(-0.1, -0.1, 0.1);
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GlStateManager.depthMask(true);
            R2DUtils.drawRect(7.0, -20.0, 7.300000190734863, -17.5, color);
            R2DUtils.drawRect(-7.300000190734863, -20.0, -7.0, -17.5, color);
            R2DUtils.drawRect(4.0, -20.299999237060547, 7.300000190734863, -20.0, color);
            R2DUtils.drawRect(-7.300000190734863, -20.299999237060547, -4.0, -20.0, color);
            R2DUtils.drawRect(-7.0, 3.0, -4.0, 3.299999952316284, color);
            R2DUtils.drawRect(4.0, 3.0, 7.0, 3.299999952316284, color);
            R2DUtils.drawRect(-7.300000190734863, 0.8, -7.0, 3.299999952316284, color);
            R2DUtils.drawRect(7.0, 0.5, 7.300000190734863, 3.299999952316284, color);
            R2DUtils.drawRect(7.0, -20.0, 7.300000190734863, -17.5, color);
            R2DUtils.drawRect(-7.300000190734863, -20.0, -7.0, -17.5, color);
            R2DUtils.drawRect(4.0, -20.299999237060547, 7.300000190734863, -20.0, color);
            R2DUtils.drawRect(-7.300000190734863, -20.299999237060547, -4.0, -20.0, color);
            R2DUtils.drawRect(-7.0, 3.0, -4.0, 3.299999952316284, color);
            R2DUtils.drawRect(4.0, 3.0, 7.0, 3.299999952316284, color);
            R2DUtils.drawRect(-7.300000190734863, 0.8, -7.0, 3.299999952316284, color);
            R2DUtils.drawRect(7.0, 0.5, 7.300000190734863, 3.299999952316284, color);
            GL11.glDisable(3042);
            GL11.glEnable(2929);
            GlStateManager.popMatrix();
        }

        public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
            R2DUtils.enableGL2D();
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            R2DUtils.drawVLine(x *= 2.0f, (y *= 2.0f) + 1.0f, (y1 *= 2.0f) - 2.0f, borderC);
            R2DUtils.drawVLine((x1 *= 2.0f) - 1.0f, y + 1.0f, y1 - 2.0f, borderC);
            drawHLine(x + 2.0f, x1 - 3.0f, y, borderC);
            drawHLine(x + 2.0f, x1 - 3.0f, y1 - 1.0f, borderC);
            drawHLine(x + 1.0f, x + 1.0f, y + 1.0f, borderC);
            drawHLine(x1 - 2.0f, x1 - 2.0f, y + 1.0f, borderC);
            drawHLine(x1 - 2.0f, x1 - 2.0f, y1 - 2.0f, borderC);
            drawHLine(x + 1.0f, x + 1.0f, y1 - 2.0f, borderC);
            R2DUtils.drawRect(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
            GL11.glScalef(2.0f, 2.0f, 2.0f);
            R2DUtils.disableGL2D();
            Gui.drawRect(0, 0, 0, 0, 0);
        }

        public static void drawRect(double x2, double y2, double x1, double y1, int color) {
            R2DUtils.enableGL2D();
            R2DUtils.glColor(color);
            R2DUtils.drawRect(x2, y2, x1, y1);
            R2DUtils.disableGL2D();
        }

        private static void drawRect(double x2, double y2, double x1, double y1) {
            GL11.glBegin(7);
            GL11.glVertex2d(x2, y1);
            GL11.glVertex2d(x1, y1);
            GL11.glVertex2d(x1, y2);
            GL11.glVertex2d(x2, y2);
            GL11.glEnd();
        }

        public static void glColor(int hex) {
            float alpha = (float) (hex >> 24 & 255) / 255.0f;
            float red = (float) (hex >> 16 & 255) / 255.0f;
            float green = (float) (hex >> 8 & 255) / 255.0f;
            float blue = (float) (hex & 255) / 255.0f;
            GL11.glColor4f(red, green, blue, alpha);
        }

        public static void drawRect(float x, float y, float x1, float y1, int color) {
            R2DUtils.enableGL2D();
            glColor(color);
            R2DUtils.drawRect(x, y, x1, y1);
            R2DUtils.disableGL2D();
        }

        public static void drawBorderedRect(float x, float y, float x1, float y1, float width, int borderColor) {
            R2DUtils.enableGL2D();
            glColor(borderColor);
            R2DUtils.drawRect(x + width, y, x1 - width, y + width);
            R2DUtils.drawRect(x, y, x + width, y1);
            R2DUtils.drawRect(x1 - width, y, x1, y1);
            R2DUtils.drawRect(x + width, y1 - width, x1 - width, y1);
            R2DUtils.disableGL2D();
        }

        public static void drawBorderedRect(float x, float y, float x1, float y1, int insideC, int borderC) {
            R2DUtils.enableGL2D();
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            R2DUtils.drawVLine(x *= 2.0f, y *= 2.0f, y1 *= 2.0f, borderC);
            R2DUtils.drawVLine((x1 *= 2.0f) - 1.0f, y, y1, borderC);
            drawHLine(x, x1 - 1.0f, y, borderC);
            drawHLine(x, x1 - 2.0f, y1 - 1.0f, borderC);
            R2DUtils.drawRect(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
            GL11.glScalef(2.0f, 2.0f, 2.0f);
            R2DUtils.disableGL2D();
        }

        public static void drawGradientRect(float x, float y, float x1, float y1, int topColor, int bottomColor) {
            R2DUtils.enableGL2D();
            GL11.glShadeModel(7425);
            GL11.glBegin(7);
            glColor(topColor);
            GL11.glVertex2f(x, y1);
            GL11.glVertex2f(x1, y1);
            glColor(bottomColor);
            GL11.glVertex2f(x1, y);
            GL11.glVertex2f(x, y);
            GL11.glEnd();
            GL11.glShadeModel(7424);
            R2DUtils.disableGL2D();
        }

        public static void drawHLine(float x, float y, float x1, int y1) {
            if (y < x) {
                float var5 = x;
                x = y;
                y = var5;
            }
            R2DUtils.drawRect(x, x1, y + 1.0f, x1 + 1.0f, y1);
        }

        public static void drawVLine(float x, float y, float x1, int y1) {
            if (x1 < y) {
                float var5 = y;
                y = x1;
                x1 = var5;
            }
            R2DUtils.drawRect(x, y + 1.0f, x + 1.0f, x1, y1);
        }

        public static void drawHLine(float x, float y, float x1, int y1, int y2) {
            if (y < x) {
                float var5 = x;
                x = y;
                y = var5;
            }
            R2DUtils.drawGradientRect(x, x1, y + 1.0f, x1 + 1.0f, y1, y2);
        }

        public static void drawRect(float x, float y, float x1, float y1) {
            GL11.glBegin(7);
            GL11.glVertex2f(x, y1);
            GL11.glVertex2f(x1, y1);
            GL11.glVertex2f(x1, y);
            GL11.glVertex2f(x, y);
            GL11.glEnd();
        }
    }
}