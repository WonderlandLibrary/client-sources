/*
 * Decompiled with CFR 0.150.
 */
package skizzle.util;

public class Timer {
    public long lastNano;
    public long lastMS = System.currentTimeMillis();

    public boolean hasNanoElapsedMili(long Nigga, boolean Nigga2) {
        Timer Nigga3;
        if ((float)(System.nanoTime() - Nigga3.lastNano) > (float)Nigga * Float.intBitsToFloat(9.1199046E8f ^ 0x7F2FFAD0)) {
            if (Nigga2) {
                Nigga3.reset();
            }
            return true;
        }
        return false;
    }

    public boolean hasTimeElapsed(long Nigga, boolean Nigga2) {
        Timer Nigga3;
        if (System.currentTimeMillis() - Nigga3.lastMS > Nigga) {
            if (Nigga2) {
                Nigga3.reset();
            }
            return true;
        }
        return false;
    }

    public static {
        throw throwable;
    }

    public int getDelay() {
        Timer Nigga;
        return (int)(System.currentTimeMillis() - Nigga.lastMS);
    }

    public double getNanoInMiliDelay() {
        Timer Nigga;
        return (float)(System.nanoTime() - Nigga.lastNano) / Float.intBitsToFloat(9.3812538E8f ^ 0x7E9E8C5B);
    }

    public Timer() {
        Timer Nigga;
        Nigga.lastNano = System.nanoTime();
    }

    public void reset() {
        Nigga.lastMS = System.currentTimeMillis();
    }

    public boolean hasNanoElapsed(long Nigga, boolean Nigga2) {
        Timer Nigga3;
        if (System.nanoTime() - Nigga3.lastNano > Nigga) {
            if (Nigga2) {
                Nigga3.reset();
            }
            return true;
        }
        return false;
    }

    public long getNanoDelay() {
        Timer Nigga;
        return System.nanoTime() - Nigga.lastNano;
    }

    public void resetNano() {
        Nigga.lastNano = System.nanoTime();
    }
}

