package xyz.vergoclient.util.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.glBlendFuncSeparate;
import static xyz.vergoclient.ui.notifications.ingame.NotificationManager.interpolate;

public class ColorUtils {
    /*

        Someone cool and sexy gave us this code.
        Forever grateful <3

     */

    public static int darker(int hexColor, int factor) {
        float alpha = (float) (hexColor >> 24 & 255);
        float red = Math.max((float) (hexColor >> 16 & 255) - (float) (hexColor >> 16 & 255) / (100.0F / (float) factor), 0.0F);
        float green = Math.max((float) (hexColor >> 8 & 255) - (float) (hexColor >> 8 & 255) / (100.0F / (float) factor), 0.0F);
        float blue = Math.max((float) (hexColor & 255) - (float) (hexColor & 255) / (100.0F / (float) factor), 0.0F);
        return (int) ((float) (((int) alpha << 24) + ((int) red << 16) + ((int) green << 8)) + blue);
    }

    public static Color brighter(Color color, float FACTOR) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int alpha = color.getAlpha();

        /* From 2D group:
         * 1. black.brighter() should return grey
         * 2. applying brighter to blue will always return blue, brighter
         * 3. non pure color (non zero rgb) will eventually return white
         */
        int i = (int) (1.0 / (1.0 - FACTOR));
        if (r == 0 && g == 0 && b == 0) {
            return new Color(i, i, i, alpha);
        }
        if (r > 0 && r < i) r = i;
        if (g > 0 && g < i) g = i;
        if (b > 0 && b < i) b = i;

        return new Color(Math.min((int) (r / FACTOR), 255),
                Math.min((int) (g / FACTOR), 255),
                Math.min((int) (b / FACTOR), 255),
                alpha);
    }

    //The next few methods are for interpolating colors
    public static int interpolateColor(Color color1, Color color2, float amount) {
        amount = Math.min(1, Math.max(0, amount));
        return interpolateColorC(color1, color2, amount).getRGB();
    }

    public static int interpolateColor(int color1, int color2, float amount) {
        amount = Math.min(1, Math.max(0, amount));
        Color cColor1 = new Color(color1);
        Color cColor2 = new Color(color2);
        return interpolateColorC(cColor1, cColor2, amount).getRGB();
    }

    public static Color interpolateColorC(Color color1, Color color2, float amount) {
        amount = Math.min(1, Math.max(0, amount));
        return new Color(interpolateInt(color1.getRed(), color2.getRed(), amount),
                interpolateInt(color1.getGreen(), color2.getGreen(), amount),
                interpolateInt(color1.getBlue(), color2.getBlue(), amount),
                interpolateInt(color1.getAlpha(), color2.getAlpha(), amount));
    }

    public static int interpolateInt(int oldValue, int newValue, double interpolationValue){
        return interpolate(oldValue, newValue, (float) interpolationValue).intValue();
    }

    public static Color rainbow(int speed, int index, float saturation, float brightness, float opacity) {
        int angle = (int) ((System.currentTimeMillis() / speed + index) % 360);
        float hue = angle / 360f;
        Color color = new Color(Color.HSBtoRGB(hue, saturation, brightness));
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.min(255, (int) (opacity * 255))));
    }

    public static int applyOpacity(int color, float opacity) {
        Color old = new Color(color);
        return applyOpacity(old, opacity).getRGB();
    }

    //Opacity value ranges from 0-1
    public static Color applyOpacity(Color color, float opacity) {
        opacity = Math.min(1, Math.max(0, opacity));
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (color.getAlpha() * opacity));
    }

    public static void glDrawFilledQuad(final double x,
                                        final double y,
                                        final double width,
                                        final double height,
                                        final int colour) {
        // Enable blending
        final boolean restore = glEnableBlend();
        // Disable texture drawing
        glDisable(GL_TEXTURE_2D);
        // Set color
        glColour(colour);

        // Begin rect
        glBegin(GL_QUADS);
        {
            glVertex2d(x, y);
            glVertex2d(x, y + height);
            glVertex2d(x + width, y + height);
            glVertex2d(x + width, y);
        }
        // Draw the rect
        glEnd();

        // Disable blending
        glRestoreBlend(restore);
        // Re-enable texture drawing
        glEnable(GL_TEXTURE_2D);
    }

    public static void glDrawFilledQuad(final double x,
                                        final double y,
                                        final double width,
                                        final double height,
                                        final int startColour,
                                        final int endColour) {
        // Enable blending
        final boolean restore = glEnableBlend();
        // Disable texture drawing
        glDisable(GL_TEXTURE_2D);

        glShadeModel(GL_SMOOTH);

        // Begin rect
        glBegin(GL_QUADS);
        {
            glColour(startColour);
            glVertex2d(x, y);

            glColour(endColour);
            glVertex2d(x, y + height);
            glVertex2d(x + width, y + height);

            glColour(startColour);
            glVertex2d(x + width, y);
        }
        // Draw the rect
        glEnd();

        glShadeModel(GL_FLAT);

        // Disable blending
        glRestoreBlend(restore);
        // Re-enable texture drawing
        glEnable(GL_TEXTURE_2D);
    }

    protected static Minecraft mc = Minecraft.getMinecraft();

    public static void glDrawFramebuffer(final int framebufferTexture, final int width, final int height) {
        // Bind the texture of our framebuffer
        glBindTexture(GL_TEXTURE_2D, framebufferTexture);
        // Disable alpha testing so fading out outline works
        glDisable(GL_ALPHA_TEST);
        // Make sure blend is enabled
        final boolean restore = glEnableBlend();
        // Draw the frame buffer texture upside-down
        glBegin(GL_QUADS);
        {
            glTexCoord2f(0, 1);
            glVertex2f(0, 0);

            glTexCoord2f(0, 0);
            glVertex2f(0, height);

            glTexCoord2f(1, 0);
            glVertex2f(width, height);

            glTexCoord2f(1, 1);
            glVertex2f(width, 0);
        }
        glEnd();
        // Restore blend
        glRestoreBlend(restore);
        // Restore alpha test
        glEnable(GL_ALPHA_TEST);
    }

    public static void drawQuads(float x, float y, float width, float height) {
        if (mc.gameSettings.ofFastRender) return;
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(x, y);
        glTexCoord2f(0, 1);
        glVertex2f(x, y + height);
        glTexCoord2f(1, 1);
        glVertex2f(x + width, y + height);
        glTexCoord2f(1, 0);
        glVertex2f(x + width, y);
        glEnd();
    }

    public static void drawQuads() {
        if (mc.gameSettings.ofFastRender) return;
        ScaledResolution sr = new ScaledResolution(mc);
        float width = (float) sr.getScaledWidth_double();
        float height = (float) sr.getScaledHeight_double();
        glBegin(GL_QUADS);
        glTexCoord2f(0, 1);
        glVertex2f(0, 0);
        glTexCoord2f(0, 0);
        glVertex2f(0, height);
        glTexCoord2f(1, 0);
        glVertex2f(width, height);
        glTexCoord2f(1, 1);
        glVertex2f(width, 0);
        glEnd();
    }

    public static Color fadeColor(Color color, int index, int count) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs(((float) (System.currentTimeMillis() % 2000L) / 1000.0F + (float) index * 2.0F / (float) count * 2.0F) % 2 - 1.0F);
        brightness = 0.5F + 0.5F * brightness;
        hsb[2] = brightness % 2.0F;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    public static Color fadeColorHorizontal(Color color, int index, int count) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
        float brightness = Math.abs(((float) (System.currentTimeMillis() % 2000L) / 1000.0F + (float) index * 2.0F / (float) count * 2.0F) % 2 - 1.0F);
        brightness = 0.5F + 0.5F * brightness;
        hsb[2] = brightness % 2.0F;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    public static int fadeTo(int startColour, int endColour, double progress) {
        double invert = 1.0 - progress;
        int r = (int) ((startColour >> 16 & 0xFF) * invert +
                (endColour >> 16 & 0xFF) * progress);
        int g = (int) ((startColour >> 8 & 0xFF) * invert +
                (endColour >> 8 & 0xFF) * progress);
        int b = (int) ((startColour & 0xFF) * invert +
                (endColour & 0xFF) * progress);
        int a = (int) ((startColour >> 24 & 0xFF) * invert +
                (endColour >> 24 & 0xFF) * progress);
        return ((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8) |
                (b & 0xFF);
    }

    public static int fadeBetween(int startColour, int endColour, double progress) {
        if (progress > 1) progress = 1 - progress % 1;
        return fadeTo(startColour, endColour, progress);
    }

    public static int fadeBetween(int startColour, int endColour, long offset) {
        return fadeBetween(startColour, endColour, ((System.currentTimeMillis() + offset) % 2000L) / 1000.0);
    }

    public static int fadeBetween(int startColour, int endColour) {
        return fadeBetween(startColour, endColour, 0L);
    }

    public static boolean glEnableBlend() {
        final boolean wasEnabled = glIsEnabled(GL_BLEND);

        if (!wasEnabled) {
            glEnable(GL_BLEND);
            glBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        }

        return wasEnabled;
    }

    public static void glColour(final int color) {
        glColor4ub((byte) (color >> 16 & 0xFF),
                (byte) (color >> 8 & 0xFF),
                (byte) (color & 0xFF),
                (byte) (color >> 24 & 0xFF));
    }

    public static void glDrawSidewaysGradientRect(final double x,
                                                  final double y,
                                                  final double width,
                                                  final double height,
                                                  final int startColour,
                                                  final int endColour) {
        // Enable blending
        final boolean restore = glEnableBlend();
        // Disable texture drawing
        glDisable(GL_TEXTURE_2D);
        // Enable vertex colour changing
        glShadeModel(GL_SMOOTH);

        // Begin rect
        glBegin(GL_QUADS);
        {
            // Start fade
            glColour(startColour);
            glVertex2d(x, y);
            glVertex2d(x, y + height);
            // End fade
            glColour(endColour);
            glVertex2d(x + width, y + height);
            glVertex2d(x + width, y);
        }
        // Draw the rect
        glEnd();

        // Restore shade model
        glShadeModel(GL_FLAT);
        // Re-enable texture drawing
        glEnable(GL_TEXTURE_2D);
        // Disable blending
        glRestoreBlend(restore);
    }

    public static void glRestoreBlend(final boolean wasEnabled) {
        if (!wasEnabled) {
            glDisable(GL_BLEND);
        }
    }

    public static void drawRoundedRect(float x, float y, float width, float height, float radius, int color) {
        float x1 = x + width, // @off
                y1 = y + height;
        final float f = (color >> 24 & 0xFF) / 255.0F,
                f1 = (color >> 16 & 0xFF) / 255.0F,
                f2 = (color >> 8 & 0xFF) / 255.0F,
                f3 = (color & 0xFF) / 255.0F; // @on
        GL11.glPushAttrib(0);
        GL11.glScaled(0.5, 0.5, 0.5);

        x *= 2;
        y *= 2;
        x1 *= 2;
        y1 *= 2;

        glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(f1, f2, f3, f);
        GlStateManager.enableBlend();
        glEnable(GL11.GL_LINE_SMOOTH);

        GL11.glBegin(GL11.GL_POLYGON);
        final double v = Math.PI / 180;

        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x + radius + MathHelper.sin((float) (i * v)) * (radius * -1), y + radius + MathHelper.cos((float) (i * v)) * (radius * -1));
        }

        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x + radius + MathHelper.sin((float) (i * v)) * (radius * -1), y1 - radius + MathHelper.cos((float) (i * v)) * (radius * -1));
        }

        for (int i = 0; i <= 90; i += 3) {
            GL11.glVertex2d(x1 - radius + MathHelper.sin((float) (i * v)) * radius, y1 - radius + MathHelper.cos((float) (i * v)) * radius);
        }

        for (int i = 90; i <= 180; i += 3) {
            GL11.glVertex2d(x1 - radius + MathHelper.sin((float) (i * v)) * radius, y + radius + MathHelper.cos((float) (i * v)) * radius);
        }

        GL11.glEnd();

        glEnable(GL11.GL_TEXTURE_2D);
        glDisable(GL11.GL_LINE_SMOOTH);
        glEnable(GL11.GL_TEXTURE_2D);

        GL11.glScaled(2, 2, 2);

        GL11.glPopAttrib();
        GL11.glColor4f(1, 1, 1, 1);
    }

    public static void drawHollowRoundedRect(double x,
                                             double y,
                                             double width,
                                             double height,
                                             double cornerRadius,
                                             boolean smoothed,
                                             Color color) {
        glDisable(GL_TEXTURE_2D);
        glEnable(GL_LINE_SMOOTH);
        glEnable(GL_BLEND);
        GL11.glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255.0F, color.getAlpha() / 255F);
        glLineWidth(1.0f);
        glBegin(GL_LINE_LOOP);
        double cornerX = x + width - cornerRadius;
        double cornerY = y + height - cornerRadius;
        for (int i = 0; i <= 90; i += 30)
            glVertex2d(cornerX + Math.sin(i * Math.PI / 180.0D) * cornerRadius, cornerY + Math.cos(i * Math.PI / 180.0D) * cornerRadius);
        glEnd();
        cornerX = x + width - cornerRadius;
        cornerY = y + cornerRadius;
        glBegin(GL_LINE_LOOP);
        for (int i = 90; i <= 180; i += 30)
            glVertex2d(cornerX + Math.sin(i * Math.PI / 180.0D) * cornerRadius, cornerY + Math.cos(i * Math.PI / 180.0D) * cornerRadius);
        glEnd();
        cornerX = x + cornerRadius;
        cornerY = y + cornerRadius;
        glBegin(GL_LINE_LOOP);
        for (int i = 180; i <= 270; i += 30)
            glVertex2d(cornerX + Math.sin(i * Math.PI / 180.0D) * cornerRadius, cornerY + Math.cos(i * Math.PI / 180.0D) * cornerRadius);
        glEnd();
        cornerX = x + cornerRadius;
        cornerY = y + height - cornerRadius;
        glBegin(GL_LINE_LOOP);
        for (int i = 270; i <= 360; i += 30)
            glVertex2d(cornerX + Math.sin(i * Math.PI / 180.0D) * cornerRadius, cornerY + Math.cos(i * Math.PI / 180.0D) * cornerRadius);
        glEnd();
        glDisable(GL_BLEND);
        glDisable(GL_LINE_SMOOTH);
        glEnable(GL_TEXTURE_2D);
        glDrawLine(x + cornerRadius, y, x + width - cornerRadius, y, 1.0f, smoothed, color.getRGB());
        glDrawLine(x + cornerRadius, y + height, x + width - cornerRadius, y + height, 1.0f, smoothed, color.getRGB());
        glDrawLine(x, y + cornerRadius, x, y + height - cornerRadius, 1.0f, smoothed, color.getRGB());
        glDrawLine(x + width, y + cornerRadius, x + width, y + height - cornerRadius, 1.0f, smoothed, color.getRGB());
    }

    public static void glDrawLine(final double x,
                                  final double y,
                                  final double x1,
                                  final double y1,
                                  final float lineWidth,
                                  final boolean smoothed,
                                  final int colour) {
        // Enable blending (required for anti-aliasing)
        final boolean restore = glEnableBlend();
        // Disable texture drawing
        glDisable(GL_TEXTURE_2D);
        // Set line width
        glLineWidth(lineWidth);

        if (smoothed) {
            // Enable line anti-aliasing
            glEnable(GL_LINE_SMOOTH);
            glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        }

        glColour(colour);

        // Begin line
        glBegin(GL_LINES);
        {
            // Start
            glVertex2d(x, y);
            // End
            glVertex2d(x1, y1);
        }
        // Draw the line
        glEnd();

        // Restore blend
        glRestoreBlend(restore);
        if (smoothed) {
            // Disable line anti-aliasing
            glDisable(GL_LINE_SMOOTH);
            glHint(GL_LINE_SMOOTH_HINT, GL_DONT_CARE);
        }
        // Re-enable texture drawing
        glEnable(GL_TEXTURE_2D);
    }

}
