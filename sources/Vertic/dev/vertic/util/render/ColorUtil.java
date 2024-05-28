package dev.vertic.util.render;

import java.awt.*;

public class ColorUtil {

    public static Color getColor(Color color1, Color color2, long ms, int offset) {
        double scale = (((System.currentTimeMillis() + offset) % ms) / (double) ms) * 2;
        double finalScale = scale > 1 ? 2 - scale : scale;
        return getGradient(color1, color2, finalScale);
    }

    public static Color getColor(Color color1, Color color2, Color color3, long ms, int offset) {
        double scale = (((System.currentTimeMillis() + offset) % ms) / (double) ms) * 3;
        if(scale > 2) {
            return getGradient(color3, color1, scale - 2);
        } else if(scale > 1) {
            return getGradient(color2, color3, scale - 1);
        } else {
            return getGradient(color1, color2, scale);
        }
    }

    public static Color getGradient(Color color1, Color color2, double scale) {
        scale = Math.max(0, Math.min(1, scale));
        return new Color((int) (color1.getRed() + (color2.getRed() - color1.getRed()) * scale),
                (int) (color1.getGreen() + (color2.getGreen() - color1.getGreen()) * scale),
                (int) (color1.getBlue() + (color2.getBlue() - color1.getBlue()) * scale));
    }

    /*
    * Skidded from java.awt.Color
    * lmfao
    * */
    public static Color brighter(final Color color, final float FACTOR) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int alpha = color.getAlpha();
        int i = (int)(1.0/(1.0-FACTOR));
        if ( r == 0 && g == 0 && b == 0) {
            return new Color(i, i, i, alpha);
        }
        if ( r > 0 && r < i ) r = i;
        if ( g > 0 && g < i ) g = i;
        if ( b > 0 && b < i ) b = i;
        return new Color(Math.min((int)(r/FACTOR), 255),
                Math.min((int)(g/FACTOR), 255),
                Math.min((int)(b/FACTOR), 255),
                alpha);
    }
    public static Color darker(final Color color, final float FACTOR) {
        return new Color(Math.max((int)(color.getRed()  *FACTOR), 0),
                Math.max((int)(color.getGreen()*FACTOR), 0),
                Math.max((int)(color.getBlue() *FACTOR), 0),
                color.getAlpha());
    }

}
