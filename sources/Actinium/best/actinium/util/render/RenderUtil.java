package best.actinium.util.render;

import best.actinium.Actinium;
import best.actinium.module.impl.visual.HudModule;
import best.actinium.util.IAccess;
import net.minecraft.block.Block;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.glTexParameteri;

public class RenderUtil implements IAccess {
    public static void image(final ResourceLocation imageLocation, final float x, final float y, final float width, final float height, final Color color) {
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

    public static boolean isHovering(float x, float y, float width, float height, int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }

    public static double getX(EntityLivingBase entity) {
        return entity.prevPosX + (entity.posX - entity.prevPosX) * mc.timer.renderPartialTicks;
    }

    public static double getY(EntityLivingBase entity) {
        return entity.prevPosY + (entity.posY - entity.prevPosY) * mc.timer.renderPartialTicks;
    }

    public static double getZ(EntityLivingBase entity) {
        return entity.prevPosZ + (entity.posZ - entity.prevPosZ) * mc.timer.renderPartialTicks;
    }

    public static void color(Color color) {
        GL11.glColor4d(color.getRed(), color.getGreen(), color.getBlue(), 255);
    }

    public static void drawPosESP(double x2, double y2, double z2, float width, final Color color,final Color fillColor) {
        float alpha = 1.0f;

        final float x = (float) (x2 - RenderUtil.mc.getRenderManager().renderPosX);
        final float y = (float) (y2 - RenderUtil.mc.getRenderManager().renderPosY);
        final float z = (float) (z2 - RenderUtil.mc.getRenderManager().renderPosZ);

        float[] rgba = color.getRGBColorComponents(null);
        float[] rgbaFill = fillColor.getRGBColorComponents(null);

        GL11.glLineWidth(2);
        GL11.glColor4f(rgbaFill[0], rgbaFill[1], rgbaFill[2], alpha);
        otherDrawBoundingBox(x, y, z, width, mc.thePlayer.height + 0.1f);

        GL11.glLineWidth(2);
        GL11.glColor4f(rgba[0], rgba[1], rgba[2], alpha);
        otherDrawOutlinedBoundingBox(x, y, z, width, mc.thePlayer.height + 0.1f);


        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void drawEntityServerESP(final Entity entity, float width, final Color color,final Color fillColor) {
        double d0 = entity.serverPosX / 32.0;
        double d2 = entity.serverPosY / 32.0;
        double d3 = entity.serverPosZ / 32.0;
        float alpha = 1.0f;

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
        float[] rgbaFill = fillColor.getRGBColorComponents(null);

        GL11.glLineWidth(2);
        GL11.glColor4f(rgbaFill[0], rgbaFill[1], rgbaFill[2], alpha);
        otherDrawBoundingBox(x, y, z, width, entity.height + 0.1f);

        GL11.glLineWidth(2);
        GL11.glColor4f(rgba[0], rgba[1], rgba[2], alpha);
        otherDrawOutlinedBoundingBox(x, y, z, width, entity.height + 0.1f);


        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void drawLine(final double x, final double y, final double z, final double x2, final double y2, final double z2, final float lineWdith) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(lineWdith);
        GL11.glColor4f(Actinium.INSTANCE.getModuleManager().get(HudModule.class).color2.getColor().getRed(),
                Actinium.INSTANCE.getModuleManager().get(HudModule.class).color2.getColor().getGreen(),
                Actinium.INSTANCE.getModuleManager().get(HudModule.class).color2.getColor().getBlue(), 255);
        GL11.glBegin(2);
        Vec3 renderPos = new Vec3(x2 - mc.getRenderManager().renderPosX, y2 - mc.getRenderManager().renderPosY, z2 - mc.getRenderManager().renderPosZ);
        GL11.glVertex3d(renderPos.xCoord, renderPos.yCoord, renderPos.zCoord);

        renderPos = new Vec3(x - mc.getRenderManager().renderPosX, y - mc.getRenderManager().renderPosY, z - mc.getRenderManager().renderPosZ);
        GL11.glVertex3d(renderPos.xCoord, renderPos.yCoord, renderPos.zCoord);
        GL11.glEnd();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    public static void drawBlockESP(BlockPos blockPosI, Color color, final float alpha) {
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);

        GlStateManager.color(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, alpha);
        final float x = (float) (blockPosI.getX() - RenderUtil.mc.getRenderManager().renderPosX);
        final float y = (float) (blockPosI.getY() - RenderUtil.mc.getRenderManager().renderPosY);
        final float z = (float) (blockPosI.getZ() - RenderUtil.mc.getRenderManager().renderPosZ);
        final Block block = RenderUtil.mc.theWorld.getBlockState(blockPosI).getBlock();
        drawBoundingBox2(new AxisAlignedBB(x, y, z, x + block.getBlockBoundsMaxX(), y + block.getBlockBoundsMaxY(), z + block.getBlockBoundsMaxZ()));


        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static void otherDrawOutlinedBoundingBox(float x, float y, float z, double width, double height) {
        width *= 1.5;

        float x1 = (float) (x - width);
        float z1 = (float) (z - width);
        float x2 = (float) (x + width);
        float z2 = (float) (z - width);
        float x3 = (float) (x + width);
        float z3 = (float) (z + width);
        float x4 = (float) (x - width);
        float z4 = (float) (z + width);

        float y2 = (float) (y + height);

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

    public static void otherDrawBoundingBox(float x, float y, float z, double width, double height) {
        width *= 1.5;

        float x1 = (float) (x - width);
        float z1 = (float) (z - width);
        float x2 = (float) (x + width);
        float z2 = (float) (z - width);
        float x3 = (float) (x + width);
        float z3 = (float) (z + width);
        float x4 = (float) (x - width);
        float z4 = (float) (z + width);

        float y2 = (float) (y + height);

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
}
