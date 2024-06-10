 package me.vene.skilled.values;

 import java.math.BigDecimal;
 import me.vene.skilled.utilities.MathUtil;
 import me.vene.skilled.utilities.StringRegistry;
 
public class NumberValue
{
    private String name;
    private double value;
    private double max;
    private double min;
    
    public NumberValue(final String name, final double value, final double min, final double max) {
        this.name = StringRegistry.register(name);
        this.value = value;
        this.max = max;
        this.min = min;
    }
    
    public String getName() {
        return StringRegistry.register(this.name);
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setValue(final double newValue) {
        this.value = MathUtil.clamp(newValue, this.min, this.max);
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
