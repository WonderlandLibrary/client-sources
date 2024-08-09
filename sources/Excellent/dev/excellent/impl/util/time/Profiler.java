package dev.excellent.impl.util.time;

import lombok.Getter;

@Getter
public class Profiler {
    private long totalTime = 0;
    private long lastStart;
    private long lastTime;

    public void start() {
        lastStart = System.nanoTime();
    }

    public void stop() {
        totalTime += System.nanoTime() - lastStart;
        start();
    }

    public void reset() {
        lastTime = totalTime;
        totalTime = 0;
    }

    public long getLastTimeInMillis() {
        return lastTime / 1_000_000;
    }
}