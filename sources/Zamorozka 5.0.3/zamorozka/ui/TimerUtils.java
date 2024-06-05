package zamorozka.ui;

public class TimerUtils {

    public static float timerSpeed = 1.0F;

    private long lastMS = 0L;
    private final long prevMS = 0L;
    private long time = -1L;

    public boolean isDelay(long delay) {
        return System.currentTimeMillis() - lastMS >= delay;
    }

    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public void setLastMS(long lastMS) {
        this.lastMS = lastMS;
    }

    public void setLastMS() {
        this.lastMS = System.currentTimeMillis();
    }

    public int convertToMS(int d) {
        return 1000 / d;
    }

    public boolean hasReached(float f) {
        return (float) (getCurrentMS() - this.lastMS) >= f;
    }

    public void reset() {
        this.lastMS = getCurrentMS();
    }

    public boolean delay(float milliSec) {
        return (float) (getTime() - this.prevMS) >= milliSec;
    }

    public long getTime() {
        return System.nanoTime() / 1000000L;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void resetTimers() {
        this.time = System.currentTimeMillis();
    }

    public long getTimerTime() {
        return this.time;
    }
}