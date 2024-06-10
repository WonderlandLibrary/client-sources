package xyz.gucciclient.utils;

import java.util.concurrent.*;

public class Timer2
{
    private long current;
    private long last;
    
    public void updateTimer() {
        this.current = this.getCurrentMillis();
    }
    
    public boolean sleep(final long time) {
        return this.sleep(time, TimeUnit.MILLISECONDS);
    }
    
    public boolean sleep(final long time, final TimeUnit timeUnit) {
        final long convert = timeUnit.convert(System.nanoTime() - this.last, TimeUnit.NANOSECONDS);
        if (convert >= time) {
            this.reset();
        }
        return convert >= time;
    }
    
    public void reset() {
        this.last = System.nanoTime();
    }
    
    public long getCurrentMillis() {
        return System.nanoTime() / 1000000L;
    }
    
    public final void updateLast() {
        this.last = this.getCurrentMillis();
    }
    
    public final long getLast() {
        return this.last;
    }
}
