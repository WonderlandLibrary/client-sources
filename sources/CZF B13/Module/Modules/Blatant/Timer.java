package gq.vapu.czfclient.Module.Modules.Blatant;

public class Timer {
    protected static long lastMS;
    static long prevMS;
    private final long time;
    private long lastMS1;

    public Timer() {
        //    super("Timer", new String[] { "timer" }, ModuleType.Blatant);
        Timer.prevMS = 0L;
        Timer.lastMS = -1L;
        this.time = System.nanoTime() / 1000000L;
    }

    public static boolean delay(final float milliSec) {
        return getTime() - Timer.prevMS >= milliSec;
    }

    public static boolean delay(final double milliSec) {
        return getTime() - Timer.prevMS >= milliSec;
    }

    public static void reset() {
        Timer.prevMS = getTime();
    }

    public static long getTime() {
        return System.nanoTime() / 1000000L;
    }

    public static boolean isDelayComplete(final long delay) {
        return System.currentTimeMillis() - Timer.lastMS >= delay;
    }

    public static long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public static boolean hasReached(final long milliseconds) {
        return getCurrentMS() - Timer.lastMS >= milliseconds;
    }

    public boolean delay1(final float milliSec) {
        return getTime() - Timer.prevMS >= milliSec;
    }

    public void reset1() {
        this.lastMS1 = getCurrentMS();
    }

    public long time() {
        return System.nanoTime() / 1000000L - this.time;
    }

    public long getDifference() {
        return getTime() - Timer.prevMS;
    }

    public void setDifference(final long difference) {
        Timer.prevMS = getTime() - difference;
    }

    public boolean hasTimeElapsed(final long time, final boolean reset) {
        if (this.time() >= time) {
            if (reset) {
                reset();
            }
            return true;
        }
        return false;
    }

    public void setLastMS() {
        Timer.lastMS = System.currentTimeMillis();
    }

    public long getLastMs() {
        return System.currentTimeMillis() - Timer.lastMS;
    }

    public long getLastMS() {
        return Timer.lastMS;
    }

    public void setLastMS(final long currentMS) {
        Timer.lastMS = currentMS;
    }

    public boolean hasReached(final double milliseconds) {
        return getCurrentMS() - Timer.lastMS >= milliseconds;
    }

    public boolean hasTicksElapsed(final int ticks) {
        return this.time() >= 1000 / ticks - 50;
    }

    public boolean hasTicksElapsed(final int time, final int ticks) {
        return this.time() >= time / ticks - 50;
    }
}
