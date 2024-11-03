package net.augustus.utils.skid.lorious;

public class Timer {
    public long lastValue;

    public void startTimer() {
        this.lastValue = System.currentTimeMillis();
    }

    public void reset() {
        this.lastValue = System.currentTimeMillis();
    }

    public boolean timeElapsed(long time) {
        return System.currentTimeMillis() - this.lastValue > time;
    }
}
