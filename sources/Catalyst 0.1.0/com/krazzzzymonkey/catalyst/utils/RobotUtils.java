// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.utils;

import java.awt.AWTException;
import java.awt.Robot;

public class RobotUtils
{
    private static final /* synthetic */ int[] lIIIlll;
    
    private static boolean llllIIIl(final int llIlllllIlllIll) {
        return llIlllllIlllIll == 0;
    }
    
    static {
        llllIIII();
    }
    
    public static void clickMouse(final int llIllllllIIIIlI) {
        try {
            final Robot llIllllllIIIlIl = new Robot();
            if (llllIIIl(llIllllllIIIIlI)) {
                llIllllllIIIlIl.mousePress(RobotUtils.lIIIlll[0]);
                llIllllllIIIlIl.mouseRelease(RobotUtils.lIIIlll[0]);
                "".length();
                if (-" ".length() >= 0) {
                    return;
                }
            }
            else {
                if (!llllIIlI(llIllllllIIIIlI, RobotUtils.lIIIlll[1])) {
                    return;
                }
                llIllllllIIIlIl.mousePress(RobotUtils.lIIIlll[2]);
                llIllllllIIIlIl.mouseRelease(RobotUtils.lIIIlll[2]);
                "".length();
                if ("  ".length() != "  ".length()) {
                    return;
                }
            }
            "".length();
            if (" ".length() < " ".length()) {
                return;
            }
        }
        catch (AWTException llIllllllIIIlII) {
            llIllllllIIIlII.printStackTrace();
        }
    }
    
    private static void llllIIII() {
        (lIIIlll = new int[3])[0] = (0x5F ^ 0xA ^ (0x23 ^ 0x66));
        RobotUtils.lIIIlll[1] = " ".length();
        RobotUtils.lIIIlll[2] = (-(0xFFFFCB89 & 0x74FE) & (0xFFFFF6AF & 0x59D7));
    }
    
    private static boolean llllIIlI(final int llIlllllIlllllI, final int llIlllllIllllIl) {
        return llIlllllIlllllI == llIlllllIllllIl;
    }
}
