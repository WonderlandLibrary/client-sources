package me.darkmagician6.morbid.util.font;

import org.lwjgl.opengl.*;
import me.darkmagician6.morbid.*;

public class TTF
{
    public static void drawTTFString(final String s, final double x, final double y, final int color) {
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        Morbid.chatFont.drawGoodString(MorbidWrapper.mcObj().w, lf.a(s), x + 0.5, y + 0.5, 0);
        Morbid.chatFont.drawGoodString(MorbidWrapper.mcObj().w, s, x, y, color);
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
    }
}
