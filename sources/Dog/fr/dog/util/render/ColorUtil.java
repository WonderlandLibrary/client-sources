package fr.dog.util.render;

import fr.dog.util.math.MathsUtil;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ColorUtil {
    public static float[] toGLColor(final int color) {
        return new float[] {
            (float) (color >> 16 & 255) / 255.0F,
            (float) (color >> 8 & 255) / 255.0F,
            (float) (color & 255) / 255.0F,
            (float) (color >> 24 & 255) / 255.0F
        };
    }

    public static float[] toGLColor(final Color color) {
        return new float[] {
                color.getRed() / 255.0F,
                color.getGreen() / 255.0F,
                color.getBlue() / 255.0F,
                color.getAlpha() / 255.0F
        };
    }

    public static Color getColorFromIndex(int speed, int index, Color start, Color end, boolean trueColor) {
        int angle = (int) (((System.currentTimeMillis()) / speed + index) % 360);
        angle = (angle >= 180 ? 360 - angle : angle) * 2;
        return interpolateColorC(start, end, angle / 360f);
    }

    public static Color toColor(float[] colors){
        return new Color(colors[0], colors[1], colors[2], colors[3]);
    }

    public static Color getRainbow(float seconds, float saturation, float brightness, long index) {
        float hue = ((System.currentTimeMillis() + index) % (int) (seconds * 1000)) / (seconds * 1000);
        int color = Color.HSBtoRGB(hue, saturation, brightness);
        return new Color(color);
    }

    public static Color interpolateColorC(Color color1, Color color2, float amount) {
        amount = Math.min(1, Math.max(0, amount));
        return new Color(interpolate(color1.getRed(), color2.getRed(), amount),
                interpolate(color1.getGreen(), color2.getGreen(), amount),
                interpolate(color1.getBlue(), color2.getBlue(), amount),
                interpolate(color1.getAlpha(), color2.getAlpha(), amount));
    }

    private static int interpolate(int oldValue, int newValue, float interpolationValue) {
        return (int) (oldValue + (newValue - oldValue) * interpolationValue);
    }

    public static Color getRGB(float seconds, float saturation, float brightness, long index) {
        float hue = ((System.currentTimeMillis() + index) % (int) (seconds * 1000)) / (seconds * 1000);
        int color = Color.HSBtoRGB(hue, saturation, brightness);
        return new Color(color);
    }

    public static void setColor(Color color) {
        float alpha = (color.getRGB() >> 24 & 0xFF) / 255.0F;
        float red = (color.getRGB() >> 16 & 0xFF) / 255.0F;
        float green = (color.getRGB() >> 8 & 0xFF) / 255.0F;
        float blue = (color.getRGB() & 0xFF) / 255.0F;
        GL11.glColor4f(red, green, blue, alpha);
    }
}