package ez.h.utils;

public class Counter
{
    final long prevMS = 0L;
    long lastMS;
    
    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }
    
    public void setLastMS() {
        this.lastMS = System.currentTimeMillis();
    }
    
    public boolean isDelay(final long n) {
        return System.currentTimeMillis() - this.lastMS >= n;
    }
    
    public int convertToMS(final int n) {
        return (376 + 41 + 522 + 61) / n;
    }
    
    public void reset() {
        this.lastMS = this.getCurrentMS();
    }
    
    public boolean delay(final float n) {
        final long time = this.getTime();
        this.getClass();
        return time - 0L >= n;
    }
    
    public long getTime() {
        return System.nanoTime() / 1000000L;
    }
    
    public void setLastMS(final long lastMS) {
        this.lastMS = lastMS;
    }
    
    public boolean hasReached(final float n) {
        return this.getCurrentMS() - this.lastMS >= n;
    }
}
