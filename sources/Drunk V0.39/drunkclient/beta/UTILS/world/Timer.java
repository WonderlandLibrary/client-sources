/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.UTILS.world;

public class Timer {
    public long MS = System.currentTimeMillis();

    public void reset() {
        this.MS = System.currentTimeMillis();
    }

    public boolean hasElapsed(long time, boolean reset) {
        if (System.currentTimeMillis() - this.MS <= time) return false;
        if (!reset) return true;
        this.reset();
        return true;
    }

    public boolean hasReached(double milliseconds) {
        if (!((double)(this.getCurrentTime() - this.MS) >= milliseconds)) return false;
        return true;
    }

    public boolean reach(long milliseconds) {
        if (System.currentTimeMillis() - this.MS < milliseconds) return false;
        return true;
    }

    public boolean reach(double milliseconds) {
        if (!((double)(System.currentTimeMillis() - this.MS) >= milliseconds)) return false;
        return true;
    }

    public boolean hasTimePassed(long ms) {
        if (System.currentTimeMillis() < this.MS + ms) return false;
        return true;
    }

    public long hasTimeLeft(long ms) {
        return ms + this.MS - System.currentTimeMillis();
    }

    public boolean check(float milliseconds) {
        if (!((float)this.getTime() >= milliseconds)) return false;
        return true;
    }

    public long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public long getTime() {
        return this.getCurrentTime() - this.MS;
    }
}

