package me.xatzdevelopments.util;

import java.awt.Color;

public class ColourUtil
{
    public static int getRainbow() {
        final float hue = System.currentTimeMillis() % 10000L / 10000.0f;
        final int RainbowColour = Color.HSBtoRGB(hue, 0.4f, 1.0f);
        return RainbowColour;
    }
    
    public static int getRainbowCustom(final float hueoffset, final float saturation, final float brightness) {
        final float hue = System.currentTimeMillis() % 4500L / 4500.0f;
        final int RainbowColour = Color.HSBtoRGB(hue - hueoffset / 40.0f, saturation, brightness);
        return RainbowColour;
    }
    
    public static int getColourCustom(final float hue, final float saturation, final float brightness) {
        final int RainbowColour = Color.HSBtoRGB(hue, saturation, brightness);
        return RainbowColour;
    }
}
