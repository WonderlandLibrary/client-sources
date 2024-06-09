package me.Emir.Karaguc.utils;

import java.util.concurrent.TimeUnit;

public class KaragucTimer {
    private long lastMS;

    public KaragucTimer() {
        lastMS = getCurrentMS();
    }

    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public void setLastMS() {
        this.lastMS = System.currentTimeMillis();
    }

    public boolean hasTimerReached(long ms) {
        return getCurrentMS() - lastMS >= ms;
    }

    public void reset() {
        lastMS = getCurrentMS();
    }

    public boolean sleep(long time)
    {
        return sleep(time, TimeUnit.MILLISECONDS);
    }

    public boolean sleep(long time, TimeUnit timeUnit)
    {
        long convert = timeUnit.convert(System.nanoTime() - lastMS, TimeUnit.NANOSECONDS);
        if (convert >= time)
            reset();
        return convert >= time;
    }
}
