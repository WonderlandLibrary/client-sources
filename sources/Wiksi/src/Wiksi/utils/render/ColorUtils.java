package src.Wiksi.utils.render;

import lombok.experimental.UtilityClass;
import net.minecraft.util.math.MathHelper;

import com.mojang.blaze3d.systems.RenderSystem;

import src.Wiksi.functions.impl.render.HUD;
import src.Wiksi.utils.math.MathUtil;

import java.awt.*;

@UtilityClass
public class ColorUtils {
    public int multAlpha(int c, float apc) {
        return getColor(red(c), green(c), blue(c), (float) alpha(c) * apc);
    }

    public int getColor(float r, float g, float b, float a) {
        return new Color((int) r, (int) g, (int) b, (int) a).getRGB();
    }
    public int replAlpha(int c, int a) {
        return getColor(red(c), green(c), blue(c), a);
    }
    public int multDark(int c, float brpc) {
        return getColor((float) red(c) * brpc, (float) green(c) * brpc, (float) blue(c) * brpc, (float) alpha(c));
    }

    public int overCol(int c1, int c2, float pc01) {
        return getColor((float) red(c1) * (1 - pc01) + (float) red(c2) * pc01, (float) green(c1) * (1 - pc01) + (float) green(c2) * pc01, (float) blue(c1) * (1 - pc01) + (float) blue(c2) * pc01, (float) alpha(c1) * (1 - pc01) + (float) alpha(c2) * pc01);
    }

    public int overCol(int c1, int c2) {
        return overCol(c1, c2, 0.5f);
    }


    public static int setAlpha(int color, int alpha) {
        return (color & 0x00ffffff) | (alpha << 24);
    }

    public static int red(int c) {
        return c >> 16 & 255;
    }

    public static int green(int c) {
        return c >> 8 & 255;
    }

    public static int blue(int c) {
        return c & 255;
    }

    public static int alpha(int c) {
        return c >> 24 & 255;
    }

    public static float redf(int c) {
        return (float)red(c) / 255.0F;
    }

    public static float greenf(int c) {
        return (float)green(c) / 255.0F;
    }

    public static float bluef(int c) {
        return (float)blue(c) / 255.0F;
    }

    public static float alphaf(int c) {
        return (float)alpha(c) / 255.0F;
    }

    public static int[] getRGBA(int c) {
        return new int[]{red(c), green(c), blue(c), alpha(c)};
    }

    public static float[] getRGBAf(int c) {
        return new float[]{(float)red(c) / 255.0F, (float)green(c) / 255.0F, (float)blue(c) / 255.0F, (float)alpha(c) / 255.0F};
    }

    public static int white = new Color(255, 255, 255).getRGB();
    public final int green = new Color(64, 255, 64).getRGB();
    public final int yellow = new Color(255, 255, 64).getRGB();
    public final int orange = new Color(255, 128, 32).getRGB();
    public final int red = new Color(255, 64, 64).getRGB();

    public static int rgb(int r, int g, int b) {
        return 255 << 24 | r << 16 | g << 8 | b;
    }

    public static int rgba(int r, int g, int b, int a) {
        return a << 24 | r << 16 | g << 8 | b;
    }
    public static int getColor(int r, int g, int b, int a) {
        return (new Color(r, g, b, a)).getRGB();
    }

    public static int getColor(int r, int g, int b) {
        return (new Color(r, g, b, 255)).getRGB();
    }

    public static int getColor(int br, int a) {
        return (new Color(br, br, br, a)).getRGB();
    }


    public static int gradient4(int color1, int color2, int color3, int color4, int index, int duration) {
        int cycle = 20 * duration;
        long currentTime = System.currentTimeMillis();
        int position = (int)((currentTime / 100L + (long)index) % (long)cycle);
        float segment = (float)cycle / 4.0F;
        float phase = (float)position % segment;
        float localRatio = smoothStep(0.0F, 1.0F, phase / segment);
        int startColor;
        int endColor;
        if ((float)position < segment) {
            startColor = color1;
            endColor = color2;
        } else if ((float)position < segment * 2.0F) {
            startColor = color2;
            endColor = color3;
        } else if ((float)position < segment * 3.0F) {
            startColor = color3;
            endColor = color4;
        } else {
            startColor = color4;
            endColor = color1;
        }

        return interpolate5(startColor, endColor, localRatio);
    }
    public static float smoothStep(float edge0, float edge1, float x) {
        x = clamp((x - edge0) / (edge1 - edge0), 0.0F, 1.0F);
        return x * x * x * (x * (x * 6.0F - 15.0F) + 10.0F);
    }
    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
    private static int interpolate5(int colorA, int colorB, float ratio) {
        int a = colorA >> 24 & 255;
        int r = colorA >> 16 & 255;
        int g = colorA >> 8 & 255;
        int b = colorA & 255;
        int a2 = colorB >> 24 & 255;
        int r2 = colorB >> 16 & 255;
        int g2 = colorB >> 8 & 255;
        int b2 = colorB & 255;
        int newA = (int)((float)a + (float)(a2 - a) * ratio);
        int newR = (int)((float)r + (float)(r2 - r) * ratio);
        int newG = (int)((float)g + (float)(g2 - g) * ratio);
        int newB = (int)((float)b + (float)(b2 - b) * ratio);
        return newA << 24 | newR << 16 | newG << 8 | newB;
    }





    public static void setAlphaColor(final int color, final float alpha) {
        final float red = (float) (color >> 16 & 255) / 255.0F;
        final float green = (float) (color >> 8 & 255) / 255.0F;
        final float blue = (float) (color & 255) / 255.0F;
        RenderSystem.color4f(red, green, blue, alpha);
    }

    public static int getColor(int index) {
        return HUD.getColor(index);
    }

    public static void setColor(int color) {
        setAlphaColor(color, (float) (color >> 24 & 255) / 255.0F);
    }

    public static int toColor(String hexColor) {
        int argb = Integer.parseInt(hexColor.substring(1), 16);
        return setAlpha(argb, 255);
    }



    public static float[] rgba(final int color) {
        return new float[] {
                (color >> 16 & 0xFF) / 255f,
                (color >> 8 & 0xFF) / 255f,
                (color & 0xFF) / 255f,
                (color >> 24 & 0xFF) / 255f
        };
    }

    public static int gradient(int start, int end, int index, int speed) {
        int angle = (int) ((System.currentTimeMillis() / speed + index) % 360);
        angle = (angle > 180 ? 360 - angle : angle) + 180;
        int color = interpolate(start, end, MathHelper.clamp(angle / 180f - 1, 0, 1));
        float[] hs = rgba(color);
        float[] hsb = Color.RGBtoHSB((int) (hs[0] * 255), (int) (hs[1] * 255), (int) (hs[2] * 255), null);

        hsb[1] *= 1.5F;
        hsb[1] = Math.min(hsb[1], 1.0f);

        return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
    }

    public static int interpolate(int start, int end, float value) {
        float[] startColor = rgba(start);
        float[] endColor = rgba(end);

        return rgba((int) MathUtil.interpolate(startColor[0] * 255, endColor[0] * 255, value),
                (int) MathUtil.interpolate(startColor[1] * 255, endColor[1] * 255, value),
                (int) MathUtil.interpolate(startColor[2] * 255, endColor[2] * 255, value),
                (int) MathUtil.interpolate(startColor[3] * 255, endColor[3] * 255, value));
    }

    public static void setAlphaColor(Object gradient, int color, float v) {
    }
}
