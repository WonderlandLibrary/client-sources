/*
 * Decompiled with CFR 0.150.
 */
package skizzle.util;

public class TimeHelper {
    public long lastMS = (long)162739465 ^ 0x9B33509L;

    public boolean hasReached(long Nigga) {
        TimeHelper Nigga2;
        return Nigga2.getCurrentMS() - Nigga2.lastMS >= Nigga;
    }

    public int convertToMS(int Nigga) {
        return 1000 / Nigga;
    }

    public void reset() {
        TimeHelper Nigga;
        Nigga.lastMS = Nigga.getCurrentMS();
    }

    public static {
        throw throwable;
    }

    public long getDelay() {
        TimeHelper Nigga;
        return System.currentTimeMillis() - Nigga.lastMS;
    }

    public void setLastMS(long Nigga) {
        Nigga.lastMS = Nigga;
    }

    public TimeHelper() {
        TimeHelper Nigga;
    }

    public void setLastMS() {
        Nigga.lastMS = System.currentTimeMillis();
    }

    public boolean hasTimeReached(long Nigga) {
        TimeHelper Nigga2;
        return System.currentTimeMillis() - Nigga2.lastMS >= Nigga;
    }

    public long getCurrentMS() {
        return System.nanoTime() / ((long)388472518 ^ 0x1728DC86L);
    }
}

