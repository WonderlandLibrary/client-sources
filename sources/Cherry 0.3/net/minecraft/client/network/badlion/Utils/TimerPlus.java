// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Utils;

public final class TimerPlus
{
    private long time;
    
    public TimerPlus() {
        this.time = System.nanoTime() / 1000000L;
    }
    
    public boolean hasTimeElapsed(final double d, final boolean reset) {
        if (this.time() >= d) {
            if (reset) {
                this.reset();
            }
            return true;
        }
        return false;
    }
    
    public long time() {
        return System.nanoTime() / 1000000L - this.time;
    }
    
    public void reset() {
        this.time = System.nanoTime() / 1000000L;
    }
}
