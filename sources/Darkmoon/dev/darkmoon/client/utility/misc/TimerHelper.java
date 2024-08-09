package dev.darkmoon.client.utility.misc;

public class TimerHelper {
    private long lastMS = -1L;

    public TimerHelper() {
        this.lastMS = System.currentTimeMillis();
    }

    public boolean hasReached(double delay) {
        return System.currentTimeMillis() - this.lastMS >= delay;
    }

    public boolean hasReached(boolean active, double delay) {
        return active || hasReached(delay);
    }

    public long getLastMS() {
        return lastMS;
    }

    public boolean passedMs(long ms) {
        return this.getMs(System.nanoTime() - this.lastMS) >= ms;
    }
    public long getMs(long time) {
        return time / 1000000L;
    }
    public void reset() {
        this.lastMS = System.currentTimeMillis();
    }
    public boolean hasTimeElapsed(double time) {
        return hasTimeElapsed((long) time);
    }

    public boolean hasTimeElapsed(long time, boolean reset) {
        if (System.currentTimeMillis() - lastMS > time) {
            if (reset) reset();
            return true;
        }

        return false;
    }
    public long getTimePassed() {
        return System.currentTimeMillis() - lastMS;
    }

    public long getCurrentTime() {
        return System.nanoTime() / 1000000L;
    }

    public void setTime(long time) {
        lastMS = time;
    }
}
