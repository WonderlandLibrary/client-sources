/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.client;

public class TimerUtil {
    private long lastMS;

    public TimerUtil() {
        this.reset();
    }

    public boolean hasReached(double d) {
        return (double)this.getTime() >= d;
    }

    public long getTime() {
        return System.currentTimeMillis() - this.lastMS;
    }

    public void reset() {
        this.lastMS = System.currentTimeMillis();
    }

    public boolean hasTimeElapsed(long l) {
        return System.currentTimeMillis() - this.lastMS >= l;
    }
}

