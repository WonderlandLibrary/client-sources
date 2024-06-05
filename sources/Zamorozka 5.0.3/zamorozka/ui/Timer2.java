package zamorozka.ui;

public class Timer2 {
    private long previousTime;

    public Timer2() {
        this.previousTime = -1L;
    }

    public boolean check(float milliseconds) {
        return (float) (getCurrentTime() - this.previousTime) >= milliseconds;
    }

    public void reset() {
        this.previousTime = getCurrentTime();
    }

    public short convert(float perSecond) {
        return (short) (int) (1000.0F / perSecond);
    }

    public long get() {
        return this.previousTime;
    }

    public long getCurrentTime() {
        return System.currentTimeMillis();
    }
    
    public long getTime() {
        return System.currentTimeMillis() - previousTime;
    }
}