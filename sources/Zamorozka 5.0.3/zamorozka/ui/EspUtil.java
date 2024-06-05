package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class EspUtil implements MCUtil {

    public static int player = 0;
    public static int item = 6;

    public static void drawBlockBox(BlockPos blockPos, Color color) {
        Minecraft.getMinecraft().getRenderManager();
        double x = (double) blockPos.getX() - RenderManager.renderPosX;
        Minecraft.getMinecraft().getRenderManager();
        double y = (double) blockPos.getY() - RenderManager.renderPosY;
        Minecraft.getMinecraft().getRenderManager();
        double z = (double) blockPos.getZ() - RenderManager.renderPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.0f);
        GL11.glColor4d(0.0, 1.0, 0.0, 0.15000000596046448);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d(color.getRed(), color.getGreen(), color.getBlue(), 0.25);
        EspUtil.drawFilledBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }

    public static void drawEntityBox(Entity entity, Color color) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        EspUtil.glColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), color == Color.BLACK ? 60 : 45));
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        EspUtil.drawFilledBox(new AxisAlignedBB(entity.boundingBox.minX - 0.05 - entity.posX + (entity.posX - RenderManager.renderPosX), entity.boundingBox.minY - entity.posY + (entity.posY - RenderManager.renderPosY), entity.boundingBox.minZ - 0.05 - entity.posZ + (entity.posZ - RenderManager.renderPosZ), entity.boundingBox.maxX + 0.05 - entity.posX + (entity.posX - RenderManager.renderPosX), entity.boundingBox.maxY + 0.1 - entity.posY + (entity.posY - RenderManager.renderPosY), entity.boundingBox.maxZ + 0.05 - entity.posZ + (entity.posZ - RenderManager.renderPosZ)));
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }

    public static void drawEntityBox(Entity entity, int mode) {
        Color col = null;
        if (mode == player) {
            col = Color.ORANGE;
        }
        EspUtil.drawEntityBox(entity, col);
    }

    public static void tracerLine(Entity entity, Color color) {
        Minecraft.getMinecraft().getRenderManager();
        double x = entity.posX - RenderManager.renderPosX;
        Minecraft.getMinecraft().getRenderManager();
        double y = entity.posY + (double) (entity.height / 2.0f) - RenderManager.renderPosY;
        Minecraft.getMinecraft().getRenderManager();
        double z = entity.posZ - RenderManager.renderPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        EspUtil.glColor(color);
        Vec3d eyes = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float) Math.toRadians(Minecraft.player.rotationPitch)).rotateYaw(-(float) Math.toRadians(Minecraft.player.rotationYaw));
        GL11.glBegin(1);
        GL11.glVertex3d(eyes.xCoord, (double) Minecraft.player.getEyeHeight() + eyes.yCoord, eyes.zCoord);
        GL11.glVertex3d(x, y, z);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }

    public static void drawPlatform(double x, double y, double z, Color color, double size) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        EspUtil.glColor(color);
        Minecraft.getMinecraft().getRenderManager();
        double renderX = x - RenderManager.renderPosX;
        Minecraft.getMinecraft().getRenderManager();
        double renderY = y - RenderManager.renderPosY;
        Minecraft.getMinecraft().getRenderManager();
        double renderZ = z - RenderManager.renderPosZ;
        AxisAlignedBB bb = new AxisAlignedBB(renderX + size, renderY + 0.02, renderZ + size, renderX - size, renderY, renderZ - size);
        EspUtil.drawFilledBox(bb);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }

    public static void drawPlatformOverEntity(Entity entity, Color color) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        EspUtil.glColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), color == Color.BLACK ? 60 : 45));
        Minecraft.getMinecraft().getRenderManager();
        double renderY = entity.boundingBox.maxY - RenderManager.renderPosY + 0.2;
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        AxisAlignedBB bb = new AxisAlignedBB(entity.boundingBox.minX - 0.05 - entity.posX + (entity.posX - RenderManager.renderPosX), renderY, entity.boundingBox.minZ - 0.05 - entity.posZ + (entity.posZ - RenderManager.renderPosZ), entity.boundingBox.maxX + 0.05 - entity.posX + (entity.posX - RenderManager.renderPosX), renderY, entity.boundingBox.maxZ + 0.05 - entity.posZ + (entity.posZ - RenderManager.renderPosZ));
        EspUtil.drawFilledBox(bb);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }

    public static void drawHollowRect(float posX, float posY, float posX2, float posY2, float width, int color, boolean center) {
        float corners = width / 2.0f;
        float side = width / 2.0f;
        if (center) {
            EspUtil.drawRect(posX - side, posY - corners, posX + side, posY2 + corners, color);
            EspUtil.drawRect(posX2 - side, posY - corners, posX2 + side, posY2 + corners, color);
            EspUtil.drawRect(posX - corners, posY - side, posX2 + corners, posY + side, color);
            EspUtil.drawRect(posX - corners, posY2 - side, posX2 + corners, posY2 + side, color);
        } else {
            EspUtil.drawRect(posX - width, posY - corners, posX, posY2 + corners, color);
            EspUtil.drawRect(posX2, posY - corners, posX2 + width, posY2 + corners, color);
            EspUtil.drawRect(posX - corners, posY - width, posX2 + corners, posY, color);
            EspUtil.drawRect(posX - corners, posY2, posX2 + corners, posY2 + width, color);
        }
    }

    public static void drawGradientBorderedRect(float posX, float posY, float posX2, float posY2, float width, int color, int startColor, int endColor, boolean center) {
        EspUtil.drawGradientRect(posX, posY, posX2, posY2, startColor, endColor);
        EspUtil.drawHollowRect(posX, posY, posX2, posY2, width, color, center);
    }

    public static void drawBorderedCorneredRect(float x, float y, float x2, float y2, float lineWidth, int lineColor, int bgColor) {
        EspUtil.drawRect(x, y, x2, y2, bgColor);
        float f = (float) (lineColor >> 24 & 255) / 255.0f;
        float f2 = (float) (lineColor >> 16 & 255) / 255.0f;
        float f3 = (float) (lineColor >> 8 & 255) / 255.0f;
        float f4 = (float) (lineColor & 255) / 255.0f;
        GL11.glEnable(3042);
        GL11.glEnable(3553);
        EspUtil.drawRect(x - 1.0f, y, x2 + 1.0f, y - lineWidth, lineColor);
        EspUtil.drawRect(x, y, x - lineWidth, y2, lineColor);
        EspUtil.drawRect(x - 1.0f, y2, x2 + 1.0f, y2 + lineWidth, lineColor);
        EspUtil.drawRect(x2, y, x2 + lineWidth, y2, lineColor);
        GL11.glDisable(3553);
        GL11.glDisable(3042);
    }

    public static void drawFilledBox(AxisAlignedBB bb) {
        Tessellator ts = Tessellator.getInstance();
        BufferBuilder vb = ts.getBuffer();
        vb.begin(7, DefaultVertexFormats.POSITION);
        vb.pos(bb.minX, bb.minY, bb.minZ).endVertex();
        vb.pos(bb.minX, bb.maxY, bb.minZ).endVertex();
        vb.pos(bb.maxX, bb.minY, bb.minZ).endVertex();
        vb.pos(bb.maxX, bb.maxY, bb.minZ).endVertex();
        vb.pos(bb.maxX, bb.minY, bb.maxZ).endVertex();
        vb.pos(bb.maxX, bb.maxY, bb.maxZ).endVertex();
        vb.pos(bb.minX, bb.minY, bb.maxZ).endVertex();
        vb.pos(bb.minX, bb.maxY, bb.maxZ).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION);
        vb.pos(bb.maxX, bb.maxY, bb.minZ).endVertex();
        vb.pos(bb.maxX, bb.minY, bb.minZ).endVertex();
        vb.pos(bb.minX, bb.maxY, bb.minZ).endVertex();
        vb.pos(bb.minX, bb.minY, bb.minZ).endVertex();
        vb.pos(bb.minX, bb.maxY, bb.maxZ).endVertex();
        vb.pos(bb.minX, bb.minY, bb.maxZ).endVertex();
        vb.pos(bb.maxX, bb.maxY, bb.maxZ).endVertex();
        vb.pos(bb.maxX, bb.minY, bb.maxZ).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION);
        vb.pos(bb.minX, bb.maxY, bb.minZ).endVertex();
        vb.pos(bb.maxX, bb.maxY, bb.minZ).endVertex();
        vb.pos(bb.maxX, bb.maxY, bb.maxZ).endVertex();
        vb.pos(bb.minX, bb.maxY, bb.maxZ).endVertex();
        vb.pos(bb.minX, bb.maxY, bb.minZ).endVertex();
        vb.pos(bb.minX, bb.maxY, bb.maxZ).endVertex();
        vb.pos(bb.maxX, bb.maxY, bb.maxZ).endVertex();
        vb.pos(bb.maxX, bb.maxY, bb.minZ).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION);
        vb.pos(bb.minX, bb.minY, bb.minZ).endVertex();
        vb.pos(bb.maxX, bb.minY, bb.minZ).endVertex();
        vb.pos(bb.maxX, bb.minY, bb.maxZ).endVertex();
        vb.pos(bb.minX, bb.minY, bb.maxZ).endVertex();
        vb.pos(bb.minX, bb.minY, bb.minZ).endVertex();
        vb.pos(bb.minX, bb.minY, bb.maxZ).endVertex();
        vb.pos(bb.maxX, bb.minY, bb.maxZ).endVertex();
        vb.pos(bb.maxX, bb.minY, bb.minZ).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION);
        vb.pos(bb.minX, bb.minY, bb.minZ).endVertex();
        vb.pos(bb.minX, bb.maxY, bb.minZ).endVertex();
        vb.pos(bb.minX, bb.minY, bb.maxZ).endVertex();
        vb.pos(bb.minX, bb.maxY, bb.maxZ).endVertex();
        vb.pos(bb.maxX, bb.minY, bb.maxZ).endVertex();
        vb.pos(bb.maxX, bb.maxY, bb.maxZ).endVertex();
        vb.pos(bb.maxX, bb.minY, bb.minZ).endVertex();
        vb.pos(bb.maxX, bb.maxY, bb.minZ).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION);
        vb.pos(bb.minX, bb.maxY, bb.maxZ).endVertex();
        vb.pos(bb.minX, bb.minY, bb.maxZ).endVertex();
        vb.pos(bb.minX, bb.maxY, bb.minZ).endVertex();
        vb.pos(bb.minX, bb.minY, bb.minZ).endVertex();
        vb.pos(bb.maxX, bb.maxY, bb.minZ).endVertex();
        vb.pos(bb.maxX, bb.minY, bb.minZ).endVertex();
        vb.pos(bb.maxX, bb.maxY, bb.maxZ).endVertex();
        vb.pos(bb.maxX, bb.minY, bb.maxZ).endVertex();
        ts.draw();
    }

    public static void glColor(Color color) {
        GL11.glColor4f((float) color.getRed() / 255.0f, (float) color.getGreen() / 255.0f, (float) color.getBlue() / 255.0f, (float) color.getAlpha() / 255.0f);
    }

    public static void glColor(int hex) {
        float alpha = (float) (hex >> 24 & 255) / 255.0f;
        float red = (float) (hex >> 16 & 255) / 255.0f;
        float green = (float) (hex >> 8 & 255) / 255.0f;
        float blue = (float) (hex & 255) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void drawGradientRect(float x, float y, float x1, float y1, int topColor, int bottomColor) {
        GL11.glEnable(1536);
        GL11.glShadeModel(7425);
        GL11.glBegin(7);
        EspUtil.glColor(topColor);
        GL11.glVertex2f(x, y1);
        GL11.glVertex2f(x1, y1);
        EspUtil.glColor(bottomColor);
        GL11.glVertex2f(x1, y);
        GL11.glVertex2f(x, y);
        GL11.glEnd();
        GL11.glShadeModel(7424);
        GL11.glDisable(1536);
    }

    public static void drawRect(float g, float h, float i, float j, int col1) {
        float f = (float) (col1 >> 24 & 255) / 255.0f;
        float f2 = (float) (col1 >> 16 & 255) / 255.0f;
        float f3 = (float) (col1 >> 8 & 255) / 255.0f;
        float f4 = (float) (col1 & 255) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(f2, f3, f4, f);
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

    public static void drawRect(float g, float h, float i, float j, Color c) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        EspUtil.glColor(c);
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

    public static void drawBorderedRect(float x, float y, float x2, float y2, float l1, int col1, int col2) {
        EspUtil.drawRect(x, y, x2, y2, col2);
        float f = (float) (col1 >> 24 & 255) / 255.0f;
        float f2 = (float) (col1 >> 16 & 255) / 255.0f;
        float f3 = (float) (col1 >> 8 & 255) / 255.0f;
        float f4 = (float) (col1 & 255) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glLineWidth(l1);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }

    public static void drawBorderedRect(float x, float y, float x2, float y2, float l1, Color c, Color c2) {
        EspUtil.drawRect(x, y, x2, y2, c2);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        EspUtil.glColor(c);
        GL11.glLineWidth(l1);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }

    public static void drawClickTPEspUtil(double x, double y, double z, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWidth) {
        mc.entityRenderer.setupCameraTransform(mc.timer.elapsedPartialTicks, 0);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(1.0f);
        float sinus = 1.0f - MathHelper.abs(MathHelper.sin((float) (Minecraft.getSystemTime() % 10000) / 10000.0f * 3.1415927f * 4.0f) * 1.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(1.0f - sinus, sinus, 0.0f, 0.15f);
        EspUtil.drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0), 1.0f - sinus, sinus, 0.0f, 0.15f);
        GL11.glColor4d(0.0, 0.0, 0.0, 0.5);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }

    public static void drawColorBox(AxisAlignedBB axisalignedbb, float red, float green, float blue, float alpha) {
        Tessellator ts = Tessellator.getInstance();
        BufferBuilder vb = ts.getBuffer();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
    }

    public static void drawFace(String name, int x, int y, int w, int h, boolean selected) {
        try {
            AbstractClientPlayer.getDownloadImageSkin(AbstractClientPlayer.getLocationSkin(name), name).loadTexture(Minecraft.getMinecraft().getResourceManager());
            Minecraft.getTextureManager().bindTexture(AbstractClientPlayer.getLocationSkin(name));
            GL11.glEnable(3042);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            float fw = 192.0f;
            float fh = 192.0f;
            float u = 24.0f;
            float v = 24.0f;
            Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, w, h, fw, fh);
            fw = 192.0f;
            fh = 192.0f;
            u = 120.0f;
            v = 24.0f;
            Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, w, h, fw, fh);
            GL11.glDisable(3042);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void entityESPBox(Entity entity, int mode) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        if (mode == 0) {
            GL11.glColor4d(1.0f - Minecraft.player.getDistanceToEntity(entity) / 40.0f, Minecraft.player.getDistanceToEntity(entity) / 40.0f, 0.0, 0.5);
        } else if (mode == 1) {
            GL11.glColor4d(5.0, 2.0, 4.0, 2.5);
        } else if (mode == 2) {
            GL11.glColor4d(1.0, 1.0, 0.0, 0.5);
        } else if (mode == 3) {
            GL11.glColor4d(1.0, 0.0, 0.0, 0.5);
        } else if (mode == 4) {
            GL11.glColor4d(0.0, 1.0, 0.0, 0.5);
        }
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        Minecraft.getMinecraft().getRenderManager();
        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(entity.boundingBox.minX - 0.05 - entity.posX + (entity.posX - RenderManager.renderPosX), entity.boundingBox.minY - entity.posY + (entity.posY - RenderManager.renderPosY), entity.boundingBox.minZ - 0.05 - entity.posZ + (entity.posZ - RenderManager.renderPosZ), entity.boundingBox.maxX + 0.05 - entity.posX + (entity.posX - RenderManager.renderPosX), entity.boundingBox.maxY + 0.1 - entity.posY + (entity.posY - RenderManager.renderPosY), entity.boundingBox.maxZ + 0.05 - entity.posZ + (entity.posZ - RenderManager.renderPosZ)), 1.0f, 1.0f, 1.0f, 2.0f);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }

}
