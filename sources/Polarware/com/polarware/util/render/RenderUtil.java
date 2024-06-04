package com.polarware.util.render;

import com.polarware.util.interfaces.InstanceAccess;
import com.polarware.util.shader.RiseShaders;
import com.polarware.util.vector.Vector3d;
import lombok.experimental.UtilityClass;
import net.minecraft.block.Block;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

@UtilityClass
public final class RenderUtil implements InstanceAccess {

    private static final Frustum FRUSTUM = new Frustum();
    private static final RenderManager RENDER_MANAGER = mc.getRenderManager();
    public static final int GENERIC_SCALE = 22;

    public Vector3d getCameraVector() {
        return new Vector3d(
                -RENDER_MANAGER.renderPosX,
                -RENDER_MANAGER.renderPosY,
                -RENDER_MANAGER.renderPosZ
        );
    }

    /**
     * Better to use gl state manager to avoid bugs
     */
    public void start() {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableTexture2D();
        GlStateManager.disableCull();
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
    }

    /**
     * Better to use gl state manager to avoid bugs
     */
    public void stop() {
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.enableCull();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.resetColor();
    }

    public void rectangle(final double x, final double y, final double width, final double height, final Color color) {
        start();

        if (color != null) {
            ColorUtil.glColor(color);
        }

        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x + width, y);
        GL11.glVertex2d(x + width, y + height);
        GL11.glVertex2d(x, y + height);
        GL11.glEnd();

        stop();
    }

    public void rainbowRectangle(final double x, final double y, final double width, final double height) {
        start();

        GL11.glBegin(GL11.GL_QUADS);

        for (double position = x; position <= x + width; position += 0.5) {
            color(Color.getHSBColor((float) ((position - x) / width), 1, 1));

            GL11.glVertex2d(position, y);
            GL11.glVertex2d(position + 0.5f, y);
            GL11.glVertex2d(position + 0.5f, y + height);
            GL11.glVertex2d(position, y + height);
        }

        GL11.glEnd();

        stop();
    }

    public void vertex(final double x, final double y) {
        GL11.glVertex2d(x, y);
    }
    public void rectangle(final double x, final double y, final double width, final double height) {
        rectangle(x, y, width, height, null);
    }

    public void centeredRectangle(final double x, final double y, final double width, final double height, final Color color) {
        rectangle(x - width / 2, y - height / 2, width, height, color);
    }

    public void centeredRectangle(final double x, final double y, final double width, final double height) {
        rectangle(x - width / 2, y - height / 2, width, height, null);
    }
    public static void drawBlockESP(final Vec3 blockPos,Color color, final float alpha, final float lineAlpha, final float lineWidth) {
        BlockPos blockPosI = new BlockPos(blockPos);
        GlStateManager.color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
        final float x = (float) (blockPosI.getX() - RenderUtil.mc.getRenderManager().renderPosX);
        final float y = (float) (blockPosI.getY() - RenderUtil.mc.getRenderManager().renderPosY);
        final float z = (float) (blockPosI.getZ() - RenderUtil.mc.getRenderManager().renderPosZ);
        final Block block = RenderUtil.mc.theWorld.getBlockState(blockPosI).getBlock();
        drawBoundingBox2(new AxisAlignedBB(x, y, z, x + block.getBlockBoundsMaxX(), y + block.getBlockBoundsMaxY(), z + block.getBlockBoundsMaxZ()));
        if (lineWidth > 0.0f) {
            GL11.glLineWidth(lineWidth);
            GlStateManager.color(color.getRed(), color.getGreen(), color.getBlue(), lineAlpha);
            drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + block.getBlockBoundsMaxX(), y + block.getBlockBoundsMaxY(), z + block.getBlockBoundsMaxZ()));
        }
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }



    private static void drawOutlinedBoundingBox(final AxisAlignedBB a) {
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        worldrenderer.begin(3, DefaultVertexFormats.POSITION);
        worldrenderer.pos((float)a.minX, (float)a.minY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.minY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.maxY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.maxY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.minY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.minY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.maxY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.maxY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.minY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.minY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.minY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.minY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.maxY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.maxY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.maxY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.maxY, (float)a.minZ).endVertex();
        worldrenderer.endVertex();
        tessellator.draw();
    }

    public static void drawBoundingBox2(final AxisAlignedBB a) {
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos((float)a.minX, (float)a.minY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.minY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.maxY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.maxY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.minY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.minY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.maxY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.maxY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.minY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.minY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.maxY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.maxY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.minY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.minY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.maxY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.maxY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.minY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.minY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.minY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.minY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.maxY, (float)a.minZ).endVertex();
        worldrenderer.pos((float)a.minX, (float)a.maxY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.maxY, (float)a.maxZ).endVertex();
        worldrenderer.pos((float)a.maxX, (float)a.maxY, (float)a.minZ).endVertex();
        worldrenderer.endVertex();
        tessellator.draw();
    }


        public void verticalGradient(final double x, final double y, final double width, final double height, final Color topColor, final Color bottomColor) {
        start();
        GlStateManager.alphaFunc(516, 0);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glBegin(GL11.GL_QUADS);

        ColorUtil.glColor(topColor);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x + width, y);

        ColorUtil.glColor(bottomColor);
        GL11.glVertex2d(x + width, y + height);
        GL11.glVertex2d(x, y + height);

        GL11.glEnd();
        GL11.glShadeModel(GL11.GL_FLAT);
        stop();
    }
    public void begin(final int glMode) {
        GL11.glBegin(glMode);
    }

    public void gradientCentered(double x, double y, final double width, final double height, final Color topColor, final Color bottomColor) {
        x -= width / 2;
        y -= height / 2;
        verticalGradient(x, y, width, height, topColor, bottomColor);
    }

    public void horizontalGradient(final double x, final double y, final double width, final double height, final Color leftColor, final Color rightColor) {
        start();
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glBegin(GL11.GL_QUADS);

        ColorUtil.glColor(leftColor);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y + height);

        ColorUtil.glColor(rightColor);
        GL11.glVertex2d(x + width, y + height);
        GL11.glVertex2d(x + width, y);

        GL11.glEnd();
        GL11.glShadeModel(GL11.GL_FLAT);
        stop();
    }

    public void horizontalCenteredGradient(final double x, final double y, final double width, final double height, final Color leftColor, final Color rightColor) {
        horizontalGradient(x - width / 2, y - height / 2, width, height, leftColor, rightColor);
    }

    public void image(final ResourceLocation imageLocation, final float x, final float y, final float width, final float height, final Color color) {
        glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0F);
        color(color);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        mc.getTextureManager().bindTexture(imageLocation);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
        GlStateManager.resetColor();
        GlStateManager.disableBlend();
    }

    public void image(final ResourceLocation imageLocation, final double x, final double y, final double width, final double height, Color color) {
        image(imageLocation, (float) x, (float) y, (float) width, (float) height, color);
    }

    public void image(final ResourceLocation imageLocation, final float x, final float y, final float width, final float height) {
        image(imageLocation, x, y, width, height, Color.WHITE);
    }

    public void image(final ResourceLocation imageLocation, final double x, final double y, final double width, final double height) {
        image(imageLocation, (float) x, (float) y, (float) width, (float) height);
    }

    public static void dropShadow(final int loops, final double x, final double y, final double width, final double height, final double opacity, final double edgeRadius) {
        GlStateManager.alphaFunc(516, 0);
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();

        for (float margin = 0; margin <= loops / 2f; margin += 0.5f) {
            roundedRectangle(x - margin / 2f, y - margin / 2f,
                    width + margin, height + margin, edgeRadius,
                    new Color(0, 0, 0, (int) Math.max(0.5f, (opacity - margin * 1.2) / 5.5f)));
        }
    }

    public void color(final double red, final double green, final double blue, final double alpha) {
        GL11.glColor4d(red, green, blue, alpha);
    }

    public void color(final double red, final double green, final double blue) {
        color(red, green, blue, 1);
    }

    public void color(Color color) {
        if (color == null)
            color = Color.white;
        color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);
    }

    public void color(Color color, final int alpha) {
        if (color == null)
            color = Color.white;
        color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 0.5);
    }

    public void polygon(final double x, final double y, double sideLength, final double amountOfSides, final boolean filled, final Color color) {
        sideLength /= 2;
        start();
        if (color != null)
            color(color);
        if (!filled) GL11.glLineWidth(2);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        begin(filled ? GL11.GL_TRIANGLE_FAN : GL11.GL_LINE_STRIP);
        {
            for (double i = 0; i <= amountOfSides / 4; i++) {
                final double angle = i * 4 * (Math.PI * 2) / 360;
                vertex(x + (sideLength * Math.cos(angle)) + sideLength, y + (sideLength * Math.sin(angle)) + sideLength);
            }
        }
        end();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        stop();
    }

    public void polygon(final double x, final double y, final double sideLength, final int amountOfSides, final boolean filled) {
        polygon(x, y, sideLength, amountOfSides, filled, null);
    }

    public void polygon(final double x, final double y, final double sideLength, final int amountOfSides, final Color color) {
        polygon(x, y, sideLength, amountOfSides, true, color);
    }

    public void polygon(final double x, final double y, final double sideLength, final int amountOfSides) {
        polygon(x, y, sideLength, amountOfSides, true, null);
    }

    public void polygonCentered(double x, double y, final double sideLength, final int amountOfSides, final boolean filled, final Color color) {
        x -= sideLength / 2;
        y -= sideLength / 2;
        polygon(x, y, sideLength, amountOfSides, filled, color);
    }

    public void polygonCentered(double x, double y, final double sideLength, final int amountOfSides, final boolean filled) {
        x -= sideLength / 2;
        y -= sideLength / 2;
        polygon(x, y, sideLength, amountOfSides, filled, null);
    }

    public void polygonCentered(double x, double y, final double sideLength, final int amountOfSides, final Color color) {
        x -= sideLength / 2;
        y -= sideLength / 2;
        polygon(x, y, sideLength, amountOfSides, true, color);
    }

    public void polygonCentered(double x, double y, final double sideLength, final int amountOfSides) {
        x -= sideLength / 2;
        y -= sideLength / 2;
        polygon(x, y, sideLength, amountOfSides, true, null);
    }

    public void circle(final double x, final double y, final double radius, final boolean filled, final Color color) {
        polygon(x, y, radius, 360, filled, color);
    }
    public void circle(final double x, final double y, final double radius, final double sides, final boolean filled, final Color color) {
        polygon(x, y, radius, sides, filled, color);
    }

    public void circle(final double x, final double y, final double radius, final boolean filled) {
        polygon(x, y, radius, 360, filled);
    }

    public void circle(final double x, final double y, final double radius, final Color color) {
        polygon(x, y, radius, 360, color);
    }

    public void circle(final double x, final double y, final double radius) {
        polygon(x, y, radius, 360);
    }
    public void circleCentered(double x, double y, final double radius, final boolean filled, final Color color) {
        x -= radius / 2;
        y -= radius / 2;
        polygon(x, y, radius, 360, filled, color);
    }

    public void circleCentered(double x, double y, final double radius, final boolean filled) {
        x -= radius / 2;
        y -= radius / 2;
        polygon(x, y, radius, 360, filled);
    }

    public void circleCentered(double x, double y, final double radius, final boolean filled, final int sides) {
        x -= radius / 2;
        y -= radius / 2;
        polygon(x, y, radius, sides, filled);
    }

    public void circleCentered(double x, double y, final double radius, final Color color) {
        x -= radius / 2;
        y -= radius / 2;
        polygon(x, y, radius, 360, color);
    }

    public void circleCentered(double x, double y, final double radius) {
        x -= radius / 2;
        y -= radius / 2;
        polygon(x, y, radius, 360);
    }

    public void triangle(final double x, final double y, final double sideLength, final boolean filled,
                         final Color color) {
        polygon(x, y, sideLength, 3, filled, color);
    }

    public void triangle(final double x, final double y, final double sideLength, final boolean filled) {
        polygon(x, y, sideLength, 3, filled);
    }

    public void triangle(final double x, final double y, final double sideLength, final Color color) {
        polygon(x, y, sideLength, 3, color);
    }
    public static void drawEntityServerESP(final Entity entity, final Color color, final float alpha, final float lineAlpha, final float lineWidth) {
        double d0 = entity.serverPosX / 32.0;
        double d2 = entity.serverPosY / 32.0;
        double d3 = entity.serverPosZ / 32.0;
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase livingBase = (EntityLivingBase) entity;
            d0 = livingBase.realPosX / 32.0;
            d2 = livingBase.realPosY / 32.0;
            d3 = livingBase.realPosZ / 32.0;
        }
        final float x = (float) (d0 - RenderUtil.mc.getRenderManager().renderPosX);
        final float y = (float) (d2 - RenderUtil.mc.getRenderManager().renderPosY);
        final float z = (float) (d3 - RenderUtil.mc.getRenderManager().renderPosZ);

        float[] rgba = color.getRGBColorComponents(null);
        GL11.glColor4f(rgba[0], rgba[1], rgba[2], alpha);
        //0.2
        otherDrawBoundingBox(entity, x, y, z, entity.width - 0.288f, entity.height + 0.1f);

        if (lineWidth > 0.0f) {
            GL11.glLineWidth(lineWidth);
            GL11.glColor4f(rgba[0], rgba[1], rgba[2], lineAlpha);
            otherDrawOutlinedBoundingBox(entity, x, y, z, entity.width - 0.288f, entity.height + 0.1f);
        }

        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void otherDrawOutlinedBoundingBox(Entity entity, float x, float y, float z, double width, double height) {
        width *= 1.5;
        float yaw1 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 45.0F;
        float newYaw1;
        if (yaw1 < 0.0F) {
            newYaw1 = 0.0F;
            newYaw1 += 360.0F - Math.abs(yaw1);
        } else {
            newYaw1 = yaw1;
        }

        newYaw1 *= -1.0F;
        newYaw1 = (float)((double)newYaw1 * (Math.PI / 180.0));
        float yaw2 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 135.0F;
        float newYaw2;
        if (yaw2 < 0.0F) {
            newYaw2 = 0.0F;
            newYaw2 += 360.0F - Math.abs(yaw2);
        } else {
            newYaw2 = yaw2;
        }

        newYaw2 *= -1.0F;
        newYaw2 = (float)((double)newYaw2 * (Math.PI / 180.0));
        float yaw3 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 225.0F;
        float newYaw3;
        if (yaw3 < 0.0F) {
            newYaw3 = 0.0F;
            newYaw3 += 360.0F - Math.abs(yaw3);
        } else {
            newYaw3 = yaw3;
        }

        newYaw3 *= -1.0F;
        newYaw3 = (float)((double)newYaw3 * (Math.PI / 180.0));
        float yaw4 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 315.0F;
        float newYaw4;
        if (yaw4 < 0.0F) {
            newYaw4 = 0.0F;
            newYaw4 += 360.0F - Math.abs(yaw4);
        } else {
            newYaw4 = yaw4;
        }

        newYaw4 *= -1.0F;
        newYaw4 = (float)((double)newYaw4 * (Math.PI / 180.0));
        float x1 = (float)(Math.sin((double)newYaw1) * width + (double)x);
        float z1 = (float)(Math.cos((double)newYaw1) * width + (double)z);
        float x2 = (float)(Math.sin((double)newYaw2) * width + (double)x);
        float z2 = (float)(Math.cos((double)newYaw2) * width + (double)z);
        float x3 = (float)(Math.sin((double)newYaw3) * width + (double)x);
        float z3 = (float)(Math.cos((double)newYaw3) * width + (double)z);
        float x4 = (float)(Math.sin((double)newYaw4) * width + (double)x);
        float z4 = (float)(Math.cos((double)newYaw4) * width + (double)z);
        float y2 = (float)((double)y + height);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION);
        worldrenderer.pos((double)x1, (double)y, (double)z1).endVertex();
        worldrenderer.pos((double)x1, (double)y2, (double)z1).endVertex();
        worldrenderer.pos((double)x2, (double)y2, (double)z2).endVertex();
        worldrenderer.pos((double)x2, (double)y, (double)z2).endVertex();
        worldrenderer.pos((double)x1, (double)y, (double)z1).endVertex();
        worldrenderer.pos((double)x4, (double)y, (double)z4).endVertex();
        worldrenderer.pos((double)x3, (double)y, (double)z3).endVertex();
        worldrenderer.pos((double)x3, (double)y2, (double)z3).endVertex();
        worldrenderer.pos((double)x4, (double)y2, (double)z4).endVertex();
        worldrenderer.pos((double)x4, (double)y, (double)z4).endVertex();
        worldrenderer.pos((double)x4, (double)y2, (double)z4).endVertex();
        worldrenderer.pos((double)x3, (double)y2, (double)z3).endVertex();
        worldrenderer.pos((double)x2, (double)y2, (double)z2).endVertex();
        worldrenderer.pos((double)x2, (double)y, (double)z2).endVertex();
        worldrenderer.pos((double)x3, (double)y, (double)z3).endVertex();
        worldrenderer.pos((double)x4, (double)y, (double)z4).endVertex();
        worldrenderer.pos((double)x4, (double)y2, (double)z4).endVertex();
        worldrenderer.pos((double)x1, (double)y2, (double)z1).endVertex();
        worldrenderer.pos((double)x1, (double)y, (double)z1).endVertex();
        worldrenderer.endVertex();
        tessellator.draw();
    }

    public static void otherDrawBoundingBox(Entity entity, float x, float y, float z, double width, double height) {
        width *= 1.5;
        float yaw1 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 45.0F;
        float newYaw1;
        if (yaw1 < 0.0F) {
            newYaw1 = 0.0F;
            newYaw1 += 360.0F - Math.abs(yaw1);
        } else {
            newYaw1 = yaw1;
        }

        newYaw1 *= -1.0F;
        newYaw1 = (float)((double)newYaw1 * (Math.PI / 180.0));
        float yaw2 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 135.0F;
        float newYaw2;
        if (yaw2 < 0.0F) {
            newYaw2 = 0.0F;
            newYaw2 += 360.0F - Math.abs(yaw2);
        } else {
            newYaw2 = yaw2;
        }

        newYaw2 *= -1.0F;
        newYaw2 = (float)((double)newYaw2 * (Math.PI / 180.0));
        float yaw3 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 225.0F;
        float newYaw3;
        if (yaw3 < 0.0F) {
            newYaw3 = 0.0F;
            newYaw3 += 360.0F - Math.abs(yaw3);
        } else {
            newYaw3 = yaw3;
        }

        newYaw3 *= -1.0F;
        newYaw3 = (float)((double)newYaw3 * (Math.PI / 180.0));
        float yaw4 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 315.0F;
        float newYaw4;
        if (yaw4 < 0.0F) {
            newYaw4 = 0.0F;
            newYaw4 += 360.0F - Math.abs(yaw4);
        } else {
            newYaw4 = yaw4;
        }

        newYaw4 *= -1.0F;
        newYaw4 = (float)((double)newYaw4 * (Math.PI / 180.0));
        float x1 = (float)(Math.sin((double)newYaw1) * width + (double)x);
        float z1 = (float)(Math.cos((double)newYaw1) * width + (double)z);
        float x2 = (float)(Math.sin((double)newYaw2) * width + (double)x);
        float z2 = (float)(Math.cos((double)newYaw2) * width + (double)z);
        float x3 = (float)(Math.sin((double)newYaw3) * width + (double)x);
        float z3 = (float)(Math.cos((double)newYaw3) * width + (double)z);
        float x4 = (float)(Math.sin((double)newYaw4) * width + (double)x);
        float z4 = (float)(Math.cos((double)newYaw4) * width + (double)z);
        float y2 = (float)((double)y + height);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos((double)x1, (double)y, (double)z1).endVertex();
        worldrenderer.pos((double)x1, (double)y2, (double)z1).endVertex();
        worldrenderer.pos((double)x2, (double)y2, (double)z2).endVertex();
        worldrenderer.pos((double)x2, (double)y, (double)z2).endVertex();
        worldrenderer.pos((double)x2, (double)y, (double)z2).endVertex();
        worldrenderer.pos((double)x2, (double)y2, (double)z2).endVertex();
        worldrenderer.pos((double)x3, (double)y2, (double)z3).endVertex();
        worldrenderer.pos((double)x3, (double)y, (double)z3).endVertex();
        worldrenderer.pos((double)x3, (double)y, (double)z3).endVertex();
        worldrenderer.pos((double)x3, (double)y2, (double)z3).endVertex();
        worldrenderer.pos((double)x4, (double)y2, (double)z4).endVertex();
        worldrenderer.pos((double)x4, (double)y, (double)z4).endVertex();
        worldrenderer.pos((double)x4, (double)y, (double)z4).endVertex();
        worldrenderer.pos((double)x4, (double)y2, (double)z4).endVertex();
        worldrenderer.pos((double)x1, (double)y2, (double)z1).endVertex();
        worldrenderer.pos((double)x1, (double)y, (double)z1).endVertex();
        worldrenderer.pos((double)x1, (double)y, (double)z1).endVertex();
        worldrenderer.pos((double)x2, (double)y, (double)z2).endVertex();
        worldrenderer.pos((double)x3, (double)y, (double)z3).endVertex();
        worldrenderer.pos((double)x4, (double)y, (double)z4).endVertex();
        worldrenderer.pos((double)x1, (double)y2, (double)z1).endVertex();
        worldrenderer.pos((double)x2, (double)y2, (double)z2).endVertex();
        worldrenderer.pos((double)x3, (double)y2, (double)z3).endVertex();
        worldrenderer.pos((double)x4, (double)y2, (double)z4).endVertex();
        worldrenderer.endVertex();
        tessellator.draw();
    }

    public void triangle(final double x, final double y, final double sideLength) {
        polygon(x, y, sideLength, 3);
    }

    public void triangleCentered(double x, double y, final double sideLength, final boolean filled,
                                 final Color color) {
        x -= sideLength / 2;
        y -= sideLength / 2;
        polygon(x, y, sideLength, 3, filled, color);
    }

    public void triangleCentered(double x, double y, final double sideLength, final boolean filled) {
        x -= sideLength / 2;
        y -= sideLength / 2;
        polygon(x, y, sideLength, 3, filled);
    }

    public void triangleCentered(double x, double y, final double sideLength, final Color color) {
        x -= sideLength / 2;
        y -= sideLength / 2;
        polygon(x, y, sideLength, 3, color);
    }

    public void triangleCentered(double x, double y, final double sideLength) {
        x -= sideLength / 2;
        y -= sideLength / 2;
        polygon(x, y, sideLength, 3);
    }

    public void renderItemIcon(final double x, final double y, final ItemStack itemStack) {
        if (itemStack != null) {
            GlStateManager.pushMatrix();
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            RenderHelper.enableGUIStandardItemLighting();

            mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, x, y);

            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popMatrix();
        }
    }

    public static void drawRoundedGradientRect(double x, double y, double width, double height, double radius, Color firstColor, Color secondColor, boolean vertical) {
        RiseShaders.RGQ_SHADER.draw(x, y, width, height, radius, firstColor, secondColor, vertical);
    }

    public void roundedRectangle(double x, double y, double width, double height, double radius, Color color) {
        RiseShaders.RQ_SHADER.draw(x, y, width, height, radius, color);
    }

    public void roundedOutlineRectangle(double x, double y, double width, double height, double radius, double borderSize, Color color) {
        RiseShaders.ROQ_SHADER.draw(x, y, width, height, radius, borderSize, color);
    }

    public void roundedOutlineGradientRectangle(double x, double y, double width, double height, double radius, double borderSize, Color color1, Color color2) {
        RiseShaders.ROGQ_SHADER.draw(x, y, width, height, radius, borderSize, color1, color2);
    }

    public void end() {
        GL11.glEnd();
    }

    public void circle(final double x, final double y, final float radius) {
        GL11.glEnable(GL11.GL_POINT_SMOOTH);
        GL11.glPointSize(radius * 4);
        GL11.glBegin(GL11.GL_POINTS);
        GL11.glVertex2d(x, y);
        GL11.glEnd();
        GL11.glDisable(GL11.GL_POINT_SMOOTH);
    }

    public void quad(final double x, final double y, final double width, final double height, final Color color) {
        ColorUtil.glColor(color);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x + width, y);
        GL11.glVertex2d(x + width, y + height);
        GL11.glVertex2d(x, y + height);
        GL11.glEnd();
    }

    public void scissor(double x, double y, double width, double height) {
        final ScaledResolution sr = mc.scaledResolution;
        final double scale = sr.getScaleFactor();

        y = sr.getScaledHeight() - y;

        x *= scale;
        y *= scale;
        width *= scale;
        height *= scale;

        GL11.glScissor((int) x, (int) (y - height), (int) width, (int) height);
    }

    public void drawLine(double x, double y, double z, double x1, double y1, double z1, final Color color, final float width) {
        x = x - mc.getRenderManager().renderPosX;
        x1 = x1 - mc.getRenderManager().renderPosX;
        y = y - mc.getRenderManager().renderPosY;
        y1 = y1 - mc.getRenderManager().renderPosY;
        z = z - mc.getRenderManager().renderPosZ;
        z1 = z1 - mc.getRenderManager().renderPosZ;

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(width);

        color(color);
        GL11.glBegin(2);
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x1, y1, z1);
        GL11.glEnd();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    public boolean isInViewFrustrum(final Entity entity) {
        return (isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck);
    }

    private boolean isInViewFrustrum(final AxisAlignedBB bb) {
        final Entity current = mc.getRenderViewEntity();
        FRUSTUM.setPosition(current.posX, current.posY, current.posZ);
        return FRUSTUM.isBoundingBoxInFrustum(bb);
    }

    public static Framebuffer createFrameBuffer(final Framebuffer framebuffer) {
        if (framebuffer == null || framebuffer.framebufferWidth != mc.displayWidth || framebuffer.framebufferHeight != mc.displayHeight) {

            if (framebuffer != null) {
                framebuffer.deleteFramebuffer();
            }

            return new Framebuffer(mc.displayWidth, mc.displayHeight, false);
        }
        return framebuffer;
    }

    public static Vec3 getRenderPos(double x, double y, double z) {

        x -= mc.getRenderManager().renderPosX;
        y -= mc.getRenderManager().renderPosY;
        z -= mc.getRenderManager().renderPosZ;

        return new Vec3(x, y, z);
    }

    public static void glVertex3D(Vec3 vector3d) {
        GL11.glVertex3d(vector3d.xCoord, vector3d.yCoord, vector3d.zCoord);
    }

    public static void drawBoundingBox(final AxisAlignedBB aa) {

        glBegin(GL_QUADS);
        glVertex3D(getRenderPos(aa.minX, aa.minY, aa.minZ));
        glVertex3D(getRenderPos(aa.minX, aa.maxY, aa.minZ));
        glVertex3D(getRenderPos(aa.maxX, aa.minY, aa.minZ));
        glVertex3D(getRenderPos(aa.maxX, aa.maxY, aa.minZ));
        glVertex3D(getRenderPos(aa.maxX, aa.minY, aa.maxZ));
        glVertex3D(getRenderPos(aa.maxX, aa.maxY, aa.maxZ));
        glVertex3D(getRenderPos(aa.minX, aa.minY, aa.maxZ));
        glVertex3D(getRenderPos(aa.minX, aa.maxY, aa.maxZ));
        end();

        glBegin(GL_QUADS);
        glVertex3D(getRenderPos(aa.maxX, aa.maxY, aa.minZ));
        glVertex3D(getRenderPos(aa.maxX, aa.minY, aa.minZ));
        glVertex3D(getRenderPos(aa.minX, aa.maxY, aa.minZ));
        glVertex3D(getRenderPos(aa.minX, aa.minY, aa.minZ));
        glVertex3D(getRenderPos(aa.minX, aa.maxY, aa.maxZ));
        glVertex3D(getRenderPos(aa.minX, aa.minY, aa.maxZ));
        glVertex3D(getRenderPos(aa.maxX, aa.maxY, aa.maxZ));
        glVertex3D(getRenderPos(aa.maxX, aa.minY, aa.maxZ));
        end();

        glBegin(GL_QUADS);
        glVertex3D(getRenderPos(aa.minX, aa.maxY, aa.minZ));
        glVertex3D(getRenderPos(aa.maxX, aa.maxY, aa.minZ));
        glVertex3D(getRenderPos(aa.maxX, aa.maxY, aa.maxZ));
        glVertex3D(getRenderPos(aa.minX, aa.maxY, aa.maxZ));
        glVertex3D(getRenderPos(aa.minX, aa.maxY, aa.minZ));
        glVertex3D(getRenderPos(aa.minX, aa.maxY, aa.maxZ));
        glVertex3D(getRenderPos(aa.maxX, aa.maxY, aa.maxZ));
        glVertex3D(getRenderPos(aa.maxX, aa.maxY, aa.minZ));
        end();

        glBegin(GL_QUADS);
        glVertex3D(getRenderPos(aa.minX, aa.minY, aa.minZ));
        glVertex3D(getRenderPos(aa.maxX, aa.minY, aa.minZ));
        glVertex3D(getRenderPos(aa.maxX, aa.minY, aa.maxZ));
        glVertex3D(getRenderPos(aa.minX, aa.minY, aa.maxZ));
        glVertex3D(getRenderPos(aa.minX, aa.minY, aa.minZ));
        glVertex3D(getRenderPos(aa.minX, aa.minY, aa.maxZ));
        glVertex3D(getRenderPos(aa.maxX, aa.minY, aa.maxZ));
        glVertex3D(getRenderPos(aa.maxX, aa.minY, aa.minZ));
        end();

        glBegin(GL_QUADS);
        glVertex3D(getRenderPos(aa.minX, aa.minY, aa.minZ));
        glVertex3D(getRenderPos(aa.minX, aa.maxY, aa.minZ));
        glVertex3D(getRenderPos(aa.minX, aa.minY, aa.maxZ));
        glVertex3D(getRenderPos(aa.minX, aa.maxY, aa.maxZ));
        glVertex3D(getRenderPos(aa.maxX, aa.minY, aa.maxZ));
        glVertex3D(getRenderPos(aa.maxX, aa.maxY, aa.maxZ));
        glVertex3D(getRenderPos(aa.maxX, aa.minY, aa.minZ));
        glVertex3D(getRenderPos(aa.maxX, aa.maxY, aa.minZ));
        end();

        glBegin(GL_QUADS);
        glVertex3D(getRenderPos(aa.minX, aa.maxY, aa.maxZ));
        glVertex3D(getRenderPos(aa.minX, aa.minY, aa.maxZ));
        glVertex3D(getRenderPos(aa.minX, aa.maxY, aa.minZ));
        glVertex3D(getRenderPos(aa.minX, aa.minY, aa.minZ));
        glVertex3D(getRenderPos(aa.maxX, aa.maxY, aa.minZ));
        glVertex3D(getRenderPos(aa.maxX, aa.minY, aa.minZ));
        glVertex3D(getRenderPos(aa.maxX, aa.maxY, aa.maxZ));
        glVertex3D(getRenderPos(aa.maxX, aa.minY, aa.maxZ));
        end();
    }

    public void renderPlayerHead(AbstractClientPlayer player, int x, int y, double scale) {
        glPushMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

        mc.getTextureManager().bindTexture(player.getLocationSkin());

        glTranslated(x, y, 0.0);
        glScaled(scale, scale, 0.0);

        Gui.drawModalRectWithCustomSizedTexture(0, 0, 24, 24, 24, 24, 192.0f, 192.0f);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 120, 24, 24, 24, 192.0f, 192.0f);

        GlStateManager.disableBlend();
        glPopMatrix();
    }
}