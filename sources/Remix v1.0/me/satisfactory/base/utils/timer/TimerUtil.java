package me.satisfactory.base.utils.timer;

public final class TimerUtil
{
    public double time;
    
    public TimerUtil() {
        this.time = (double)(System.nanoTime() / 1000000L);
    }
    
    public boolean hasTimeElapsed(final double time, final boolean reset) {
        if (this.getTime() >= time) {
            if (reset) {
                this.reset();
            }
            return true;
        }
        return false;
    }
    
    public boolean hasTimeElapsed(final double time) {
        return this.getTime() >= time;
    }
    
    public double getTime() {
        return System.nanoTime() / 1000000L - this.time;
    }
    
    public void reset() {
        this.time = (double)(System.nanoTime() / 1000000L);
    }
}
