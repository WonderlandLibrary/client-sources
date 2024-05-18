package me.enrythebest.reborn.cracked.util;

public final class Timer
{
    private long previousTime;
    
    public Timer() {
        this.previousTime = -1L;
    }
    
    public boolean hasReach(final float var1) {
        return getCurrentTime() - this.previousTime >= var1;
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
    
    public static short convert(final float var0) {
        return (short)(1000.0f / var0);
    }
}
