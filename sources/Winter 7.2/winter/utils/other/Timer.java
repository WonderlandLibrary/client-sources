/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.other;

public class Timer {
    private long prevMS = 0;

    public boolean hasPassed(float milliSec) {
        if ((float)(this.getTime() - this.prevMS) >= milliSec) {
            return true;
        }
        return false;
    }

    public void reset() {
        this.prevMS = this.getTime();
    }

    public long getTime() {
        return System.nanoTime() / 1000000;
    }

    public long getDifference() {
        return this.getTime() - this.prevMS;
    }

    public void setDifference(long difference) {
        this.prevMS = this.getTime() - difference;
    }
}

