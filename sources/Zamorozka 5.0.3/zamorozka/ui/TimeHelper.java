package zamorozka.ui;

public class TimeHelper {
    private long lastMS = 0L;

    public boolean isDelayComplete(long delay) {
        return System.currentTimeMillis() - this.lastMS >= delay;
    }

    public void reset() {
        this.lastMS = this.getCurrentMS();
    }

    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public void setLastMS(long lastMS) {
        this.lastMS = lastMS;
    }

    public boolean hasReached(long milliseconds) {
        return this.getCurrentMS() - this.lastMS >= milliseconds;
    }

    public boolean hasReachedfloat(float timeLeft) {
        return (float) (this.getCurrentMS() - this.lastMS) >= timeLeft;
    }

    public void setLastMS() {
        this.lastMS = System.currentTimeMillis();
    }

    public int convertToMS(int perSecond) {
        return 1000 / perSecond;
    }
}
