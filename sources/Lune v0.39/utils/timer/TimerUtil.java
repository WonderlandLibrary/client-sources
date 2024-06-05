
package me.superskidder.lune.utils.timer;

import net.minecraft.util.Timer;

public class TimerUtil {
    private long lastMS;
    public Timer timerSpeed;

    public TimerUtil() {
        this.lastMS = 0L;
    }

    private long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }

    public boolean hasTimeElapsed(long time) {
        return this.time() >= time;
    }

    public boolean hasTimeElapsed(long time, boolean reset) {
        if (this.time() >= time) {
            if (reset) {
                this.reset();
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean sleep(final long time) {
        if (time() >= time) {
            reset();
            return true;
        }
        return false;
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

    public boolean isDelayComplete(long delay) {
        if (System.currentTimeMillis() - this.lastMS > delay) {
            return true;
        }
        return false;
    }

    public boolean delay(Double milliSec) {
        if ((float) (this.getTime() - this.lastMS) >= milliSec) {
            return true;
        }
        return false;
    }

    public boolean check(float milliseconds) {
        return getTime() >= milliseconds;
    }

    public boolean reach(long time) {
        return this.time() >= time;
    }

    public long time() {
        return System.nanoTime() / 1000000L - this.time();
    }

}
