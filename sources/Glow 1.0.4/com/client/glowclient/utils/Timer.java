package com.client.glowclient.utils;

public class Timer
{
    private long lastMS;
    
    public boolean hasBeenSet() {
        return this.lastMS > -1L;
    }
    
    public boolean D(final double n) {
        return this.hasBeenSet() && (n == System.currentTimeMillis() - this.lastMS || n == System.currentTimeMillis() - this.lastMS + 50L);
    }
    
    public Timer() {
        this(false);
    }
    
    public Timer(final boolean b) {
        final long lastMS = -1L;
        super();
        this.lastMS = lastMS;
        if (b) {
            this.reset();
        }
    }
    
    public void reset() {
        this.lastMS = System.currentTimeMillis();
    }
    
    public boolean delay(final double n) {
        return this.hasBeenSet() && n < System.currentTimeMillis() - this.lastMS;
    }
    
    public void destroy() {
        this.lastMS = -1L;
    }
}
