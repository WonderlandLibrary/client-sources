/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.helpers.render;

import cc.diablo.helpers.render.RenderUtils;
import cc.diablo.module.impl.render.HUD;
import java.awt.Color;
import net.minecraft.util.MathHelper;

public class ColorHelper {
    public static int getAstolfo(int delay, float offset, float hueSetting) {
        float hue;
        float speed = (float)HUD.delay.getVal();
        for (hue = (float)(System.currentTimeMillis() % (long)delay) + offset; hue > speed; hue -= speed) {
        }
        if ((double)(hue /= speed) > 0.5) {
            hue = 0.5f - (hue - 0.5f);
        }
        return Color.HSBtoRGB(hue += hueSetting, 0.5f, 1.0f);
    }

    public static int getColor(int offset) {
        switch (HUD.colorMode.getMode()) {
            case "Astolfo": {
                return ColorHelper.getAstolfo(10000000, offset, 0.5f);
            }
            case "Wave": {
                return ColorHelper.getColorWave(new Color((int)HUD.red.getVal(), (int)HUD.green.getVal(), (int)HUD.blue.getVal()), offset).getRGB();
            }
            case "Solid": {
                return new Color((int)HUD.red.getVal(), (int)HUD.green.getVal(), (int)HUD.blue.getVal()).getRGB();
            }
            case "Rainbow": {
                return RenderUtils.getRainbow(6000, offset, 1.0f);
            }
            case "Rainbow Light": {
                return RenderUtils.getRainbow(6000, offset, 0.55f);
            }
        }
        return ColorHelper.getAstolfo(10000000, offset, 0.5f);
    }

    public static int getColor(Color color) {
        return ColorHelper.getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public static int getColor(int brightness, int alpha) {
        return ColorHelper.getColor(brightness, brightness, brightness, alpha);
    }

    public static int getColor(int red, int green, int blue) {
        return ColorHelper.getColor(red, green, blue, 255);
    }

    public static int getColor(int red, int green, int blue, int alpha) {
        int color = MathHelper.clamp_int(alpha, 0, 255) << 24;
        color |= MathHelper.clamp_int(red, 0, 255) << 16;
        color |= MathHelper.clamp_int(green, 0, 255) << 8;
        return color |= MathHelper.clamp_int(blue, 0, 255);
    }

    public static Color getAstolfoColor(int delay, float offset) {
        float hue;
        float speed = (float)HUD.delay.getVal();
        for (hue = (float)(System.currentTimeMillis() % (long)delay) + offset; hue > speed; hue -= speed) {
        }
        if ((double)(hue /= speed) > 0.5) {
            hue = 0.5f - (hue - 0.5f);
        }
        return Color.getHSBColor(hue += 0.5f, 0.5f, 1.0f);
    }

    public static Color getColorWave(Color color, float offset) {
        float hue;
        float speed = (float)HUD.delay.getVal();
        for (hue = (float)(System.currentTimeMillis() % 10000000L) + offset; hue > speed; hue -= speed) {
        }
        if ((double)(hue /= speed) > 0.5) {
            hue = 0.5f - (hue - 0.5f);
        }
        float[] colors = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        return Color.getHSBColor(colors[0], 1.0f, hue += 0.5f);
    }
}

