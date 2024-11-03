package net.augustus.utils;

import net.augustus.utils.interfaces.MC;

import java.awt.Color;

public class ColorUtil {
    private float current = 0.0F;

    private boolean reached = false;

    public int getHue(double paramDouble) {
        double d = 1.0D - paramDouble / 360.0D;
        return (int)Long.parseLong(Integer.toHexString(Color.HSBtoRGB((float)d, 1.0F, 1.0F)), 16);
    }

    public int getHue(double paramDouble, float paramFloat1, float paramFloat2) {
        double d = 1.0D - paramDouble / 360.0D;
        return (int)Long.parseLong(Integer.toHexString(Color.HSBtoRGB((float)d, paramFloat1, paramFloat2)), 16);
    }

    public Color getRainbowColor(int paramInt1, int paramInt2, float paramFloat) {
        double d = Math.ceil(((System.currentTimeMillis() + paramInt1) / paramInt2));
        d %= 100.0D;
        return new Color(Color.HSBtoRGB((float)d / 100.0F, 1.0F, paramFloat));
    }

    public Color getSaturationFadeColor(Color paramColor, int paramInt, float paramFloat) {
        float[] arrayOfFloat = new float[3];
        Color.RGBtoHSB(paramColor.getRed(), paramColor.getGreen(), paramColor.getBlue(), arrayOfFloat);
        return new Color(Color.HSBtoRGB(arrayOfFloat[0], arrayOfFloat[1], (float)((float)(Math.sin(paramInt * 0.5D - ((MC.mc).thePlayer.ticksExisted) * 0.22F) * 0.5D + 0.5D) * 0.6D + 0.4D)));
    }
}
