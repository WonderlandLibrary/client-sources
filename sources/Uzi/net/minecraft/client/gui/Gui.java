package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Gui {
    public static final ResourceLocation optionsBackground = new ResourceLocation("textures/gui/options_background.png");
    public static final ResourceLocation statIcons = new ResourceLocation("textures/gui/container/stats_icons.png");
    public static final ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");
    protected float zLevel;
    private static final String __OBFID = "CL_00000662";
    public static Gui instance = new Gui();

    public static void draw2D(final Entity e, final double posX, final double posY, final double posZ, final float alpha, final float red, final float green, final float blue) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(posX, posY, posZ);
        GL11.glNormal3f(0.0f, 0.0f, 0.0f);
        GlStateManager.rotate(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(-0.1, -0.1, 0.1);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GlStateManager.func_179098_w();
        GlStateManager.depthMask(true);
        drawOutlineRect(-5.4f, -20.2f, 5.4f, 2.2f, 0xFF000000);
        drawOutlineRect(-5.0f, -19.8f, 5f, 1.8f, 0xFF000000);
        drawOutlineRect(-5.2f, -20.0f, 5.2f, 2.0f, new Color(red, green, blue, alpha).getRGB());
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glEnable(2896);
        GlStateManager.popMatrix();
    }

    public static void draw2D(final Entity e, final double posX, final double posY, final double posZ, final int color) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(posX, posY, posZ);
        GL11.glNormal3f(0.0f, 0.0f, 0.0f);
        GlStateManager.rotate(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.scale(-0.1, -0.1, 0.1);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GlStateManager.func_179098_w();
        GlStateManager.depthMask(true);
        drawDarkOutlineRect(-5.3f, -20.1f, 5.3f, 2.1f, 0xFF000000);
        drawDarkOutlineRect(-5.0f, -19.8f, 5f, 1.8f, 0xFF000000);
        drawOutlineRect(-5.2f, -20.0f, 5.2f, 2.0f, color);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glEnable(2896);
        GlStateManager.popMatrix();
    }

    public static void drawOutlineRect(final float drawX, final float drawY, final float drawWidth, final float drawHeight, final int color) {
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        drawRect(drawX, drawY, drawWidth, drawY + 0.2f, color);
        drawRect(drawX, drawY + 0.2f, drawX + 0.2f, drawHeight, color);
        drawRect(drawWidth - 0.2f, drawY + 0.2f, drawWidth, drawHeight - 0.2f, color);
        drawRect(drawX + 0.2f, drawHeight - 0.2f, drawWidth, drawHeight, color);
    }

    public static void drawDarkOutlineRect(final float drawX, final float drawY, final float drawWidth, final float drawHeight, final int color) {
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        drawRect(drawX, drawY, drawWidth, drawY + 0.1f, color);
        drawRect(drawX, drawY + 0.1f, drawX + 0.1f, drawHeight, color);
        drawRect(drawWidth - 0.1f, drawY + 0.1f, drawWidth, drawHeight - 0.2f, color);
        drawRect(drawX + 0.1f, drawHeight - 0.1f, drawWidth, drawHeight, color);
    }

    public void drawOutlinedRect(final float drawX, final float drawY, final float drawWidth, final float drawHeight, final float alpha, final float red, final float green, final float blue) {
        drawRectColor(drawX, drawY, drawWidth, drawY + 0.5f, alpha, red, green, blue);
        drawRectColor(drawX, drawY + 0.5f, drawX + 0.5f, drawHeight, alpha, red, green, blue);
        drawRectColor(drawWidth - 0.5f, drawY + 0.5f, drawWidth, drawHeight - 0.5f, alpha, red, green, blue);
        drawRectColor(drawX + 0.5f, drawHeight - 0.5f, drawWidth, drawHeight, alpha, red, green, blue);
    }

    public static void drawCheck(final int x, final int y, final int color) {
        Minecraft.getMinecraft().fontRendererObj.drawString("\u2713", x, y, color);
        Minecraft.getMinecraft().fontRendererObj.drawString("\u2713", x, (int) (y + 0.5), color);
    }

    public static void drawRoundedRect(final float x, final float y, final float maxX, final float maxY, final int color) {
        drawRect(x + 0.5f, y, maxX - 0.5f, y + 0.5f, color);
        drawRect(x + 0.5f, maxY - 0.5f, maxX - 0.5f, maxY, color);
        drawRect(x, y + 0.5f, maxX, maxY - 0.5f, color);
    }

    public void drawTexturedRectangle(final ResourceLocation texture, final float x, final float y, final float width, final float height, final float u, final float v, final float uWidth, final float vHeight) {
        GL11.glEnable(3042);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        final float scale = 0.00390625f;
        final Tessellator tessellator = Tessellator.instance;
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(x, y + height, 0.0, u * scale, (v + vHeight) * scale);
        worldRenderer.addVertexWithUV(x + width, y + height, 0.0, (u + uWidth) * scale, (v + vHeight) * scale);
        worldRenderer.addVertexWithUV(x + width, y, 0.0, (u + uWidth) * scale, v * scale);
        worldRenderer.addVertexWithUV(x, y, 0.0, u * scale, v * scale);
        worldRenderer.draw();
        GL11.glDisable(3042);
    }

    public static void scissorBox(final int x, final int y, final int xend, final int yend) {
        final int width = xend - x;
        final int height = yend - y;
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        final int factor = sr.getScaleFactor();
        final int bottomY = Minecraft.getMinecraft().currentScreen.height - yend;
        GL11.glScissor(x * factor, bottomY * factor, width * factor, height * factor);
    }

    public int getFadeHex(final int hex1, final int hex2, final double ratio) {
        int r = hex1 >> 16;
        int g = hex1 >> 8 & 0xFF;
        int b = hex1 & 0xFF;
        r += (int) (((hex2 >> 16) - r) * ratio);
        g += (int) (((hex2 >> 8 & 0xFF) - g) * ratio);
        b += (int) (((hex2 & 0xFF) - b) * ratio);
        return r << 16 | g << 8 | b;
    }

    public static void start3DGLConstants() {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glDepthMask(false);
    }

    public static void finish3DGLConstants() {
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }

    public static void drawTriangle(final double cx, final double cy, final float g, final float theta, final float h, final int c) {
        GL11.glTranslated(cx, cy, 0.0);
        GL11.glRotatef(180.0f + theta, 0.0f, 0.0f, 1.0f);
        final float f = (c >> 24 & 0xFF) / 255.0f;
        final float f2 = (c >> 16 & 0xFF) / 255.0f;
        final float f3 = (c >> 8 & 0xFF) / 255.0f;
        final float f4 = (c & 0xFF) / 255.0f;
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(h);
        GL11.glBegin(6);
        GL11.glVertex2d(0.0, (double) (1.0f * g));
        GL11.glVertex2d((double) (1.0f * g), (double) (-(1.0f * g)));
        GL11.glVertex2d((double) (-(1.0f * g)), (double) (-(1.0f * g)));
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glRotatef(-180.0f - theta, 0.0f, 0.0f, 1.0f);
        GL11.glTranslated(-cx, -cy, 0.0);
    }

    public static void drawTextureID(final float g, final float y, final float width, final float height, final int id) {
        GL11.glPushMatrix();
        GL11.glBindTexture(3553, id);
        GL11.glTranslatef(g, y, 0.0f);
        final Tessellator tessellator = Tessellator.instance;
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertexWithUV(0.0, height, 0.0, 0.0, 1.0);
        worldRenderer.addVertexWithUV(width, height, 0.0, 1.0, 1.0);
        worldRenderer.addVertexWithUV(width, 0.0, 0.0, 1.0, 0.0);
        worldRenderer.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
        worldRenderer.draw();
        GL11.glPopMatrix();
    }

    public static void drawRect(final double d, final double e, final double f2, final double f3, final float red, final float green, final float blue, final float alpha) {
        GlStateManager.enableBlend();
        GlStateManager.func_179090_x();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glPushMatrix();
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(7);
        GL11.glVertex2d(f2, e);
        GL11.glVertex2d(d, e);
        GL11.glVertex2d(d, f3);
        GL11.glVertex2d(f2, f3);
        GL11.glEnd();
        GlStateManager.func_179098_w();
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }

    public static void drawRect(final double d, final double e, final double f2, final double f3, final int paramColor) {
        final float alpha = (paramColor >> 24 & 0xFF) / 255.0f;
        final float red = (paramColor >> 16 & 0xFF) / 255.0f;
        final float green = (paramColor >> 8 & 0xFF) / 255.0f;
        final float blue = (paramColor & 0xFF) / 255.0f;
        GlStateManager.enableBlend();
        GlStateManager.func_179090_x();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glPushMatrix();
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(7);
        GL11.glVertex2d(f2, e);
        GL11.glVertex2d(d, e);
        GL11.glVertex2d(d, f3);
        GL11.glVertex2d(f2, f3);
        GL11.glEnd();
        GlStateManager.func_179098_w();
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }

    public static void drawRectColor(final double d, final double e, final double f2, final double f3, final float alpha, final float red, final float green, final float blue) {
        GlStateManager.enableBlend();
        GlStateManager.func_179090_x();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glPushMatrix();
        GL11.glColor4f(alpha, red, green, blue);
        GL11.glBegin(7);
        GL11.glVertex2d(f2, e);
        GL11.glVertex2d(d, e);
        GL11.glVertex2d(d, f3);
        GL11.glVertex2d(f2, f3);
        GL11.glEnd();
        GlStateManager.func_179098_w();
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }

    public int fadeColor(final int color1, final int color2) {
        float alpha = (color1 >> 24 & 0xFF) / 255.0f;
        final float red = (color1 >> 16 & 0xFF) / 255.0f;
        final float green = (color1 >> 8 & 0xFF) / 255.0f;
        final float blue = (color1 & 0xFF) / 255.0f;
        final float alpha2 = (color2 >> 24 & 0xFF) / 255.0f;
        final float red2 = (color2 >> 16 & 0xFF) / 255.0f;
        final float green2 = (color2 >> 8 & 0xFF) / 255.0f;
        final float blue2 = (color2 & 0xFF) / 255.0f;
        alpha += 0.001f;
        if (alpha >= 1.0f) {
            alpha = 1.0f;
        }
        return 0;
    }

    public static void drawGradientRect(final double x, final double y, final double x2, final double y2, final int col1, final int col2) {
        final float f = (col1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (col1 & 0xFF) / 255.0f;
        final float f5 = (col2 >> 24 & 0xFF) / 255.0f;
        final float f6 = (col2 >> 16 & 0xFF) / 255.0f;
        final float f7 = (col2 >> 8 & 0xFF) / 255.0f;
        final float f8 = (col2 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glColor4f(f6, f7, f8, f5);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }

    public static void drawGradient(final double x, final double y, final double x2, final double y2, final int col1, final int col2) {
        final float f = (col1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (col1 & 0xFF) / 255.0f;
        final float f5 = (col2 >> 24 & 0xFF) / 255.0f;
        final float f6 = (col2 >> 16 & 0xFF) / 255.0f;
        final float f7 = (col2 >> 8 & 0xFF) / 255.0f;
        final float f8 = (col2 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glColor4f(f6, f7, f8, f5);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }

    public void drawFilledBBESP(final AxisAlignedBB axisalignedbb, final int color) {
        GL11.glPushMatrix();
        final float red = (color >> 24 & 0xFF) / 255.0f;
        final float green = (color >> 16 & 0xFF) / 255.0f;
        final float blue = (color >> 8 & 0xFF) / 255.0f;
        final float alpha = (color & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        drawFilledBox(axisalignedbb);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public void drawFilledBoxForEntity(final Entity entity, final AxisAlignedBB axisalignedbb, final int color) {
        GL11.glPushMatrix();
        final float red = (color >> 24 & 0xFF) / 255.0f;
        final float green = (color >> 16 & 0xFF) / 255.0f;
        final float blue = (color >> 8 & 0xFF) / 255.0f;
        final float alpha = (color & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        drawFilledBox(axisalignedbb);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public static void startSmooth() {
        GL11.glEnable(2848);
        GL11.glEnable(2881);
        GL11.glEnable(2832);
        GL11.glEnable(3042);
    }

    public static void endSmooth() {
        GL11.glDisable(2848);
        GL11.glDisable(2881);
        GL11.glDisable(2832);
        GL11.glDisable(3042);
    }

    public static void drawSCBox(double x, double y, double z, double x2, double y2, double z2) {
        x -= RenderManager.renderPosX;
        x2 -= RenderManager.renderPosX;
        y -= RenderManager.renderPosY;
        y2 -= RenderManager.renderPosY;
        z -= RenderManager.renderPosZ;
        z2 -= RenderManager.renderPosZ;
        GL11.glBegin(7);
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x, y2, z);
        GL11.glVertex3d(x2, y, z);
        GL11.glVertex3d(x2, y2, z);
        GL11.glVertex3d(x2, y, z2);
        GL11.glVertex3d(x2, y2, z2);
        GL11.glVertex3d(x, y, z2);
        GL11.glVertex3d(x, y2, z2);
        GL11.glVertex3d(x2, y2, z);
        GL11.glVertex3d(x2, y, z);
        GL11.glVertex3d(x, y2, z);
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x, y2, z2);
        GL11.glVertex3d(x, y, z2);
        GL11.glVertex3d(x2, y2, z2);
        GL11.glVertex3d(x2, y, z2);
        GL11.glVertex3d(x, y2, z);
        GL11.glVertex3d(x2, y2, z);
        GL11.glVertex3d(x2, y2, z2);
        GL11.glVertex3d(x, y2, z2);
        GL11.glVertex3d(x, y2, z);
        GL11.glVertex3d(x, y2, z2);
        GL11.glVertex3d(x2, y2, z2);
        GL11.glVertex3d(x2, y2, z);
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x2, y, z);
        GL11.glVertex3d(x2, y, z2);
        GL11.glVertex3d(x, y, z2);
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x, y, z2);
        GL11.glVertex3d(x2, y, z2);
        GL11.glVertex3d(x2, y, z);
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x, y2, z);
        GL11.glVertex3d(x, y, z2);
        GL11.glVertex3d(x, y2, z2);
        GL11.glVertex3d(x2, y, z2);
        GL11.glVertex3d(x2, y2, z2);
        GL11.glVertex3d(x2, y, z);
        GL11.glVertex3d(x2, y2, z);
        GL11.glVertex3d(x, y2, z2);
        GL11.glVertex3d(x, y, z2);
        GL11.glVertex3d(x, y2, z);
        GL11.glVertex3d(x, y, z);
        GL11.glVertex3d(x2, y2, z);
        GL11.glVertex3d(x2, y, z);
        GL11.glVertex3d(x2, y2, z2);
        GL11.glVertex3d(x2, y, z2);
        GL11.glEnd();
    }

    public void drawBoundingBoxESP(final AxisAlignedBB axisalignedbb, final float width, final int color) {
        GL11.glPushMatrix();
        final float red = (color >> 24 & 0xFF) / 255.0f;
        final float green = (color >> 16 & 0xFF) / 255.0f;
        final float blue = (color >> 8 & 0xFF) / 255.0f;
        final float alpha = (color & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(width);
        GL11.glColor4f(red, green, blue, alpha);
        drawOutlinedBox(axisalignedbb);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public void draw2DPlayerESP(final EntityPlayer ep, final double d, final double d1, final double d2) {
        final float distance = Minecraft.getMinecraft().thePlayer.getDistanceToEntity(ep);
        final float scale = (float) (0.09 + Minecraft.getMinecraft().thePlayer.getDistance(ep.posX, ep.posY, ep.posZ) / 10000.0);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d, (float) d1, (float) d2);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glScalef(-scale, -scale, scale);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glScaled(0.5, 0.5, 0.5);
        this.drawOutlineRect(-13.0f, -45.0f, 13.0f, 5.0f, -65536);
        GL11.glScaled(2.0, 2.0, 2.0);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glEnable(2896);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }

    public void drawOutlineForEntity(final Entity e, final AxisAlignedBB axisalignedbb, final float width, final float red, final float green, final float blue, final float alpha) {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(width);
        GL11.glColor4f(red, green, blue, alpha);
        drawOutlinedBox(axisalignedbb);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }

    public static void drawLines(final AxisAlignedBB bb, final float width, final float red, final float green, final float blue, final float alpha) {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(width);
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawing(2);
        worldRenderer.addVertex(bb.minX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        tessellator.draw();
        worldRenderer.startDrawing(2);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        tessellator.draw();
        worldRenderer.startDrawing(2);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.maxZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        tessellator.draw();
        worldRenderer.startDrawing(2);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.minZ);
        tessellator.draw();
        worldRenderer.startDrawing(2);
        worldRenderer.addVertex(bb.maxX, bb.minY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.minY, bb.maxZ);
        tessellator.draw();
        worldRenderer.startDrawing(2);
        worldRenderer.addVertex(bb.maxX, bb.maxY, bb.minZ);
        worldRenderer.addVertex(bb.minX, bb.maxY, bb.maxZ);
        tessellator.draw();
        GL11.glLineWidth(1.0f);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }

    public void drawOutlineBox(final AxisAlignedBB axisalignedbb, final float width, final int color) {
        GL11.glPushMatrix();
        final float red = (color >> 24 & 0xFF) / 255.0f;
        final float green = (color >> 16 & 0xFF) / 255.0f;
        final float blue = (color >> 8 & 0xFF) / 255.0f;
        final float alpha = (color & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(width);
        GL11.glColor4f(red, green, blue, alpha);
        drawOutlinedBox(axisalignedbb);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    public static void drawLines(AxisAlignedBB par1AxisAlignedBB) {
        GL11.glPushMatrix();
        GL11.glBegin(2);
        GL11.glVertex3d(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY,
                par1AxisAlignedBB.minZ);
        GL11.glVertex3d(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY,
                par1AxisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY,
                par1AxisAlignedBB.minZ);
        GL11.glVertex3d(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY,
                par1AxisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY,
                par1AxisAlignedBB.maxZ);
        GL11.glVertex3d(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY,
                par1AxisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY,
                par1AxisAlignedBB.minZ);
        GL11.glVertex3d(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY,
                par1AxisAlignedBB.minZ);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY,
                par1AxisAlignedBB.minZ);
        GL11.glVertex3d(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY,
                par1AxisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glBegin(2);
        GL11.glVertex3d(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY,
                par1AxisAlignedBB.minZ);
        GL11.glVertex3d(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY,
                par1AxisAlignedBB.maxZ);
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    public static void drawFilledBox(final AxisAlignedBB boundingBox) {
        if (boundingBox == null) {
            return;
        }
        GL11.glBegin(7);
        GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        GL11.glEnd();
        GL11.glBegin(7);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        GL11.glEnd();
    }

    public static void drawOutlinedBox(final AxisAlignedBB boundingBox) {
        if (boundingBox == null) {
            return;
        }
        GL11.glBegin(3);
        GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        GL11.glEnd();
        GL11.glBegin(3);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        GL11.glEnd();
        GL11.glBegin(1);
        GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        GL11.glEnd();
    }

    public void drawBoundingBox(final AxisAlignedBB axisalignedbb) {
        final Tessellator tessellator = Tessellator.instance;
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        worldRenderer.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        worldRenderer.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        worldRenderer.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        worldRenderer.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        worldRenderer.draw();
        worldRenderer.startDrawingQuads();
        worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
        worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
        worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
        worldRenderer.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
        worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
        worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
        worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
        worldRenderer.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
        worldRenderer.draw();
    }

    /**
     * Draw a 1 pixel wide horizontal line. Args: x1, x2, y, color
     */
    protected void drawHorizontalLine(int startX, int endX, int y, int color) {
        if (endX < startX) {
            int var5 = startX;
            startX = endX;
            endX = var5;
        }

        drawRect(startX, y, endX + 1, y + 1, color);
    }

    /**
     * Draw a 1 pixel wide vertical line. Args : x, y1, y2, color
     */
    protected void drawVerticalLine(int x, int startY, int endY, int color) {
        if (endY < startY) {
            int var5 = startY;
            startY = endY;
            endY = var5;
        }

        drawRect(x, startY + 1, x + 1, endY, color);
    }

    /**
     * Draws a solid color rectangle with the specified coordinates and color (ARGB format). Args: x1, y1, x2, y2, color
     */
    public static void drawRect(int left, int top, int right, int bottom, int color) {
        int var5;

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

        float var11 = (float) (color >> 24 & 255) / 255.0F;
        float var6 = (float) (color >> 16 & 255) / 255.0F;
        float var7 = (float) (color >> 8 & 255) / 255.0F;
        float var8 = (float) (color & 255) / 255.0F;
        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer var10 = var9.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.func_179090_x();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(var6, var7, var8, var11);
        var10.startDrawingQuads();
        var10.addVertex((double) left, (double) bottom, 0.0D);
        var10.addVertex((double) right, (double) bottom, 0.0D);
        var10.addVertex((double) right, (double) top, 0.0D);
        var10.addVertex((double) left, (double) top, 0.0D);
        var9.draw();
        GlStateManager.func_179098_w();
        GlStateManager.disableBlend();
    }

    /**
     * Draws a rectangle with a vertical gradient between the specified colors (ARGB format). Args : x1, y1, x2, y2,
     * topColor, bottomColor
     */
    protected void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
        float var7 = (float) (startColor >> 24 & 255) / 255.0F;
        float var8 = (float) (startColor >> 16 & 255) / 255.0F;
        float var9 = (float) (startColor >> 8 & 255) / 255.0F;
        float var10 = (float) (startColor & 255) / 255.0F;
        float var11 = (float) (endColor >> 24 & 255) / 255.0F;
        float var12 = (float) (endColor >> 16 & 255) / 255.0F;
        float var13 = (float) (endColor >> 8 & 255) / 255.0F;
        float var14 = (float) (endColor & 255) / 255.0F;
        GlStateManager.func_179090_x();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator var15 = Tessellator.getInstance();
        WorldRenderer var16 = var15.getWorldRenderer();
        var16.startDrawingQuads();
        var16.func_178960_a(var8, var9, var10, var7);
        var16.addVertex((double) right, (double) top, (double) this.zLevel);
        var16.addVertex((double) left, (double) top, (double) this.zLevel);
        var16.func_178960_a(var12, var13, var14, var11);
        var16.addVertex((double) left, (double) bottom, (double) this.zLevel);
        var16.addVertex((double) right, (double) bottom, (double) this.zLevel);
        var15.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.func_179098_w();
    }

    /**
     * Renders the specified text to the screen, center-aligned. Args : renderer, string, x, y, color
     */
    public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawStringWithShadow(text, (float) (x - fontRendererIn.getStringWidth(text) / 2), (float) y, color);
    }

    /**
     * Renders the specified text to the screen. Args : renderer, string, x, y, color
     */
    public void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawStringWithShadow(text, (float) x, (float) y, color);
    }

    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     */
    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
        float var7 = 0.00390625F;
        float var8 = 0.00390625F;
        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer var10 = var9.getWorldRenderer();
        var10.startDrawingQuads();
        var10.addVertexWithUV((double) (x + 0), (double) (y + height), (double) this.zLevel, (double) ((float) (textureX + 0) * var7), (double) ((float) (textureY + height) * var8));
        var10.addVertexWithUV((double) (x + width), (double) (y + height), (double) this.zLevel, (double) ((float) (textureX + width) * var7), (double) ((float) (textureY + height) * var8));
        var10.addVertexWithUV((double) (x + width), (double) (y + 0), (double) this.zLevel, (double) ((float) (textureX + width) * var7), (double) ((float) (textureY + 0) * var8));
        var10.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) this.zLevel, (double) ((float) (textureX + 0) * var7), (double) ((float) (textureY + 0) * var8));
        var9.draw();
    }

    public void func_175174_a(float p_175174_1_, float p_175174_2_, int p_175174_3_, int p_175174_4_, int p_175174_5_, int p_175174_6_) {
        float var7 = 0.00390625F;
        float var8 = 0.00390625F;
        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer var10 = var9.getWorldRenderer();
        var10.startDrawingQuads();
        var10.addVertexWithUV((double) (p_175174_1_ + 0.0F), (double) (p_175174_2_ + (float) p_175174_6_), (double) this.zLevel, (double) ((float) (p_175174_3_ + 0) * var7), (double) ((float) (p_175174_4_ + p_175174_6_) * var8));
        var10.addVertexWithUV((double) (p_175174_1_ + (float) p_175174_5_), (double) (p_175174_2_ + (float) p_175174_6_), (double) this.zLevel, (double) ((float) (p_175174_3_ + p_175174_5_) * var7), (double) ((float) (p_175174_4_ + p_175174_6_) * var8));
        var10.addVertexWithUV((double) (p_175174_1_ + (float) p_175174_5_), (double) (p_175174_2_ + 0.0F), (double) this.zLevel, (double) ((float) (p_175174_3_ + p_175174_5_) * var7), (double) ((float) (p_175174_4_ + 0) * var8));
        var10.addVertexWithUV((double) (p_175174_1_ + 0.0F), (double) (p_175174_2_ + 0.0F), (double) this.zLevel, (double) ((float) (p_175174_3_ + 0) * var7), (double) ((float) (p_175174_4_ + 0) * var8));
        var9.draw();
    }

    public void func_175175_a(int p_175175_1_, int p_175175_2_, TextureAtlasSprite p_175175_3_, int p_175175_4_, int p_175175_5_) {
        Tessellator var6 = Tessellator.getInstance();
        WorldRenderer var7 = var6.getWorldRenderer();
        var7.startDrawingQuads();
        var7.addVertexWithUV((double) (p_175175_1_ + 0), (double) (p_175175_2_ + p_175175_5_), (double) this.zLevel, (double) p_175175_3_.getMinU(), (double) p_175175_3_.getMaxV());
        var7.addVertexWithUV((double) (p_175175_1_ + p_175175_4_), (double) (p_175175_2_ + p_175175_5_), (double) this.zLevel, (double) p_175175_3_.getMaxU(), (double) p_175175_3_.getMaxV());
        var7.addVertexWithUV((double) (p_175175_1_ + p_175175_4_), (double) (p_175175_2_ + 0), (double) this.zLevel, (double) p_175175_3_.getMaxU(), (double) p_175175_3_.getMinV());
        var7.addVertexWithUV((double) (p_175175_1_ + 0), (double) (p_175175_2_ + 0), (double) this.zLevel, (double) p_175175_3_.getMinU(), (double) p_175175_3_.getMinV());
        var6.draw();
    }

    /**
     * Draws a textured rectangle at z = 0. Args: x, y, u, v, width, height, textureWidth, textureHeight
     */
    public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
        float var8 = 1.0F / textureWidth;
        float var9 = 1.0F / textureHeight;
        Tessellator var10 = Tessellator.getInstance();
        WorldRenderer var11 = var10.getWorldRenderer();
        var11.startDrawingQuads();
        var11.addVertexWithUV((double) x, (double) (y + height), 0.0D, (double) (u * var8), (double) ((v + (float) height) * var9));
        var11.addVertexWithUV((double) (x + width), (double) (y + height), 0.0D, (double) ((u + (float) width) * var8), (double) ((v + (float) height) * var9));
        var11.addVertexWithUV((double) (x + width), (double) y, 0.0D, (double) ((u + (float) width) * var8), (double) (v * var9));
        var11.addVertexWithUV((double) x, (double) y, 0.0D, (double) (u * var8), (double) (v * var9));
        var10.draw();
    }

    /**
     * Draws a scaled, textured, tiled modal rect at z = 0. This method isn't used anywhere in vanilla code.
     */
    public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
        float var10 = 1.0F / tileWidth;
        float var11 = 1.0F / tileHeight;
        Tessellator var12 = Tessellator.getInstance();
        WorldRenderer var13 = var12.getWorldRenderer();
        var13.startDrawingQuads();
        var13.addVertexWithUV((double) x, (double) (y + height), 0.0D, (double) (u * var10), (double) ((v + (float) vHeight) * var11));
        var13.addVertexWithUV((double) (x + width), (double) (y + height), 0.0D, (double) ((u + (float) uWidth) * var10), (double) ((v + (float) vHeight) * var11));
        var13.addVertexWithUV((double) (x + width), (double) y, 0.0D, (double) ((u + (float) uWidth) * var10), (double) (v * var11));
        var13.addVertexWithUV((double) x, (double) y, 0.0D, (double) (u * var10), (double) (v * var11));
        var12.draw();
    }
}
