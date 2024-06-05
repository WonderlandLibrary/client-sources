/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.helpers;

import cc.diablo.helpers.MathHelper;

public final class TimerUtil {
    private long time = System.nanoTime() / 1000000L;
    private final long prevMS = 0L;

    public boolean reach(long time) {
        return this.time() >= time;
    }

    public void reset() {
        this.time = System.nanoTime() / 1000000L;
    }

    public boolean sleep(long time) {
        if (this.time() >= time) {
            this.reset();
            return true;
        }
        return false;
    }

    public short convertToMS(float perSecond) {
        return (short)(1000.0f / perSecond);
    }

    public long time() {
        return System.nanoTime() / 1000000L - this.time;
    }

    public boolean delay(float milliSec) {
        return (float)MathHelper.getIncremental(this.time() - this.prevMS, 50.0) >= milliSec;
    }
}

