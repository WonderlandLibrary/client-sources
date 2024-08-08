package me.xatzdevelopments.util;

public class HTimer {
    public long lastMS = System.currentTimeMillis();

    public void reset() {
        this.lastMS = System.currentTimeMillis();
    }

    public boolean hasTimedElapsed(long l, boolean bl) {
        if (System.currentTimeMillis() - this.lastMS > l) {
            if (bl) {
                this.reset();
            }
            return true;
        }
        return false;
    }
}
