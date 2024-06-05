package xyz.vergoclient.util.main;

public class Timer
{
    public long lastMs;

    public Timer() {
        this.lastMs = 0L;
    }

    public void reset() {
        this.lastMs = System.currentTimeMillis();
    }

    public boolean delay(final long nextDelay) {
        return System.currentTimeMillis() - this.lastMs >= nextDelay;
    }
}