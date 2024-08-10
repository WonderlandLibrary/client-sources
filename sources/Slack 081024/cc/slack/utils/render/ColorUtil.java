package cc.slack.utils.render;

import cc.slack.utils.client.IMinecraft;
import cc.slack.start.Slack;
import cc.slack.features.modules.impl.render.Interface;

import java.awt.*;

public class ColorUtil implements IMinecraft {
    public enum themeStyles {
        ASTOLFO, SLACK, RAINBOW, SLACK_STATIC, CHRISTMAS, CUSTOM
    }


    public static Color rainbow(final int count, final float bright, final float st) {
        double v1 = Math.ceil((double)(System.currentTimeMillis() + count * 109)) / 5.0;
        return Color.getHSBColor(((float)((v1 %= 360.0) / 360.0) < 0.5) ? (-(float)(v1 / 360.0)) : ((float)(v1 / 360.0)), st, bright);
    }

    public static Color getThemeColor(themeStyles t, boolean start) {
        Interface hud = Slack.getInstance().getModuleManager().getInstance(Interface.class);
        if (start) {
            switch (t) {
                case SLACK_STATIC:
                    return new Color(90, 150, 200);
                case SLACK:
                    return new Color(59, 145, 217);
                case ASTOLFO:
                    return new Color(81, 230, 230);
                case CHRISTMAS:
                    return new Color(240, 81, 81);
                case CUSTOM:
                    return new Color(hud.r1.getValue(), hud.g1.getValue(), hud.b1.getValue());
            }
        } else {
            switch (t) {
                case SLACK_STATIC:
                    return new Color(90, 150, 200);
                case SLACK:
                    return new Color(26, 49, 83);
                case ASTOLFO:
                    return new Color(228, 99, 214);
                case CHRISTMAS:
                    return new Color(240, 240, 240);
                case CUSTOM:
                    return new Color(hud.r2.getValue(), hud.g2.getValue(), hud.b2.getValue());
            }
        }
        return new Color(1,1,1);
    }

    public static Color getColor() {
        return getColor(true);
    }

    public static Color getColor(boolean timeMove) {
        return getColor(0.0, timeMove);
    }

    public static Color getColor(double r) {
        return getColor(r, true);
    }

    public static Color getColor(double r, boolean timeMove) {
        return getColor(Slack.getInstance().getModuleManager().getInstance(Interface.class).theme.getValue(), r, timeMove);
    }

    public static Color getColor(themeStyles t, double r) {
        return getColor(t, r, true);
    }
    public static Color getColor(themeStyles t, double r, boolean timeMove) {
        if (timeMove) {
            r += System.currentTimeMillis() / 3000.0;
        }
        if (t == themeStyles.RAINBOW) {
            return rainbow(-100 + (int) (r * 10), 1.0f, 0.47f);
        }
        r = r % 2;
        if (r < 1) {
            return mix (getThemeColor(t, true), getThemeColor(t, false), r);
        } else {
            return mix (getThemeColor(t, false), getThemeColor(t, true), r - 1);
        }
    }

    public static Color mix(Color c1, Color c2, Double percent) {
        int r = (int) (c1.getRed() * (1 - percent) + c2.getRed() * percent);
        int g = (int) (c1.getGreen() * (1 - percent) + c2.getGreen() * percent);
        int b = (int) (c1.getBlue() * (1 - percent) + c2.getBlue() * percent);
        int a = (int) (c1.getAlpha() * (1 - percent) + c2.getAlpha() * percent);
        return new Color(r,g,b,a);
    }

    public static Color getHealthColor(float health, float maxHealth) {
        float[] fractions = new float[]{0.0F, 0.5F, 1.0F};
        Color[] colors = new Color[]{new Color(108, 0, 0), new Color(255, 51, 0), Color.GREEN};
        float progress = health / maxHealth;
        return blendColors(fractions, colors, progress).brighter();
    }

    public static Color blendColors(float[] fractions, Color[] colors, float progress) {
        if (fractions.length == colors.length) {
            int[] indices = getFractionIndices(fractions, progress);
            float[] range = new float[]{fractions[indices[0]], fractions[indices[1]]};
            Color[] colorRange = new Color[]{colors[indices[0]], colors[indices[1]]};
            float max = range[1] - range[0];
            float value = progress - range[0];
            float weight = value / max;
            Color color = blend(colorRange[0], colorRange[1], (double)(1.0F - weight));
            return color;
        } else {
            throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
        }
    }

    public static Color blend(Color color1, Color color2, double ratio) {
        float r = (float)ratio;
        float ir = 1.0F - r;
        float[] rgb1 = color1.getColorComponents(new float[3]);
        float[] rgb2 = color2.getColorComponents(new float[3]);
        float red = rgb1[0] * r + rgb2[0] * ir;
        float green = rgb1[1] * r + rgb2[1] * ir;
        float blue = rgb1[2] * r + rgb2[2] * ir;
        if (red < 0.0F) {
            red = 0.0F;
        } else if (red > 255.0F) {
            red = 255.0F;
        }

        if (green < 0.0F) {
            green = 0.0F;
        } else if (green > 255.0F) {
            green = 255.0F;
        }

        if (blue < 0.0F) {
            blue = 0.0F;
        } else if (blue > 255.0F) {
            blue = 255.0F;
        }

        Color color3 = null;

        try {
            color3 = new Color(red, green, blue);
        } catch (IllegalArgumentException var13) {
        }

        return color3;
    }


    public static int[] getFractionIndices(float[] fractions, float progress) {
        int[] range = new int[2];

        int startPoint;
        for(startPoint = 0; startPoint < fractions.length && fractions[startPoint] <= progress; ++startPoint) {
        }

        if (startPoint >= fractions.length) {
            startPoint = fractions.length - 1;
        }

        range[0] = startPoint - 1;
        range[1] = startPoint;
        return range;
    }
}
