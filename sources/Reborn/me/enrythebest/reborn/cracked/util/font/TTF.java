package me.enrythebest.reborn.cracked.util.font;

import org.lwjgl.opengl.*;
import me.enrythebest.reborn.cracked.*;
import net.minecraft.src.*;

public class TTF
{
    public static void drawTTFString(final String var0, final double var1, final double var3, final int var5) {
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        Morbid.chatFont.drawGoodString(MorbidWrapper.mcObj().ingameGUI, StringUtils.stripControlCodes(var0), var1 + 0.5, var3 + 0.5, 0);
        Morbid.chatFont.drawGoodString(MorbidWrapper.mcObj().ingameGUI, var0, var1, var3, var5);
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
    }
}
