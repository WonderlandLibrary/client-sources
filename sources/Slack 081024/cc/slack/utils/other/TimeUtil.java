package cc.slack.utils.other;

public class TimeUtil {

    public long currentMs;

    public TimeUtil() {
        reset();
    }

    public boolean hasReached(int milliseconds) {
        return elapsed() >= (long) milliseconds;
    }

    public boolean hasReached(long milliseconds) {
        return elapsed() >= milliseconds;
    }

    public void resetWithOffset(long offset) {
        this.currentMs = this.getTime() + offset;
    }

    public long elapsed() {
        return System.currentTimeMillis() - currentMs;
    }

    public void reset() {
        currentMs = System.currentTimeMillis();
    }
    public boolean isDelayComplete(float delay) {
        return (float)(System.currentTimeMillis() - this.currentMs) > delay;
    }

    private long getTime() {
        return System.nanoTime() / 1000000L;
    }

}
