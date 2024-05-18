// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.utils;

import java.math.RoundingMode;
import java.math.BigDecimal;

public class MathUtils
{
    private static final /* synthetic */ int[] llllIII;
    
    private static void llIIIIllI() {
        (llllIII = new int[1])[0] = "  ".length();
    }
    
    static {
        llIIIIllI();
    }
    
    private static int llIIIlIII(final float n, final float n2) {
        return fcmpl(n, n2);
    }
    
    public static double getMiddleDouble(final int llIlIIlIIlIIlII, final int llIlIIlIIlIIIll) {
        return (llIlIIlIIlIIlII + (double)llIlIIlIIlIIIll) / 2.0;
    }
    
    public static float getAngleDifference(final float llIlIIlIIIIllll, final float llIlIIlIIIlIIlI) {
        final float llIlIIlIIIlIIIl = Math.abs(llIlIIlIIIlIIlI - llIlIIlIIIIllll) % 360.0f;
        float n;
        if (llIIIlIIl(llIIIlIII(llIlIIlIIIlIIIl, 180.0f))) {
            n = 360.0f - llIlIIlIIIlIIIl;
            "".length();
            if ("   ".length() <= 0) {
                return 0.0f;
            }
        }
        else {
            n = llIlIIlIIIlIIIl;
        }
        final float llIlIIlIIIlIIII = n;
        return llIlIIlIIIlIIII;
    }
    
    public static int getMiddle(final int llIlIIlIIlIlIII, final int llIlIIlIIlIIlll) {
        return (llIlIIlIIlIlIII + llIlIIlIIlIIlll) / MathUtils.llllIII[0];
    }
    
    public static double round(final double llIlIIlIIIllIlI, final int llIlIIlIIIlllII) {
        if (llIIIIlll(llIlIIlIIIlllII)) {
            throw new IllegalArgumentException();
        }
        BigDecimal llIlIIlIIIllIll = new BigDecimal(llIlIIlIIIllIlI);
        llIlIIlIIIllIll = llIlIIlIIIllIll.setScale(llIlIIlIIIlllII, RoundingMode.HALF_UP);
        return llIlIIlIIIllIll.doubleValue();
    }
    
    private static boolean llIIIlIIl(final int llIlIIlIIIIlIII) {
        return llIlIIlIIIIlIII > 0;
    }
    
    private static boolean llIIIIlll(final int llIlIIlIIIIlIlI) {
        return llIlIIlIIIIlIlI < 0;
    }
}
