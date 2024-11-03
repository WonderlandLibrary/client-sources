package net.augustus.utils;

import java.awt.Color;

import net.augustus.utils.interfaces.MC;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author sendQueue <Vinii>
 *
 *         Further info at Vinii.de or github@vinii.de, file created at 20.10.2020.
 *         Use is only authorized if given credit!
 *
 */
public class GL11Util implements MC {

    /**
     * Makes a color darker with a custom factor.
     * <p>
     * <h1>CAUTION: 0 < FACTOR < 1!</h1>
     * <p>
     * Might be inconsistent because of rounding errors.
     *
     * @param color
     * @param factor
     * @return
     */
    public static Color darker(Color color, float factor) {
        //in case of keks
        factor = MathHelper.clamp_float(factor, 0.001f, 0.999f);

        return new Color(Math.max((int) (color.getRed() * factor), 0), Math.max((int) (color.getGreen() * factor), 0),
                Math.max((int) (color.getBlue() * factor), 0), color.getAlpha());
    }

    /**
     * Makes a color brighter/lighter with a custom factor.
     * <p>
     * <h1>CAUTION: 0 < FACTOR < 1!</h1>
     * <p>
     * Might be inconsistent because of rounding errors.
     *
     * @param color
     * @param factor
     * @return
     */
    public static Color brighter(Color color, float factor) {
        //in case of keks
        factor = MathHelper.clamp_float(factor, 0.001f, 0.999f);

        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int alpha = color.getAlpha();

        /*
         * From 2D group: 1. black.brighter() should return grey 2. applying brighter to
         * blue will always return blue, brighter 3. non pure color (non zero rgb) will
         * eventually return white
         */

        int i = (int) (1.0 / (1.0 - factor));
        if (r == 0 && g == 0 && b == 0) {
            return new Color(i, i, i, alpha);
        }
        if (r > 0 && r < i)
            r = i;
        if (g > 0 && g < i)
            g = i;
        if (b > 0 && b < i)
            b = i;

        return new Color(Math.min((int) (r / factor), 255), Math.min((int) (g / factor), 255),
                Math.min((int) (b / factor), 255), alpha);
    }

    /**
     * Defines a rectangle (scissorBox) in window coordinates not GL's: from
     * https://vinii.de/github/LWJGLUtil/scissorBoxGL.png to
     * https://vinii.de/github/LWJGLUtil/scissorBoxWindow.png
     *
     */
    public static void scissorBox(final int x, final int y, final int width, final int height) {
        final ScaledResolution scaledResolution = new ScaledResolution(mc);
        final int factor = scaledResolution.getScaleFactor();

        GL11.glEnable(GL11.GL_SCISSOR_TEST);

        GL11.glScissor(x * factor, (scaledResolution.getScaledHeight() - (y + height)) * factor,
                ((x + width) - x) * factor, ((y + height) - y) * factor);

        // disable GL_SCISSOR_TEST after bounding
    }


    /**
     * Draws a rectangle.
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param color
     */
    public static void drawRect(final float x, final float y, final float width, final float height, final int color) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);

        GL11.glPushMatrix();
        GL11.glBegin(7);

        glColor(color);

        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y + height);
        GL11.glVertex2f(x + width, y + height);
        GL11.glVertex2f(x + width, y);

        GL11.glEnd();
        GL11.glPopMatrix();

        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }

    /**
     * Draws rect with rounded corners, how it's made:
     * https://vinii.de/github/LWJGLUtil/roundedRect.png
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param radius
     * @param color
     */
    public static void drawRoundedRect(final float x, final float y, final float width, final float height,
                                       final float radius, final int color) {
        float x2 = x + ((radius / 2f) + 0.5f);
        float y2 = y + ((radius / 2f) + 0.5f);
        float calcWidth = (width - ((radius / 2f) + 0.5f));
        float calcHeight = (height - ((radius / 2f) + 0.5f));
        // top (pink)
        relativeRect(x2 + radius / 2f, y2 - radius / 2f - 0.5f, x2 + calcWidth - radius / 2f, y + calcHeight - radius / 2f,
                color);
        // bottom (yellow)
        relativeRect(x2 + radius / 2f, y2, x2 + calcWidth - radius / 2f, y2 + calcHeight + radius / 2f + 0.5f, color);
        // left (red)
        relativeRect((x2 - radius / 2f - 0.5f), y2 + radius / 2f, x2 + calcWidth, y2 + calcHeight - radius / 2f, color);
        // right (green)
        relativeRect(x2, y2 + radius / 2f + 0.5f, x2 + calcWidth + radius / 2f + 0.5f, y2 + calcHeight - radius / 2f,
                color);

        // left top circle
        polygonCircle(x, y - 0.15, radius * 2, 360, color);
        // right top circle
        polygonCircle(x + calcWidth - radius + 1.0, y - 0.15, radius * 2, 360, color);
        // left bottom circle
        polygonCircle(x, y + calcHeight - radius + 1, radius * 2, 360, color);
        // right bottom circle
        polygonCircle(x + calcWidth - radius + 1, y + calcHeight - radius + 1, radius * 2, 360, color);
    }
    public static void drawRoundedRect2(final double x, final double y, final double width, final double height, final double radius, final int color) {
        drawRoundedRect((float)x, (float)y, (float)width, (float)height, (float) radius, color);
    }

    /**
     * Draws a rect, as in {@link Gui}#drawRect.
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param color
     */
    public static void relativeRect(final float left, final float top, final float right, final float bottom,
                                    final int color) {

        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        glColor(color);
		/*        worldRenderer.begin -> .func_181668_a
        worldRenderer.pos -> .func_181662_b*/
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(left, bottom, 0).endVertex();
        worldRenderer.pos(right, bottom, 0).endVertex();
        worldRenderer.pos(right, top, 0).endVertex();
        worldRenderer.pos(left, top, 0).endVertex();

        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    /**
     * Draws a polygon circle
     */
    public static final void polygonCircle(final double x, final double y, double sideLength, final double degree,
                                           final int color) {
        sideLength *= 0.5;

        GL11.glEnable(GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);

        GlStateManager.disableAlpha();

        glColor(color);

        GL11.glLineWidth(1);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        // since its filled, otherwise GL_LINE_STRIP
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        for (double i = 0; i <= degree; i++) {
            final double angle = i * (Math.PI * 2) / degree;

            GL11.glVertex2d(x + (sideLength * Math.cos(angle)) + sideLength,
                    y + (sideLength * Math.sin(angle)) + sideLength);
        }

        GL11.glColor4f(1, 1, 1, 1);
        GL11.glEnd();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);

        GlStateManager.enableAlpha();

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL_BLEND);
    }

    public static void drawFilledCircle(final float xx, final float yy, final float radius, final Color color) {
        int sections = 1920;
        double dAngle = 2 * Math.PI / sections;
        float x, y;
        glPushAttrib(GL_ENABLE_BIT);
        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_LINE_SMOOTH);
        glBegin(GL11.GL_TRIANGLE_FAN);
        for (int i = 0; i < sections; i++) {
            x = (float) (radius * Math.sin((i * dAngle)));
            y = (float) (radius * Math.cos((i * dAngle)));

            glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);
            glVertex2f(xx + x, yy + y);
        }
        GlStateManager.color(0, 0, 0);
        glEnd();
        glPopAttrib();
    }

    /**
     *
     * Draws a horizontal gradient rect
     *
     */
    public static void drawHorizontalGradient(final float x, final float y, final float width, final float height,
                                              final int leftColor, final int rightColor) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);

        GL11.glPushMatrix();
        GL11.glBegin(7);

        glColor(leftColor);

        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y + height);

        glColor(rightColor);

        GL11.glVertex2f(x + width, y + height);
        GL11.glVertex2f(x + width, y);

        GL11.glEnd();
        GL11.glPopMatrix();

        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }

    /**
     * Draws a vertical gradient rect
     */
    public static void drawVerticalGradient(final float x, final float y, final float width, final float height,
                                            final int topColor, final int bottomColor) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);

        GL11.glPushMatrix();
        GL11.glBegin(7);

        glColor(topColor);

        GL11.glVertex2f(x, y + height);
        GL11.glVertex2f(x + width, y + height);

        glColor(bottomColor);

        GL11.glVertex2f(x + width, y);
        GL11.glVertex2f(x, y);

        GL11.glEnd();
        GL11.glPopMatrix();

        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }

    /**
     * Sets color from hex
     *
     * @param hex
     */
    public static void glColor(final int hex) {
        //shifting
        final float alpha = (hex >> 24 & 255) / 255f;
        final float red = (hex >> 16 & 255) / 255f;
        final float green = (hex >> 8 & 255) / 255f;
        final float blue = (hex & 255) / 255f;

        GL11.glColor4f(red, green, blue, alpha);
    }
}
