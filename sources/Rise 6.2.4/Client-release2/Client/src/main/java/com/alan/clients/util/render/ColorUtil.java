package com.alan.clients.util.render;

import com.alan.clients.Client;
import com.alan.clients.ui.theme.ThemeManager;
import com.alan.clients.util.font.Font;
import com.alan.clients.util.math.MathUtil;
import com.alan.clients.util.vector.Vector2d;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public final class ColorUtil {

    private ColorUtil() {
    }

    /**
     * Method which colors using a hex code
     *
     * @param hex used hex code
     */
    public static void glColor(final int hex) {
        final float a = (hex >> 24 & 0xFF) / 255.0F;
        final float r = (hex >> 16 & 0xFF) / 255.0F;
        final float g = (hex >> 8 & 0xFF) / 255.0F;
        final float b = (hex & 0xFF) / 255.0F;
        GL11.glColor4f(r, g, b, a);
    }

    /**
     * Method which colors using a color
     *
     * @param color used color
     */
    public static void glColor(final Color color) {
        GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
    }

    public static Color darker(final Color color, final float factor) {
        return new Color(Math.max((int) (color.getRed() * factor), 0), Math.max((int) (color.getGreen() * factor), 0), Math.max((int) (color.getBlue() * factor), 0), color.getAlpha());
    }

    public static Color brighter(final Color color, final float factor) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        final int alpha = color.getAlpha();

        final int i = (int) (1 / (1 - factor));
        if (red == 0 && green == 0 && blue == 0) {
            return new Color(i, i, i, alpha);
        }

        if (red > 0 && red < i) red = i;
        if (green > 0 && green < i) green = i;
        if (blue > 0 && blue < i) blue = i;

        return new Color(Math.min((int) (red / factor), 255), Math.min((int) (green / factor), 255), Math.min((int) (blue / factor), 255), alpha);
    }

    public static Color withRed(final Color color, final int red) {
        return new Color(red, color.getGreen(), color.getBlue());
    }

    public static Color withGreen(final Color color, final int green) {
        return new Color(color.getRed(), green, color.getBlue());
    }

    public static Color withBlue(final Color color, final int blue) {
        return new Color(color.getRed(), color.getGreen(), blue);
    }

    public static Color withAlpha(final Color color, final int alpha) {
        if (alpha == color.getAlpha()) return color;
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) MathUtil.clamp(0, 255, alpha));
    }

    public static Color mixColors(final Color color1, final Color color2, final double percent) {
        final double inverse_percent = 1.0 - percent;
        final int redPart = (int) (color1.getRed() * percent + color2.getRed() * inverse_percent);
        final int greenPart = (int) (color1.getGreen() * percent + color2.getGreen() * inverse_percent);
        final int bluePart = (int) (color1.getBlue() * percent + color2.getBlue() * inverse_percent);
        return new Color(redPart, greenPart, bluePart);
    }

    /**
     * Determines the blending factor between the themes two accent colors at a specific screen coordinate
     *
     * @param screenCoordinates The screen coordinate to calculate the blend factor for
     * @return The blending factor, in a range of [0, 1] (inclusive) between the two accent colors of the theme
     */
    public static double getBlendFactor(Vector2d screenCoordinates) {
        return Math.sin(System.currentTimeMillis() / 600.0D + screenCoordinates.getX() * 0.005D + screenCoordinates.getY() * 0.06D) * 0.5D + 0.5D;
    }

    public static Color rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 10.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.6f, 1f);
    }

    /**
     * Interpolates between the HUD theme color based on the index of each character in the string.
     *
     * @param font   the font to draw the text with
     * @param text   the text to draw
     * @param x      the x position to draw the text
     * @param y      the y position to draw the text
     * @param shadow whether to draw the text with a shadow
     */
    public static void drawInterpolatedText(final Font font, final String text, final double x, final double y, final boolean shadow) {
        float w = 0;
        ThemeManager themeManager = Client.INSTANCE.getThemeManager();
        for (int i = 0; i < text.length(); i++) {
            final String character = String.valueOf(text.charAt(i));
            final Color color = ColorUtil.mixColors(themeManager.getTheme().getFirstColor(), themeManager.getTheme().getSecondColor(), Math.sin(i * 0.095) * 0.5D + 0.5D);

            if (shadow) {
                font.drawWithShadow(character, x + w, y, color.getRGB());
            } else {
                font.draw(character, x + w, y, color.getRGB());
            }

            w += font.width(character) + 0.5f;
        }
    }

    /**
     * Interpolates between the HUD theme color based on the index of each character in the string.
     *
     * @param font the font to draw the text with
     * @param text the text to draw
     * @param x    the x position to draw the text
     * @param y    the y position to draw the text
     */
    public static void drawInterpolatedText(final Font font, final String text, final double x, final double y) {
        drawInterpolatedText(font, text, x, y, true);
    }

}
