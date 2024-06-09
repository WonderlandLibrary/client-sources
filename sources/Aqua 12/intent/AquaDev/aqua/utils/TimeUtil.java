// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.utils;

public class TimeUtil
{
    private long lastMS;
    
    public TimeUtil() {
        this.lastMS = 0L;
    }
    
    public long getCurrentMS() {
        return System.currentTimeMillis();
    }
    
    public boolean hasReached(final long milliseconds) {
        return this.getCurrentMS() - this.lastMS >= milliseconds;
    }
    
    public void reset() {
        this.lastMS = this.getCurrentMS();
    }
    
    public int convertToMS(final int perSecond) {
        return 1000 / perSecond;
    }
    
    public void setCurrentDifference(final int difference) {
        this.lastMS = System.currentTimeMillis() - difference;
    }
    
    public boolean hasTimePassed(final long delay) {
        return System.currentTimeMillis() >= this.lastMS + delay;
    }
    
    public void setLastMS() {
        this.lastMS = System.currentTimeMillis();
    }
    
    public boolean hasTimeElapsed(final long time, final boolean reset) {
        if (this.lastMS > System.currentTimeMillis()) {
            this.lastMS = System.currentTimeMillis();
        }
        if (System.currentTimeMillis() - this.lastMS > time) {
            if (reset) {
                this.reset();
            }
            return true;
        }
        return false;
    }
}
