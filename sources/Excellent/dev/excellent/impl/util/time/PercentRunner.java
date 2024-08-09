package dev.excellent.impl.util.time;

import dev.excellent.impl.util.math.Mathf;

public class PercentRunner {
    private final long lastMS = System.currentTimeMillis();
    private final long timeLimit;

    private PercentRunner(long timeLimit) {
        this.timeLimit = timeLimit;
    }

    public static PercentRunner create(long timeLimit) {
        return new PercentRunner(timeLimit);
    }

    public float getPercent() {
        return Mathf.clamp01((System.currentTimeMillis() - lastMS) / (float) timeLimit);
    }

    public float getPercent010() {
        float percent = getPercent();
        return Mathf.clamp01((percent > 0.5F ? 1.0F - percent : percent) * 2.0F);
    }
}

