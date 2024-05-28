package arsenic.module.property.impl.rangeproperty;

import java.util.Random;

public class RangeValue {

    private final Random random = new Random();
    private final double minBound, maxBound, inc;
    private double min, max;

    public RangeValue(double minBound, double maxBound, double min, double max, double inc) {
        this.minBound = minBound;
        this.maxBound = maxBound;
        this.min = min;
        this.max = max;
        this.inc = inc;
    }

    private double getCorrectedValue(double value) {
        value = Math.min(Math.max(value, minBound), maxBound);
        return Math.round(value * (1.0D / inc)) / (1.0D / inc);
    }

    public double getMin() { return min; }

    public void setMinSilently(double min) {
        this.min = Math.min(this.max, getCorrectedValue(min));
    }

    public void setMin(double min) {
        setMinSilently(min);
        onUpdate();
    }

    public double getMax() { return max; }

    public void setMaxSilently(double max) {
        this.max = Math.max(this.min, getCorrectedValue(max));
    }

    public void setMax(double min) {
        setMaxSilently(min);
        onUpdate();
    }

    public double getMaxBound() { return maxBound; }

    public double getMinBound() { return minBound; }

    public double getRandomInRange() { return getMin() + (random.nextDouble() * (getMax() - getMin())); }

    public void onUpdate() {

    }
}