// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.utils;

import intent.AquaDev.aqua.Aqua;
import java.awt.Color;

public class ColorUtil
{
    public static Color getClickGUIColor() {
        if (Aqua.setmgr == null) {
            return Color.white;
        }
        return new Color(Aqua.setmgr.getSetting("HUDColor").getColor());
    }
    
    public static int[] getRGB(final int hex) {
        final int a = hex >> 24 & 0xFF;
        final int r = hex >> 16 & 0xFF;
        final int g = hex >> 8 & 0xFF;
        final int b = hex & 0xFF;
        return new int[] { r, g, b, a };
    }
}
