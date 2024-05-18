// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Utils;

public class TimeMeme
{
    private long prevTime;
    
    public TimeMeme() {
        this.reset();
    }
    
    public boolean hasPassed(final double milli) {
        return this.getTime() - this.prevTime >= milli;
    }
    
    public long getTime() {
        return System.nanoTime() / 1000000L;
    }
    
    public void reset() {
        this.prevTime = this.getTime();
    }
}
