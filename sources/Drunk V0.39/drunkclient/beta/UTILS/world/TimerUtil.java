/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.UTILS.world;

public class TimerUtil {
    private long lastMS;
    private long currentMs;

    private long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public boolean hasReached(double milliseconds) {
        if (!((double)(this.getCurrentMS() - this.lastMS) >= milliseconds)) return false;
        return true;
    }

    public void reset() {
        this.lastMS = this.getCurrentMS();
    }

    public boolean delay(float milliSec) {
        if (!((float)(this.getTime() - this.lastMS) >= milliSec)) return false;
        return true;
    }

    public long getTime() {
        return System.nanoTime() / 1000000L;
    }

    public boolean isDelayComplete(double delay) {
        if (!((double)System.currentTimeMillis() - delay >= (double)this.lastMS)) return false;
        return true;
    }

    public boolean hasTimeElapsed(long time, boolean reset) {
        if (System.currentTimeMillis() - this.lastMS <= time) return false;
        if (!reset) return true;
        this.reset();
        return true;
    }

    public void setTime(long time) {
        this.lastMS = time;
    }

    public void setLastMS() {
        this.lastMS = System.currentTimeMillis();
    }

    public boolean hasElapsed(long milliseconds) {
        if (System.currentTimeMillis() - this.currentMs <= milliseconds) return false;
        return true;
    }

    public boolean check(float milliseconds) {
        if (!((float)this.getTime() >= milliseconds)) return false;
        return true;
    }
}

