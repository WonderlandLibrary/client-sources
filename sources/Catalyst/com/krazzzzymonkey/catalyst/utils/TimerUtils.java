// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.utils;

public class TimerUtils
{
    private /* synthetic */ long lastMS;
    private static final /* synthetic */ int[] llIlllll;
    private /* synthetic */ long prevMS;
    
    static {
        lIlIIIllII();
    }
    
    public TimerUtils() {
        this.lastMS = 0L;
        this.prevMS = 0L;
    }
    
    private static void lIlIIIllII() {
        (llIlllll = new int[3])[0] = " ".length();
        TimerUtils.llIlllll[1] = ((0x8A ^ 0x8D ^ (0x18 ^ 0x35)) & (((0xA6 ^ 0xC5) & ~(0x3F ^ 0x5C)) ^ (0x38 ^ 0x12) ^ -" ".length()));
        TimerUtils.llIlllll[2] = (0xFFFFE3FC & 0x1FEB);
    }
    
    private static int lIlIIIllll(final float n, final float n2) {
        return fcmpl(n, n2);
    }
    
    public boolean delay(final float lIllIlIllIIllll) {
        int n;
        if (lIlIIIlllI(lIlIIlIIII((float)(this.getTime() - this.prevMS), lIllIlIllIIllll))) {
            n = TimerUtils.llIlllll[0];
            "".length();
            if (null != null) {
                return ((77 + 4 + 140 + 26 ^ 25 + 61 + 22 + 76) & (0x2D ^ 0x16 ^ (0xE8 ^ 0x9C) ^ -" ".length())) != 0x0;
            }
        }
        else {
            n = TimerUtils.llIlllll[1];
        }
        return n != 0;
    }
    
    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }
    
    public void setLastMS(final long lIllIlIlllIIlll) {
        this.lastMS = lIllIlIlllIIlll;
    }
    
    private static int lIlIIlIIII(final float n, final float n2) {
        return fcmpl(n, n2);
    }
    
    public int convertToMS(final int lIllIlIllIlllll) {
        return TimerUtils.llIlllll[2] / lIllIlIllIlllll;
    }
    
    private static boolean lIlIIIlllI(final int lIllIlIllIIllII) {
        return lIllIlIllIIllII >= 0;
    }
    
    public boolean isDelay(final long lIllIlIlllIlllI) {
        if (lIlIIIlllI(lIlIIIllIl(System.currentTimeMillis() - this.lastMS, lIllIlIlllIlllI))) {
            return TimerUtils.llIlllll[0] != 0;
        }
        return TimerUtils.llIlllll[1] != 0;
    }
    
    public void reset() {
        this.lastMS = this.getCurrentMS();
    }
    
    public void setLastMS() {
        this.lastMS = System.currentTimeMillis();
    }
    
    private static int lIlIIIllIl(final long n, final long n2) {
        return lcmp(n, n2);
    }
    
    public boolean hasReached(final float lIllIlIllIllIII) {
        int n;
        if (lIlIIIlllI(lIlIIIllll((float)(this.getCurrentMS() - this.lastMS), lIllIlIllIllIII))) {
            n = TimerUtils.llIlllll[0];
            "".length();
            if ((0x70 ^ 0x6F ^ (0x52 ^ 0x49)) < 0) {
                return ((0x18 ^ 0x2F ^ (0x6F ^ 0x14)) & (27 + 67 + 17 + 144 ^ 135 + 77 - 96 + 63 ^ -" ".length())) != 0x0;
            }
        }
        else {
            n = TimerUtils.llIlllll[1];
        }
        return n != 0;
    }
    
    public long getTime() {
        return System.nanoTime() / 1000000L;
    }
}
