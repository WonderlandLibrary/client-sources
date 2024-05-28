package arsenic.module.property.impl.doubleproperty;

public class DoubleValue {
    private final double minBound, maxBound, inc;
    private double value;

    public DoubleValue(double minBound, double maxBound, double value, double inc) {
        this.minBound = minBound;
        this.maxBound = maxBound;
        this.value = value;
        this.inc = inc;
    }

    private double getCorrectedValue(double value) {
        value = Math.round(value * (1.0D / inc)) / (1.0D / inc);
        return Math.min(Math.max(value, minBound), maxBound);
    }

    public double getInput() { return value; }

    public void setInputSilently(double value) {
        this.value = getCorrectedValue(value);
    }
    public void setInput(double value) {
        setInputSilently(value);
        onUpdate();
    }

    public double getMaxBound() { return maxBound; }

    public double getMinBound() { return minBound; }

    public void onUpdate() {

    }
}
