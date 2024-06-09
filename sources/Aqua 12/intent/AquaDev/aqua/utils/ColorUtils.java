// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.utils;

import net.minecraft.util.MathHelper;
import java.awt.Color;

public class ColorUtils
{
    public static Color getColorAlpha(final Color color, final int alpha) {
        return getColorAlpha(color.getRGB(), alpha);
    }
    
    public static Color getColorAlpha(final int color, final int alpha) {
        final Color color2 = new Color(new Color(color).getRed(), new Color(color).getGreen(), new Color(color).getBlue(), alpha);
        return color2;
    }
    
    public static int getColor(final int red, final int green, final int blue, final int alpha) {
        int color = MathHelper.clamp_int(alpha, 0, 255) << 24;
        color |= MathHelper.clamp_int(red, 0, 255) << 16;
        color |= MathHelper.clamp_int(green, 0, 255) << 8;
        color |= MathHelper.clamp_int(blue, 0, 255);
        return color;
    }
}
