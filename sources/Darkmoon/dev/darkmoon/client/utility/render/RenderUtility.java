package dev.darkmoon.client.utility.render;

import com.jhlabs.image.GaussianFilter;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.module.impl.render.Hud;
import dev.darkmoon.client.module.impl.render.Particle;
import dev.darkmoon.client.utility.render.blur.GaussianBlur;
import dev.darkmoon.client.utility.render.shader.Shader;
import dev.darkmoon.client.utility.Utility;
import dev.darkmoon.client.utility.misc.DiscordPresence;
import dev.darkmoon.client.utility.render.shader.ShaderUtility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ConcurrentModificationException;
import java.util.HashMap;

import static dev.darkmoon.client.utility.render.ColorUtility.setColor;
import static org.lwjgl.opengl.GL11.*;

public class RenderUtility implements Utility {
    private static final Tessellator TESSELLATOR = Tessellator.getInstance();
    private static final BufferBuilder BUILDER = TESSELLATOR.getBuffer();
    private static final Shader ROUNDED_GRADIENT = new Shader("darkmoon/shaders/rounded_gradient.fsh", true);
    private static final Shader ROUNDED = new Shader("darkmoon/shaders/rounded.fsh", true);
    public static Shader roundedOutlineShader = new Shader("darkmoon/shaders/outlinerect.frag", true);
    private static final Shader GRADIENT_MASK = new Shader("darkmoon/shaders/gradient_mask.fsh", true);
    private static final Shader ROUNDED_TEXTURE = new Shader("darkmoon/shaders/rounded_texture.fsh", true);
    private static final HashMap<Integer, Integer> SHADOW_CACHE = new HashMap<>();
    public static Frustum FRUSTUM = new Frustum();
    private static int PROFILE_ID = -1337;
    public static void color(int color) {
        color(color, (float) (color >> 24 & 255) / 255.0F);
    }
    public static void drawRound(float x, float y, float width, float height, float radius, Color color) {
        drawRound(x, y, width, height, radius, false, color);
    }
    public static void drawRect4(double x, double y, double width, double height, int color) {
        GlStateManager.resetColor();
        GLUtility.setup2DRendering(() -> GLUtility.render(GL11.GL_QUADS, () -> {
            color(color);
            GL11.glVertex2d(x, y);
            GL11.glVertex2d(x, height);
            GL11.glVertex2d(width, height);
            GL11.glVertex2d(width, y);
        }));
    }
    public static void renderBreadCrumbs(final Vec3d vec3s, int i, Color color, String mode) {

        GlStateManager.disableDepth();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        try {

            boolean draw = true;

            final double x = vec3s.x - (mc.getRenderManager()).renderPosX;
            final double y = vec3s.y - (mc.getRenderManager()).renderPosY;
            final double z = vec3s.z - (mc.getRenderManager()).renderPosZ;

            final double distanceFromPlayer = mc.player.getDistance(vec3s.x, vec3s.y - 1, vec3s.z);
            int quality = (int) (distanceFromPlayer * 4 + 10);

            if (quality > 350)
                quality = 350;

            if (i % 10 != 0 && distanceFromPlayer > 25) {
                draw = false;
            }

            if (i % 3 == 0 && distanceFromPlayer > 15) {
                draw = false;
            }

            if (draw) {

                GL11.glPushMatrix();
                GL11.glTranslated(x, y, z);

                final float scale = 0.04f;
                GL11.glScalef(-scale, -scale, -scale);

                GL11.glRotated(-(mc.getRenderManager()).playerViewY, 0.0D, 1.0D, 0.0D);
                GlStateManager.rotate(mc.getRenderManager().playerViewX, mc.gameSettings.thirdPersonView == 2 ? -1.f : 1.f, .0f, .0f);

                Color c;
                if (Particle.modeSetting.is("Client")) {
                    c = ColorUtility.getColor(i);
                } else {
                    c = Particle.color.getColor();
                }

                RenderUtility.drawFilledCircleNoGL(0, 0, 0.7, c.hashCode(), quality);

                if (distanceFromPlayer < 4)
                    RenderUtility.drawFilledCircleNoGL(0, 0, 1.4, new Color(c.getRed(), c.getGreen(), c.getBlue(), 50).hashCode(), quality);

                if (distanceFromPlayer < 20)
                    RenderUtility.drawFilledCircleNoGL(0, 0, 2.3, new Color(c.getRed(), c.getGreen(), c.getBlue(), 30).hashCode(), quality);

                GL11.glScalef(0.8f, 0.8f, 0.8f);

                GL11.glPopMatrix();

            }

        } catch (final ConcurrentModificationException ignored) {
        }

        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GlStateManager.enableDepth();

        GL11.glColor3d(255, 255, 255);
    }
    public static void drawFilledCircleNoGL(final int x, final int y, final double r, final int c, final int quality) {
        final float f = ((c >> 24) & 0xff) / 255F;
        final float f1 = ((c >> 16) & 0xff) / 255F;
        final float f2 = ((c >> 8) & 0xff) / 255F;
        final float f3 = (c & 0xff) / 255F;

        GL11.glColor4f(f1, f2, f3, f);
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);

        for (int i = 0; i <= 360 / quality; i++) {
            final double x2 = Math.sin(((i * quality * Math.PI) / 180)) * r;
            final double y2 = Math.cos(((i * quality * Math.PI) / 180)) * r;
            GL11.glVertex2d(x + x2, y + y2);
        }

        GL11.glEnd();
    }
    public static int rgba(int r, int g, int b, int a) {
        return a << 24 | r << 16 | g << 8 | b;
    }
    public static int rgba(double r, double g, double b, double a) {
        return rgba((int) r, (int) g, (int) b, (int) a);
    }
    public static void drawRound(float x, float y, float width, float height, float radius, boolean blur, Color color) {
        GlStateManager.resetColor();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        ROUNDED.useProgram();

        setupRoundedRectUniforms(x, y, width, height, radius, ROUNDED);
        ROUNDED.setUniformi("blur", blur ? 1 : 0);
        ROUNDED.setUniformf("color", color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f,
                color.getAlpha() / 255f);

        ShaderUtility.drawQuads(x - 1, y - 1, width + 2, height + 2);
        ROUNDED.unloadProgram();
        GlStateManager.disableBlend();
    }
    public static void drawCircle(final float x, final float y, float start, float end, final float radius, final int color, final int linewidth) {
        GlStateManager.color(0.0f, 0.0f, 0.0f, 0.0f);
        if (start > end) {
            final float endOffset = end;
            end = start;
            start = endOffset;
        }
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        RenderUtility.enableSmoothLine((float)linewidth);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glBegin(3);
        for (float i = end; i >= start; i -= 4.0f) {
            RenderUtility.glHexColor(color, 255);
            final float cos = (float)(Math.cos(i * 3.141592653589793 / 180.0) * radius * 1.0);
            final float sin = (float)(Math.sin(i * 3.141592653589793 / 180.0) * radius * 1.0);
            GL11.glVertex2f(x + cos, y + sin);
        }
        GL11.glEnd();
        RenderUtility.disableSmoothLine();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    public static void drawCircleIndicator(final float x, final float y, float start, float end, final float radius, final int color1, final int color2, final int linewidth) {
        GlStateManager.color(0.0f, 0.0f, 0.0f, 0.0f);
        if (start > end) {
            final float endOffset = end;
            end = start;
            start = endOffset;
        }
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        RenderUtility.enableSmoothLine((float)linewidth);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glBegin(3);
        for (float i = end; i >= start; i -= 4.0f) {
            if (i % 2 == 0) {
                RenderUtility.glHexColor(color1, 255);
            } else {
                RenderUtility.glHexColor(color2, 255);
            }
            final float cos = (float)(Math.cos(i * 3.141592653589793 / 180.0) * radius * 1.0);
            final float sin = (float)(Math.sin(i * 3.141592653589793 / 180.0) * radius * 1.0);
            GL11.glVertex2f(x + cos, y + sin);
        }
        GL11.glEnd();
        RenderUtility.disableSmoothLine();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
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
    public static void glHexColor(int hex, int alpha) {
        float red = (float)(hex >> 16 & 255) / 255.0F;
        float green = (float)(hex >> 8 & 255) / 255.0F;
        float blue = (float)(hex & 255) / 255.0F;
        GlStateManager.color(red, green, blue, (float)alpha / 255.0F);
    }
    public static void color(int color, float alpha) {
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        GlStateManager.color(r, g, b, alpha);
    }
    public static void drawRect(float x, float y, float x2, float y2, int color) {
        drawGradientRect(x, y, x2, y2, color, color, color, color);
    }

    public static void drawGradientHorizontal1(float x, float y, float width, float height, float radius, Color left, Color right) {
        drawGradientRound1(x, y, width, height, radius, left, left, right, right);
    }

    public static void drawGradientHorizontal1(float x, float y, float width, float height, float radius, int left, int right) {
        drawGradientRound1(x, y, width, height, radius, left, left, right, right);
    }
    public static void drawGradientHorizontal(float x, float y, float width, float height, float radius, Color left,
                                              Color right) {
        drawGradientRound(x, y, width, height, radius, left, left, right, right);
    }
    private static void setupRoundedRectUniforms(float x, float y, float width, float height, float radius,
                                                 Shader roundedTexturedShader) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        roundedTexturedShader.setUniformf("location", x * sr.getScaleFactor(),
                (Minecraft.getMinecraft().displayHeight - (height * sr.getScaleFactor())) - (y * sr.getScaleFactor()));
        roundedTexturedShader.setUniformf("rectSize", width * sr.getScaleFactor(), height * sr.getScaleFactor());
        roundedTexturedShader.setUniformf("radius", radius * sr.getScaleFactor());
    }
    public static void drawGradientRound1(float x, float y, float width, float height, float radius, int bottomLeft, int topLeft, int bottomRight, int topRight) {
        GlStateManager.resetColor();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        ROUNDED_GRADIENT.useProgram();
        setupRoundedRectUniforms(x, y, width, height, radius, ROUNDED_GRADIENT);
        // Bottom Left
        ROUNDED_GRADIENT.setUniformf("color1", ColorUtility.getRed(bottomLeft) / 255f, ColorUtility.getGreen(bottomLeft) / 255f, ColorUtility.getBlue(bottomLeft) / 255f, ColorUtility.getAlpha(bottomLeft) / 255f);
        //Top left
        ROUNDED_GRADIENT.setUniformf("color2", ColorUtility.getRed(topLeft) / 255f, ColorUtility.getGreen(topLeft) / 255f, ColorUtility.getBlue(topLeft) / 255f, ColorUtility.getAlpha(topLeft) / 255f);
        //Bottom Right
        ROUNDED_GRADIENT.setUniformf("color3", ColorUtility.getRed(bottomRight) / 255f, ColorUtility.getGreen(bottomRight) / 255f, ColorUtility.getBlue(bottomRight) / 255f, ColorUtility.getAlpha(bottomRight) / 255f);
        //Top Right
        ROUNDED_GRADIENT.setUniformf("color4", ColorUtility.getRed(topRight) / 255f, ColorUtility.getGreen(topRight) / 255f, ColorUtility.getBlue(topRight) / 255f, ColorUtility.getAlpha(topRight) / 255f);
        ShaderUtility.drawQuads(x - 1, y - 1, width + 2, height + 2);
        ROUNDED_GRADIENT.unloadProgram();
        GlStateManager.disableBlend();
    }
    public static void drawGradientRound1(float x, float y, float width, float height, float radius, Color bottomLeft, Color topLeft, Color bottomRight, Color topRight) {
        GlStateManager.resetColor();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        ROUNDED_GRADIENT.useProgram();
        setupRoundedRectUniforms(x, y, width, height, radius, ROUNDED_GRADIENT);
        // Bottom Left
        ROUNDED_GRADIENT.setUniformf("color1", bottomLeft.getRed() / 255f, bottomLeft.getGreen() / 255f, bottomLeft.getBlue() / 255f, bottomLeft.getAlpha() / 255f);
        //Top left
        ROUNDED_GRADIENT.setUniformf("color2", topLeft.getRed() / 255f, topLeft.getGreen() / 255f, topLeft.getBlue() / 255f, topLeft.getAlpha() / 255f);
        //Bottom Right
        ROUNDED_GRADIENT.setUniformf("color3", bottomRight.getRed() / 255f, bottomRight.getGreen() / 255f, bottomRight.getBlue() / 255f, bottomRight.getAlpha() / 255f);
        //Top Right
        ROUNDED_GRADIENT.setUniformf("color4", topRight.getRed() / 255f, topRight.getGreen() / 255f, topRight.getBlue() / 255f, topRight.getAlpha() / 255f);
        ShaderUtility.drawQuads(x - 1, y - 1, width + 2, height + 2);
        ROUNDED_GRADIENT.unloadProgram();
        GlStateManager.disableBlend();
    }
    public static void drawGradientRound(float x, float y, float width, float height, float radius, Color bottomLeft,
                                         Color topLeft, Color bottomRight, Color topRight) {
        GlStateManager.resetColor();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        ROUNDED_GRADIENT.useProgram();
        setupRoundedRectUniforms(x, y, width, height, radius, ROUNDED_GRADIENT);
        // Bottom Left
        ROUNDED_GRADIENT.setUniformf("color1", bottomLeft.getRed() / 255f, bottomLeft.getGreen() / 255f,
                bottomLeft.getBlue() / 255f, bottomLeft.getAlpha() / 255f);
        //Top left
        ROUNDED_GRADIENT.setUniformf("color2", topLeft.getRed() / 255f, topLeft.getGreen() / 255f,
                topLeft.getBlue() / 255f, topLeft.getAlpha() / 255f);
        //Bottom Right
        ROUNDED_GRADIENT.setUniformf("color3", bottomRight.getRed() / 255f, bottomRight.getGreen() / 255f,
                bottomRight.getBlue() / 255f, bottomRight.getAlpha() / 255f);
        //Top Right
        ROUNDED_GRADIENT.setUniformf("color4", topRight.getRed() / 255f, topRight.getGreen() / 255f,
                topRight.getBlue() / 255f, topRight.getAlpha() / 255f);
        ShaderUtility.drawQuads(x - 1, y - 1, width + 2, height + 2);
        ROUNDED_GRADIENT.unloadProgram();
        GlStateManager.disableBlend();
    }
    public static void drawGradientCornerLR(float x, float y, float width, float height, float radius, Color topLeft, Color bottomRight) {
        Color mixedColor = ColorUtility.interpolateColorC(topLeft, bottomRight, .5f);
        drawGradientRound(x, y, width, height, radius, mixedColor, topLeft, bottomRight, mixedColor);
    }
    public static void scissorRect(float x, float y, float width, double height) {
        ScaledResolution sr = new ScaledResolution(mc);
        int factor = sr.getScaleFactor();
        GL11.glScissor((int) (x * (float) factor), (int) (((float) sr.getScaledHeight() - height) * (float) factor), (int) ((width - x) * (float) factor), (int) ((height - y) * (float) factor));
    }
    public static void drawTriangle(int x, int y, int height, int width, float scale, int color) {
        boolean needBlend = !GL11.glIsEnabled(GL11.GL_BLEND);
        if (needBlend)
            GL11.glEnable(GL11.GL_BLEND);
        int alpha = (color >> 24) & 0xFF;
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;
        int red_2 = Math.max(red - 40, 0);
        int green_2 = Math.max(green - 40, 0);
        int blue_2 = Math.max(blue - 40, 0);
        float scaledWidth = width * scale;
        float scaledHeight = height * scale;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glBegin(GL11.GL_POLYGON);
        GL11.glColor4f(red / 255f, green / 255f, blue / 255f, alpha / 255f);
        GL11.glVertex2d(x, y - scaledHeight);
        GL11.glVertex2d(x - scaledWidth, y);
        GL11.glVertex2d(x, y - 3);
        GL11.glEnd();
        GL11.glBegin(GL11.GL_POLYGON);
        GL11.glColor4f(red_2 / 255f, green_2 / 255f, blue_2 / 255f, alpha / 255f);
        GL11.glVertex2d(x, y - scaledHeight);
        GL11.glVertex2d(x, y - 3);
        GL11.glVertex2d(x + scaledWidth, y);
        GL11.glEnd();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        if (needBlend)
            GL11.glDisable(GL11.GL_BLEND);
    }
    public static void drawTriangle(int red, int green, int blue) {
        boolean needBlend = !GL11.glIsEnabled(GL11.GL_BLEND);
        if (needBlend)
            GL11.glEnable(GL11.GL_BLEND);
        int alpha = 255;
        int red_2 = Math.max(red - 40, 0);
        int green_2 = Math.max(green - 40, 0);
        int blue_2 = Math.max(blue - 40, 0);
        float width = 6, height = 12;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glBegin(GL11.GL_POLYGON);
        GL11.glColor4f(red / 255f, green / 255f, blue / 255f, alpha / 255f);
        GL11.glVertex2d(0, 0 - height);
        GL11.glVertex2d(0 - width, 0);
        GL11.glVertex2d(0, 0 - 3);
        GL11.glEnd();
        GL11.glBegin(GL11.GL_POLYGON);
        GL11.glColor4f(red_2 / 255f, green_2 / 255f, blue_2 / 255f, alpha / 255f);
        GL11.glVertex2d(0, 0 - height);
        GL11.glVertex2d(0, 0 - 3);
        GL11.glVertex2d(0 + width, 0);
        GL11.glEnd();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        if (needBlend)
            GL11.glDisable(GL11.GL_BLEND);
    }

    public static void drawRect2(double x, double y, double width, double height, int color) {
        drawRect(x, y, x + width, y + height, color);
    }
    public static void setAlphaLimit(float limit) {
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(GL_GREATER, (float) (limit * .01));
    }
    public static void drawCircle(float xx, float yy, float radius, Color col, float width, float position, float round) {
        int sections = 100;
        double dAngle = (double)(round * 2.0f) * Math.PI / (double)sections;
        float x = 0.0f;
        float y = 0.0f;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glLineWidth(width);
        GL11.glShadeModel(7425);
        GL11.glBegin(2);
        int i = (int)position;
        while ((float)i < position + (float)sections) {
            x = (float)((double)radius * Math.cos((double)i * dAngle));
            y = (float)((double)radius * Math.sin((double)i * dAngle));
            GL11.glColor4f((float)col.getRed() / 255.0f, (float)col.getGreen() / 255.0f, (float)col.getBlue() / 255.0f, (float)col.getAlpha() / 255.0f);
            GL11.glVertex2f(xx + x, yy + y);
            ++i;
        }
        for (i = (int)(position + (float)sections); i > (int)position; --i) {
            x = (float)((double)radius * Math.cos((double)i * dAngle));
            y = (float)((double)radius * Math.sin((double)i * dAngle));
            GL11.glColor4f((float)col.getRed() / 255.0f, (float)col.getGreen() / 255.0f, (float)col.getBlue() / 255.0f, (float)col.getAlpha() / 255.0f);
            GL11.glVertex2f(xx + x, yy + y);
        }
        GlStateManager.color(0.0f, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
    }
    public static void drawLoadingCircle(Color color, float sizeAmount) {
        float status = (float)((double)System.currentTimeMillis() * 0.1 % 400.0);
        float size = sizeAmount;
        ScaledResolution res = new ScaledResolution(mc);
        float radius = (float)res.getScaledWidth() / 20.0f;
        drawCircle((float)res.getScaledWidth() / 2.0f, (float)res.getScaledHeight() / 2.0f - 65.0f, radius, new Color(33, 33, 33, 255), 5.0f, 0.0f, 1.0f);
        drawCircle((float)res.getScaledWidth() / 2.0f, (float)res.getScaledHeight() / 2.0f - 65.0f, radius, new Color(color.getRGB()), 7.0f, status, size);
    }

    public static void drawRect(double left, double top, double right, double bottom, int color) {
        resetColor();
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

    public static void drawBlurredShadow(float x, float y, float width, float height, int blurRadius, Color color) {
        GL11.glPushMatrix();
        GlStateManager.alphaFunc(516, 0.01f);
        float _X = (x -= (float)blurRadius) - 0.25f;
        float _Y = (y -= (float)blurRadius) + 0.25f;
        int identifier = (int)((width += (float)(blurRadius * 2)) * (height += (float)(blurRadius * 2)) + width + (float)(color.hashCode() * blurRadius) + (float)blurRadius);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2884);
        GL11.glEnable((int)3008);
        GlStateManager.enableBlend();
        int texId = -1;
        if (SHADOW_CACHE.containsKey(identifier)) {
            texId = SHADOW_CACHE.get(identifier);
            GlStateManager.bindTexture(texId);
        } else {
            if (width <= 0.0f) {
                width = 1.0f;
            }
            if (height <= 0.0f) {
                height = 1.0f;
            }
            BufferedImage original = new BufferedImage((int)width, (int)height, 3);
            Graphics g = original.getGraphics();
            g.setColor(color);
            g.fillRect(blurRadius, blurRadius, (int)(width - (float)(blurRadius * 2)), (int)(height - (float)(blurRadius * 2)));
            g.dispose();
            GaussianFilter op = new GaussianFilter(blurRadius);
            BufferedImage blurred = op.filter(original, null);
            texId = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), blurred, true, false);
            SHADOW_CACHE.put(identifier, texId);
        }
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glBegin((int)7);
        GL11.glTexCoord2f((float)0.0f, (float)0.0f);
        GL11.glVertex2f((float)_X, (float)_Y);
        GL11.glTexCoord2f((float)0.0f, (float)1.0f);
        GL11.glVertex2f((float)_X, (float)(_Y + height));
        GL11.glTexCoord2f((float)1.0f, (float)1.0f);
        GL11.glVertex2f((float)(_X + width), (float)(_Y + height));
        GL11.glTexCoord2f((float)1.0f, (float)0.0f);
        GL11.glVertex2f((float)(_X + width), (float)_Y);
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        resetColor();
        GL11.glEnable((int)2884);
        GL11.glPopMatrix();
    }
//    public static void applyRound(float radius, float glow, Runnable runnable) {
//        ROUNDED_TEXTURE.useProgram();
//        ROUNDED_TEXTURE.setupUniform1f("radius", radius);
//        ROUNDED_TEXTURE.setupUniform1f("glow", glow);
//        runnable.run();
//        ROUNDED_TEXTURE.unloadProgram();
//    }
public static void resetColor() {
    GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
}
    public static void applyRound(float width, float height, float round, float alpha, Runnable runnable) {
        GlStateManager.color(0, 0, 0, 0);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.disableAlpha();
        ROUNDED_TEXTURE.useProgram();
        ROUNDED_TEXTURE.setupUniform2f("size", (width - round) * 2, (height - round) * 2);
        ROUNDED_TEXTURE.setupUniform1f("round", round);
        ROUNDED_TEXTURE.setupUniform1f("alpha", alpha);
        runnable.run();
        ROUNDED_TEXTURE.unloadProgram();
        GlStateManager.disableBlend();
    }

    public static void drawProfile(float x, float y, float x2, float y2) {
        if (PROFILE_ID != -1337) {
            GlStateManager.bindTexture(PROFILE_ID);
            GlStateManager.color(1, 1, 1, 1);
            allocTextureRectangle(x, y, x2, y2);
            GlStateManager.bindTexture(0);
        } else {
            RenderUtility.drawRoundedRect(x, y, x2, y2, x2 - 1, new Color(50, 50, 50).getRGB());
            if (DiscordPresence.avatar != null) {
                PROFILE_ID = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), DiscordPresence.avatar, true, false);
            }
        }
    }
    public static void drawFace(EntityLivingBase entity, int x, int y, int width, int height) {
        drawFace(entity.getName(), x, y, width, height);
    }
    public static void bindFace(String username) throws IOException {
        AbstractClientPlayer.getDownloadImageSkin(AbstractClientPlayer.getLocationSkin(username), username)
                .loadTexture(Minecraft.getMinecraft().getResourceManager());
        Minecraft.getMinecraft().getTextureManager().bindTexture(AbstractClientPlayer.getLocationSkin(username));
    }
    public static void drawRectWH(double x, double y, double width, double height, int color) {
        drawRect(x, y, x + width, y + height, color);
    }
    public static void drawRect3(double left, double top, double right, double bottom, int color) {
        resetColor();
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
    public static void drawStack(ItemStack stack, boolean overlay, int x, int y) {
        GlStateManager.enableDepth();
        mc.getRenderItem().zLevel = 200F;
        mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
        if (overlay)
            mc.getRenderItem().renderItemOverlays(mc.fontRenderer, stack, x, y);
        mc.getRenderItem().zLevel = 0F;
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
    }

    public static float scrollAnimate(float endPoint, float current, float speed) {
        boolean shouldContinueAnimation = endPoint > current;
        if (speed < 0.0f) {
            speed = 0.0f;
        } else if (speed > 1.0f) {
            speed = 1.0f;
        }

        float dif = Math.max(endPoint, current) - Math.min(endPoint, current);
        float factor = dif * speed;
        return current + (shouldContinueAnimation ? factor : -factor);
    }


    public static void horizontalGradient(double x1, double y1, double x2, double y2, int startColor, int endColor) {
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
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double) x1, (double) y1, 0).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double) x1, (double) y2, 0).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double) x2, (double) y2, 0).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos((double) x2, (double) y1, 0).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    public static void drawFace(String username, int x, int y, int width, int height) {
        try {
            bindFace(username);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            Gui.drawScaledCustomSizeModalRect(x, y, 8.0F, 8.0F, 8, 8, width, height, 64.0F, 64.0F);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void drawRoundedRect(float x, float y, float x2, float y2, float round, int color) {
        drawRoundedGradientRect(x, y, x2, y2, round, 1, color, color, color, color);
    }
    public static void drawGradientRect(float f, float sY, double width, double height, int colour1, int colour2) {
        Gui.drawGradientRect(f, sY, (double)f + width, (double)sY + height, colour1, colour2);
    }
    public static void drawCRoundedRect(float x, float y, float x2, float y2, float round1, float round2, float round3, float round4, int color) {
        drawRoundedGradientRect(x, y, x2, y2, round1, round2, round3, round4, 1, color, color, color, color);
    }

    public static void drawRoundCircle(float x, float y, float size, float radius, int color) {
        drawRoundedRect(x - size / 2, y - size / 2, size, size, radius, color);
    }
    public static void applyGradientHorizontal(float x, float y, float width, float height, float alpha, Color left, Color right, Runnable content) {
        applyGradientMask(x, y, width, height, alpha, left, left, right, right, content);
    }
    public static void applyGradientMask(float x, float y, float width, float height, float alpha, Color bottomLeft, Color topLeft, Color bottomRight, Color topRight, Runnable content) {
        GlStateManager.color(0, 0, 0, 0);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GRADIENT_MASK.useProgram();

        GRADIENT_MASK.setupUniform2f("location", x * 2,
                (Minecraft.getMinecraft().displayHeight - (height * 2)) - (y * 2));
        GRADIENT_MASK.setupUniform2f("rectSize", width * 2, height * 2);
        GRADIENT_MASK.setupUniform1f("alpha", alpha);
        GRADIENT_MASK.setupUniform3f("color1", bottomLeft.getRed() / 255f, bottomLeft.getGreen() / 255f, bottomLeft.getBlue() / 255f);
        GRADIENT_MASK.setupUniform3f("color2", topLeft.getRed() / 255f, topLeft.getGreen() / 255f, topLeft.getBlue() / 255f);
        GRADIENT_MASK.setupUniform3f("color3", bottomRight.getRed() / 255f, bottomRight.getGreen() / 255f, bottomRight.getBlue() / 255f);
        GRADIENT_MASK.setupUniform3f("color4", topRight.getRed() / 255f, topRight.getGreen() / 255f, topRight.getBlue() / 255f);
        content.run();
        GRADIENT_MASK.unloadProgram();
        GlStateManager.disableBlend();
    }
    public static void applyGradientMaskLeft(float x, float y, float width, float height, float alpha, Color bottomLeft, Color topLeft, Color bottomRight, Color topRight, Runnable content) {
        GlStateManager.color(0, 0, 0, 0);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GRADIENT_MASK.useProgram();

        GRADIENT_MASK.setupUniform2f("location", x * 2,
                (Minecraft.getMinecraft().displayHeight - (height * 2)) - (y * 2));
        GRADIENT_MASK.setupUniform2f("rectSize", width * 2, height * 2);
        GRADIENT_MASK.setupUniform1f("alpha", alpha);
        GRADIENT_MASK.setupUniform3f("color1", bottomLeft.getRed() / 255f, bottomLeft.getGreen() / 255f, bottomLeft.getBlue() / 255f);
        GRADIENT_MASK.setupUniform3f("color2", topLeft.getRed() / 255f, topLeft.getGreen() / 255f, topLeft.getBlue() / 255f);
        GRADIENT_MASK.setupUniform3f("color3", bottomRight.getRed() / 255f, bottomRight.getGreen() / 255f, bottomRight.getBlue() / 255f);
        GRADIENT_MASK.setupUniform3f("color4", topRight.getRed() / 255f, topRight.getGreen() / 255f, topRight.getBlue() / 255f);
        content.run();
        GRADIENT_MASK.unloadProgram();
        GlStateManager.disableBlend();
    }
    public static void drawRectWithOutline(float x, float y, float width, float height, int color, int outlineColor) {
        drawRect(x - 0.5f, y - 0.5f, width + 1, height + 1, outlineColor);
        drawRect(x, y, width, height, color);
    }

    public static void drawRectNoWH(double left, double top, double right, double bottom, int color) {
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
        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f, f1, f2, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(left, bottom, 0.0D).endVertex();
        bufferbuilder.pos(right, bottom, 0.0D).endVertex();
        bufferbuilder.pos(right, top, 0.0D).endVertex();
        bufferbuilder.pos(left, top, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawLine(double x, double y, double x1, double y1, float width, int color) {
        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(width);
        setColor(color);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GlStateManager.resetColor();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);

        GL11.glPopMatrix();
    }

    public static void drawLeftSideRoundedGradientRect(float x, float y, float x2, float y2, float round, float value, int color1, int color2, int color3, int color4) {
        float[] c1 = ColorUtility.getRGBAf(color1);
        float[] c2 = ColorUtility.getRGBAf(color2);
        float[] c3 = ColorUtility.getRGBAf(color3);
        float[] c4 = ColorUtility.getRGBAf(color4);

        GlStateManager.color(0, 0, 0, 0);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        ROUNDED_GRADIENT.useProgram();
        ROUNDED_GRADIENT.setupUniform2f("size", x2 * 2, y2 * 2);
        ROUNDED_GRADIENT.setupUniform4f("round", 0, 0, round, round);
        ROUNDED_GRADIENT.setupUniform2f("smoothness", 0.f, 1.5f);
        ROUNDED_GRADIENT.setupUniform1f("value", value);
        ROUNDED_GRADIENT.setupUniform4f("color1", c1[0], c1[1], c1[2], c1[3]);
        ROUNDED_GRADIENT.setupUniform4f("color2", c2[0], c2[1], c2[2], c2[3]);
        ROUNDED_GRADIENT.setupUniform4f("color3", c3[0], c3[1], c3[2], c3[3]);
        ROUNDED_GRADIENT.setupUniform4f("color4", c4[0], c4[1], c4[2], c4[3]);

        allocTextureRectangle(x, y, x2, y2);
        ROUNDED_GRADIENT.unloadProgram();
        GlStateManager.disableBlend();
    }

    public static void drawBlockBox(BlockPos blockPos, Color color, int alpha) {
        double x = mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * mc.timer.renderPartialTicks;
        double y = mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * mc.timer.renderPartialTicks;
        double z = mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * mc.timer.renderPartialTicks;
        mc.entityRenderer.setupCameraTransform(mc.getRenderPartialTicks(), 2);
        GL11.glPushMatrix();
        AntiAliasingUtility.hook(true, false, false);

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(1);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GlStateManager.color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, alpha / 255F);
        drawSelectionBoundingBox(mc.world.getBlockState(blockPos).getSelectedBoundingBox(mc.world, blockPos)
                .grow(0.0020000000949949026D).offset(-x, -y, -z));
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        AntiAliasingUtility.unhook(true, false, false);
        GL11.glPopMatrix();
    }

    public static void drawSelectionBoundingBox(AxisAlignedBB boundingBox) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        tessellator.draw();
        vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        tessellator.draw();
        vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        tessellator.draw();
    }

    public static void drawLeftSideRoundedRect(float x, float y, float x2, float y2, float round, int color) {
        drawLeftSideRoundedGradientRect(x, y, x2, y2, round, 1, color, color, color, color);
    }

    public static void drawHeader(float x, float y, float width, float height) {
        RenderUtility.drawGradientRect(x + width / 2, y, width / 2, height,  new Color(0xB6B6B6).getRGB(), Color.TRANSLUCENT, Color.TRANSLUCENT, new Color(0xB6B6B6).getRGB());
        RenderUtility.drawGradientRect(x, y, width / 2, height, Color.TRANSLUCENT, new Color(0xB6B6B6).getRGB(), new Color(0xB6B6B6).getRGB(),  Color.TRANSLUCENT);
    }

    public static void drawRoundedRect(float x, float y, float x2, float y2, float round, float swapX, float swapY, int firstColor, int secondColor) {
        float[] c = ColorUtility.getRGBAf(firstColor);
        float[] c1 = ColorUtility.getRGBAf(secondColor);

        GlStateManager.color(0, 0, 0, 0);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        ROUNDED.useProgram();
        ROUNDED.setupUniform2f("size", (x2 - round) * 2, (y2 - round) * 2);
        ROUNDED.setupUniform1f("round", round);
        ROUNDED.setupUniform2f("smoothness", 0.f, 1.5f);
        ROUNDED.setupUniform2f("swap", swapX, swapY);
        ROUNDED.setupUniform4f("firstColor", c[0], c[1], c[2], c[3]);
        ROUNDED.setupUniform4f("secondColor", c1[0], c1[1], c1[2], c[3]);

        allocTextureRectangle(x, y, x2, y2);
        ROUNDED.unloadProgram();

        GlStateManager.disableBlend();
    }

    public static void drawVGradientRect(final float left, final float top, final float right, final float bottom, final int startColor, final int endColor) {
        float f = (float) (startColor >> 24 & 0xFF) / 255.0f;
        float f1 = (float) (startColor >> 16 & 0xFF) / 255.0f;
        float f2 = (float) (startColor >> 8 & 0xFF) / 255.0f;
        float f3 = (float) (startColor & 0xFF) / 255.0f;
        float f4 = (float) (endColor >> 24 & 0xFF) / 255.0f;
        float f5 = (float) (endColor >> 16 & 0xFF) / 255.0f;
        float f6 = (float) (endColor >> 8 & 0xFF) / 255.0f;
        float f7 = (float) (endColor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f1, f2, f3, f);
        glVertex2d(left, top);
        glVertex2d(right, top);
        GL11.glColor4f(f5, f6, f7, f4);
        glVertex2d(right, bottom);
        glVertex2d(left, bottom);
        GL11.glEnd();
        GL11.glPopMatrix();
        GlStateManager.resetColor();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawGradientRect(float x, float y, float width, float height, int color1, int color2, int color3, int color4) {
        float[] c1 = ColorUtility.getRGBAf(color1);
        float[] c2 = ColorUtility.getRGBAf(color2);
        float[] c3 = ColorUtility.getRGBAf(color3);
        float[] c4 = ColorUtility.getRGBAf(color4);

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        BUILDER.begin(7, DefaultVertexFormats.POSITION_COLOR);
        BUILDER.pos(x, height + y, 0.0D).color(c1[0], c1[1], c1[2], c1[3]).endVertex();
        BUILDER.pos(width + x, height + y, 0.0D).color(c2[0], c2[1], c2[2], c2[3]).endVertex();
        BUILDER.pos(width + x, y, 0.0D).color(c3[0], c3[1], c3[2], c3[3]).endVertex();
        BUILDER.pos(x, y, 0.0D).color(c4[0], c4[1], c4[2], c4[3]).endVertex();
        TESSELLATOR.draw();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRoundedGradientRect(float x, float y, float x2, float y2, float round, float value, int color1, int color2, int color3, int color4) {
        float[] c1 = ColorUtility.getRGBAf(color1);
        float[] c2 = ColorUtility.getRGBAf(color2);
        float[] c3 = ColorUtility.getRGBAf(color3);
        float[] c4 = ColorUtility.getRGBAf(color4);

        GlStateManager.color(0, 0, 0, 0);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        ROUNDED_GRADIENT.useProgram();
        ROUNDED_GRADIENT.setupUniform2f("size", x2 * 2, y2 * 2);
        ROUNDED_GRADIENT.setupUniform4f("round", round, round, round, round);
        ROUNDED_GRADIENT.setupUniform2f("smoothness", 0.f, 1.5f);
        ROUNDED_GRADIENT.setupUniform1f("value", value);
        ROUNDED_GRADIENT.setupUniform4f("color1", c1[0], c1[1], c1[2], c1[3]);
        ROUNDED_GRADIENT.setupUniform4f("color2", c2[0], c2[1], c2[2], c2[3]);
        ROUNDED_GRADIENT.setupUniform4f("color3", c3[0], c3[1], c3[2], c3[3]);
        ROUNDED_GRADIENT.setupUniform4f("color4", c4[0], c4[1], c4[2], c4[3]);

        allocTextureRectangle(x, y, x2, y2);
        ROUNDED_GRADIENT.unloadProgram();
        GlStateManager.disableBlend();
    }

    public static void drawItemStack(ItemStack itemStack, int x, int y) {
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.enableDepth();

        RenderHelper.enableGUIStandardItemLighting();

        mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, x, y);
        mc.getRenderItem().renderItemOverlays(mc.fontRenderer, itemStack, x, y);

        RenderHelper.disableStandardItemLighting();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableDepth();
    }
    public static void drawItemStackHud(ItemStack itemStack, int x, int y) {
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.enableDepth();

        RenderHelper.enableGUIStandardItemLighting();

        mc.getRenderItem().renderItemOverlays(mc.fontRenderer, itemStack, x, y);
        mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, x, y);

        RenderHelper.disableStandardItemLighting();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableDepth();
    }
    public static void drawItemStack2(ItemStack itemStack, int x, int y) {
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.enableDepth();

        RenderHelper.enableGUIStandardItemLighting();

        mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, x + 2, y);
        mc.getRenderItem().renderItemOverlays(mc.fontRenderer, itemStack, x, y);

        RenderHelper.disableStandardItemLighting();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.disableDepth();
    }
    public static void drawRoundedGradientRect(float x, float y, float x2, float y2, float round1, float round2, float round3, float round4, float value, int color1, int color2, int color3, int color4) {
        float[] c1 = ColorUtility.getRGBAf(color1);
        float[] c2 = ColorUtility.getRGBAf(color2);
        float[] c3 = ColorUtility.getRGBAf(color3);
        float[] c4 = ColorUtility.getRGBAf(color4);

        GlStateManager.color(0, 0, 0, 0);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        ROUNDED_GRADIENT.useProgram();
        ROUNDED_GRADIENT.setupUniform2f("size", x2 * 2, y2 * 2);
        ROUNDED_GRADIENT.setupUniform4f("round", round1, round2, round3, round4);
        ROUNDED_GRADIENT.setupUniform2f("smoothness", 0.f, 1.5f);
        ROUNDED_GRADIENT.setupUniform1f("value", value);
        ROUNDED_GRADIENT.setupUniform4f("color1", c1[0], c1[1], c1[2], c1[3]);
        ROUNDED_GRADIENT.setupUniform4f("color2", c2[0], c2[1], c2[2], c2[3]);
        ROUNDED_GRADIENT.setupUniform4f("color3", c3[0], c3[1], c3[2], c3[3]);
        ROUNDED_GRADIENT.setupUniform4f("color4", c4[0], c4[1], c4[2], c4[3]);

        allocTextureRectangle(x, y, x2, y2);
        ROUNDED_GRADIENT.unloadProgram();
        GlStateManager.disableBlend();
    }


    public static void drawGlow(float x, float y, float width, float height, int glowRadius, Color color) {
        BufferedImage original;
        GaussianFilter op;
        glPushMatrix();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.01f);
        width = width + glowRadius * 2;
        height = height + glowRadius * 2;
        x = x - glowRadius;
        y = y - glowRadius;
        float _X = x - 0.25f;
        float _Y = y + 0.25f;
        int identifier = String.valueOf(width * height + width + 1000000000 * glowRadius + glowRadius).hashCode();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        glDisable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GlStateManager.enableBlend();
        int texId = -1;


        if (SHADOW_CACHE.containsKey(identifier)) {
            texId = SHADOW_CACHE.get(identifier);
            GlStateManager.bindTexture(texId);
        } else {
            if (width <= 0) {
                width = 1;
            }

            if (height <= 0) {
                height = 1;
            }
            original = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB_PRE);

            Graphics g = original.getGraphics();
            g.setColor(Color.white);
            g.fillRect(glowRadius, glowRadius, (int) (width - glowRadius * 2), (int) (height - glowRadius * 2));
            g.dispose();

            op = new GaussianFilter(glowRadius);

            BufferedImage blurred = op.filter(original, null);
            texId = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), blurred, true, false);
            SHADOW_CACHE.put(identifier, texId);
        }

        setColor(color);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(_X, _Y);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(_X, _Y + height);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(_X + width, _Y + height);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(_X + width, _Y);
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.resetColor();
        glEnable(GL_CULL_FACE);
        glPopMatrix();
    }

    public static void drawGradientGlow(float x, float y, float width, float height, int glowRadius, Color color1, Color color2) {
        BufferedImage original = null;
        GaussianFilter op = null;
        glPushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.01f);
        width = width + glowRadius * 2;
        height = height + glowRadius * 2;
        x = x - glowRadius;
        y = y - glowRadius;
        float _X = x - 0.25f;
        float _Y = y + 0.25f;
        int identifier = (int) (width * height * glowRadius);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        glDisable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GlStateManager.enableBlend();
        int texId = -1;


        if (SHADOW_CACHE.containsKey(identifier)) {
            texId = SHADOW_CACHE.get(identifier);
            GlStateManager.bindTexture(texId);
        } else {
            if (width <= 0) {
                width = 1;
            }

            if (height <= 0) {
                height = 1;
            }
            original = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB_PRE);

            Graphics g = original.getGraphics();
            g.setColor(Color.white);
            g.fillRect(glowRadius, glowRadius, (int) width - glowRadius * 2, (int) height - glowRadius * 2);
            g.dispose();

            op = new GaussianFilter(glowRadius);

            BufferedImage blurred = op.filter(original, null);
            texId = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), blurred, true, false);
            SHADOW_CACHE.put(identifier, texId);
        }

        GlStateManager.resetColor();

        GL11.glBegin(GL11.GL_QUADS);
        setColor(color1);
        GL11.glTexCoord2f(0, 0); // top left
        GL11.glVertex2f(_X, _Y);
        GL11.glTexCoord2f(0, 1); // bottom left
        GL11.glVertex2f(_X, _Y + height);
        setColor(color2);
        GL11.glTexCoord2f(1, 1); // bottom right
        GL11.glVertex2f(_X + width, _Y + height);
        GL11.glTexCoord2f(1, 0); // top right
        GL11.glVertex2f(_X + width, _Y);

        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.resetColor();
        glEnable(GL_CULL_FACE);
        glPopMatrix();
    }//k

    public static void drawDarkMoonShader(float x, float y, float width, float height, float round) {
        Color gradientColor1 = ColorUtility2.interpolateColorsBackAndForth(Hud.hueInterpolate.get() ? 15 : 4, 0, DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0], DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1], Hud.hueInterpolate.get());
        Color gradientColor2 = ColorUtility2.interpolateColorsBackAndForth(Hud.hueInterpolate.get() ? 15 : 4, 90, DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0], DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1], Hud.hueInterpolate.get());
        Color gradientColor3 = ColorUtility2.interpolateColorsBackAndForth(Hud.hueInterpolate.get() ? 15 : 4, 180, DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0], DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1], Hud.hueInterpolate.get());
        Color gradientColor4 = ColorUtility2.interpolateColorsBackAndForth(Hud.hueInterpolate.get() ? 15 : 4, 270, DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0], DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1], Hud.hueInterpolate.get());
        if (DarkMoon.getInstance().getModuleManager().blurContainer.isEnabled() && DarkMoon.getInstance().getModuleManager().blurContainer.multiBooleanSetting.get(0)) {
        drawBlur(() -> {
                    drawRect(x, y - 1, width + 2f, height + 2f, new Color(23, 21, 21, 153).getRGB());
                }, DarkMoon.getInstance().getModuleManager().blurContainer.blurVisual.get());
    } else {
            drawRect(x - 1, y - 1, width + 2f, height + 2f, new Color(23, 21, 21, 153).getRGB());
        }
        drawGradientRect(x - 1, y - 1, 1.7f, height + 2f, gradientColor1.getRGB(), gradientColor2.getRGB(), gradientColor3.getRGB(), gradientColor4.getRGB());
     //   drawGradientGlow(x + 1.3f, y - 1, 2f, height + 2f, 9, gradientColor1, gradientColor2, gradientColor3, gradientColor4);
        drawGradientGlow(x - 1.5f, y - 1, 2.6f, height + 2f, 9, gradientColor1, gradientColor2, gradientColor3, gradientColor4);
    }
    public static void drawBlurredCircleShadow(double x, double y, double width, double height, int blurRadius, Color color) {
        drawBlurredCircleShadow(color, x, y, width, height, blurRadius);
    }
    public static void drawCircle(float var0, float var1, float var2, float var3, float var4, float var5, boolean var6, int var7) {
        GlStateManager.color(0.0F, 0.0F, 0.0F, 0.0F);
        if (var2 > var3) {
            float var11 = var3;
            var3 = var2;
            var2 = var11;
        }

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        ColorUtility.setColor(var7);
        GL11.glEnable(2848);
        GL11.glLineWidth(var5);
        GL11.glBegin(3);

        float var8;
        float var9;
        float var10;
        for(var10 = var3; var10 >= var2; var10 -= 4.0F) {
            var9 = (float)(Math.cos((double)var10 * Math.PI / 180.0) * (double)var4 * 1.0);
            var8 = (float)(Math.sin((double)var10 * Math.PI / 180.0) * (double)var4 * 1.0);
            GL11.glVertex2f(var0 + var9, var1 + var8);
        }

        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glEnable(2848);
        GL11.glBegin(var6 ? 6 : 3);

        for(var10 = var3; var10 >= var2; var10 -= 4.0F) {
            var9 = (float)Math.cos((double)var10 * Math.PI / 180.0) * var4;
            var8 = (float)Math.sin((double)var10 * Math.PI / 180.0) * var4;
            GL11.glVertex2f(var0 + var9, var1 + var8);
        }

        GL11.glEnd();
        GL11.glDisable(2848);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRectWithGlow(double X, double Y, double Width, double Height, double GlowRange, double GlowMultiplier, Color color) {
        for (float i = 1; i < GlowRange; i += 0.5f) {
            drawRoundedRect99(X - (GlowRange - i), Y - (GlowRange - i), Width + (GlowRange - i), Height + (GlowRange - i), injectAlpha(color, (int) (Math.round(i * GlowMultiplier))).getRGB());
        }
    }
    public static Color injectAlpha(final Color color, final int alpha) {
        int alph = MathHelper.clamp(alpha, 0, 255);
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alph);
    }

    public static void drawRoundedRect99(double x, double y, double x1, double y1, int insideC) {
        drawRect((float) (x + 0.5), (float) y, (float) x1 - 0.5f, (float) y + 0.5f, insideC);
        drawRect((float) (x + 0.5f), (float) y1 - 0.5f, (float) x1 - 0.5f, (float) y1, insideC);
        drawRect((float) x, (float) y + 0.5f, (float) x1, (float) y1 - 0.5f, insideC);
    }
    public static void drawBlurredCircleShadow(Color color, double x, double y, double width, double height, int blurRadius) {
        width = width + blurRadius * 2;
        height = height + blurRadius * 2;
        x = x - blurRadius;
        y = y - blurRadius;

        float _X = (float) (x - 0.25f);
        float _Y = (float) (y + 0.25f);

        int identifier = String.valueOf(width * height + width + 1000000000 * blurRadius + blurRadius).hashCode();
        GL11.glDisable(GL_DEPTH_TEST);

        GlStateManager.enableTexture2D();

        GlStateManager.disableCull();
        GlStateManager.enableBlend();

        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(GL_GREATER, 0.0f);

        int texId = -1;
        if (SHADOW_CACHE.containsKey(identifier)) {
            texId = SHADOW_CACHE.get(identifier);

            GlStateManager.bindTexture(texId);
        } else {
            BufferedImage original = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);

            Graphics g = original.getGraphics();
            g.setColor(Color.white);
            g.fillOval(blurRadius, blurRadius, (int) (width - blurRadius * 2), (int) (height - blurRadius * 2));
            g.dispose();

            GaussianFilter op = new GaussianFilter(blurRadius);

            BufferedImage blurred = op.filter(original, null);

            texId = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), blurred, true, false);

            SHADOW_CACHE.put(identifier, texId);
        }


        GlStateManager.color(color);

        GlStateManager.glBegin(GL11.GL_QUADS);

        GlStateManager.glTexCoord2f(0, 0);
        GlStateManager.glVertex2f(_X, _Y);

        GlStateManager.glTexCoord2f(0, 1);
        GlStateManager.glVertex2f(_X, _Y + (int) height);

        GlStateManager.glTexCoord2f(1, 1);
        GlStateManager.glVertex2f(_X + (int) width, _Y + (int) height);

        GlStateManager.glTexCoord2f(1, 0);
        GlStateManager.glVertex2f(_X + (int) width, _Y);
        GlStateManager.glEnd();


        GlStateManager.enableTexture2D();
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.color(1, 1, 1, 1);
        GL11.glEnable(GL_DEPTH_TEST);

    }
    public static void drawBlur(Runnable data, float radius) {
        StencilUtility.initStencilToWrite();
        data.run();
        StencilUtility.readStencilBuffer(1);
        GaussianBlur.renderBlur(radius);
        StencilUtility.uninitStencilBuffer();
    }
    public static void glColor(int hex) {
        float alpha = (float) (hex >> 24 & 0xFF) / 255.0f;
        float red = (float) (hex >> 16 & 0xFF) / 255.0f;
        float green = (float) (hex >> 8 & 0xFF) / 255.0f;
        float blue = (float) (hex & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
    }
    public static void drawTriangleArrow(float x, float y, float width, float height, int firstColor, int secondColor) {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        enableSmoothLine(1F);
        GL11.glRotatef(180 + 90, 0F, 0F, 1.0F);

        // fill.
        GL11.glBegin(9);
        glColor(firstColor);
        GL11.glVertex2f(x, y - 2);
        GL11.glVertex2f(x + width, y + height);
        GL11.glVertex2f(x + width, y);
        GL11.glVertex2f(x, y - 2);
        GL11.glEnd();

        GL11.glBegin(9);
        glColor(secondColor);
        GL11.glVertex2f(x + width, y);
        GL11.glVertex2f(x + width, y + height);
        GL11.glVertex2f(x + width * 2, y - 2);
        GL11.glVertex2f(x + width, y);
        GL11.glEnd();

        // line.
        GL11.glBegin(3);
        glColor(firstColor);
        GL11.glVertex2f(x, y - 2);
        GL11.glVertex2f(x + width, y + height);
        GL11.glVertex2f(x + width, y);
        GL11.glVertex2f(x, y - 2);
        GL11.glEnd();

        GL11.glBegin(3);
        glColor(secondColor);
        GL11.glVertex2f(x + width, y);
        GL11.glVertex2f(x + width, y + height);
        GL11.glVertex2f(x + width * 2, y - 2);
        GL11.glVertex2f(x + width, y);
        GL11.glEnd();

        disableSmoothLine();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glRotatef(-180 - 90, 0F, 0F, 1.0F);
        GL11.glPopMatrix();
    }
    public static void drawRoundOutline(float x, float y, float width, float height, float radius, float outlineThickness, Color color, Color outlineColor) {
        GL11.glDisable(GL_DEPTH_TEST);

        resetColor();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        roundedOutlineShader.useProgram();

        setupRoundedRectUniforms(x, y, width, height, radius, roundedOutlineShader);
        roundedOutlineShader.setUniformf("outlineThickness", outlineThickness * DarkMoon.getInstance().getScaleMath().getScale());
        roundedOutlineShader.setUniformf("color", color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        roundedOutlineShader.setUniformf("outlineColor", outlineColor.getRed() / 255f, outlineColor.getGreen() / 255f, outlineColor.getBlue() / 255f, outlineColor.getAlpha() / 255f);


        dev.darkmoon.client.utility.render.ShaderUtility.drawQuads(x - (2 + outlineThickness), y - (2 + outlineThickness), width + (4 + outlineThickness * 2), height + (4 + outlineThickness * 2));
        roundedOutlineShader.unloadProgram();
        GlStateManager.disableBlend();
        GL11.glEnable(GL_DEPTH_TEST);

    }
    public static void drawGradientGlow(float x, float y, float width, float height, int glowRadius, Color color1, Color color2, Color color3, Color color4) {
        glPushMatrix();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.01f);

        width = width + glowRadius * 2;
        height = height + glowRadius * 2;
        x = x - glowRadius;
        y = y - glowRadius;

        float _X = x - 0.25f;
        float _Y = y + 0.25f;

        int identifier = (int) (width * height + width + color1.hashCode() * glowRadius + glowRadius);

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        glDisable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GlStateManager.enableBlend();

        int texId = -1;
        if (SHADOW_CACHE.containsKey(identifier)) {
            texId = SHADOW_CACHE.get(identifier);

            GlStateManager.bindTexture(texId);
        } else {
            if (width <= 0) width = 1;
            if (height <= 0) height = 1;
            BufferedImage original = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB_PRE);

            Graphics g = original.getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(glowRadius, glowRadius, (int) (width - glowRadius * 2), (int) (height - glowRadius * 2));
            g.dispose();

            GaussianFilter op = new GaussianFilter(glowRadius);

            BufferedImage blurred = op.filter(original, null);


            texId = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), blurred, true, false);

            SHADOW_CACHE.put(identifier, texId);
        }

        GL11.glColor4f(1f, 1f, 1f, 1f);

        GL11.glBegin(GL11.GL_QUADS);
        setColor(color1);
        GL11.glTexCoord2f(0, 0); // top left
        GL11.glVertex2f(_X, _Y);

        setColor(color2);
        GL11.glTexCoord2f(0, 1); // bottom left
        GL11.glVertex2f(_X, _Y + height);

        setColor(color3);
        GL11.glTexCoord2f(1, 1); // bottom right
        GL11.glVertex2f(_X + width, _Y + height);

        setColor(color4);
        GL11.glTexCoord2f(1, 0); // top right
        GL11.glVertex2f(_X + width, _Y);
        GL11.glEnd();

        GlStateManager.enableTexture2D();
        GlStateManager.shadeModel(7424);
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.resetColor();

        glEnable(GL_CULL_FACE);
        glPopMatrix();
    }
    public static void drawBlurredShadowGradient(float x, float y, float width, float height, int blurRadius, Color color1, Color color2, Color color3, Color color4) {
        glPushMatrix();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.01f);

        width = width + blurRadius * 2;
        height = height + blurRadius * 2;
        x = x - blurRadius;
        y = y - blurRadius;

        float _X = x - 0.25f;
        float _Y = y + 0.25f;

        double identifier = (int) (width * height * 13212 / Math.sin(blurRadius));

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        glDisable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GlStateManager.enableBlend();

        int texId = -1;
        if (SHADOW_CACHE.containsKey(identifier)) {
            texId = SHADOW_CACHE.get(identifier);
            GlStateManager.bindTexture(texId);
        } else {
            BufferedImage original = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB);
            Graphics g = original.getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(blurRadius, blurRadius, (int) (width - blurRadius * 2), (int) (height - blurRadius * 2));
            g.dispose();

            GaussianFilter op = new GaussianFilter(blurRadius);

            BufferedImage blurred = op.filter(original, null);
            texId = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), blurred, true, false);
            SHADOW_CACHE.put((int) identifier, texId);
        }
        GL11.glColor4f(1f, 1f, 1f, 1f);

        GL11.glBegin(GL11.GL_QUADS);
        setColor(color1);
        GL11.glTexCoord2f(0, 0); // top left
        GL11.glVertex2f(_X, _Y);

        setColor(color2);
        GL11.glTexCoord2f(0, 1); // bottom left
        GL11.glVertex2f(_X, _Y + height);

        setColor(color3);
        GL11.glTexCoord2f(1, 1); // bottom right
        GL11.glVertex2f(_X + width, _Y + height);

        setColor(color4);
        GL11.glTexCoord2f(1, 0); // top right
        GL11.glVertex2f(_X + width, _Y);
        GL11.glEnd();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        glEnable(GL_CULL_FACE);
        GlStateManager.resetColor();
        glPopMatrix();
    }
    public static void scaleStart(float x, float y, float scale) {
        glPushMatrix();
        glTranslatef(x, y, 0);
        glScalef(scale, scale, 1);
        glTranslatef(-x, -y, 0);
    }

    public static void scaleEnd() {
        glPopMatrix();
    }

    public static void drawImage(ResourceLocation tex, float x, float y, float x2, float y2) {
        mc.getTextureManager().bindTexture(tex);
        GlStateManager.color(1, 1, 1, 1);
        allocTextureRectangle(x, y, x2, y2);
        GlStateManager.bindTexture(0);
    }
    public static void drawImage(ResourceLocation resourceLocation, float x, float y, float width, float height, Color color) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        setColor(color.getRGB());
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, width, height);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public static void allocTextureRectangle(float x, float y, float width, float height) {
        BUILDER.begin(7, DefaultVertexFormats.POSITION_TEX);
        BUILDER.pos(x, y, 0).tex(0, 0).endVertex();
        BUILDER.pos(x, y + height, 0).tex(0, 1).endVertex();
        BUILDER.pos(x + width, y + height, 0).tex(1, 1).endVertex();
        BUILDER.pos(x + width, y, 0).tex(1, 0).endVertex();
        TESSELLATOR.draw();
    }

    public static boolean isHovered(float mouseX, float mouseY, float x, float y, float width, float height) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }

    public static boolean isHovered(double mouseX, double mouseY, double x, double y, double width, double height) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }
    public static boolean isInViewFrustum(Entity entity) {
        return (isInViewFrustum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck);
    }

    private static boolean isInViewFrustum(AxisAlignedBB bb) {
        Entity current = mc.getRenderViewEntity();
        if (current != null) {
            FRUSTUM.setPosition(current.posX, current.posY, current.posZ);
        }
        return FRUSTUM.isBoundingBoxInFrustum(bb);
    }

    public static Vec3d project2D(int scaleFactor, double x, double y, double z) {
        float xPos = (float) x;
        float yPos = (float) y;
        float zPos = (float) z;
        IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
        FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
        FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
        FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelview);
        GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
        GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);
        if (GLU.gluProject(xPos, yPos, zPos, modelview, projection, viewport, vector))
            return new Vec3d((vector.get(0) / scaleFactor), ((Display.getHeight() - vector.get(1)) / scaleFactor),
                    vector.get(2));
        return null;
    }

    public static double interpolate(double current, double old, double scale) {
        return old + (current - old) * scale;
    }
}
