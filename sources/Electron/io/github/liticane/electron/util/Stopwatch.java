package io.github.liticane.electron.util;

public class Stopwatch {
    private long lastTime;

    public Stopwatch(long lastTime) {
        this.lastTime = lastTime;
    }

    public Stopwatch() {
        this.lastTime = System.currentTimeMillis();
    }

    public boolean hasElapsed(long time, boolean reset) {
        if (System.currentTimeMillis() >= lastTime + time) {
            if (reset)
                reset();

            return true;
        }

        return false;
    }

    public boolean hasElapsed(long time) {
        return System.currentTimeMillis() >= lastTime + time;
    }

    public void reset() {
        this.lastTime = System.currentTimeMillis();
    }
}