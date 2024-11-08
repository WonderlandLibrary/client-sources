package eze.settings;

public class NumberSetting extends Setting
{
    public double value;
    public double minimum;
    public double maximum;
    public double increment;
    
    public double getValue() {
        return this.value;
    }
    
    public void setValue(final double value) {
        final double precision = 1.0 / this.increment;
        this.value = Math.round(Math.max(this.minimum, Math.min(this.maximum, value)) * precision) / precision;
    }
    
    public void increment(final boolean positive) {
        this.setValue(this.getValue() + (positive ? 1 : -1) * this.increment);
    }
    
    public double getMinimum() {
        return this.minimum;
    }
    
    public void setMinimum(final double minimum) {
        this.minimum = minimum;
    }
    
    public double getMaximum() {
        return this.maximum;
    }
    
    public void setMaximum(final double maximum) {
        this.maximum = maximum;
    }
    
    public double getIncrement() {
        return this.increment;
    }
    
    public void setIncrement(final double increment) {
        this.increment = increment;
    }
    
    public NumberSetting(final String name, final double value, final double minimum, final double maximum, final double increment) {
        this.name = name;
        this.value = value;
        this.minimum = minimum;
        this.maximum = maximum;
        this.increment = increment;
    }
}
