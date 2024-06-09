package me.travis.wurstplus.util;

/**
 * Simple timer class, mostly used for keeping track of when st00f occured.
 */
public final class Timer {

    private long lastCheck = getSystemTime();

    /**
     * Checks if the passed time reached the targetted time.
     */
    public boolean hasReach(int targetTime) {
        return getTimePassed() >= targetTime;
    }

    public long getTimePassed() {
        return getSystemTime() - lastCheck;
    }

    public void reset() {
        lastCheck = getSystemTime();
    }

    public static long getSystemTime() {
        return System.nanoTime() / (long) (1E6);
    }
    
}