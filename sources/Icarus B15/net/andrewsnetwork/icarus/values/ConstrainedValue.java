package net.andrewsnetwork.icarus.values;

import net.andrewsnetwork.icarus.module.*;

public class ConstrainedValue<T> extends Value
{
    private T min;
    private T max;
    
    @SuppressWarnings("unchecked")
	public ConstrainedValue(final String name, final String commandName, final T value, final T min, final T max, final Module module) {
        super(name, commandName, value, module);
        this.min = min;
        this.max = max;
    }
    
    public T getMax() {
        return this.max;
    }
    
    public void setMax(final T max) {
        this.max = max;
    }
    
    public T getMin() {
        return this.min;
    }
    
    public void setMin(final T min) {
        this.min = min;
    }
}
