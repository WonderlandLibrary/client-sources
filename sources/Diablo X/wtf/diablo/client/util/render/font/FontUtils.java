package wtf.diablo.client.util.render.font;

import wtf.diablo.client.font.TTFFontRenderer;
import wtf.diablo.client.util.render.ColorUtil;

import java.awt.*;

/**
 * Credit spiny fish :3
 */
public final class FontUtils {

    private FontUtils() {}

    public static void drawTextWithGradientColor(final String text, final TTFFontRenderer renderer, final double x, final double y, float t, final float spread, final Color...colors) {
        final int length = text.length();
        int w = 0;
        final float fullSize = renderer.getWidth(text);
        for (int i = 0; i < length; ++i) {
            final char c = text.charAt(i);
            final Color color = ColorUtil.interpolateColor(t, colors);
            final String in = c + "";

            renderer.drawStringWithShadow(in, x + w, y, color.getRGB());

            w += renderer.getWidth(in) * 0.9;
            t += spread / fullSize;
        }
    }

    /**
     *
     * @param text   The string to be rendered.
     * @param font   The font of the text.
     * @param x      The base x position of the rectangle.
     * @param y      The base Y position of the rectangle.
     * @param width  The width of the rectangle.
     * @param height The height of the rectangle.
     * @param color  The color of the text.
     */
    public static void drawTextInsideRectangle(
            final String text,
            final TTFFontRenderer font,
            final double x,
            final double y,
            final double width,
            final double height,
            final Color color
    ) {
        final String[] subText = text.split(" ");
        final float stringHeight = font.getHeight("H");

        float xOffset = 0;
        float yOffset = 0;
        for (final String sub : subText) {
            final float textWidth = font.getWidth(sub);

            if (xOffset + textWidth > 100) {
                xOffset = 0;
                yOffset += stringHeight;
            }

            font.drawStringWithShadow(sub, 5 + xOffset, y + 5 + yOffset, -1);

            xOffset += textWidth;
            if (xOffset > 100) {
                xOffset = 0;
                yOffset += stringHeight;
            }
        }
    }
}
