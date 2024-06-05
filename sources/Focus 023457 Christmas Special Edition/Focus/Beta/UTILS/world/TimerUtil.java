package Focus.Beta.UTILS.world;

public class TimerUtil {
    private long lastMS;
    private long currentMs;

    private long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public boolean hasReached(double milliseconds) {
        if ((double) (this.getCurrentMS() - this.lastMS) >= milliseconds) {
            return true;
        }
        return false;
    }

    public void reset() {
        this.lastMS = this.getCurrentMS();
    }

    public boolean delay(float milliSec) {
        if ((float) (this.getTime() - this.lastMS) >= milliSec) {
            return true;
        }
        return false;
    }

    public long getTime() {
        return System.nanoTime() / 1000000L;
    }

    public boolean isDelayComplete(double delay) {
        return System.currentTimeMillis() - delay >= lastMS;
    }

    public boolean hasTimeElapsed(long time, boolean reset) {
        if (System.currentTimeMillis() - this.lastMS > time) {
            if (reset) {
                this.reset();
            }
            return true;
        }
        return false;
    }

    public void setTime(long time) {
        lastMS = time;
    }

    public void setLastMS() {
        this.lastMS = System.currentTimeMillis();
    }

    public boolean hasElapsed(long milliseconds) {
        return (System.currentTimeMillis() - currentMs) > milliseconds;
    }

    public boolean check(float milliseconds) {
        return getTime() >= milliseconds;
    }

}
