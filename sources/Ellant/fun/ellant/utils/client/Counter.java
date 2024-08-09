package fun.ellant.utils.client;

import lombok.Setter;

public class Counter {
    @Setter
    private long lastMS;

    private Counter() {
        reset();
    }

    public static Counter create() {
        return new Counter();
    }

    public void reset() {
        lastMS = System.currentTimeMillis();
    }

    public long elapsedTime() {
        return System.currentTimeMillis() - lastMS;
    }

    public boolean hasReached(long time) {
        return elapsedTime() >= time;
    }

    public boolean hasReached(long time, boolean reset) {
        boolean hasElapsed = elapsedTime() >= time;
        if (hasElapsed && reset) {
            reset();
        }
        return hasElapsed;
    }

    public boolean hasReached(double ms) {
        return elapsedTime() >= ms;
    }

    public boolean delay(long ms) {
        boolean hasDelayElapsed = elapsedTime() - ms >= 0;
        if (hasDelayElapsed) {
            reset();
        }
        return hasDelayElapsed;
    }
}