// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.utils.other;

public class TimerUtil
{
    private long currentMs;
    
    public TimerUtil() {
        this.reset();
    }
    
    public long getCurrentMs() {
        return this.currentMs;
    }
    
    public void resetWithOffset(final long offset) {
        this.currentMs = System.currentTimeMillis() + offset;
    }
    
    public boolean hasElapsed(final long milliseconds) {
        return this.getTime() > milliseconds;
    }
    
    public boolean hasTimeElapsed(final long time, final boolean reset) {
        if (this.getTime() > time) {
            if (reset) {
                this.reset();
            }
            return true;
        }
        return false;
    }
    
    public void reset() {
        this.currentMs = System.currentTimeMillis();
    }
    
    public long getTime() {
        return System.currentTimeMillis() - this.currentMs;
    }
    
    public void setTime(final long time) {
        this.currentMs = time;
    }
}
