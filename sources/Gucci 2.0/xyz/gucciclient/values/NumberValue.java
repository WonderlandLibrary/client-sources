package xyz.gucciclient.values;

import java.math.*;

public class NumberValue
{
    private String name;
    private double value;
    private double max;
    private double min;
    
    public NumberValue(final String name, final double value, final double min, final double max) {
        this.name = name;
        this.value = value;
        this.max = max;
        this.min = min;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setValue(final double newValue) {
        this.value = newValue;
        if (newValue < this.getMin()) {
            this.value = this.getMin();
        }
    }
    
    public double getValue() {
        return this.round(this.value, 2);
    }
    
    public double getMin() {
        return this.min;
    }
    
    public double getMax() {
        return this.max;
    }
    
    public void increase() {
        if (this.round(this.value, 2) < this.max) {
            ++this.value;
        }
    }
    
    public void decrease() {
        if (this.round(this.value, 2) > this.min) {
            --this.value;
        }
    }
    
    private double round(final double doubleValue, final int numOfDecimals) {
        BigDecimal bigDecimal = new BigDecimal(doubleValue);
        bigDecimal = bigDecimal.setScale(numOfDecimals, 4);
        return bigDecimal.doubleValue();
    }
}
