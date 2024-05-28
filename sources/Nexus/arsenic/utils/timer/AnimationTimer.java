package arsenic.utils.timer;

import java.util.function.Supplier;

public class AnimationTimer {

    int maxMs;
    long lastTick;
    long ticksLived;
    private final TickMode tickMode;
    private final Supplier<Boolean> func;

    public AnimationTimer(int maxMs, Supplier<Boolean> func) {
        this(maxMs, func, TickMode.SINE);
    }

    public AnimationTimer(int maxMs, Supplier<Boolean> func, TickMode tickMode) {
        this.maxMs = maxMs;
        this.func = func;
        this.tickMode = tickMode;
    }

    public float getPercent() {
        long tickDifference = lastTick - System.currentTimeMillis();
        lastTick = System.currentTimeMillis();
        ticksLived = Math.max(0, Math.min(maxMs, ticksLived + (tickDifference * (func.get() ? -1 : 1))));
        return tickMode.toSmoothPercent((float) ticksLived / maxMs);
    }

    public void setElapsedMs(int ms) {
        lastTick = System.currentTimeMillis();
        ticksLived = ms;
    }
}
