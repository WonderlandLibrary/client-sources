package me.tojatta.api.utilities.value.impl;

import me.tojatta.api.utilities.value.*;
import java.lang.reflect.*;

public class TypeNumber<T extends Number> extends Value<T>
{
    private T minimum;
    private T maximum;
    
    public TypeNumber(final String label, final Object object, final Field field, final T minimum, final T maximum) {
        super(label, object, field);
        this.minimum = minimum;
        this.maximum = maximum;
    }
    
    @Override
    public void setValue(T value) {
        if (value.doubleValue() > this.maximum.doubleValue()) {
            value = this.maximum;
        }
        else if (value.doubleValue() < this.minimum.doubleValue()) {
            value = this.minimum;
        }
        super.setValue(value);
    }
    
    public T getMinimum() {
        return this.minimum;
    }
    
    public void setMinimum(final T minimum) {
        this.minimum = minimum;
    }
    
    public T getMaximum() {
        return this.maximum;
    }
    
    public void setMaximum(final T maximum) {
        this.maximum = maximum;
    }
}
