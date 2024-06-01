package io.github.liticane.clients.util.render;

import io.github.liticane.clients.util.interfaces.IMethods;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.*;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11C.GL_GREATER;

public class RenderUtil implements IMethods {
    public static int[] enabledCaps = new int[32];
    public static void enableCaps(int... caps) {
        for (int cap : caps) glEnable(cap);
        enabledCaps = caps;
    }
    public static void resetColor() {
        GlStateManager.color(1, 1, 1, 1);
    }
    public static void setAlphaLimit(float limit) {
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(GL_GREATER, (float) (limit * .01));
    }



    public static void renderBoundingBox(EntityLivingBase entityLivingBase, Color color, float alpha) {
        AxisAlignedBB bb = getInterpolatedBoundingBox(entityLivingBase);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableTexture2D();
        enableCaps(GL_BLEND, GL_POINT_SMOOTH, GL_POLYGON_SMOOTH, GL_LINE_SMOOTH);

        glDisable(GL_DEPTH_TEST);
        glDepthMask(false);
        glLineWidth(3);
        float actualAlpha = .3f * alpha;
        glColor4f(color.getRed(), color.getGreen(), color.getBlue(), actualAlpha);
        color(color.getRGB(), actualAlpha);
        RenderGlobal.renderCustomBoundingBox(bb, false, true);
        //or true
        glDepthMask(true);
        glEnable(GL_DEPTH_TEST);

        for (int cap : enabledCaps) glDisable(cap);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();

        GlStateManager.popMatrix();
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
    public static void drawBlockESP(BlockPos blockPosI, Color color, final float alpha, final float lineAlpha, final float lineWidth) {
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);

        GlStateManager.color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, alpha);
        final float x = (float) (blockPosI.getX() - RenderUtil.mc.getRenderManager().renderPosX);
        final float y = (float) (blockPosI.getY() - RenderUtil.mc.getRenderManager().renderPosY);
        final float z = (float) (blockPosI.getZ() - RenderUtil.mc.getRenderManager().renderPosZ);
        final Block block = RenderUtil.mc.world.getBlockState(blockPosI).getBlock();
        drawBoundingBox2(new AxisAlignedBB(x, y, z, x + block.getBlockBoundsMaxX(), y + block.getBlockBoundsMaxY(), z + block.getBlockBoundsMaxZ()));


        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
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
    public static double[] getInterpolatedPos(Entity entity) {
        float ticks = mc.timer.renderPartialTicks;
        return new double[]{
                interpolate(entity.lastTickPosX, entity.posX, ticks) - mc.getRenderManager().viewerPosX,
                interpolate(entity.lastTickPosY, entity.posY, ticks) - mc.getRenderManager().viewerPosY,
                interpolate(entity.lastTickPosZ, entity.posZ, ticks) - mc.getRenderManager().viewerPosZ
        };
    }
    public static Double interpolate(double oldValue, double newValue, double interpolationValue){
        return (oldValue + (newValue - oldValue) * interpolationValue);
    }
    public static AxisAlignedBB getInterpolatedBoundingBox(Entity entity) {
        final double[] renderingEntityPos = getInterpolatedPos(entity);
        final double entityRenderWidth = entity.width / 1.7;
        return new AxisAlignedBB(renderingEntityPos[0] - entityRenderWidth,
                renderingEntityPos[1], renderingEntityPos[2] - entityRenderWidth, renderingEntityPos[0] + entityRenderWidth,
                renderingEntityPos[1] + entity.height + (entity.isSneaking() ? -0.3 : 0.18), renderingEntityPos[2] + entityRenderWidth).expand(0.15, 0.15, 0.15);
    }
    public static void color(int color, float alpha) {
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        GlStateManager.color(r, g, b, alpha);
    }

    public static void drawCustomImage(int v, int v1, int i, int i1, ResourceLocation resourceLocation, int i2) {
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getInstance());
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(new Color(i2).getRed() / 255f, new Color(i2).getGreen() / 255f, new Color(i2).getBlue() / 255f, new Color(i2).getAlpha() / 255f);
        Minecraft.getInstance().getTextureManager().bindTexture(resourceLocation);
        Gui.drawModalRectWithCustomSizedTexture(v, v1, 0.0f, 0.0f, i, i1, (float) i, (float) i1);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

}
