package de.tired.api.extension.processors.extensions.generally;

import de.tired.api.extension.AbstractExtension;
import de.tired.api.util.math.Vec;
import de.tired.api.util.render.Translate;
import de.tired.api.util.shader.renderapi.AnimationUtil;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import javax.vecmath.Vector3d;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.opengl.GL11.*;

public class RenderProcessor extends AbstractExtension {

    private float xPos1 = 0;
    public double xOffset = 0;
    public double xOffset1 = 0;
    public float animationY;
    private Translate translate;
    private final static IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
    private final static FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
    private final static FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
    public int yOffset = 0;
    private int fadeAlpha = 0;


    public double interpolate(double current, double old, double scale) {
        return old + (current - old) * scale;
    }

    public static void drawLine(double x, double y, double z, double x2, double y2, double z2,  Color color, int thickness) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glEnable(2848);
        GL11.glLineWidth(thickness);
        GlStateManager.color((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F);
        worldrenderer.begin(1, DefaultVertexFormats.POSITION);
        worldrenderer.pos((double)x, (double)y, z).endVertex();
        worldrenderer.pos((double)x2, (double)y2, z2).endVertex();
        tessellator.draw();
        GL11.glDisable(2848);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public void pop() {
        GlStateManager.popMatrix();
    }

    public ScaledResolution getResolution() {
        return new ScaledResolution(MC);
    }

    public Vector3d project(double x, double y, double z) {
        final FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);
        GL11.glGetFloat(GL_MODELVIEW_MATRIX, modelview);
        GL11.glGetFloat(GL_PROJECTION_MATRIX, projection);
        GL11.glGetInteger(GL_VIEWPORT, viewport);
        if (GLU.gluProject((float) x, (float) y, (float) z, modelview, projection, viewport, vector)) {
            return new Vector3d(vector.get(0) / getResolution().getScaleFactor(), (Display.getHeight() - vector.get(1)) / getResolution().getScaleFactor(), vector.get(2));
        }
        return null;
    }

    private final Frustum frustrum;


    public static void drawRoundedRectangle(double left, double top, double right, double bottom, double radius, int color) {
        glScaled(0.5D, 0.5D, 0.5D);
        left *= 2.0D;
        top *= 2.0D;
        right *= 2.0D;
        bottom *= 2.0D;
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_LINE_SMOOTH);
        GlStateManager.enableBlend();
        glColor(color);
        glBegin(9);

        int i;
        for (i = 0; i <= 90; i += 1)
            glVertex2d(left + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, top + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D);
        for (i = 90; i <= 180; i += 1)
            glVertex2d(left + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, bottom - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D);
        for (i = 0; i <= 90; i += 1)
            glVertex2d(right - radius + Math.sin(i * Math.PI / 180.0D) * radius, bottom - radius + Math.cos(i * Math.PI / 180.0D) * radius);
        for (i = 90; i <= 180; i += 1)
            glVertex2d(right - radius + Math.sin(i * Math.PI / 180.0D) * radius, top + radius + Math.cos(i * Math.PI / 180.0D) * radius);
        glEnd();
        glEnable(GL_TEXTURE_2D);
        glScaled(2.0D, 2.0D, 2.0D);
        glColor4d(1, 1, 1, 1);
    }

    public static void glColor(int hex) {
        float alpha = (hex >> 24 & 0xFF) / 255.0F;
        float red = (hex >> 16 & 0xFF) / 255.0F;
        float green = (hex >> 8 & 0xFF) / 255.0F;
        float blue = (hex & 0xFF) / 255.0F;
        GL11.glColor4f(red, green, blue, alpha);
    }

    public Framebuffer createFramebuffer(Framebuffer framebuffer, boolean depth) {
        if (framebuffer == null || framebuffer.framebufferWidth != MC.displayWidth || framebuffer.framebufferHeight != MC.displayHeight) {
            if (framebuffer != null) {
                framebuffer.deleteFramebuffer();
            }
            return new Framebuffer(MC.displayWidth, MC.displayHeight, depth);
        }
        return framebuffer;
    }


    public void drawBordered(double x, double y, double x2, double y2, double thickness, int inside, int outline) {
        double fix = 0.0;
        if (thickness < 1.0) {
            fix = 1.0;
        }
        drawRect(x + thickness, y + thickness, x2 - thickness, y2 - thickness, inside);
        drawRect(x, y + 1.0 - fix, x + thickness, y2, outline);
        drawRect(x, y, x2 - 1.0 + fix, y + thickness, outline);
        drawRect(x2 - thickness, y, x2, y2 - 1.0 + fix, outline);
        drawRect(x + 1.0 - fix, y2 - thickness, x2, y2, outline);
    }

    public void drawOutlineRect2(final float drawX, final float drawY, final float drawWidth, final float drawHeight, final int color) {
        drawRect(drawX, drawY, drawWidth, drawY + 0.5f, color);
        drawRect(drawX, drawY + 0.5f, drawX + 0.5f, drawHeight, color);
        drawRect(drawWidth - 0.5f, drawY + 0.5f, drawWidth, drawHeight - 0.5f, color);
        drawRect(drawX + 0.5f, drawHeight - 0.5f, drawWidth, drawHeight, color);
    }

    public void drawOutlineRect(final float drawX, final float drawY, final float drawWidth, final float drawHeight, final int color) {
        Gui.drawRect(drawX, drawY, drawWidth, drawHeight, color);
        drawRect(drawX, drawY, drawWidth, drawY + 0.5f, color);
        drawRect(drawX, drawY + 0.5f, drawX + 0.5f, drawHeight, color);
        drawRect(drawWidth - 0.5f, drawY + 0.5f, drawWidth, drawHeight - 0.5f, color);
        drawRect(drawX + 0.5f, drawHeight - 0.5f, drawWidth, drawHeight, color);
    }


    public float delta;

    public RenderProcessor() {
        translate = new Translate(0, 0);
        frustrum = new Frustum();
    }

    public void drawCheckMark(float x, float y, int width, int color) {
        float f = (float) (color >> 24 & 255) / 255.0F;
        float f1 = (float) (color >> 16 & 255) / 255.0F;
        float f2 = (float) (color >> 8 & 255) / 255.0F;
        float f3 = (float) (color & 255) / 255.0F;
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(1.5F);
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glVertex2d((double) (x + (float) width) - 6.5D, (double) (y + 4.0F));
        GL11.glVertex2d((double) (x + (float) width) - 11.5D, (double) (y + 10.0F));
        GL11.glVertex2d((double) (x + (float) width) - 13.5D, (double) (y + 8.0F));
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void drawScaledCustomSizeModalCircle(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
        float f = 1.0F / tileWidth;
        float f1 = 1.0F / tileHeight;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_TEX);

        GL11.glPushMatrix();
        float xRadius = width / 2f;
        float yRadius = height / 2f;
        float uRadius = (((u + (float) uWidth) * f) - (u * f)) / 2f;
        float vRadius = (((v + (float) vHeight) * f1) - (v * f1)) / 2f;
        for (int i = 0; i <= 360; i += 10) {
            double xPosOffset = Math.sin(i * Math.PI / 180.0D);
            double yPosOffset = Math.cos(i * Math.PI / 180.0D);
            worldrenderer.pos(x + xRadius + xPosOffset * xRadius, y + yRadius + yPosOffset * yRadius, 0).tex(u * f + uRadius + xPosOffset * uRadius, v * f1 + vRadius + yPosOffset * vRadius).endVertex();
        }
        tessellator.draw();
        GL11.glPopMatrix();
    }

    public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
        float f = (col1 >> 24 & 0xFF) / 255.0F;
        float f1 = (col1 >> 16 & 0xFF) / 255.0F;
        float f2 = (col1 >> 8 & 0xFF) / 255.0F;
        float f3 = (col1 & 0xFF) / 255.0F;

        float f4 = (col2 >> 24 & 0xFF) / 255.0F;
        float f5 = (col2 >> 16 & 0xFF) / 255.0F;
        float f6 = (col2 >> 8 & 0xFF) / 255.0F;
        float f7 = (col2 & 0xFF) / 255.0F;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glShadeModel(GL11.GL_SMOOTH);

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

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glColor4d(255, 255, 255, 255);

    }

    public void drawPlayerHead(EntityPlayer player, double x, double y, int width, int height) {
        AbstractClientPlayer clientPlayer = (AbstractClientPlayer) player;
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        MC.getTextureManager().bindTexture(clientPlayer.getLocationSkin());
        Gui.drawScaledCustomSizeModalRect(x, y, 8.0f, 8.0f, 8, 8, width, height, 64.0f, 64.0f);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public void drawPlayerHeadRes(ResourceLocation location, int x, int y, int width, int height) {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GL11.glColor4f(1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
        MC.getTextureManager().bindTexture(location);
        Gui.drawScaledCustomSizeModalRect(x, y, 8.0f, 8.0f, 8, 8, width, height, 8, 8);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn) {
        return networkPlayerInfoIn.getDisplayName() != null ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName(networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
    }

    public boolean isInViewFrustrum(Entity entity) {
        return isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
    }

    public void drawHorizontalLine(float x, float y, float x1, float thickness, int color) {
        drawRect(x, y, x1, y + thickness, color);
    }

    public void drawVerticalLine(float x, float y, float y1, float thickness, int color) {
        drawRect(x, y, x + thickness, y1, color);
    }

    public void drawRect(double left, double top, double right, double bottom, int color) {
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
        float f3 = (float) (color >> 24 & 255) / 255.0f;
        float f = (float) (color >> 16 & 255) / 255.0f;
        float f1 = (float) (color >> 8 & 255) / 255.0f;
        float f2 = (float) (color & 255) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(left, bottom, 0.0).endVertex();
        worldrenderer.pos(right, bottom, 0.0).endVertex();
        worldrenderer.pos(right, top, 0.0).endVertex();
        worldrenderer.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }


    public boolean isInViewFrustrum(AxisAlignedBB bb) {
        Entity current = MC.getRenderViewEntity();
        frustrum.setPosition(current.posX, current.posY, current.posZ);
        return frustrum.isBoundingBoxInFrustum(bb);
    }


    public void line(Vec firstPoint, Vec secondPoint, int color) {
        line(firstPoint.getX(), firstPoint.getY(), secondPoint.getX(), secondPoint.getY(), color);
    }

    private void line(double x, double y, double x1, double y1, int color) {
        GL11.glPushMatrix();
        boolean blend = GL11.glIsEnabled(GL11.GL_BLEND);
        boolean texture2D = GL11.glIsEnabled(GL11.GL_TEXTURE_2D);
        boolean lineSmooth = GL11.glIsEnabled(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(1.5F);
        GlStateManager.color((float) (color >> 16 & 255) / 255.0F, (float) (color >> 8 & 255) / 255.0F, (float) (color & 255) / 255.0F, (float) (color >> 24 & 255) / 255.0F);
        GL11.glBegin(1);
        GL11.glVertex2f((float) x1, (float) y1);
        GL11.glVertex2f((float) x, (float) y);
        GL11.glEnd();
        if (!lineSmooth) {
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
        }

        if (texture2D) {
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }

        if (!blend) {
            GL11.glDisable(GL11.GL_BLEND);
        }

        GL11.glPopMatrix();
    }

    public void renderHotbar(boolean animation, boolean rect) {
        this.animationY = (float) AnimationUtil.getAnimationState(animationY, MC.currentScreen instanceof GuiChat ? 0 : 23, 440);
        GlStateManager.disableDepth();
        GlStateManager.disableBlend();
        RenderHelper.disableStandardItemLighting();
        push();

        ScaledResolution sr = new ScaledResolution(MC);

        if (MC.currentScreen instanceof GuiChat) {
            if (xOffset < (float) (sr.getScaledWidth() / 2 - 89) + 22f - 27f) {
                xOffset += 5;
            }
            if (xOffset1 < sr.getScaledWidth_double() - (float) (sr.getScaledWidth() / 2 - 89) + 22f - 24.5f - 178f) {
                xOffset1 += 5;
            }

        } else {
            if (xOffset > 0) {
                xOffset -= 5;
            }
            if (xOffset1 > 0) {
                xOffset1 -= 5;
            }
            if (yOffset > 0) {
                yOffset--;
            }
        }
        int item = MC.thePlayer.inventory.currentItem + 1;

        float xPos = item * 20 + 1.5f;
        if (animation) {

            if (xPos1 < xPos) {
                xPos1 += .5;
            } else if (xPos1 > xPos) {
                xPos1 -= .5;
            }

            if (xPos1 == xPos + 0.5 || xPos1 == xPos - 0.5) {
                xPos1 = xPos;
            }

        } else {
            xPos1 = xPos;
        }


        float selectionheight = 23;

        if (rect) {
            drawRoundedRect((int) ((float) (sr.getScaledWidth() / 2 - 89) + 22f - 24.5f), sr.getScaledHeight() - animationY, (int) (sr.getScaledWidth_double() / 2 + 90), sr.getScaledHeight() + animationY, 2, -1);
        }

        drawRoundedRectangle((int) (sr.getScaledWidth() / 2 - 88) + (int) xPos1 - (int) 25, (int) (sr.getScaledHeight() - (int) selectionheight - animationY + 23), (int) ((int) sr.getScaledWidth() / 2 - (int) 91) + (int) xPos1, sr.getScaledHeight() + 3, 1, Integer.MIN_VALUE);

        pop();
    }

    public final void push() {
        GL11.glPushMatrix();
    }

    public final void enable(int glTarget) {
        GL11.glEnable(glTarget);
    }

    public final void disable(int glTarget) {
        GL11.glDisable(glTarget);
    }

    public final void start() {
        enable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        disable(GL11.GL_TEXTURE_2D);
        disable(GL11.GL_CULL_FACE);
        GlStateManager.disableAlpha();
    }

    public final void stop() {
        GlStateManager.enableAlpha();
        enable(GL11.GL_CULL_FACE);
        enable(GL11.GL_TEXTURE_2D);
        disable(GL11.GL_BLEND);
        color(Color.white);
    }

    public final void rotate(double x, double y, double z, double angle) {
        GL11.glRotated(angle, x, y, z);
    }

    public static void color(double red, double green, double blue, double alpha) {
        GL11.glColor4d(red, green, blue, alpha);
    }

    public final void color(double red, double green, double blue) {
        color(red, green, blue, 1);
    }

    public static void color(Color color) {
        if (color == null)
            color = Color.white;
        color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);
    }

    public final void vertex(double x, double y) {
        GL11.glVertex2d(x, y);
    }

    public final void begin(int glMode) {
        GL11.glBegin(glMode);
    }

    public final void end() {
        GL11.glEnd();
    }


    public final void polygon(double x, double y, double sideLength, double amountOfSides, boolean filled, Color color) {
        sideLength /= 2;
        start();
        if (color != null)
            color(color);
        if (!filled) GL11.glLineWidth(1);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        begin(filled ? GL11.GL_TRIANGLE_FAN : GL11.GL_LINE_STRIP);
        {
            for (double i = 0; i <= amountOfSides; i++) {
                double angle = i * (Math.PI * 2) / amountOfSides;
                vertex(x + (sideLength * Math.cos(angle)) + sideLength, y + (sideLength * Math.sin(angle)) + sideLength);
            }
        }
        end();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        stop();
    }


    public final void clearColor() {
        GlStateManager.color(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public final void triangle(double x, double y, double sideLength, Color color) {
        polygon(x, y, sideLength, 3, true, color);
    }

    public void drawRoundedRect(float x, float y, float x2, float y2, float round, int color) {

        x = (float) ((double) x + ((double) (round / 2.0f) + 0.5));
        y = (float) ((double) y + ((double) (round / 2.0f) + 0.5));
        x2 = (float) ((double) x2 - ((double) (round / 2.0f) + 0.5));
        y2 = (float) ((double) y2 - ((double) (round / 2.0f) + 0.5));
        GlStateManager.pushMatrix();
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        Gui.drawRect(x, y, x2, y2, color);
        renderObjectCircle(x2 - round / 2.0f, y + round / 2.0f, round, color);
        renderObjectCircle(x + round / 2.0f, y2 - round / 2.0f, round, color);
        renderObjectCircle(x + round / 2.0f, y + round / 2.0f, round, color);
        renderObjectCircle(x2 - round / 2.0f, y2 - round / 2.0f, round, color);
        Gui.drawRect(x - round / 2.0f - 0.5f, y + round / 2.0f, x2, y2 - round / 2.0f, color);
        Gui.drawRect(x, y + round / 2.0f, x2 + round / 2.0f + 0.5f, y2 - round / 2.0f, color);
        Gui.drawRect(x + round / 2.0f, y - round / 2.0f - 0.5f, x2 - round / 2.0f, y2 - round / 2.0f, color);
        Gui.drawRect(x + round / 2.0f, y, x2 - round / 2.0f, y2 + round / 2.0f + 0.5f, color);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.popMatrix();
    }

    public void renderObjectCircle(double x, double y, double round, int color) {
        arc(x, y, 0, 360, (float) round, color);
    }

    public float[] getColor(int color) {
        if ((color & -67108864) == 0) {
            color |= -16777216;
        }

        return new float[]{(float) (color >> 16 & 255) / 255.0F, (float) (color >> 8 & 255) / 255.0F, (float) (color & 255) / 255.0F, (float) (color >> 24 & 255) / 255.0F};
    }

    public static void color(int color) {
        float f = (float) (color >> 24 & 255) / 255.0f;
        float f1 = (float) (color >> 16 & 255) / 255.0f;
        float f2 = (float) (color >> 8 & 255) / 255.0f;
        float f3 = (float) (color & 255) / 255.0f;
        GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
    }


    public void renderGradient(int x, int y, int width, int height, int color, int color2) {
        float alpha = (color >> 24 & 0xFF) / 255.0F;
        float R = (color >> 16 & 0xFF) / 255.0F;
        float G = (color >> 8 & 0xFF) / 255.0F;
        float B = (color & 0xFF) / 255.0F;
        float alphaArg2 = (color2 >> 24 & 0xFF) / 255.0F;
        float RArg2 = (color2 >> 16 & 0xFF) / 255.0F;
        float GArg2 = (color2 >> 8 & 0xFF) / 255.0F;
        float BArg2 = (color2 & 0xFF) / 255.0F;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        doSmooth();
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(R, G, B, alpha);
        GL11.glVertex2d(width, y);
        GL11.glVertex2d(x, y);

        GL11.glColor4f(RArg2, GArg2, BArg2, alphaArg2);
        GL11.glVertex2d(x, height);
        GL11.glVertex2d(width, height);
        GL11.glEnd();
        GL11.glPopMatrix();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH); //-- disabling smoothing from "doSmooth();"
        GL11.glShadeModel(GL11.GL_FLAT);
        GlStateManager.resetColor();
    }

    private void doSmooth() {
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glShadeModel(GL11.GL_SMOOTH);
    }

    public void arc(double x, double y, double start, double end, float radius, int color) {
        arcEllipse(x, y, start, end, radius, radius, color);
    }
    public void arcEllipse(float x, float y, float start, float end, float w, float h, int color) {
        GlStateManager.color(0.0F, 0.0F, 0.0F);
        GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);

        float temp;
        if (start > end) {
            temp = end;
            end = start;
            start = temp;
        }
        float alpha = (float) (color >> 24 & 255) / 255.0F;
        float R = (float) (color >> 16 & 255) / 255.0F;
        float G = (float) (color >> 8 & 255) / 255.0F;
        float B = (float) (color & 255) / 255.0F;
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.color(R, G, B, alpha);
        float i;
        float ldx;
        float ldy;
        if (alpha > 0.5F) {
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GL11.glLineWidth(2);
            GL11.glBegin(3);

            for (i = end; i >= start; i -= 4.0F) {
                ldx = (float) Math.cos((double) i * Math.PI / 180.0D) * w * 1.001F;
                ldy = (float) Math.sin((double) i * Math.PI / 180.0D) * h * 1.001F;
                GL11.glVertex2f(x + ldx, y + ldy);
            }

            GL11.glEnd();
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
        }

        GL11.glBegin(6);

        for (i = end; i >= start; i -= 4.0F) {
            ldx = (float) Math.cos((double) i * Math.PI / 180.0D) * w;
            ldy = (float) Math.sin((double) i * Math.PI / 180.0D) * h;
            GL11.glVertex2f(x + ldx, y + ldy);
        }

        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public void arcEllipse(double x, double y, double start, double end, double w, double h, int color) {
        GlStateManager.color(0.0F, 0.0F, 0.0F);
        GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);

        float temp;
        if (start > end) {
            temp = (float) end;
            end = start;
            start = temp;
        }
        float alpha = (float) (color >> 24 & 255) / 255.0F;
        float R = (float) (color >> 16 & 255) / 255.0F;
        float G = (float) (color >> 8 & 255) / 255.0F;
        float B = (float) (color & 255) / 255.0F;
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.color(R, G, B, alpha);
        float i;
        float ldx;
        float ldy;
        if (alpha > 0.5F) {
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GL11.glLineWidth(2);
            GL11.glBegin(3);

            for (i = (float) end; i >= start; i -= 4.0F) {
                ldx = (float) ((float) Math.cos((double) i * Math.PI / 180.0D) * w * 1.001F);
                ldy = (float) ((float) Math.sin((double) i * Math.PI / 180.0D) * h * 1.001F);
                GL11.glVertex2f((float) (x + ldx), (float) (y + ldy));
            }

            GL11.glEnd();
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
        }

        GL11.glBegin(6);

        for (i = (float) end; i >= start; i -= 4.0F) {
            ldx = (float) ((float) Math.cos((double) i * Math.PI / 180.0D) * w);
            ldy = (float) ((float) Math.sin((double) i * Math.PI / 180.0D) * h);
            GL11.glVertex2f((float) (x + ldx), (float) (y + ldy));
        }

        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    public void drawOutlinedBoundingBox(AxisAlignedBB aa) {
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

    public void drawBoundingBox(AxisAlignedBB aa) {
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

    public void drawRoundedRect2(int x, int y, int width, int height, int cornerRadius, Color color) {
        Gui.drawRect(x, y + cornerRadius, x + cornerRadius, y + height - cornerRadius, color.getRGB());
        Gui.drawRect(x + cornerRadius, y, x + width - cornerRadius, y + height, color.getRGB());
        Gui.drawRect(x + width - cornerRadius, y + cornerRadius, x + width, y + height - cornerRadius, color.getRGB());

        this.drawArc(x + cornerRadius, y + cornerRadius, cornerRadius, 0, 90, color);
        this.drawArc(x + width - cornerRadius, y + cornerRadius, cornerRadius, 270, 360, color);
        this.drawArc(x + width - cornerRadius, y + height - cornerRadius, cornerRadius, 180, 270, color);
        this.drawArc(x + cornerRadius, y + height - cornerRadius, cornerRadius, 90, 180, color);
    }


    private void drawArc(int x, int y, int radius, int startAngle, int endAngle, Color color) {

        GL11.glPushMatrix();
        GL11.glEnable(GL_BLEND);
        GL11.glDisable(GL_TEXTURE_2D);
        GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f((float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255, (float) color.getAlpha() / 255);

        WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();

        worldRenderer.begin(6, DefaultVertexFormats.POSITION);
        worldRenderer.pos(x, y, 0).endVertex();

        for (int i = (int) (startAngle / 360.0 * 100); i <= (int) (endAngle / 360.0 * 100); i++) {
            double angle = (Math.PI * 2 * i / 100) + Math.toRadians(180);
            worldRenderer.pos(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, 0).endVertex();
        }

        Tessellator.getInstance().draw();

        GL11.glEnable(GL_TEXTURE_2D);
        GL11.glDisable(GL_BLEND);
        GL11.glPopMatrix();
    }

    private void drawArc2(float x, float y, int radius, int startAngle, int endAngle, Color color) {

        GL11.glPushMatrix();
        GL11.glEnable(GL_BLEND);
        GL11.glDisable(GL_TEXTURE_2D);
        GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f((float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255, (float) color.getAlpha() / 255);

        final WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();

        worldRenderer.begin(6, DefaultVertexFormats.POSITION);
        worldRenderer.pos(x, y, 0).endVertex();

        for (int i = (int) (startAngle / 360.0 * 100); i <= (int) (endAngle / 360.0 * 100); i++) {
            double angle = (Math.PI * 2 * i / 100) + Math.toRadians(180);
            worldRenderer.pos(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, 0).endVertex();
        }

        Tessellator.getInstance().draw();
        GL11.glEnable(GL_TEXTURE_2D);
        GL11.glDisable(GL_BLEND);
        GL11.glPopMatrix();
    }

}
