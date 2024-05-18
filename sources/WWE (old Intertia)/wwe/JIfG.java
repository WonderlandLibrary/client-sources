package wwe;

import java.awt.*;

public class JIfG
{
    public JIfG() {
        super();
    }
    
    public static Color JzFV(final long n, final float n2) {
        final Color color = new Color((int)Long.parseLong(Integer.toHexString(Color.HSBtoRGB((System.nanoTime() + n) / 1.0E10f % 1.0f, 1.0f, 1.0f)), 16));
        return new Color(color.getRed() / 255.0f * n2, color.getGreen() / 255.0f * n2, color.getBlue() / 255.0f * n2, color.getAlpha() / 255.0f);
    }
    
    public static int HGwC(final int n) {
        return Color.getHSBColor((float)(Math.ceil((System.currentTimeMillis() + n) / 20.0) % 360.0 / 360.0), 0.8f, 0.7f).getRGB();
    }
    
    public static int aPMj(final int n, final int n2) {
        return Color.getHSBColor((System.currentTimeMillis() + n2) % n / (float)n, 1.0f, 1.0f).getRGB();
    }
    
    public static int JKug(final int n, final int n2) {
        return Color.getHSBColor((System.currentTimeMillis() - n2) % n / (float)n, 1.0f, 1.0f).getRGB();
    }
}
