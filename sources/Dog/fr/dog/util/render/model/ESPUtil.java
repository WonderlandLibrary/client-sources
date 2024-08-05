package fr.dog.util.render.model;

import fr.dog.util.InstanceAccess;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;

public class ESPUtil implements InstanceAccess {
    public static void drawBoundingBox(AxisAlignedBB a) {
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3d(a.minX, a.minY, a.minZ);
        GL11.glVertex3d(a.minX, a.minY, a.maxZ);
        GL11.glVertex3d(a.minX, a.maxY, a.maxZ);
        GL11.glVertex3d(a.minX, a.maxY, a.minZ);

        GL11.glVertex3d(a.minX, a.minY, a.maxZ);
        GL11.glVertex3d(a.maxX, a.minY, a.maxZ);
        GL11.glVertex3d(a.maxX, a.maxY, a.maxZ);
        GL11.glVertex3d(a.minX, a.maxY, a.maxZ);

        GL11.glVertex3d(a.maxX, a.minY, a.maxZ);
        GL11.glVertex3d(a.maxX, a.minY, a.minZ);
        GL11.glVertex3d(a.maxX, a.maxY, a.minZ);
        GL11.glVertex3d(a.maxX, a.maxY, a.maxZ);

        GL11.glVertex3d(a.maxX, a.minY, a.minZ);
        GL11.glVertex3d(a.minX, a.minY, a.minZ);
        GL11.glVertex3d(a.minX, a.maxY, a.minZ);
        GL11.glVertex3d(a.maxX, a.maxY, a.minZ);

        GL11.glVertex3d(a.minX, a.maxY, a.minZ);
        GL11.glVertex3d(a.minX, a.maxY, a.maxZ);
        GL11.glVertex3d(a.maxX, a.maxY, a.maxZ);
        GL11.glVertex3d(a.maxX, a.maxY, a.minZ);

        GL11.glVertex3d(a.minX, a.minY, a.minZ);
        GL11.glVertex3d(a.minX, a.minY, a.maxZ);
        GL11.glVertex3d(a.maxX, a.minY, a.maxZ);
        GL11.glVertex3d(a.maxX, a.minY, a.minZ);
        GL11.glEnd();
    }


    public static void drawFilledHitbox(AxisAlignedBB bb, Color color, float transparency) {
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GlStateManager.disableCull();
        GL11.glDepthMask(false);
        setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (transparency * 255)));
        drawBoundingBox(bb);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        GlStateManager.enableCull();
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static AxisAlignedBB getDaFuckingRenderPosAxisAlignedWithMargin(Entity entity, float expand) {
        AxisAlignedBB bb = entity.getEntityBoundingBox();

        bb = bb.expand(expand, expand, expand);

        float margin = (float) Math.abs(bb.maxX - bb.minX) / 2f;
        float entityheight = (float) Math.abs(bb.minY - bb.maxY);

        double x1 = (entity.lastTickPosX - margin + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks) - RenderManager.renderPosX;
        double y1 = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks) - RenderManager.renderPosY;
        double z1 = (entity.lastTickPosZ - margin + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks) - RenderManager.renderPosZ;

        double x2 = (entity.lastTickPosX + margin + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks) - RenderManager.renderPosX;
        double y2 = (entity.lastTickPosY + entityheight + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks) - RenderManager.renderPosY;
        double z2 = (entity.lastTickPosZ + margin + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks) - RenderManager.renderPosZ;
        return new AxisAlignedBB(x1, y1, z1, x2, y2, z2);
    }

    public static void filledBlockESP(BlockPos pos, Color color, float transparency) {
        start();
        setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (transparency * 255)));

        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);

        double x1 = axisAlignedBB.minX - RenderManager.renderPosX;
        double y1 = axisAlignedBB.minY - RenderManager.renderPosY;
        double z1 = axisAlignedBB.minZ - RenderManager.renderPosZ;

        double x2 = axisAlignedBB.maxX - RenderManager.renderPosX;
        double y2 = axisAlignedBB.maxY - RenderManager.renderPosY;
        double z2 = axisAlignedBB.maxZ - RenderManager.renderPosZ;

        drawBoundingBox(new AxisAlignedBB(x1, y1, z1, x2, y2, z2));

        stop();
    }

    public static void setColor(Color color) {
        float alpha = (color.getRGB() >> 24 & 0xFF) / 255.0F;
        float red = (color.getRGB() >> 16 & 0xFF) / 255.0F;
        float green = (color.getRGB() >> 8 & 0xFF) / 255.0F;
        float blue = (color.getRGB() & 0xFF) / 255.0F;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void start() {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableTexture2D();
        GlStateManager.disableCull();
        GlStateManager.disableAlpha();
        GlStateManager.disableDepth();
    }

    public static void stop() {
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.enableCull();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
