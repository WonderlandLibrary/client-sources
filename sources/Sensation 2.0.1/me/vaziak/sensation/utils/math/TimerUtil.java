package me.vaziak.sensation.utils.math;

/**
 * @author Spec
 */
public class TimerUtil {

    private long prevMS;

    public boolean hasPassed(double milliSec) {

        return (float) (System.currentTimeMillis() - prevMS) >= milliSec;
    }

    public void reset() {
        prevMS = System.currentTimeMillis();
    }
    public void addTime(int time) {
        prevMS = System.currentTimeMillis() + time;
    }
}