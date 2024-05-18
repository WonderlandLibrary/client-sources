// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.utils;

public class TimerUtils
{
    private long prevMS;
    private long lastMS;
    
    public TimerUtils() {
        this.prevMS = 0L;
    }
    
    public boolean delay(final float milliSec) {
        return this.getTime() - this.prevMS >= milliSec;
    }
    
    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }
    
    public long getLastMS() {
        return this.lastMS;
    }
    
    public boolean hasReached(final long milliseconds) {
        return this.getCurrentMS() - this.lastMS >= milliseconds;
    }
    
    public long getTime() {
        return this.getCurrentMS() - this.lastMS;
    }
    
    public void reset() {
        this.lastMS = this.getCurrentMS();
    }
    
    public void setLastMS(final long currentMS) {
        this.lastMS = currentMS;
    }
}
