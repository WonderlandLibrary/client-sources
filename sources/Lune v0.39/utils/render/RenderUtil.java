package me.superskidder.lune.utils.render;

import java.util.function.*;
import java.util.*;
import java.util.List;

import me.superskidder.lune.events.EventRender3D;
import me.superskidder.lune.utils.math.Vec2f;
import me.superskidder.lune.utils.math.Vec3f;
import me.superskidder.lune.utils.gl.GLClientState;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;

import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;

public class RenderUtil {
    public static final Tessellation tessellator;
    private static final List<Integer> csBuffer;
    private static final Consumer<Integer> ENABLE_CLIENT_STATE;
    private static int lastScale;
    private static int lastScaleWidth;
    private static int lastScaleHeight;
    private static ResourceLocation shader = new ResourceLocation("shaders/post/blur.json");

    private static final Consumer<Integer> DISABLE_CLIENT_STATE;
    private static ShaderGroup blurShader;
    private static Minecraft mc = Minecraft.getMinecraft();
    private static Framebuffer buffer;

    public static float delta;
    private static Frustum frustrum;

    static {

        tessellator = Tessellation.createExpanding(4, 1.0f, 2.0f);
        csBuffer = new ArrayList<Integer>();
        ENABLE_CLIENT_STATE = GL11::glEnableClientState;
        DISABLE_CLIENT_STATE = GL11::glEnableClientState;
    }

    public static int width() {
        return new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth();
    }

    public static int height() {
        return new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight();
    }

    public static void drawBordered(double x2, double y2, double width, double height, double length, int innerColor, int outerColor) {
        Gui.drawRect(x2, y2, x2 + width, y2 + height, innerColor);
        Gui.drawRect(x2 - length, y2, x2, y2 + height, outerColor);
        Gui.drawRect(x2 - length, y2 - length, x2 + width, y2, outerColor);
        Gui.drawRect(x2 + width, y2 - length, x2 + width + length, y2 + height + length, outerColor);
        Gui.drawRect(x2 - length, y2 + height, x2 + width, y2 + height + length, outerColor);
    }

    public static double interpolation(final double newPos, final double oldPos) {
        return oldPos + (newPos - oldPos) * Minecraft.getMinecraft().timer.renderPartialTicks;
    }

    public static int getHexRGB(final int hex) {
        return 0xFF000000 | hex;
    }

    public static void drawCustomImage(final int x, final int y, final int width, final int height,
                                       final ResourceLocation image) {
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, (float) width, (float) height);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static void drawOutlineBox(BlockPos blockPos, Color color) {
        Minecraft.getMinecraft().getRenderManager();
        double x = blockPos.getX() - RenderManager.renderPosX;
        Minecraft.getMinecraft().getRenderManager();
        double y = blockPos.getY() - RenderManager.renderPosY;
        Minecraft.getMinecraft().getRenderManager();
        double z = blockPos.getZ() - RenderManager.renderPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.0F);
        GL11.glColor4d(0.0D, 1.0D, 0.0D, 0.1500000059604645D);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        color(color.getRGB(), 0.3F);
        drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D));
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }

    public static void color(int color, float alpha) {
        float red = (color >> 16 & 0xFF) / 255.0F;
        float green = (color >> 8 & 0xFF) / 255.0F;
        float blue = (color & 0xFF) / 255.0F;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void drawEntityESP(final double x, final double y, final double z, final double width,
                                     final double height, final float red, final float green, final float blue, final float alpha,
                                     final float lineRed, final float lineGreen, final float lineBlue, final float lineAlpha,
                                     final float lineWdith) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
        GL11.glLineWidth(lineWdith);
        GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
        drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width));
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public static void drawRoundedRect(float x, float y, float x2, float y2, final float round, final int color) {
        x += (float) (round / 2.0f + 0.5);
        y += (float) (round / 2.0f + 0.5);
        x2 -= (float) (round / 2.0f + 0.5);
        y2 -= (float) (round / 2.0f + 0.5);
        Gui.drawRect((int) x, (int) y, (int) x2, (int) y2, color);
        circle(x2 - round / 2.0f, y + round / 2.0f, round, color);
        circle(x + round / 2.0f, y2 - round / 2.0f, round, color);
        circle(x + round / 2.0f, y + round / 2.0f, round, color);
        circle(x2 - round / 2.0f, y2 - round / 2.0f, round, color);
        Gui.drawRect((int) (x - round / 2.0f - 0.5f), (int) (y + round / 2.0f), (int) x2, (int) (y2 - round / 2.0f),
                color);
        Gui.drawRect((int) x, (int) (y + round / 2.0f), (int) (x2 + round / 2.0f + 0.5f), (int) (y2 - round / 2.0f),
                color);
        Gui.drawRect((int) (x + round / 2.0f), (int) (y - round / 2.0f - 0.5f), (int) (x2 - round / 2.0f),
                (int) (y2 - round / 2.0f), color);
        Gui.drawRect((int) (x + round / 2.0f), (int) y, (int) (x2 - round / 2.0f), (int) (y2 + round / 2.0f + 0.5f),
                color);
    }

    public static void circle(final float x, final float y, final float radius, final int fill) {
        arc(x, y, 0.0f, 360.0f, radius, fill);
    }

    public static void circle(final float x, final float y, final float radius, final Color fill) {
        arc(x, y, 0.0f, 360.0f, radius, fill);
    }

    public static void arc(final float x, final float y, final float start, final float end, final float radius,
                           final int color) {
        arcEllipse(x, y, start, end, radius, radius, color);
    }

    public static void arc(final float x, final float y, final float start, final float end, final float radius,
                           final Color color) {
        arcEllipse(x, y, start, end, radius, radius, color);
    }

    public static void arcEllipse(final float x, final float y, float start, float end, final float w, final float h,
                                  final int color) {
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
        float temp = 0.0f;
        if (start > end) {
            temp = end;
            end = start;
            start = temp;
        }
        final float var11 = (color >> 24 & 0xFF) / 255.0f;
        final float var12 = (color >> 16 & 0xFF) / 255.0f;
        final float var13 = (color >> 8 & 0xFF) / 255.0f;
        final float var14 = (color & 0xFF) / 255.0f;
        final Tessellator var15 = Tessellator.getInstance();
        final WorldRenderer var16 = var15.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(var12, var13, var14, var11);
        if (var11 > 0.5f) {
            GL11.glEnable(2848);
            GL11.glLineWidth(2.0f);
            GL11.glBegin(3);
            for (float i = end; i >= start; i -= 4.0f) {
                final float ldx = (float) Math.cos(i * 3.141592653589793 / 180.0) * w * 1.001f;
                final float ldy = (float) Math.sin(i * 3.141592653589793 / 180.0) * h * 1.001f;
                GL11.glVertex2f(x + ldx, y + ldy);
            }
            GL11.glEnd();
            GL11.glDisable(2848);
        }
        GL11.glBegin(6);
        for (float i = end; i >= start; i -= 4.0f) {
            final float ldx = (float) Math.cos(i * 3.141592653589793 / 180.0) * w;
            final float ldy = (float) Math.sin(i * 3.141592653589793 / 180.0) * h;
            GL11.glVertex2f(x + ldx, y + ldy);
        }
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void arcEllipse(final float x, final float y, float start, float end, final float w, final float h,
                                  final Color color) {
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
        float temp = 0.0f;
        if (start > end) {
            temp = end;
            end = start;
            start = temp;
        }
        final Tessellator var9 = Tessellator.getInstance();
        final WorldRenderer var10 = var9.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f,
                color.getAlpha() / 255.0f);
        if (color.getAlpha() > 0.5f) {
            GL11.glEnable(2848);
            GL11.glLineWidth(2.0f);
            GL11.glBegin(3);
            for (float i = end; i >= start; i -= 4.0f) {
                final float ldx = (float) Math.cos(i * 3.141592653589793 / 180.0) * w * 1.001f;
                final float ldy = (float) Math.sin(i * 3.141592653589793 / 180.0) * h * 1.001f;
                GL11.glVertex2f(x + ldx, y + ldy);
            }
            GL11.glEnd();
            GL11.glDisable(2848);
        }
        GL11.glBegin(6);
        for (float i = end; i >= start; i -= 4.0f) {
            final float ldx = (float) Math.cos(i * 3.141592653589793 / 180.0) * w;
            final float ldy = (float) Math.sin(i * 3.141592653589793 / 180.0) * h;
            GL11.glVertex2f(x + ldx, y + ldy);
        }
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawBoundingBox(final AxisAlignedBB aa) {
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
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

    public static void drawOutlinedBoundingBox(final AxisAlignedBB aa) {
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
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

//    public static void drawBorderedRect(final double x, final double y, final double x2, final double d, final float l1,
//                                        final int col1, final int col2) {
//        Gui.drawRect(x, y, x2, d, col2);
//        final float f = (col1 >> 24 & 0xFF) / 255.0f;
//        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
//        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
//        final float f4 = (col1 & 0xFF) / 255.0f;
//        GL11.glEnable(3042);
//        GL11.glDisable(3553);
//        GL11.glBlendFunc(770, 771);
//        GL11.glEnable(2848);
//        GL11.glPushMatrix();
//        GL11.glColor4f(f2, f3, f4, f);
//        GL11.glLineWidth(l1);
//        GL11.glBegin(1);
//        GL11.glVertex2d(x, y);
//        GL11.glVertex2d(x, d);
//        GL11.glVertex2d(x2, d);
//        GL11.glVertex2d(x2, y);
//        GL11.glVertex2d(x, y);
//        GL11.glVertex2d(x2, y);
//        GL11.glVertex2d(x, d);
//        GL11.glVertex2d(x2, d);
//        GL11.glEnd();
//        GL11.glPopMatrix();
//        GL11.glEnable(3553);
//        GL11.glDisable(3042);
//        GL11.glDisable(2848);
//    }

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
        Minecraft.getMinecraft().entityRenderer.setupCameraTransform(Minecraft.getMinecraft().timer.renderPartialTicks, 0);
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

    public static void drawImage(final ResourceLocation image, final int x, final int y, final int width,
                                 final int height) {
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, (float) width, (float) height);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
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

    public static double getAnimationState(double animation, final double finalState, final double speed) {
        final float add = (float) (RenderUtil.delta * speed);
        animation = ((animation < finalState) ? ((animation + add < finalState) ? (animation += add) : finalState)
                : ((animation - add > finalState) ? (animation -= add) : finalState));
        return animation;
    }

    public static void drawRect(float x1, float y1, float x2, float y2, int color) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        color(color);
        GL11.glBegin(7);
        GL11.glVertex2d(x2, y1);
        GL11.glVertex2d(x1, y1);
        GL11.glVertex2d(x1, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }

    public static void color(int color) {
        float f = (color >> 24 & 0xFF) / 255.0F;
        float f1 = (color >> 16 & 0xFF) / 255.0F;
        float f2 = (color >> 8 & 0xFF) / 255.0F;
        float f3 = (color & 0xFF) / 255.0F;
        GL11.glColor4f(f1, f2, f3, f);
    }

    public static void entityESPBox(Entity e2, Color color, EventRender3D event) {
        double width;
        double height;
        float red;
        float green;
        float blue;
        float alpha;
        float lineRed;
        float lineGreen;
        float lineBlue;
        float lineAlpha;
        float lineWdith;
        Minecraft.getMinecraft().getRenderManager();
        double posX = e2.lastTickPosX + (e2.posX - e2.lastTickPosX) * (double) event.getPartialTicks()
                - RenderManager.renderPosX;
        Minecraft.getMinecraft().getRenderManager();
        double posY = e2.lastTickPosY + (e2.posY - e2.lastTickPosY) * (double) event.getPartialTicks()
                - RenderManager.renderPosY;
        Minecraft.getMinecraft().getRenderManager();
        double posZ = e2.lastTickPosZ + (e2.posZ - e2.lastTickPosZ) * (double) event.getPartialTicks()
                - RenderManager.renderPosZ;
        AxisAlignedBB box2 = AxisAlignedBB.fromBounds(posX - (double) e2.width, posY, posZ - (double) e2.width,
                posX + (double) e2.width, posY + (double) e2.height + 0.2, posZ + (double) e2.width);
        if (e2 instanceof EntityLivingBase) {
            box2 = AxisAlignedBB.fromBounds(posX - (double) e2.width + 0.2, posY, posZ - (double) e2.width + 0.2,
                    posX + (double) e2.width - 0.2, posY + (double) e2.height + (e2.isSneaking() ? 0.02 : 0.2),
                    posZ + (double) e2.width - 0.2);
        }
        GL11.glLineWidth(1.0f);
        GL11.glColor4f((float) color.getRed() / 255.0f, (float) color.getGreen() / 255.0f,
                (float) color.getBlue() / 255.0f, 1.0f);
//	        RenderUtil.drawOutlinedBoundingBox(box2);
        width = e2.getEntityBoundingBox().maxX - e2.getEntityBoundingBox().minX - 1;
        height = e2.getEntityBoundingBox().maxY - e2.getEntityBoundingBox().minY + 0.25;
//            red = 1.0f;
//            green = 1.5f;
//            blue = 1.0f;
        alpha = 0.2f;
//            lineRed = 1.0f;
//            lineGreen = 1.0f;
//            lineBlue = 1.0f;
        lineAlpha = 0.5f;
        lineWdith = 2.0f;
        red = color.getRed();
        green = color.getGreen();
        blue = color.getBlue();
//            alpha = color.getAlpha();
        lineRed = color.getRed();
        lineGreen = color.getGreen();
        lineBlue = color.getBlue();
//            lineAlpha = color.getAlpha();

        RenderUtil.drawEntityESP(posX, posY, posZ, width, height, red, green, blue, alpha, lineRed, lineGreen, lineBlue,
                0, lineWdith);
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
        GL11.glEnable((int) 3042);
        GL11.glDisable((int) 3553);
        GL11.glBlendFunc((int) 770, (int) 771);
        GL11.glEnable((int) 2848);
        GL11.glShadeModel((int) 7425);
        GL11.glPushMatrix();
        GL11.glBegin((int) 7);
        GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
        GL11.glVertex2d((double) left, (double) top);
        GL11.glVertex2d((double) left, (double) bottom);
        GL11.glColor4f((float) f5, (float) f6, (float) f7, (float) f4);
        GL11.glVertex2d((double) right, (double) bottom);
        GL11.glVertex2d((double) right, (double) top);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int) 3553);
        GL11.glDisable((int) 3042);
        GL11.glDisable((int) 2848);
        GL11.glShadeModel((int) 7424);
    }

    public static void drawRect(double d, double e, double f, double g, int color) {
        GL11.glPushMatrix();
        GL11.glEnable((int) 3042);
        GL11.glDisable((int) 3553);
        GL11.glBlendFunc((int) 770, (int) 771);
        GL11.glEnable((int) 2848);
        GL11.glPushMatrix();
        RenderUtil.color(color);
        GL11.glBegin((int) 7);
        GL11.glVertex2d((double) f, (double) e);
        GL11.glVertex2d((double) d, (double) e);
        GL11.glVertex2d((double) d, (double) g);
        GL11.glVertex2d((double) f, (double) g);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int) 3553);
        GL11.glDisable((int) 3042);
        GL11.glDisable((int) 2848);
        GL11.glPopMatrix();
    }
    public static void drawRect(double d, double e, double f, double g, Color color) {
        GL11.glPushMatrix();
        GL11.glEnable((int) 3042);
        GL11.glDisable((int) 3553);
        GL11.glBlendFunc((int) 770, (int) 771);
        GL11.glEnable((int) 2848);
        GL11.glPushMatrix();
        RenderUtil.color(color.getRGB());
        GL11.glBegin((int) 7);
        GL11.glVertex2d((double) f, (double) e);
        GL11.glVertex2d((double) d, (double) e);
        GL11.glVertex2d((double) d, (double) g);
        GL11.glVertex2d((double) f, (double) g);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int) 3553);
        GL11.glDisable((int) 3042);
        GL11.glDisable((int) 2848);
        GL11.glPopMatrix();
    }
    public static void rectangleBordered(double x, double y, double x1, double y1, double width, int internalColor,
                                         int borderColor) {
        RenderUtil.rectangle(x + width, y + width, x1 - width, y1 - width, internalColor);
        GlStateManager.color((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
        RenderUtil.rectangle(x + width, y, x1 - width, y + width, borderColor);
        GlStateManager.color((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
        RenderUtil.rectangle(x, y, x + width, y1, borderColor);
        GlStateManager.color((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
        RenderUtil.rectangle(x1 - width, y, x1, y1, borderColor);
        GlStateManager.color((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
        RenderUtil.rectangle(x + width, y1 - width, x1 - width, y1, borderColor);
        GlStateManager.color((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
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
        float var11 = (float) (color >> 24 & 255) / 255.0f;
        float var6 = (float) (color >> 16 & 255) / 255.0f;
        float var7 = (float) (color >> 8 & 255) / 255.0f;
        float var8 = (float) (color & 255) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((int) 770, (int) 771, (int) 1, (int) 0);
        GlStateManager.color((float) var6, (float) var7, (float) var8, (float) var11);
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(left, bottom, 0.0).endVertex();
        worldRenderer.pos(right, bottom, 0.0).endVertex();
        worldRenderer.pos(right, top, 0.0).endVertex();
        worldRenderer.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.color((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
    }

    public static void drawIcon(float x, float y, int sizex, int sizey, ResourceLocation resourceLocation) {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GL11.glTranslatef(x, y, 10.0F);
        drawScaledRect(0, 0, 0.0F, 0.0F, sizex, sizey, sizex, sizey, (float) sizex, (float) sizey);
        GlStateManager.disableAlpha();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();
        GlStateManager.disableRescaleNormal();
        GL11.glDisable(2848);
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }

    public static void drawScaledRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height,
                                      float tileWidth, float tileHeight) {
        Gui.drawScaledCustomSizeModalRect(x, y, u, v, uWidth, vHeight, width, height, tileWidth, tileHeight);
    }

    public static Vec3 interpolateRender(EntityPlayer player) {
        float part = Minecraft.getMinecraft().timer.renderPartialTicks;
        double interpX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) part;
        double interpY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) part;
        double interpZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) part;
        return new Vec3(interpX, interpY, interpZ);
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

    public static void post3D() {
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        GL11.glColor4f(1, 1, 1, 1);
    }

    public static void glColor(int hex) {
        float alpha = (hex >> 24 & 0xFF) / 255.0F;
        float red = (hex >> 16 & 0xFF) / 255.0F;
        float green = (hex >> 8 & 0xFF) / 255.0F;
        float blue = (hex & 0xFF) / 255.0F;
        GL11.glColor4f(red, green, blue, alpha);
    }

//    public static void blockESPBox(BlockPos blockPos) {
//        double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
//        double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
//        double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
//        GL11.glBlendFunc(770, 771);
//        GL11.glEnable(GL11.GL_BLEND);
//        GL11.glLineWidth(2.0F);
//        GL11.glColor4d(0, 1, 0, 0.15F);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GL11.glDisable(GL11.GL_DEPTH_TEST);
//        GL11.glDepthMask(false);
//        // drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
//        GL11.glColor4d(1, 0, 0, 0.5F);
//        RenderGlobal.func_181561_a(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glEnable(GL11.GL_DEPTH_TEST);
//        GL11.glDepthMask(true);
//        GL11.glDisable(GL11.GL_BLEND);
//    }

    public static void drawFilledCircle(float xx, float yy, float radius, Color col) {
        int sections = 50;
        double dAngle = 6.283185307179586 / (double) sections;
        GL11.glPushMatrix();
        GL11.glEnable((int) 3042);
        GL11.glDisable((int) 3553);
        GL11.glBlendFunc((int) 770, (int) 771);
        GL11.glEnable((int) 2848);
        GL11.glBegin((int) 6);
        int i = 0;
        while (i < sections) {
            float x = (float) ((double) radius * Math.sin((double) ((double) i * dAngle)));
            float y = (float) ((double) radius * Math.cos((double) ((double) i * dAngle)));
            GL11.glColor4f((float) ((float) col.getRed() / 255.0f), (float) ((float) col.getGreen() / 255.0f),
                    (float) ((float) col.getBlue() / 255.0f), (float) ((float) col.getAlpha() / 255.0f));
            GL11.glVertex2f((float) (xx + x), (float) (yy + y));
            ++i;
        }
        GlStateManager.color((float) 0.0f, (float) 0.0f, (float) 0.0f);
        GL11.glEnd();
        GL11.glEnable((int) 3553);
        GL11.glDisable((int) 3042);
        GL11.glDisable((int) 2848);
        GL11.glPopMatrix();
    }

    public static double[] convertTo2D(final double x, final double y, final double z) {
        final FloatBuffer screenCoords = BufferUtils.createFloatBuffer(3);
        final IntBuffer viewport = BufferUtils.createIntBuffer(16);
        final FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
        final FloatBuffer projection = BufferUtils.createFloatBuffer(16);
        GL11.glGetFloat(2982, modelView);
        GL11.glGetFloat(2983, projection);
        GL11.glGetInteger(2978, viewport);
        final boolean result = GLU.gluProject((float) x, (float) y, (float) z, modelView, projection, viewport,
                screenCoords);
        return (double[]) (result
                ? new double[]{screenCoords.get(0), Display.getHeight() - screenCoords.get(1), screenCoords.get(2)}
                : null);
    }

    public static void doGlScissor(float x, float y, int width, int height) {
        Minecraft mc = Minecraft.getMinecraft();
        int scaleFactor = 1;
        int k = mc.gameSettings.guiScale;
        if (k == 0) {
            k = 1000;
        }
        while (scaleFactor < k && mc.displayWidth / (scaleFactor + 1) >= 320
                && mc.displayHeight / (scaleFactor + 1) >= 240) {
            ++scaleFactor;
        }
        GL11.glScissor((int) (x * scaleFactor), (int) (mc.displayHeight - (y + height) * scaleFactor),
                (int) (width * scaleFactor), (int) (height * scaleFactor));

    }

    public static void drawCircle(double x, double y, double radius, int c) {
        float alpha = (float) (c >> 24 & 255) / 255.0f;
        float red = (float) (c >> 16 & 255) / 255.0f;
        float green = (float) (c >> 8 & 255) / 255.0f;
        float blue = (float) (c & 255) / 255.0f;
        boolean blend = GL11.glIsEnabled((int) 3042);
        boolean line = GL11.glIsEnabled((int) 2848);
        boolean texture = GL11.glIsEnabled((int) 3553);
        if (!blend) {
            GL11.glEnable((int) 3042);
        }
        if (!line) {
            GL11.glEnable((int) 2848);
        }
        if (texture) {
            GL11.glDisable((int) 3553);
        }
        GL11.glBlendFunc((int) 770, (int) 771);
        GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
        GL11.glBegin((int) 9);
        int i = 0;
        while (i <= 360) {
            GL11.glVertex2d(
                    (double) ((double) x + Math.sin((double) ((double) i * 3.141526 / 180.0)) * (double) radius),
                    (double) ((double) y + Math.cos((double) ((double) i * 3.141526 / 180.0)) * (double) radius));
            ++i;
        }

        GL11.glEnd();
        if (texture) {
            GL11.glEnable((int) 3553);
        }
        if (!line) {
            GL11.glDisable((int) 2848);
        }
        if (!blend) {
            GL11.glDisable((int) 3042);
        }
    }

    public static void drawFullCircle(int cx, int cy, double r, int segments, float lineWidth, int part, int c) {
        GL11.glScalef((float) 0.5f, (float) 0.5f, (float) 0.5f);
        r *= 2.0;
        cx *= 2;
        cy *= 2;
        float f2 = (float) (c >> 24 & 255) / 255.0f;
        float f22 = (float) (c >> 16 & 255) / 255.0f;
        float f3 = (float) (c >> 8 & 255) / 255.0f;
        float f4 = (float) (c & 255) / 255.0f;
        GL11.glEnable((int) 3042);
        GL11.glLineWidth((float) lineWidth);
        GL11.glDisable((int) 3553);
        GL11.glEnable((int) 2848);
        GL11.glBlendFunc((int) 770, (int) 771);
        GL11.glColor4f((float) f22, (float) f3, (float) f4, (float) f2);
        GL11.glBegin((int) 3);
        int i = segments - part;
        while (i <= segments) {
            double x = Math.sin((double) i * 3.141592653589793 / 180.0) * r;
            double y = Math.cos((double) i * 3.141592653589793 / 180.0) * r;
            GL11.glVertex2d((double) ((double) cx + x), (double) ((double) cy + y));
            ++i;
        }
        GL11.glEnd();
        GL11.glDisable((int) 2848);
        GL11.glEnable((int) 3553);
        GL11.glDisable((int) 3042);
        GL11.glScalef((float) 2.0f, (float) 2.0f, (float) 2.0f);
    }

    public static boolean isInViewFrustrum(AxisAlignedBB bb) {
        Entity current = Minecraft.getMinecraft().getRenderViewEntity();
        frustrum.setPosition(current.posX, current.posY, current.posZ);
        return frustrum.isBoundingBoxInFrustum(bb);
    }

    public static boolean isInViewFrustrum(Entity entity) {
        return RenderUtil.isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
    }

    public static void drawHollowBox(float x, float y, float x1, float y1, float thickness, int color) {
        RenderUtil.drawHorizontalLine(x, y, x1, thickness, color);
        RenderUtil.drawHorizontalLine(x, y1, x1, thickness, color);
        RenderUtil.drawVerticalLine(x, y, y1, thickness, color);
        RenderUtil.drawVerticalLine(x1 - thickness, y, y1, thickness, color);
    }

    public static void drawHorizontalLine(float x, float y, float x1, float thickness, int color) {
        RenderUtil.drawRect2(x, y, x1, y + thickness, color);
    }

    public static void drawRect2(double x, double y, double x2, double y2, int color) {
        RenderUtil.drawRect(x, y, x2, y2, color);
    }

    public static void drawVerticalLine(float x, float y, float y1, float thickness, int color) {
        RenderUtil.drawRect2(x, y, x + thickness, y1, color);
    }

    public static int rainbow(int delay) {
        double rainbow = Math.ceil((double) (System.currentTimeMillis() + (long) delay) / 10.0);
        return Color.getHSBColor((float) ((rainbow %= 360.0) / 360.0), 0.5f, 1.0f).getRGB();
    }

    public static void drawCircle2(final float x, final float y, final float radius, final int start, final int end) {
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        glColor(new Color(255, 255, 255).getRGB());
        GL11.glEnable(2848);
        GL11.glLineWidth(2.0f);
        GL11.glBegin(3);
        for (float i = (float) end; i >= start; i -= 4.0f) {
            GL11.glVertex2f((float) (x + Math.cos(i * 3.141592653589793 / 180.0) * (radius * 1.001f)),
                    (float) (y + Math.sin(i * 3.141592653589793 / 180.0) * (radius * 1.001f)));
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
    }

    public static void initFboAndShader(double d, int categoryY, int i, int j, float f) {
        try {

            blurShader = new ShaderGroup(mc.getTextureManager(), mc.getResourceManager(), mc.getFramebuffer(), shader);
            blurShader.createBindFramebuffers(mc.displayWidth, mc.displayHeight);
            buffer = new Framebuffer(mc.displayWidth, mc.displayHeight, true);
            buffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setShaderConfigs(float intensity, float blurWidth, float blurHeight) {
        blurShader.getShaders().get(0).getShaderManager().getShaderUniform("Radius").set(intensity);
        blurShader.getShaders().get(1).getShaderManager().getShaderUniform("Radius").set(intensity);

        blurShader.getShaders().get(0).getShaderManager().getShaderUniform("BlurDir").set(blurWidth, blurHeight);
        blurShader.getShaders().get(1).getShaderManager().getShaderUniform("BlurDir").set(blurHeight, blurWidth);
    }

    public static void blurArea(int x, int y, int width, int height, float intensity, float blurWidth,
                                float blurHeight) {
        ScaledResolution scale = new ScaledResolution(mc);
        int factor = scale.getScaleFactor();
        int factor2 = scale.getScaledWidth();
        int factor3 = scale.getScaledHeight();
        if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3 || buffer == null
                || blurShader == null) {
            initFboAndShader(blurHeight, factor3, factor3, factor3, blurHeight);
        }
        lastScale = factor;
        lastScaleWidth = factor2;
        lastScaleHeight = factor3;

        if (OpenGlHelper.isFramebufferEnabled()) {

            buffer.framebufferClear();

            GL11.glScissor(x * factor, (mc.displayHeight - (y * factor) - height * factor), width * factor,
                    height * factor);
            GL11.glEnable(GL11.GL_SCISSOR_TEST);

            setShaderConfigs(intensity, blurWidth, blurHeight);
            buffer.bindFramebuffer(true);
            blurShader.loadShaderGroup(mc.timer.renderPartialTicks);

            mc.getFramebuffer().bindFramebuffer(true);

            GL11.glDisable(GL11.GL_SCISSOR_TEST);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ZERO,
                    GL11.GL_ONE);
            // buffer.func_178038_a(mc.displayWidth, mc.displayHeight, false);
            GlStateManager.disableBlend();
            GL11.glScalef(factor, factor, 0);

        }
    }

    public static void blurArea(float left, float f, float g, float h, float intensity) {
        intensity = Math.max(intensity, 1);
        ScaledResolution scale = new ScaledResolution(mc);
        int factor = scale.getScaleFactor();
        int factor2 = scale.getScaledWidth();
        int factor3 = scale.getScaledHeight();
        if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3 || buffer == null
                || blurShader == null) {
            initFboAndShader(intensity, factor3, factor3, factor3, intensity);
        }
        lastScale = factor;
        lastScaleWidth = factor2;
        lastScaleHeight = factor3;

        buffer.framebufferClear();

        GL11.glScissor((int) (left * factor), (int) (mc.displayHeight - (f * factor) - h * factor), (int) (g * factor),
                (int) (h) * factor);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);

        setShaderConfigs(intensity, 1, 0);
        buffer.bindFramebuffer(true);
        blurShader.loadShaderGroup(mc.timer.renderPartialTicks);

        mc.getFramebuffer().bindFramebuffer(true);

        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ZERO, GL11.GL_ONE);
        // buffer.func_178038_a(mc.displayWidth, mc.displayHeight, false);
        GlStateManager.disableBlend();
        RenderHelper.enableGUIStandardItemLighting();
    }

    public static void blurAreaBoarder(int x, int y, int width, int height, float intensity, float blurWidth,
                                       float blurHeight) {
        ScaledResolution scale = new ScaledResolution(mc);
        int factor = scale.getScaleFactor();
        int factor2 = scale.getScaledWidth();
        int factor3 = scale.getScaledHeight();
        if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3 || buffer == null
                || blurShader == null) {
            initFboAndShader(blurHeight, factor3, factor3, factor3, blurHeight);
        }
        lastScale = factor;
        lastScaleWidth = factor2;
        lastScaleHeight = factor3;

        GL11.glScissor(x * factor, (mc.displayHeight - (y * factor) - height * factor), width * factor,
                height * factor);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);

        setShaderConfigs(intensity, blurWidth, blurHeight);
        buffer.bindFramebuffer(true);
        blurShader.loadShaderGroup(mc.timer.renderPartialTicks);

        mc.getFramebuffer().bindFramebuffer(true);

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    public static void blurAreaBoarder(int x, int y, int width, int height, float intensity) {
        ScaledResolution scale = new ScaledResolution(mc);
        int factor = scale.getScaleFactor();
        int factor2 = scale.getScaledWidth();
        int factor3 = scale.getScaledHeight();
        if (lastScale != factor || lastScaleWidth != factor2 || lastScaleHeight != factor3 || buffer == null
                || blurShader == null) {
            initFboAndShader(intensity, factor3, factor3, factor3, intensity);
        }
        lastScale = factor;
        lastScaleWidth = factor2;
        lastScaleHeight = factor3;

        GL11.glScissor(x * factor, (mc.displayHeight - (y * factor) - height * factor), width * factor,
                (height) * factor);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);

        setShaderConfigs(intensity, 1, 0);
        buffer.bindFramebuffer(true);

        blurShader.loadShaderGroup(mc.timer.renderPartialTicks);

        mc.getFramebuffer().bindFramebuffer(true);

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

//    public static void blockESPBox(BlockPos blockPos, int red, int green, int blue, float linewidth) {
//        double x = blockPos.getX() - Minecraft.getMinecraft().getRenderManager().renderPosX;
//        double y = blockPos.getY() - Minecraft.getMinecraft().getRenderManager().renderPosY;
//        double z = blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
//        GL11.glBlendFunc(770, 771);
//        GL11.glEnable(GL11.GL_BLEND);
//        GL11.glLineWidth(linewidth);
//        GL11.glColor4d(red, green, blue, 0.15F);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GL11.glDisable(GL11.GL_DEPTH_TEST);
//        GL11.glDepthMask(false);
//        // drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
//        GL11.glColor4d(red, green, blue, 0.5F);
//        // drawBox
//        RenderGlobal.func_181561_a(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        GL11.glEnable(GL11.GL_DEPTH_TEST);
//        GL11.glDepthMask(true);
//        GL11.glDisable(GL11.GL_BLEND);
//    }

    public static Color rainbow(long time, float count, float fade) {
        float hue = ((float) time + (1.0F + count) * 2.0E8F) / 1.0E10F % 1.0F;
        long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()), 16);
        Color c = new Color((int) color);
        return new Color((float) c.getRed() / 255.0F * fade, (float) c.getGreen() / 255.0F * fade, (float) c.getBlue() / 255.0F * fade, (float) c.getAlpha() / 255.0F);
    }

    public static void entityESPBox2(Entity e, Color color, EventRender3D event) {
        double posX = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double) event.getPartialTicks() - Minecraft.getMinecraft().getRenderManager().renderPosX;
        double posY = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double) event.getPartialTicks() - Minecraft.getMinecraft().getRenderManager().renderPosY;
        double posZ = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double) event.getPartialTicks() - Minecraft.getMinecraft().getRenderManager().renderPosZ;
        GL11.glColor4f((float) ((float) color.getRed() / 255.0f), (float) ((float) color.getGreen() / 255.0f), (float) ((float) color.getBlue() / 255.0f), color.getAlpha() / 255.0f);
        RenderUtil.drawBoundingBox((AxisAlignedBB) new AxisAlignedBB(posX + 0.5, posY, posZ - 0.5, posX - 0.5, posY + 1.9, posZ + 0.5));

    }

    public static class R2DUtils {
        public static void enableGL2D() {
            GL11.glDisable((int) 2929);
            GL11.glEnable((int) 3042);
            GL11.glDisable((int) 3553);
            GL11.glBlendFunc((int) 770, (int) 771);
            GL11.glDepthMask((boolean) true);
            GL11.glEnable((int) 2848);
            GL11.glHint((int) 3154, (int) 4354);
            GL11.glHint((int) 3155, (int) 4354);
        }

        public static void disableGL2D() {
            GL11.glEnable((int) 3553);
            GL11.glDisable((int) 3042);
            GL11.glEnable((int) 2929);
            GL11.glDisable((int) 2848);
            GL11.glHint((int) 3154, (int) 4352);
            GL11.glHint((int) 3155, (int) 4352);
        }

        public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
            R2DUtils.enableGL2D();
            GL11.glScalef((float) 0.5f, (float) 0.5f, (float) 0.5f);
            R2DUtils.drawVLine(x *= 2.0f, (y *= 2.0f) + 1.0f, (y1 *= 2.0f) - 2.0f, borderC);
            R2DUtils.drawVLine((x1 *= 2.0f) - 1.0f, y + 1.0f, y1 - 2.0f, borderC);
            R2DUtils.drawHLine(x + 2.0f, x1 - 3.0f, y, borderC);
            R2DUtils.drawHLine(x + 2.0f, x1 - 3.0f, y1 - 1.0f, borderC);
            R2DUtils.drawHLine(x + 1.0f, x + 1.0f, y + 1.0f, borderC);
            R2DUtils.drawHLine(x1 - 2.0f, x1 - 2.0f, y + 1.0f, borderC);
            R2DUtils.drawHLine(x1 - 2.0f, x1 - 2.0f, y1 - 2.0f, borderC);
            R2DUtils.drawHLine(x + 1.0f, x + 1.0f, y1 - 2.0f, borderC);
            R2DUtils.drawRect(x + 1.0f, y + 1.0f, x1 - 1.0f, y1 - 1.0f, insideC);
            GL11.glScalef((float) 2.0f, (float) 2.0f, (float) 2.0f);
            R2DUtils.disableGL2D();
            Gui.drawRect(0, 0, 0, 0, 0);
        }

        public static void drawRect(double x2, double y2, double x1, double y1, int color) {
            R2DUtils.enableGL2D();
            R2DUtils.glColor(color, color, color, color);
//							R2DUtils.drawRect(x2, y2, x1, y1, color);
            R2DUtils.drawRect(x2, y2, x1, y1);
            R2DUtils.disableGL2D();
        }

        private static void drawRect(double x2, double y2, double x1, double y1) {
            GL11.glBegin((int) 7);
            GL11.glVertex2d((double) x2, (double) y1);
            GL11.glVertex2d((double) x1, (double) y1);
            GL11.glVertex2d((double) x1, (double) y2);
            GL11.glVertex2d((double) x2, (double) y2);
            GL11.glEnd();
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

        public static void drawGradientRect(float x, float y, float x1, float y1, int topColor, int bottomColor) {
            R2DUtils.enableGL2D();
            GL11.glShadeModel((int) 7425);
            GL11.glBegin((int) 7);
            RenderUtil.glColor(topColor);
            GL11.glVertex2f((float) x, (float) y1);
            GL11.glVertex2f((float) x1, (float) y1);
            RenderUtil.glColor(bottomColor);
            GL11.glVertex2f((float) x1, (float) y);
            GL11.glVertex2f((float) x, (float) y);
            GL11.glEnd();
            GL11.glShadeModel((int) 7424);
            R2DUtils.disableGL2D();
        }

        public static void glColor(float alpha, int redRGB, int greenRGB, int blueRGB) {
            float red = 0.003921569F * redRGB;
            float green = 0.003921569F * greenRGB;
            float blue = 0.003921569F * blueRGB;
            GL11.glColor4f(red, green, blue, alpha);
        }
    }

    public static void drawblock(double a, double a2, double a3, int a4, int a5, float a6) {
        float a7 = (float) (a4 >> 24 & 255) / 255.0f;
        float a8 = (float) (a4 >> 16 & 255) / 255.0f;
        float a9 = (float) (a4 >> 8 & 255) / 255.0f;
        float a10 = (float) (a4 & 255) / 255.0f;
        float a11 = (float) (a5 >> 24 & 255) / 255.0f;
        float a12 = (float) (a5 >> 16 & 255) / 255.0f;
        float a13 = (float) (a5 >> 8 & 255) / 255.0f;
        float a14 = (float) (a5 & 255) / 255.0f;
        org.lwjgl.opengl.GL11.glPushMatrix();
        org.lwjgl.opengl.GL11.glEnable((int) 3042);
        org.lwjgl.opengl.GL11.glBlendFunc((int) 770, (int) 771);
        org.lwjgl.opengl.GL11.glDisable((int) 3553);
        org.lwjgl.opengl.GL11.glEnable((int) 2848);
        org.lwjgl.opengl.GL11.glDisable((int) 2929);
        org.lwjgl.opengl.GL11.glDepthMask((boolean) false);
        org.lwjgl.opengl.GL11.glColor4f((float) a8, (float) a9, (float) a10, (float) a7);
        drawOutlinedBoundingBox(new AxisAlignedBB(a, a2, a3, a + 1.0, a2 + 1.0, a3 + 1.0));
        org.lwjgl.opengl.GL11.glLineWidth((float) a6);
        org.lwjgl.opengl.GL11.glColor4f((float) a12, (float) a13, (float) a14, (float) a11);
        drawOutlinedBoundingBox(new AxisAlignedBB(a, a2, a3, a + 1.0, a2 + 1.0, a3 + 1.0));
        org.lwjgl.opengl.GL11.glDisable((int) 2848);
        org.lwjgl.opengl.GL11.glEnable((int) 3553);
        org.lwjgl.opengl.GL11.glEnable((int) 2929);
        org.lwjgl.opengl.GL11.glDepthMask((boolean) true);
        org.lwjgl.opengl.GL11.glDisable((int) 3042);
        org.lwjgl.opengl.GL11.glPopMatrix();
    }

    public static void drawArc(float n, float n2, double n3, final int n4, final int n5, final double n6,
                               final int n7) {
        n3 *= 2.0;
        n *= 2.0f;
        n2 *= 2.0f;
        final float n8 = (n4 >> 24 & 0xFF) / 255.0f;
        final float n9 = (n4 >> 16 & 0xFF) / 255.0f;
        final float n10 = (n4 >> 8 & 0xFF) / 255.0f;
        final float n11 = (n4 & 0xFF) / 255.0f;
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glLineWidth((float) n7);
        GL11.glEnable(2848);
        GL11.glColor4f(n9, n10, n11, n8);
        GL11.glBegin(3);
        int n12 = n5;
        while (n12 <= n6) {
            GL11.glVertex2d(n + Math.sin(n12 * 3.141592653589793 / 180.0) * n3,
                    n2 + Math.cos(n12 * 3.141592653589793 / 180.0) * n3);
            ++n12;
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);

    }

    public static void drawFancy(double d, double e, double f2, double f3, int paramColor) {
        float alpha = (float) (paramColor >> 24 & 255) / 255.0F;
        float red = (float) (paramColor >> 16 & 255) / 255.0F;
        float green = (float) (paramColor >> 8 & 255) / 255.0F;
        float blue = (float) (paramColor & 255) / 255.0F;
        GlStateManager.enableBlend();
        GlStateManager.disableTextures();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glPushMatrix();
        GL11.glEnable(2848);
        GL11.glEnable(2881);
        GL11.glEnable(2832);
        GL11.glEnable(3042);
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(7);
        GL11.glVertex2d(f2 + 1.300000011920929D, e);
        GL11.glVertex2d(d + 1.0D, e);
        GL11.glVertex2d(d - 1.300000011920929D, f3);
        GL11.glVertex2d(f2 - 1.0D, f3);
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glDisable(2881);
        GL11.glDisable(2832);
        GL11.glDisable(3042);
        GlStateManager.enableTextures();
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }

//    public static void rectTexture(float x, float y, float w, float h, Texture texture, int color) {
//        if (texture == null) {
//            return;
//        }
//        GlStateManager.color(0, 0, 0);
//        GL11.glColor4f(0, 0, 0, 0);
//
//        x = Math.round(x);
//        w = Math.round(w);
//        y = Math.round(y);
//        h = Math.round(h);
//
//        float var11 = (float) (color >> 24 & 255) / 255.0F;
//        float var6 = (float) (color >> 16 & 255) / 255.0F;
//        float var7 = (float) (color >> 8 & 255) / 255.0F;
//        float var8 = (float) (color & 255) / 255.0F;
//
//        GlStateManager.enableBlend();
//        GlStateManager.disableTexture2D();
//        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
//        GlStateManager.color(var6, var7, var8, var11);
//
//        GL11.glEnable(GL11.GL_BLEND);
//        GL11.glEnable(GL11.GL_TEXTURE_2D);
//        texture.bind();
//
//        float tw = (w / texture.getTextureWidth()) / (w / texture.getImageWidth());
//        float th = (h / texture.getTextureHeight()) / (h / texture.getImageHeight());
//
//        GL11.glBegin(GL11.GL_QUADS);
//        GL11.glTexCoord2f(0, 0);
//        GL11.glVertex2f(x, y);
//        GL11.glTexCoord2f(0, th);
//        GL11.glVertex2f(x, y + h);
//        GL11.glTexCoord2f(tw, th);
//        GL11.glVertex2f(x + w, y + h);
//        GL11.glTexCoord2f(tw, 0);
//        GL11.glVertex2f(x + w, y);
//        GL11.glEnd();
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        GL11.glDisable(GL11.GL_BLEND);
//        GlStateManager.enableTexture2D();
//        GlStateManager.disableBlend();
//    }

    public static void drawSolidBlockESP(double x, double y, double z, float red, float green, float blue,
                                         float alpha) {
        GL11.glPushMatrix();
        GL11.glEnable((int) 3042);
        GL11.glBlendFunc((int) 770, (int) 771);
        GL11.glDisable((int) 3553);
        GL11.glEnable((int) 2848);
        GL11.glDisable((int) 2929);
        GL11.glDepthMask((boolean) false);
        GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
        RenderUtil.drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glColor3f(1, 1, 1);
        GL11.glDisable((int) 2848);
        GL11.glEnable((int) 3553);
        GL11.glEnable((int) 2929);
        GL11.glDepthMask((boolean) true);
        GL11.glDisable((int) 3042);
        GL11.glPopMatrix();
    }

    public static void renderItem(final ItemStack item, final int xPos, final int yPos, final int zPos) {
        GL11.glPushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.enableBlend();
        GL11.glBlendFunc(770, 771);
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        final IBakedModel ibakedmodel = mc.getRenderItem().getItemModelMesher().getItemModel(item);
        mc.getRenderItem().textureManager.bindTexture(TextureMap.locationBlocksTexture);
        GlStateManager.scale(16.0f, 16.0f, 0.0f);
        GL11.glTranslated((double) ((xPos - 7.85f) / 16.0f), (double) ((-5 + yPos) / 16.0f), (double) (zPos / 16.0f));
        GlStateManager.rotate(180.0f, 1.0f, 0.0f, 0.0f);
        GlStateManager.disableLighting();
        ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GUI);
        if (ibakedmodel.isBuiltInRenderer()) {
            GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(-0.5f, -0.5f, -0.5f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.enableRescaleNormal();
            TileEntityItemStackRenderer.instance.renderByItem(item);
        } else {
            mc.getRenderItem().renderModel(ibakedmodel, -1, item);
        }
        GlStateManager.enableAlpha();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
    }

    public static void drawEntityOnScreen(int p_147046_0_, int p_147046_1_, int p_147046_2_, float p_147046_3_,
                                          float p_147046_4_, EntityLivingBase p_147046_5_) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate(p_147046_0_, p_147046_1_, 40.0f);
        GlStateManager.scale(-p_147046_2_, p_147046_2_, p_147046_2_);
        GlStateManager.rotate(180.0f, 0.0f, 0.0f, 1.0f);
        float var6 = p_147046_5_.renderYawOffset;
        float var7 = p_147046_5_.rotationYaw;
        float var8 = p_147046_5_.rotationPitch;
        float var9 = p_147046_5_.prevRotationYawHead;
        float var10 = p_147046_5_.rotationYawHead;
        GlStateManager.rotate(135.0f, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0f, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate((-(float) Math.atan(p_147046_4_ / 40.0f)) * 20.0f, 1.0f, 0.0f, 0.0f);
        p_147046_5_.renderYawOffset = (float) Math.atan(p_147046_3_ / 40.0f) * -14.0f;
        p_147046_5_.rotationYaw = (float) Math.atan(p_147046_3_ / 40.0f) * -14.0f;
        p_147046_5_.rotationPitch = (-(float) Math.atan(p_147046_4_ / 40.0f)) * 15.0f;
        p_147046_5_.rotationYawHead = p_147046_5_.rotationYaw;
        p_147046_5_.prevRotationYawHead = p_147046_5_.rotationYaw;
        GlStateManager.translate(0.0f, 0.0f, 0.0f);
        RenderManager var11 = Minecraft.getMinecraft().getRenderManager();
        var11.setPlayerViewY(180.0f);
        var11.setRenderShadow(false);
        var11.renderEntityWithPosYaw(p_147046_5_, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        var11.setRenderShadow(true);
        p_147046_5_.renderYawOffset = var6;
        p_147046_5_.rotationYaw = var7;
        p_147046_5_.rotationPitch = var8;
        p_147046_5_.prevRotationYawHead = var9;
        p_147046_5_.rotationYawHead = var10;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    public static void enableSmoothLine(float width) {
        GL11.glDisable((int) 3008);
        GL11.glEnable((int) 3042);
        GL11.glBlendFunc((int) 770, (int) 771);
        GL11.glDisable((int) 3553);
        GL11.glDisable((int) 2929);
        GL11.glDepthMask((boolean) false);
        GL11.glEnable((int) 2884);
        GL11.glEnable((int) 2848);
        GL11.glHint((int) 3154, (int) 4354);
        GL11.glHint((int) 3155, (int) 4354);
        GL11.glLineWidth((float) width);
    }

    public static void disableSmoothLine() {
        GL11.glEnable((int) 3553);
        GL11.glEnable((int) 2929);
        GL11.glDisable((int) 3042);
        GL11.glEnable((int) 3008);
        GL11.glDepthMask((boolean) true);
        GL11.glCullFace((int) 1029);
        GL11.glDisable((int) 2848);
        GL11.glHint((int) 3154, (int) 4352);
        GL11.glHint((int) 3155, (int) 4352);
    }

    public static void drawOutline(double x, double y, double width, double height, double lineWidth, int color) {
        RenderUtil.drawRect(x, y, x + width, y + lineWidth, color);
        RenderUtil.drawRect(x, y, x + lineWidth, y + height, color);
        RenderUtil.drawRect(x, y + height - lineWidth, x + width, y + height, color);
        RenderUtil.drawRect(x + width - lineWidth, y, x + width, y + height, color);
    }

    public static double interpolate(double newPos, double oldPos) {
        return oldPos + (newPos - oldPos) * (double) Minecraft.getMinecraft().timer.renderPartialTicks;
    }

    public static double interpolate(double current, double old, double scale) {
        return old + (current - old) * scale;
    }
}
