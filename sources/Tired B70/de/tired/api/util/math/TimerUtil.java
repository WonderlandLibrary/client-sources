package de.tired.api.util.math;

public class TimerUtil {

    private long curTime;

    public TimerUtil() {
        doReset();
    }

    public boolean reachedTime(long time) {
        return System.nanoTime() / 1000000L - curTime >= time;
    }

    public void doReset() {
        this.curTime = System.nanoTime() / 1000000L;
    }

}
