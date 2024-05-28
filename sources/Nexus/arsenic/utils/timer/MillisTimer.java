package arsenic.utils.timer;

public class MillisTimer {

    private long currentMs;

    public MillisTimer() {
        reset();
    }

    public long lastReset() {
        return currentMs;
    }

    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public boolean hasElapsed(long milliseconds) {
        return elapsed() > milliseconds;
    }

    public long elapsed() {
        return System.currentTimeMillis() - currentMs;
    }

    public MillisTimer reset() {
        currentMs = System.currentTimeMillis();
        return this;
    }

}