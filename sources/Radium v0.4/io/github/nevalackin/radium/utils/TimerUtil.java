package io.github.nevalackin.radium.utils;

public final class TimerUtil {

    private long currentMs;

    public TimerUtil() {
        reset();
    }

    public long getCurrentMs() {
        return currentMs;
    }

    public boolean hasElapsed(long milliseconds) {
        return (System.currentTimeMillis() - currentMs) > milliseconds;
    }

    public void reset() {
        currentMs = System.currentTimeMillis();
    }

}
