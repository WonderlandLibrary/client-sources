package us.dev.api.timing;

import java.util.concurrent.TimeUnit;

/**
 * @author Foundry
 */
public final class Timer {
    private long previousTime;

    public boolean hasReach(long time) {
        return this.hasReach(time, TimeUnit.MILLISECONDS);
    }

    public boolean hasReach(long time, TimeUnit timeUnit) {
        final long convert = timeUnit.convert(System.nanoTime() - this.previousTime, TimeUnit.NANOSECONDS);
        if (convert >= time) {
            this.reset();
        }
        return convert >= time;
    }

    public long getPreviousTime() {
        return previousTime;
    }

    public long getTime() {
        return getTime(TimeUnit.MILLISECONDS);
    }

    public long getTime(final TimeUnit timeUnit) {
        return timeUnit.convert(System.nanoTime() - this.previousTime, TimeUnit.NANOSECONDS);
    }

    public void reset() {
        this.previousTime = System.nanoTime();
    }
}
