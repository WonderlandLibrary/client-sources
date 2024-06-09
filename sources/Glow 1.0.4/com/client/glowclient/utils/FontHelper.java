package com.client.glowclient.utils;

import net.minecraft.client.gui.*;

public class FontHelper
{
    public FontHelper() {
        super();
    }
    
    public static void drawRightJustifiedStringWithShadow(final FontRenderer fontRenderer, final String s, final int n, final int n2, final int n3) {
        fontRenderer.drawStringWithShadow(s, (float)(n - fontRenderer.getStringWidth(s)), (float)n2, n3);
    }
    
    public static void drawCenteredStringWithShadow(final FontRenderer fontRenderer, final String s, final int n, final int n2, final int n3) {
        fontRenderer.drawStringWithShadow(s, (float)(n - fontRenderer.getStringWidth(s) / 2), (float)n2, n3);
    }
    
    public static void drawLeftJustifiedStringWithShadow(final FontRenderer fontRenderer, final String s, final int n, final int n2, final int n3) {
        fontRenderer.drawStringWithShadow(s, (float)n, (float)n2, n3);
    }
}
