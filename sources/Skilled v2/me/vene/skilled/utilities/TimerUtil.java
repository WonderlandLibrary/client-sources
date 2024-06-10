 package me.vene.skilled.utilities;

public class TimerUtil
{
    private long lastTime;
    
    public TimerUtil() {
        this.reset();
    }
    
    public boolean hasReached(final float milliseconds) {
        return getCurrentMS() - this.lastTime >= milliseconds;
    }
    
    public boolean hasReached(final float lastTime, final float milliseconds) {
        return getCurrentMS() - lastTime >= milliseconds;
    }
    
    public void reset() {
        this.lastTime = getCurrentMS();
    }
    
    public static long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }
}
