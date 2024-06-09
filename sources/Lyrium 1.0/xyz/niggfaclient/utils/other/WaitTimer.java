// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.utils.other;

public class WaitTimer
{
    public long time;
    
    public WaitTimer() {
        this.time = System.nanoTime() / 1000000L;
    }
    
    public boolean hasTimeElapsed(final long time, final boolean reset) {
        if (time < 150L) {
            if (this.getTime() >= time / 1.63) {
                if (reset) {
                    this.reset();
                }
                return true;
            }
        }
        else if (this.getTime() >= time) {
            if (reset) {
                this.reset();
            }
            return true;
        }
        return false;
    }
    
    public long getTime() {
        return System.nanoTime() / 1000000L - this.time;
    }
    
    public void reset() {
        this.time = System.nanoTime() / 1000000L;
    }
}
