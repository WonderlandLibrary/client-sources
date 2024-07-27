package dev.nexus.modules.setting;

@SuppressWarnings("all")
public class NumberSetting extends Setting {
    private double min, max, value, increment;

    public NumberSetting(String name, double min, double max, double value, double increment) {
        super(name);
        this.min = min;
        this.max = max;
        this.value = value;
        this.increment = increment;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getValue() {
        return value;
    }

    public double getPowValue() {
        return (double) value * (double) value;
    }

    public float getePowValueFloat() {
        return (float) value * (float) value;
    }

    public int getePowValueInt() {
        return (int) value * (int) value;
    }

    public int getValueInt() {
        return (int) value;
    }

    public float getValueFloat() {
        return (float) value;
    }

    public double getIncrement() {
        return increment;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setValue(double value) {
        double precision = 1.0D / this.increment;
        this.value = Math.round(Math.max(this.min, Math.min(this.max, value)) * precision) / precision;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setIncrement(double increment) {
        this.increment = increment;
    }
}
