package dev.nexus.modules.setting;

@SuppressWarnings("all")
public class RangeSetting extends Setting {
    private double min, max, valueMin, valueMax, increment;

    public RangeSetting(String name, double min, double max, double valueMin, double valueMax, double increment) {
        super(name);
        this.min = min;
        this.max = max;
        this.valueMin = valueMin;
        this.valueMax = valueMax;
        this.increment = increment;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getValueMax() {
        return valueMax;
    }

    public int getValueMaxInt() {
        return (int) valueMax;
    }

    public float getValueMaxFloat() {
        return (float) valueMax;
    }

    public double getValueMin() {
        return valueMin;
    }

    public int getValueMinInt() {
        return (int) valueMin;
    }

    public float getValueMinFloat() {
        return (float) valueMin;
    }

    public double getIncrement() {
        return increment;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setValueMin(double value) {
        double precision = 1.0D / this.increment;
        this.valueMin = Math.round(Math.max(this.min, Math.min(this.max, value)) * precision) / precision;
    }

    public void setValueMax(double value) {
        double precision = 1.0D / this.increment;
        this.valueMax = Math.round(Math.max(this.min, Math.min(this.max, value)) * precision) / precision;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setIncrement(double increment) {
        this.increment = increment;
    }
}
