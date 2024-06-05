package ClassSub;

public class Class205
{
    public long lastMs;
    
    
    public Class205() {
        this.lastMs = 0L;
    }
    
    public boolean isDelayComplete(final long n) {
        if (Class334.username.length() < 1) {
            System.exit(0);
        }
        return System.currentTimeMillis() - this.lastMs > n;
    }
    
    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }
    
    public void reset() {
        this.lastMs = System.currentTimeMillis();
    }
    
    public long getLastMs() {
        return this.lastMs;
    }
    
    public void setLastMs(final int n) {
        this.lastMs = System.currentTimeMillis() + n;
    }
    
    public boolean hasReached(final long n) {
        return this.getCurrentMS() - this.lastMs >= n;
    }
    
    public boolean isDelayComplete(final float n) {
        return System.currentTimeMillis() - this.lastMs > n;
    }
    
    public boolean isDelayComplete(final Double n) {
        return System.currentTimeMillis() - this.lastMs > n;
    }
}
