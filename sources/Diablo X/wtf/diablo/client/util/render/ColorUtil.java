package wtf.diablo.client.util.render;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import wtf.diablo.client.setting.impl.ColorSetting;
import wtf.diablo.client.util.render.color.EnumPallete;

import java.awt.*;

public final class ColorUtil {

    /**
     * Vince. These should be inside the ClickGUI Class because you will never use these colors anywhere else. Same with Notifications.
     */
    public static final ColorSetting CATEGORY_COLOR = new ColorSetting("Category Color", new Color(7, 7, 7));
    public static final ColorSetting CATEGORY_MODULE_COLOR = new ColorSetting("Module Color", new Color(11, 11, 11));
    public static final ColorSetting CATEGORY_TEXT_COLOR = new ColorSetting("Text Color", new Color(255, 255, 255));
    public static final ColorSetting CATEGORY_MODULE_TEXT_COLOR = new ColorSetting("Module Text Color", new Color(255, 255, 255));
    public static final ColorSetting CATEGORY_MODULE_TEXT_COLOR_INACTIVE = new ColorSetting("Module Text Inactive", new Color(255, 255, 255, 255));
    public static final ColorSetting CATEGORY_MODULE_TEXT_COLOR_HOVER = new ColorSetting("Module Text Hover", new Color(255, 255, 255, 180));

    public static final ColorSetting SETTING_BACKGROUND_COLOR = new ColorSetting("Setting Background Color", new Color(36, 35, 35, 255));
    public static final ColorSetting SETTING_TEXT_COLOR = new ColorSetting("Setting Text Color", new Color(255, 255, 255));
    public static final ColorSetting SETTING_TEXT_INACTIVE_COLOR = new ColorSetting("Setting Inactive Color", new Color(125, 125, 125, 255));

    public static final ColorSetting NOTIFICATION_COLOR = new ColorSetting("Notification Color", new Color(18, 18, 18, 170));
    public static final ColorSetting NOTIFICATION_TITLE_COLOR = new ColorSetting("Notification Title Color", new Color(255, 255, 255));
    public static final ColorSetting NOTIFICATION_MESSAGE_COLOR = new ColorSetting("Notification Message Color", new Color(255, 255, 255));
    public static final ColorSetting NOTIFICATION_INFO_COLOR = new ColorSetting("Notification Info Color", new Color(41, 147, 234));
    public static final ColorSetting NOTIFICATION_WARNING_COLOR = new ColorSetting("Notification Warning Color", new Color(234, 121, 7));
    public static final ColorSetting NOTIFICATION_ERROR_COLOR = new ColorSetting("Notification Error Color", new Color(215, 49, 49));
    public static final ColorSetting NOTIFICATION_SUCCESS_COLOR = new ColorSetting("Notification Success Color", new Color(57, 229, 66));

    public static final ColorSetting AMBIENT_COLOR = new ColorSetting("Ambient Color", new Color(255,46,83,255));

    public static final Color FRIEND_COLOR = new Color(21, 137, 255);

    public static final Color PRIMARY_MAIN_COLOR = new Color(0xFF8C61B2);
    public static final Color SECONDARY_MAIN_COLOR = new Color(0xFF62437C).darker();

    private ColorUtil() {}

    public static int getRainbow(final int speed, final int offset, final float saturation, final float brightness) {
        float hue = (System.currentTimeMillis() + offset) % speed;
        hue /= speed;
        return Color.HSBtoRGB(hue, saturation, brightness);
    }

    public static Color getWaveColor(Color color, int speed, float offset) {
        float hue = (float) (System.currentTimeMillis() % 8000) + (offset);
        while (hue > speed) {
            hue -= speed;
        }
        hue /= speed;
        if (hue > 0.5) {
            hue = 0.5F - (hue - 0.5f);
        }
        hue += 0.5F;
        float[] colors = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        return Color.getHSBColor(colors[0], colors[1], hue);
    }

    //Credit to artemis
    public static int astolfo(int delay, float offset) {
        int yStart = 20;
        float speed = 3000f;
        float index = 0.3f;
        float hue = (float) (System.currentTimeMillis() % delay) + (offset);
        while (hue > speed) {
            hue -= speed;
        }
        hue /= speed;
        if (hue > 0.5) {
            hue = 0.5F - (hue - 0.5f);
        }
        hue += 0.5F;
        return Color.HSBtoRGB(hue, 0.5F, 1F);
    }

    /**
     * This doesnt consistently mix between colors. What it does is it mixes between the two colors and set the thing u want to be colored to the mixture of those colors.
     * @param color1 the color 1
     * @param color2 the color 2
     * @param offset the offset
     * @return the color
     */
    public static Color mixColor(Color color1, Color color2, int offset) {
        float delta = (offset % 100) / 100.0f; // Normalize the offset value

        int red = (int) (color1.getRed() * (1 - delta) + color2.getRed() * delta);
        int green = (int) (color1.getGreen() * (1 - delta) + color2.getGreen() * delta);
        int blue = (int) (color1.getBlue() * (1 - delta) + color2.getBlue() * delta);

        return new Color(red, green, blue);
    }

    // from weed
    public static int fadeBetween(int color1, int color2, long ms, int index) {
        long currentMillis = System.currentTimeMillis() + index / 2;
        return interpolate2(color1, color2, currentMillis % ms / (ms / 2.0f));
    }

    public static Color interpolateColor(final float t, final Color... colors) {
        if (colors == null)
            throw new IllegalArgumentException("At least one colors must be provided");
        final int colorsSize = colors.length;
        // If there is only one color there is nothing to calculate, return the color
        if (colorsSize == 1)
            return colors[0];

        // If t is negative then interpolate in the opposite direction
        if (t < 0)
            return interpolateColor(-t, colors);
            // If t > 1 loop to beginning
        else if (t > 1)
            return interpolateColor(t % 1, colors);
        else if (t == 1)
            return colors[colorsSize - 1];

        final float segmentSize = 1f / (colorsSize - 1);
        final int segmentIndex = (int) (t / segmentSize);
        final float segmentT = (t - segmentIndex * segmentSize) / segmentSize;

        final Color color1 = colors[segmentIndex];
        final Color color2 = colors[segmentIndex + 1];

        final int red = (int) (color1.getRed() * (1 - segmentT) + color2.getRed() * segmentT);
        final int green = (int) (color1.getGreen() * (1 - segmentT) + color2.getGreen() * segmentT);
        final int blue = (int) (color1.getBlue() * (1 - segmentT) + color2.getBlue() * segmentT);
        final int alpha = (int) (color1.getAlpha() * (1 - segmentT) + color2.getAlpha() * segmentT);

        return new Color(red, green, blue, alpha);
    }

    public static Color interpolatePalette(final float t, final EnumPallete palette) {
        return interpolateColor(t, palette.getColors());
    }

    // from weed
    public static int interpolate2(int color1, int color2, float offset) {
        if (offset > 1)
            offset = 1 - offset % 1;

        double invert = 1 - offset;
        int r = (int) ((color1 >> 16 & 0xFF) * invert + (color2 >> 16 & 0xFF) * offset);
        int g = (int) ((color1 >> 8 & 0xFF) * invert + (color2 >> 8 & 0xFF) * offset);
        int b = (int) ((color1 & 0xFF) * invert + (color2 & 0xFF) * offset);
        int a = (int) ((color1 >> 24 & 0xFF) * invert + (color2 >> 24 & 0xFF) * offset);
        return ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
    }

    /**
     * Sets the color to specified color using GL.
     *
     * @param color the color to set.
     */
    public static void setColor(
            final Color color
    ) {
        GL11.glColor4f((1.0F / 255) * color.getRed(), (1.0F / 255) * color.getGreen(), (1.0F / 255) * color.getBlue(), (1.0F / 255) * color.getAlpha());
    }

    public static Color interp(Color color1, Color color2, float percentage) {
        if (percentage > 1f) {
            percentage /= 100f;
        }
        return new Color((int) ((color2.getRed() - color1.getRed()) * percentage + color1.getRed()),
                (int) ((color2.getGreen() - color1.getGreen()) * percentage + color1.getGreen()),
                (int) ((color2.getBlue() - color1.getBlue()) * percentage + color1.getBlue()));
    }

    public static double[] getRGBA(final int color) {
        double r = (color >> 16 & 255) / 255.0F;
        double g = (color >> 8 & 255) / 255.0F;
        double b = (color & 255) / 255.0F;
        double a = (color >> 24 & 255) / 255.0F;
        return new double[] { r, g, b, a };
    }

    public static void setAlphaLimit(final float limit) {
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(GL11.GL_GREATER, (float) (limit * .01));
    }

    public static void resetColor() {
        GL11.glColor4f(1, 1, 1, 1);
    }

}