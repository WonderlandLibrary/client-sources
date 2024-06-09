// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.utils.visual;

import java.awt.Color;

public class ColorUtils
{
    private static final /* synthetic */ int[] llIllIII;
    
    public static int color(final float lIllIllIllIlllI, final float lIllIllIlllIIIl, final float lIllIllIllIllII, final float lIllIllIllIllll) {
        return new Color(lIllIllIllIlllI, lIllIllIlllIIIl, lIllIllIllIllII, lIllIllIllIllll).getRGB();
    }
    
    public static int getColor(final int lIllIllIlIllIll, final int lIllIllIlIlIlll, final int lIllIllIlIllIIl) {
        return ColorUtils.llIllIII[3] | lIllIllIlIllIll << ColorUtils.llIllIII[0] | lIllIllIlIlIlll << ColorUtils.llIllIII[2] | lIllIllIlIllIIl;
    }
    
    public static Color rainbow() {
        final long lIllIlllIIIllII = 999999999999L;
        final float lIllIlllIIIlIll = 1.0f;
        final float lIllIlllIIIlIlI = (System.nanoTime() + lIllIlllIIIllII) / 1.0E10f % 1.0f;
        final long lIllIlllIIIlIIl = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(lIllIlllIIIlIlI, 1.0f, 1.0f)), ColorUtils.llIllIII[0]);
        final Color lIllIlllIIIlIII = new Color((int)lIllIlllIIIlIIl);
        return new Color(lIllIlllIIIlIII.getRed() / 255.0f * lIllIlllIIIlIll, lIllIlllIIIlIII.getGreen() / 255.0f * lIllIlllIIIlIll, lIllIlllIIIlIII.getBlue() / 255.0f * lIllIlllIIIlIll, lIllIlllIIIlIII.getAlpha() / 255.0f);
    }
    
    static {
        lIlIIIIIIl();
    }
    
    private static void lIlIIIIIIl() {
        (llIllIII = new int[4])[0] = (0x3F ^ 0x17 ^ (0x9C ^ 0xA4));
        ColorUtils.llIllIII[1] = (0x9D ^ 0x85);
        ColorUtils.llIllIII[2] = (0x37 ^ 0x3F);
        ColorUtils.llIllIII[3] = -(-(0xFFFFFE9F & 0x63E5) & (0xFFFFFBF7 & 0x100668C));
    }
    
    public static int getColor(final int lIllIllIllIIllI, final int lIllIllIllIIlIl, final int lIllIllIllIIIII, final int lIllIllIllIIIll) {
        return lIllIllIllIIllI << ColorUtils.llIllIII[1] | lIllIllIllIIlIl << ColorUtils.llIllIII[0] | lIllIllIllIIIII << ColorUtils.llIllIII[2] | lIllIllIllIIIll;
    }
    
    public static int color(final int lIllIllIllllllI, final int lIllIllIllllIIl, final int lIllIllIllllIII, final int lIllIllIlllIlll) {
        return new Color(lIllIllIllllllI, lIllIllIllllIIl, lIllIllIllllIII, lIllIllIlllIlll).getRGB();
    }
}
