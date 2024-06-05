package me.dinozoid.strife.util.system;

public final class TimerUtil {

    private long currentMS;

    public TimerUtil() {
        reset();
    }

    public long lastReset() {
        return currentMS;
    }

    public boolean hasElapsed(long milliseconds) {
        return elapsed() > milliseconds;
    }

    public long elapsed() {
        return System.currentTimeMillis() - currentMS;
    }

    public void reset() {
        currentMS = System.currentTimeMillis();
    }
}
