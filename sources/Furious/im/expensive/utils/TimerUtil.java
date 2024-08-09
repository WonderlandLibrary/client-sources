package im.expensive.utils;

public class TimerUtil {
    private long lastMS;

    public TimerUtil() {
        this.reset();
    }

    public boolean hasReached(double delay) {
        return this.getTime() >= delay;
    }

    public long getTime() {
        return System.currentTimeMillis() - this.lastMS;
    }

    public void reset() {
        this.lastMS = System.currentTimeMillis();
    }

    public boolean hasTimeElapsed(long milliseconds) {
        return System.currentTimeMillis() - lastMS >= milliseconds;
    }
}