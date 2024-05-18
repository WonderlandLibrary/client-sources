package wwe;

import java.awt.*;
import wwe.modules.render.*;

public class KrtY
{
    public KrtY() {
        super();
    }
    
    public static Color DnMA(final long n, final float n2) {
        final float n3 = (System.nanoTime() + n) / 1.0E10f % 1.0f;
        float n4;
        if (vlwv.hNxW.qHPF((Class<ColorList>)VWdz.class).mode.OnGi().equalsIgnoreCase("Up")) {
            n4 = (System.nanoTime() - n) / 1.0E10f % 1.0f;
            if (KrtY.\u2007) {
                throw null;
            }
        }
        else {
            n4 = (System.nanoTime() + n) / 1.0E10f % 1.0f;
        }
        final Color color = new Color((int)Long.parseLong(Integer.toHexString(Color.HSBtoRGB(n4, 1.0f, 1.0f)), 16));
        return new Color(color.getRed() / 255.0f * n2, color.getGreen() / 255.0f * n2, color.getBlue() / 255.0f * n2, color.getAlpha() / 255.0f);
    }
    
    public static int jMyx(final String s) {
        try {
            if (s.equalsIgnoreCase("rainbow")) {
                return DnMA(0x7676E61C28D8B3C2L ^ 0x7676E61C28D8B3C2L, 1.0f).getRGB();
            }
            return Integer.parseInt(s, 16);
        }
        catch (NumberFormatException ex) {
            return -1;
        }
    }
    
    public static String hGYL(final int n) {
        return epAj(new Color(n));
    }
    
    public static String epAj(final Color color) {
        return Integer.toHexString(color.getRGB()).substring(2);
    }
}
