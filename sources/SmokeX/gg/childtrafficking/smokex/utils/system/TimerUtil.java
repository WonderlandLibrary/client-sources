// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.utils.system;

public final class TimerUtil
{
    private double currentMs;
    
    public TimerUtil() {
        this.reset();
    }
    
    public long lastReset() {
        return (long)this.currentMs;
    }
    
    public boolean hasElapsed(final double elapsed) {
        return this.elapsed() > elapsed;
    }
    
    public long elapsed() {
        return (long)(System.currentTimeMillis() - this.currentMs);
    }
    
    public String formatTime() {
        final int seconds = (int)(this.elapsed() / 1000L) % 60;
        final int minutes = (int)(this.elapsed() / 60000L % 60L);
        final int hours = (int)(this.elapsed() / 3600000L % 24L);
        return hours + "h, " + minutes + "m, " + seconds + "s";
    }
    
    public void reset() {
        this.currentMs = (double)System.currentTimeMillis();
    }
}
