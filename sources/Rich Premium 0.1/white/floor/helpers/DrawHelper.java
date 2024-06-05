package white.floor.helpers;

import clickgui.util.GLUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import white.floor.Main;
import white.floor.features.Feature;
import white.floor.features.impl.combat.KillAura;
import white.floor.features.impl.combat.KillauraTest;
import white.floor.features.impl.visuals.JumpCircle;
import white.floor.features.impl.visuals.TargetESP;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

public class DrawHelper {

    private static Minecraft mc = Minecraft.getMinecraft();
    private static int time;
    private static float animtest;
    private static boolean anim;
    private static int test;
    private static float alpheble;
    protected static float zLevel;

    private static final Frustum frustrum = new Frustum();

    public static void drawOutline(final AxisAlignedBB axisAlignedBB, final float width, final Color color) {
        glPushMatrix();
        glLineWidth(width);
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(3, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        buffer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(color.getRed(), color.getGreen(), color.getBlue(), 100).endVertex();
        tessellator.draw();
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);
        glPopMatrix();
    }

    public static void drawSelectionBoundingBox(AxisAlignedBB boundingBox) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder worldrenderer = tessellator.getBuffer();

        worldrenderer.begin(GL_LINE_STRIP, DefaultVertexFormats.POSITION);

        // Lower Rectangle
        worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();

        // Upper Rectangle
        worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();

        // Upper Rectangle
        worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();

        worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();

        worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();

        tessellator.draw();
    }

    public static void drawFilledBox(final AxisAlignedBB axisAlignedBB) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder worldRenderer = tessellator.getBuffer();

        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();

        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();

        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();

        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();

        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();

        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        tessellator.draw();
    }

    public static void startSmooth() {
        GL11.glEnable(2848);
        GL11.glEnable(2881);
        GL11.glEnable(2832);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glHint(3153, 4354);
    }

    public static void endSmooth() {
        GL11.glDisable(2848);
        GL11.glDisable(2881);
        GL11.glEnable(2832);
    }

    public static void enableGL2D() {
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(true);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
    }

    public static void glColor(final Color color, final float alpha) {
        final float red = color.getRed() / 255F;
        final float green = color.getGreen() / 255F;
        final float blue = color.getBlue() / 255F;

        GlStateManager.color(red, green, blue, alpha);
    }

    public static void glColor(final Color color, final int alpha) {
        glColor(color, alpha/255F);
    }

    public static void disableGL2D() {
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static void enableSmoothLine(float width) {
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glEnable(2884);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glHint(3155, 4354);
        GL11.glLineWidth(width);
    }

    public static void disableSmoothLine() {
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glDepthMask(true);
        GL11.glCullFace(1029);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glHint(3155, 4352);
    }

    public static void drawImage(ResourceLocation image, int x, int y, int width, int height, int color) {
        glPushMatrix();
        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
        OpenGlHelper.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        DrawHelper.setColor(color);
        mc.getTextureManager().bindTexture(image);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, width, height);
        glDepthMask(true);
        glEnable(GL_DEPTH_TEST);
        glPopMatrix();
    }

    public static int setColor(int colorHex) {
        float alpha = (colorHex >> 24 & 0xFF) / 255.0F;
        float red = (colorHex >> 16 & 0xFF) / 255.0F;
        float green = (colorHex >> 8 & 0xFF) / 255.0F;
        float blue = (colorHex & 0xFF) / 255.0F;
        GL11.glColor4f(red, green, blue, (alpha == 0.0F) ? 1.0F : alpha);
        return colorHex;
    }

    public static void drawGlow(final double x, final double y, final double x1, final double y1, final int color) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        drawVGradientRect((float) (int) x, (float) (int) y, (float) (int) x1, (float) (int) (y + (y1 - y) / 2.0), DrawHelper.setAlpha(new Color(color), 0).getRGB(), color);
        drawVGradientRect((float) (int) x, (float) (int) (y + (y1 - y) / 2.0), (float) (int) x1, (float) (int) y1, color, DrawHelper.setAlpha(new Color(color), 0).getRGB());
        final int radius = (int) ((y1 - y) / 2.0);
        drawPolygonPart(x, y + (y1 - y) / 2.0, radius, 0, color, DrawHelper.setAlpha(new Color(color), 0).getRGB());
        drawPolygonPart(x, y + (y1 - y) / 2.0, radius, 1, color, DrawHelper.setAlpha(new Color(color), 0).getRGB());
        drawPolygonPart(x1, y + (y1 - y) / 2.0, radius, 2, color, DrawHelper.setAlpha(new Color(color), 0).getRGB());
        drawPolygonPart(x1, y + (y1 - y) / 2.0, radius, 3, color, DrawHelper.setAlpha(new Color(color), 0).getRGB());
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawPolygonPart(final double x, final double y, final int radius, final int part, final int color, final int endcolor) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final float alpha2 = (endcolor >> 24 & 0xFF) / 255.0f;
        final float red2 = (endcolor >> 16 & 0xFF) / 255.0f;
        final float green2 = (endcolor >> 8 & 0xFF) / 255.0f;
        final float blue2 = (endcolor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, y, 0.0).color(red, green, blue, alpha).endVertex();
        final double TWICE_PI = 6.283185307179586;
        for (int i = part * 90; i <= part * 90 + 90; ++i) {
            final double angle = 6.283185307179586 * i / 360.0 + Math.toRadians(180.0);
            bufferbuilder.pos(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, 0.0).color(red2, green2, blue2, alpha2).endVertex();
        }
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    public static void drawVGradientRect(final float left, final float top, final float right, final float bottom, final int startColor, final int endColor) {
        final float f = (startColor >> 24 & 0xFF) / 255.0f;
        final float f2 = (startColor >> 16 & 0xFF) / 255.0f;
        final float f3 = (startColor >> 8 & 0xFF) / 255.0f;
        final float f4 = (startColor & 0xFF) / 255.0f;
        final float f5 = (endColor >> 24 & 0xFF) / 255.0f;
        final float f6 = (endColor >> 16 & 0xFF) / 255.0f;
        final float f7 = (endColor >> 8 & 0xFF) / 255.0f;
        final float f8 = (endColor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double) right, (double) top, 0.0).color(f2, f3, f4, f).endVertex();
        bufferbuilder.pos((double) left, (double) top, 0.0).color(f2, f3, f4, f).endVertex();
        bufferbuilder.pos((double) left, (double) bottom, 0.0).color(f6, f7, f8, f5).endVertex();
        bufferbuilder.pos((double) right, (double) bottom, 0.0).color(f6, f7, f8, f5).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void jelloCircle(double speed, double size, double alpha, EntityLivingBase targed) {
        if (KillauraTest.checkTarget(targed) && targed.getHealth() > 0 && Minecraft.player.getDistanceToEntity(targed) <= (float) Main.settingsManager.getSettingByName(Main.featureDirector.getModule(KillauraTest.class), "Range").getValFloat() && !targed.isDead) {
            double height = 0.8 * (1 + Math.sin(2 * Math.PI * (Feature.deltaTime() * .3)));

            final double x = targed.lastTickPosX + (targed.posX - targed.lastTickPosX) * mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosX;
            final double y = targed.lastTickPosY + (targed.posY - targed.lastTickPosY) * mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosY;
            final double z = targed.lastTickPosZ + (targed.posZ - targed.lastTickPosZ) * mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosZ;

            GlStateManager.enableBlend();
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_LINE_SMOOTH);

            if(Main.settingsManager.getSettingByName(Main.featureDirector.getModule(TargetESP.class), "DepthTest").getValBoolean())
                GlStateManager.disableDepth();

            GlStateManager.disableTexture2D();
            GlStateManager.disableAlpha();
            GL11.glLineWidth(2f);
            GL11.glShadeModel(GL11.GL_SMOOTH);
            GL11.glDisable(GL11.GL_CULL_FACE);


            if (test <= 10) {

                if (anim)
                {
                    animtest = MathHelper.lerp(animtest, 0.05 + targed.height, speed * 100 * Feature.deltaTime());
                    if (animtest > 0.04 + targed.height) anim = false;
                }
                else
                {
                    animtest = MathHelper.lerp(animtest, 0.05, speed * 100 * Feature.deltaTime());
                    if (animtest < 0.06) anim = true;
                }

                test = 10;
            }
            test--;
            double gg = mc.player.onGround ? 0.35 : 0.65;
            double y2 = 0;
            y2 += targed.getEyeHeight() - (targed.isSneaking() ? 0.25D : 0.0D);

            if (mc.player.posY < targed.posY) {
                if (animtest <= y + (mc.player.posY > targed.posY ? targed.posY - mc.player.posY : mc.player.posY - targed.posY)) {
                    anim = true;
                } else if (animtest >= y + y2 + gg + (mc.player.posY > targed.posY ? targed.posY - mc.player.posY : mc.player.posY - targed.posY)) {
                    anim = false;
                }
            } else {
                if (animtest <= y - (mc.player.posY > targed.posY ? targed.posY - mc.player.posY : mc.player.posY - targed.posY)) {
                    anim = true;
                } else if (animtest >= y + y2 + gg - (mc.player.posY > targed.posY ? targed.posY - mc.player.posY : mc.player.posY - targed.posY) - 0.1) {
                    anim = false;
                }
            }

            GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
            {
                for (int j = 0; j < 361; j++) {
                    DrawHelper.color(setAlpha(DrawHelper.astolfoColors45(j - j + 1, j, 0.5f, 10), (int) (255 * (alpha - height))));
                    double x1 = x + Math.cos(Math.toRadians(j)) * size;
                    double z1 = z - Math.sin(Math.toRadians(j)) * size;
                    GL11.glVertex3d(x1, y + animtest, z1);
                    DrawHelper.color(setAlpha(DrawHelper.astolfoColors45(j - j + 1, j, 0.5f, 10), 0));

                    double lox = 0;

                        lox = MathHelper.lerp(lox, -animtest, 0.2);

                        GL11.glVertex3d(x1, y + animtest + (lox * height), z1);
                }
            }
            GL11.glEnd();
            GL11.glBegin(GL_LINE_LOOP);
            {
                for (int j = 0; j < 360; j++) {
                    DrawHelper.setColor(DrawHelper.astolfoColors45(j - j + 15, j, 0.5f, 10).getRGB());

                    GL11.glVertex3d(x + Math.cos(Math.toRadians(j)) * size, y + animtest, z - Math.sin(Math.toRadians(j)) * size);

                }
            }
            GL11.glEnd();
            GlStateManager.enableAlpha();
            GL11.glShadeModel(GL11.GL_FLAT);
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GlStateManager.enableTexture2D();

            if(Main.settingsManager.getSettingByName(Main.featureDirector.getModule(TargetESP.class), "DepthTest").getValBoolean())
                GlStateManager.enableDepth();

            GlStateManager.disableBlend();
            GlStateManager.resetColor();
        }
    }

    public static void drawBorderedRect(double left, double top, double right, double bottom, double borderWidth,
                                        int insideColor, int borderColor, boolean borderIncludedInBounds) {
        DrawHelper.drawRect(left - (!borderIncludedInBounds ? borderWidth : 0.0),
                top - (!borderIncludedInBounds ? borderWidth : 0.0),
                right + (!borderIncludedInBounds ? borderWidth : 0.0),
                bottom + (!borderIncludedInBounds ? borderWidth : 0.0), borderColor);
        DrawHelper.drawRect(left + (borderIncludedInBounds ? borderWidth : 0.0),
                top + (borderIncludedInBounds ? borderWidth : 0.0),
                right - (borderIncludedInBounds ? borderWidth : 0.0),
                bottom - (borderIncludedInBounds ? borderWidth : 0.0), insideColor);
    }

    public static final void color(Color color) {
        if (color == null)
            color = Color.white;
        color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);
    }

    public static final void color(double red, double green, double blue, double alpha) {
        GL11.glColor4d(red, green, blue, alpha);
    }

    public static void staticJelloCircle() {
        if (KillauraTest.checkTarget(KillauraTest.target) && KillauraTest.target.getHealth() > 0 && Minecraft.player.getDistanceToEntity(KillauraTest.target) <= (float) Main.settingsManager.getSettingByName(Main.featureDirector.getModule(KillauraTest.class), "Range").getValFloat() && !KillauraTest.target.isDead) {
            double height = 0.8 * (1 + Math.sin(2 * Math.PI * (time * .3)));
            double width = KillauraTest.target.width;

            final double x = KillauraTest.target.lastTickPosX + (KillauraTest.target.posX - KillauraTest.target.lastTickPosX) * mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosX;
            final double y = KillauraTest.target.lastTickPosY + (KillauraTest.target.posY - KillauraTest.target.lastTickPosY) * mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosY;
            final double z = KillauraTest.target.lastTickPosZ + (KillauraTest.target.posZ - KillauraTest.target.lastTickPosZ) * mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosZ;

            GlStateManager.enableBlend();
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GlStateManager.disableDepth();
            GlStateManager.disableTexture2D();
            GlStateManager.disableAlpha();
            GL11.glLineWidth(1.2f);
            GL11.glShadeModel(GL11.GL_SMOOTH);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
            {
                for (int j = 0; j < 361; j++) {
                    DrawHelper.color(setAlpha(DrawHelper.astolfoColors45(j - j + 1, j, 0.5f, 10), (int) (255 * (1.3 - height))));
                    double x1 = x + Math.cos(Math.toRadians(j)) * 0.7;
                    double z1 = z - Math.sin(Math.toRadians(j)) * 0.7;
                    GL11.glVertex3d(x + Math.cos(Math.toRadians(j)) * width, y + 0.05, z - Math.sin(Math.toRadians(j)) * width);
                    DrawHelper.color(setAlpha(DrawHelper.astolfoColors45(j - j + 1, j, 0.5f, 10), 0));
                    GL11.glVertex3d(x + Math.cos(Math.toRadians(j)) * width, y + 0.05 + (0.13 * height), z - Math.sin(Math.toRadians(j)) * width);
                }
            }
            GL11.glEnd();
            GL11.glBegin(GL_LINE_LOOP);
            {
                for (int j = 0; j < 365; j++) {
                    DrawHelper.setColor(DrawHelper.astolfoColors45(j - j + 15, j, 0.5f, 10).getRGB());

                    GL11.glVertex3d(x + Math.cos(Math.toRadians(j)) * width, y + 0.05, z - Math.sin(Math.toRadians(j)) * width);
                }
            }
            GL11.glEnd();
            GlStateManager.enableAlpha();
            GL11.glShadeModel(GL11.GL_FLAT);
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GlStateManager.enableTexture2D();
            GlStateManager.enableDepth();
            GlStateManager.disableBlend();
            GlStateManager.resetColor();
        }
    }

    public static void staticJelloCircle1() {
            double height = 0.8 * (1 + Math.sin(2 * Math.PI * (time * .3)));
            double width = mc.player.width;

        final double x = mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosX;
        final double y = mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosY;
        final double z = mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * mc.timer.renderPartialTicks - mc.getRenderManager().viewerPosZ;

            GlStateManager.enableBlend();
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GlStateManager.disableTexture2D();
            GlStateManager.disableAlpha();
            GL11.glLineWidth(1.2f);
            GL11.glShadeModel(GL11.GL_SMOOTH);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
            {
                for (int j = 0; j < 361; j++) {
                    DrawHelper.color(setAlpha(Main.getClientColor(), (int) (255 * (1.3 - height))));
                    double x1 = x + Math.cos(Math.toRadians(j)) * 0.7;
                    double z1 = z - Math.sin(Math.toRadians(j)) * 0.7;
                    GL11.glVertex3d(x + Math.cos(Math.toRadians(j)) * width, y + 0.1, z - Math.sin(Math.toRadians(j)) * width);
                    DrawHelper.color(setAlpha(Main.getClientColor(), 0));
                    GL11.glVertex3d(x + Math.cos(Math.toRadians(j)) * width, y + 0.1 + (0.13 * height), z - Math.sin(Math.toRadians(j)) * width);
                }
            }
            GL11.glEnd();
            GL11.glBegin(GL_LINE_LOOP);
            {
                    for (int j = 0; j < 365; j++) {
                        DrawHelper.color(Main.getClientColor());
                        GL11.glVertex3d(x + Math.cos(Math.toRadians(j)) * width, y + 0.1, z - Math.sin(Math.toRadians(j)) * width);
                    }
            }
            GL11.glEnd();
            GlStateManager.enableAlpha();
            GL11.glShadeModel(GL11.GL_FLAT);
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GlStateManager.resetColor();
        }

    public static void drawRoundedRect(float left, float top, float right, float bottom, int smooth, Color color) {
        Gui.drawRect(((int) left + smooth), (int) top, ((int) right - smooth), (int) bottom, color.getRGB());
        Gui.drawRect((int) left, ((int) top + smooth), (int) right, ((int) bottom - smooth), color.getRGB());
        drawFilledCircle((int) left + smooth, (int) top + smooth, smooth, color);
        drawFilledCircle((int) right - smooth, (int) top + smooth, smooth, color);
        drawFilledCircle((int) right - smooth, (int) bottom - smooth, smooth, color);
        drawFilledCircle((int) left + smooth, (int) bottom - smooth, smooth, color);
    }

    public static final void drawSmoothRect(float left, float top, float right, float bottom, int color) {
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        DrawHelper.drawRect(left, top, right, bottom, color);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        DrawHelper.drawRect(left * 2.0f - 1.0f, top * 2.0f, left * 2.0f, bottom * 2.0f - 1.0f, color);
        DrawHelper.drawRect(left * 2.0f, top * 2.0f - 1.0f, right * 2.0f, top * 2.0f, color);
        DrawHelper.drawRect(right * 2.0f, top * 2.0f, right * 2.0f + 1.0f, bottom * 2.0f - 1.0f, color);
        DrawHelper.drawRect(left * 2.0f, bottom * 2.0f - 1.0f, right * 2.0f, bottom * 2.0f, color);
        GL11.glDisable(3042);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }

    public static void drawFilledCircle(int xx, int yy, float radius, Color color) {
        int sections = 50;
        double dAngle = 6.283185307179586D / sections;
        GL11.glPushAttrib(8192);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glBegin(6);
        for (int i = 0; i < sections; i++) {
            float x = (float) (radius * Math.sin(i * dAngle));
            float y = (float) (radius * Math.cos(i * dAngle));
            GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F,
                    color.getAlpha() / 255.0F);
            GL11.glVertex2f(xx + x, yy + y);
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnd();
        GL11.glPopAttrib();
    }

    public static Color setAlpha(Color color, int alpha) {
        alpha = (int) MathHelper.clamp(alpha, 0, 255);
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }
    public static Color getColorWithOpacity(Color color, int alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }
    public static void renderItem(ItemStack itemStack, int x, int y) {
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.enableDepth();
        net.minecraft.client.renderer.RenderHelper.enableGUIStandardItemLighting();
        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(itemStack, x, y);
        Minecraft.getMinecraft().getRenderItem().renderItemOverlays(Minecraft.getMinecraft().fontRendererObj, itemStack, x, y);
        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableDepth();
    }

    public static void drawRoundedRect1(double x, double y, double x1, double y1, int insideC) {
        DrawHelper.drawRect(x + 0.5, y, x1 - 0.5, y + 0.5, insideC);
        DrawHelper.drawRect(x + 0.5, y1 - 0.5, x1 - 0.5, y1, insideC);
        DrawHelper.drawRect(x, y + 0.5, x1, y1 - 0.5, insideC);
    }

    public static void prepareScissorBox(float x, float y, float x2, float y2) {
        ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft());
        int factor = scale.getScaleFactor();
        GL11.glScissor((int) (x * factor), (int) ((scale.getScaledHeight() - y2) * factor), (int) ((x2 - x) * factor), (int) ((y2 - y) * factor));
    }



    public static void drawGradientRect(double d, double e, double e2, double g, int startColor, int endColor) {
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
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double) e2, (double) e, (double) zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double) d, (double) e, (double) zLevel).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double) d, (double) g, (double) zLevel).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos((double) e2, (double) g, (double) zLevel).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static double interpolate(double current, double old, double scale) {
        return old + (current - old) * scale;
    }

    public static boolean isInViewFrustrum(Entity entity) {
        return (isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck);
    }

    private static boolean isInViewFrustrum(AxisAlignedBB bb) {
        Entity current = Minecraft.getMinecraft().getRenderViewEntity();
        frustrum.setPosition(current.posX, current.posY, current.posZ);
        return frustrum.isBoundingBoxInFrustum(bb);
    }

    public static void putVertex3d(Vec3d vec) {
        GL11.glVertex3d(vec.xCoord, vec.yCoord, vec.zCoord);
    }

    public static Vec3d getRenderPos(double x, double y, double z) {

        x = x - mc.getRenderManager().renderPosX;
        y = y - mc.getRenderManager().renderPosY;
        z = z - mc.getRenderManager().renderPosZ;

        return new Vec3d(x, y, z);
    }

    public static void drawHead(ResourceLocation skin, int x, int y, int width, int height) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(skin);
        Gui.drawScaledCustomSizeModalRect(x, y, 8.0f, 8.0f, 8, 8, width, height, 64.0f, 64.0f);
    }

    public static void draw3DCircle(JumpCircle.Circle circle, float partialTicks, double rad, float lineWidth, Color color) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GLUtils.startSmooth();
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        DrawHelper.enableSmoothLine(lineWidth);
        GL11.glBegin(3);
        Minecraft mc = Minecraft.getMinecraft();
        double x = circle.spawnX - mc.getRenderManager().viewerPosX;
        double y = circle.spawnY - mc.getRenderManager().viewerPosY;
        double z = circle.spawnZ - mc.getRenderManager().viewerPosZ;

        for(int i = 0; i <= 90; ++i) {
            DrawHelper.setColor(Main.getClientColor(i - i + 15, i, 120).getRGB());
            GL11.glVertex3d(x + rad * Math.cos((double)i * 6.283185307179586D / 45.0D), y, z + rad * Math.sin((double)i * 6.283185307179586D / 45.0D));
        }

        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        DrawHelper.disableSmoothLine();
        GLUtils.endSmooth();
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }

    public static void drawEntityOnScreen(double posX, double posY, double scale, float mouseX, float mouseY, EntityLivingBase ent, float rotate) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX, (float)posY, 50.0F);
        GlStateManager.scale((float)-scale, (float)scale, (float)scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(165.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-165.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float)Math.atan((mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float)Math.atan((mouseX / 40.0F)) * 20.0F;
        ent.rotationYaw = (float)Math.atan((mouseX / 40.0F)) * 40.0F;
        ent.rotationPitch = -((float)Math.atan((mouseY / 40.0F))) * 20.0F;
        ent.rotationYawHead = 60;
        ent.prevRotationYawHead = 60;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.doRenderEntity((Entity)ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    public static void drawRect2(double x, double y, double width, double height, int color) {
        drawRect(x, y, x + width, y + height, color);
    }

    public static void drawNewRect(double left, double top, double right, double bottom, int color) {
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
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f, f1, f2, f3);
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(left, bottom, 0.0).endVertex();
        vertexbuffer.pos(right, bottom, 0.0).endVertex();
        vertexbuffer.pos(right, top, 0.0).endVertex();
        vertexbuffer.pos(left, top, 0.0).endVertex();
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
        float f3 = (float) (color >> 24 & 0xFF) / 255.0f;
        float f = (float) (color >> 16 & 0xFF) / 255.0f;
        float f1 = (float) (color >> 8 & 0xFF) / 255.0f;
        float f2 = (float) (color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferBuilder.pos(left, bottom, 0.0).endVertex();
        bufferBuilder.pos(right, bottom, 0.0).endVertex();
        bufferBuilder.pos(right, top, 0.0).endVertex();
        bufferBuilder.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static int color(int n, int n2, int n3, int n4) {
        n4 = 255;
        return new Color(n, n2, n3, n4).getRGB();
    }

    public static int rainbow(int delay, float saturation, float brightness) {
        double rainbow = Math.ceil((System.currentTimeMillis() + delay) / 16);
        rainbow %= 360.0D;
        return Color.getHSBColor((float) (rainbow / 360.0D), saturation, brightness).getRGB();
    }

    public static Color getRainbow(final int offset, final int speed) {
        float hue = (System.currentTimeMillis() + offset) % speed;
        hue /= speed;
        return Color.getHSBColor(hue, 0.7f, 1f);
    }

    public static Color rainbow2(int delay, float saturation, float brightness) {
        double rainbow = Math.ceil((System.currentTimeMillis() + delay) / 16);
        rainbow %= 360.0D;
        return Color.getHSBColor((float) (rainbow / 360.0D), saturation, brightness);
    }

    public static Color getHealthColor(EntityLivingBase entityLivingBase) {
        float health = entityLivingBase.getHealth();
        float[] fractions = new float[]{0.0f, 0.15f, 0.55f, 0.7f, 0.9f};
        Color[] colors = new Color[]{new Color(133, 0, 0), Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN};
        float progress = health / entityLivingBase.getMaxHealth();
        return health >= 0.0f ? DrawHelper.blendColors(fractions, colors, progress).brighter() : colors[0];
    }

    public static int getRandomColor() {
        char[] letters = "012345678".toCharArray();
        String color = "0x";
        for (int i = 0; i < 6; ++i) {
            color = color + letters[new Random().nextInt(letters.length)];
        }
        return Integer.decode(color);
    }

    public static int reAlpha(int color, float alpha) {
        Color c = new Color(color);
        float r = 0.003921569f * (float) c.getRed();
        float g = 0.003921569f * (float) c.getGreen();
        float b = 0.003921569f * (float) c.getBlue();
        return new Color(r, g, b, alpha).getRGB();
    }

    public static Color getGradientOffset(Color color1, Color color2, double offset) {
        if (offset > 1.0) {
            double left = offset % 1.0;
            int off = (int) offset;
            offset = off % 2 == 0 ? left : 1.0 - left;
        }
        double inverse_percent = 1.0 - offset;
        int redPart = (int) ((double) color1.getRed() * inverse_percent + (double) color2.getRed() * offset);
        int greenPart = (int) ((double) color1.getGreen() * inverse_percent + (double) color2.getGreen() * offset);
        int bluePart = (int) ((double) color1.getBlue() * inverse_percent + (double) color2.getBlue() * offset);
        return new Color(redPart, greenPart, bluePart);
    }

    public static class Colors
    {
        public static final int WHITE;
        public static final int BLACK;
        public static final int RED;
        public static final int GREEN;
        public static final int BLUE;
        public static final int ORANGE;
        public static final int PURPLE;
        public static final int GRAY;
        public static final int DARK_RED;
        public static final int YELLOW;
        public static final int RAINBOW = Integer.MIN_VALUE;

        static {
            WHITE = DrawHelper.toRGBA(255, 255, 255, 255);
            BLACK = DrawHelper.toRGBA(0, 0, 0, 255);
            RED = DrawHelper.toRGBA(255, 0, 0, 255);
            GREEN = DrawHelper.toRGBA(0, 255, 0, 255);
            BLUE = DrawHelper.toRGBA(0, 0, 255, 255);
            ORANGE = DrawHelper.toRGBA(255, 128, 0, 255);
            PURPLE = DrawHelper.toRGBA(163, 73, 163, 255);
            GRAY = DrawHelper.toRGBA(127, 127, 127, 255);
            DARK_RED = DrawHelper.toRGBA(64, 0, 0, 255);
            YELLOW = DrawHelper.toRGBA(255, 255, 0, 255);
        }
    }
    public static int toRGBA(final int r, final int g, final int b, final int a) {
        return (r << 16) + (g << 8) + (b << 0) + (a << 24);
    }

    public static int toRGBA(final float r, final float g, final float b, final float a) {
        return toRGBA((int)(r * 255.0f), (int)(g * 255.0f), (int)(b * 255.0f), (int)(a * 255.0f));
    }

    public static int getColor(int red, int green, int blue) {
        return DrawHelper.getColor(red, green, blue, 255);
    }

    public static int getColor(int red, int green, int blue, int alpha) {
        int color = 0;
        color |= alpha << 24;
        color |= red << 16;
        color |= green << 8;
        return color |= blue;
    }

    public static int getColor(int brightness) {
        return DrawHelper.getColor(brightness, brightness, brightness, 255);
    }

    public static int getColor(int brightness, int alpha) {
        return DrawHelper.getColor(brightness, brightness, brightness, alpha);
    }

    public static Color fade(Color color, int index, int count) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs(
                ((float) (System.currentTimeMillis() % 2000L) / 1000.0f + (float) index / (float) count * 2.0f) % 2.0f
                        - 1.0f);
        brightness = 0.5f + 0.5f * brightness;
        hsb[2] = brightness % 2.0f;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }


    public static Color blendColors(float[] fractions, Color[] colors, float progress) {
        if (fractions == null) {
            throw new IllegalArgumentException("Fractions can't be null");
        }
        if (colors == null) {
            throw new IllegalArgumentException("Colours can't be null");
        }
        if (fractions.length != colors.length) {
            throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
        }
        int[] indicies = DrawHelper.getFractionIndicies(fractions, progress);
        float[] range = new float[] { fractions[indicies[0]], fractions[indicies[1]] };
        Color[] colorRange = new Color[] { colors[indicies[0]], colors[indicies[1]] };
        float max = range[1] - range[0];
        float value = progress - range[0];
        float weight = value / max;
        return DrawHelper.blend(colorRange[0], colorRange[1], 1.0f - weight);
    }

    public static int[] getFractionIndicies(float[] fractions, float progress) {
        int startPoint;
        int[] range = new int[2];
        for (startPoint = 0; startPoint < fractions.length && fractions[startPoint] <= progress; ++startPoint) {
        }
        if (startPoint >= fractions.length) {
            startPoint = fractions.length - 1;
        }
        range[0] = startPoint - 1;
        range[1] = startPoint;
        return range;
    }

    public static Color blend(Color color1, Color color2, double ratio) {
        float r = (float) ratio;
        float ir = 1.0f - r;
        float[] rgb1 = new float[3];
        float[] rgb2 = new float[3];
        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
        float red = rgb1[0] * r + rgb2[0] * ir;
        float green = rgb1[1] * r + rgb2[1] * ir;
        float blue = rgb1[2] * r + rgb2[2] * ir;
        if (red < 0.0f) {
            red = 0.0f;
        } else if (red > 255.0f) {
            red = 255.0f;
        }
        if (green < 0.0f) {
            green = 0.0f;
        } else if (green > 255.0f) {
            green = 255.0f;
        }
        if (blue < 0.0f) {
            blue = 0.0f;
        } else if (blue > 255.0f) {
            blue = 255.0f;
        }
        Color color = null;
        try {
            color = new Color(red, green, blue);
        } catch (IllegalArgumentException exp) {
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
        }
        return color;
    }

    public static int astolfo(int delay, float offset) {
        float hue;
        float speed = 3000.0f;
        for (hue = Math.abs((float) (System.currentTimeMillis() % (long) delay)
                + -offset / 21.0f * 2.0f); hue > speed; hue -= speed) {
        }
        if ((double) (hue /= speed) > 0.5) {
            hue = 0.5f - (hue - 0.5f);
        }
        return Color.HSBtoRGB(hue += 0.5f, 0.5f, 1.0f);
    }

    public static Color TwoColoreffect(Color cl1, Color cl2, double speed) {
        double thing = speed / 4.0 % 1.0;
        float val = MathHelper.clamp((float) Math.sin(Math.PI * 6 * thing) / 2.0f + 0.5f, 0.0f, 1.0f);
        return new Color(lerp((float) cl1.getRed() / 255.0f, (float) cl2.getRed() / 255.0f, val),
                lerp((float) cl1.getGreen() / 255.0f, (float) cl2.getGreen() / 255.0f, val),
                lerp((float) cl1.getBlue() / 255.0f, (float) cl2.getBlue() / 255.0f, val));
    }

    public static int astolfoColors(int yOffset, int yTotal) {
        float hue;
        float speed = 2900.0f;
        for (hue = (float) (System.currentTimeMillis() % (long) ((int) speed))
                + (float) ((yTotal - yOffset) * 9); hue > speed; hue -= speed) {
        }
        if ((double) (hue /= speed) > 0.5) {
            hue = 0.5f - (hue - 0.5f);
        }
        return Color.HSBtoRGB(hue += 0.5f, 0.5f, 1.0f);
    }
    public static Color astolfoColor(int yOffset, int yTotal) {
        float speed = 2900F;
        float hue = (float) (System.currentTimeMillis() % (int)speed) + ((yTotal - yOffset) * 9);
        while (hue > speed) {
            hue -= speed;
        }
        hue /= speed;
        if (hue > 0.5) {
            hue = 0.5F - (hue - 0.5f);
        }
        hue += 0.5F;
        return new Color(hue, 0.5f, 1F);
    }
    public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
        float f = (col1 >> 24 & 255) / 255.0f;
        float f1 = (col1 >> 16 & 255) / 255.0f;
        float f2 = (col1 >> 8 & 255) / 255.0f;
        float f3 = (col1 & 255) / 255.0f;
        float f4 = (col2 >> 24 & 255) / 255.0f;
        float f5 = (col2 >> 16 & 255) / 255.0f;
        float f6 = (col2 >> 8 & 255) / 255.0f;
        float f7 = (col2 & 255) / 255.0f;
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
    }


    public static int getTeamColor(Entity entityIn) {
        int i = -1;
        i = entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase(
                "\u043f\u0457\u0405f[\u043f\u0457\u0405cR\u043f\u0457\u0405f]\u043f\u0457\u0405c" + entityIn.getName())
                ? DrawHelper.getColor(new Color(255, 60, 60).getRGB())
                : (entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase(
                "\u043f\u0457\u0405f[\u043f\u0457\u04059B\u043f\u0457\u0405f]\u043f\u0457\u04059"
                        + entityIn.getName())
                ? DrawHelper.getColor(new Color(60, 60, 255).getRGB())
                : (entityIn.getDisplayName().getUnformattedText().equalsIgnoreCase(
                "\u043f\u0457\u0405f[\u043f\u0457\u0405eY\u043f\u0457\u0405f]\u043f\u0457\u0405e"
                        + entityIn.getName())
                ? DrawHelper.getColor(new Color(255, 255, 60).getRGB())
                : (entityIn.getDisplayName()
                .getUnformattedText().equalsIgnoreCase(
                        "\u043f\u0457\u0405f[\u043f\u0457\u0405aG\u043f\u0457\u0405f]\u043f\u0457\u0405a"
                                + entityIn.getName())
                ? DrawHelper.getColor(new Color(60, 255, 60).getRGB()) : DrawHelper.getColor(new Color(255, 255, 255).getRGB()))));
        return i;
    }

    public static int astolfoColors4(float yDist, float yTotal, float saturation) {
        float speed = 1800f;
        float hue = (System.currentTimeMillis() % (int) speed) + (yTotal - yDist) * 12;
        while (hue > speed) {
            hue -= speed;
        }
        hue /= speed;
        if (hue > 0.5) {
            hue = 0.5F - (hue - 0.5f);
        }
        hue += 0.5F;
        return Color.HSBtoRGB(hue, saturation, 1F);
    }

    public static int astolfoColors5(float yDist, float yTotal, float saturation, float speedt) {
        float speed = 1800f;
        float hue = (System.currentTimeMillis() % (int) speed) + (yTotal - yDist) * speedt;
        while (hue > speed) {
            hue -= speed;
        }
        hue /= speed;
        if (hue > 0.5) {
            hue = 0.5F - (hue - 0.5f);
        }
        hue += 0.5F;
        return Color.HSBtoRGB(hue, saturation, 1F);
    }

    public static Color astolfoColors45(float yDist, float yTotal, float saturation, float speedt) {
        float speed = 1800f;
        float hue = (System.currentTimeMillis() % (int) speed) + (yTotal - yDist) * speedt;
        while (hue > speed) {
            hue -= speed;
        }
        hue /= speed;
        if (hue > 0.5) {
            hue = 0.5F - (hue - 0.5f);
        }
        hue += 0.5F;
        return Color.getHSBColor(hue, saturation, 1F);
    }

    public static float lerp(float a, float b, float f) {
        return a + f * (b - a);
    }
}

