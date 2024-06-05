package me.darkmagician6.morbid.util;

public final class Timer
{
    private long previousTime;
    
    public Timer() {
        this.previousTime = -1L;
    }
    
    public boolean hasReach(final float milliseconds) {
        return getCurrentTime() - this.previousTime >= milliseconds;
    }
    
    public void reset() {
        this.previousTime = getCurrentTime();
    }
    
    public long get() {
        return this.previousTime;
    }
    
    public static long getCurrentTime() {
        return System.nanoTime() / 1000000L;
    }
    
    public static short convert(final float perSecond) {
        return (short)(1000.0f / perSecond);
    }
}
