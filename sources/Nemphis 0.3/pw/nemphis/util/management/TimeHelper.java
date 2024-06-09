/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.util.management;

public class TimeHelper {
    private long ms = 0;

    public long getCurrentMS() {
        return System.nanoTime() / 1000000;
    }

    public boolean hasReached(float f) {
        if ((float)(this.getCurrentMS() - this.ms) >= f) {
            return true;
        }
        return false;
    }

    public void resetTimer() {
        this.ms = this.getCurrentMS();
    }
}

