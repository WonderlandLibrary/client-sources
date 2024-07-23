package io.github.liticane.monoxide.value.impl;

import java.util.function.Supplier;
import io.github.liticane.monoxide.value.Value;
import io.github.liticane.monoxide.value.impl.slider.NumberParser;
import io.github.liticane.monoxide.util.math.MathUtil;

public class NumberValue<T extends Number> extends Value<T> {

    private T minimum, maximum;
    private final int decimals;

    public NumberValue(String label, Object owner, T value, T minimum, T maximum, int decimals) {
        super(label, owner, value);
        this.minimum = minimum;
        this.maximum = maximum;
        this.decimals = decimals;
    }

    public NumberValue(String label, Object owner, T value, T minimum, T maximum, int decimals, Supplier<Boolean>[] suppliers) {
        super(label, owner, value, suppliers);
        this.minimum = minimum;
        this.maximum = maximum;
        this.decimals = decimals;
    }

    public T getMinimum() {
    	return this.minimum;
    }
    public T getMaximum() {
    	return this.maximum;
    }

    public void setMinimum(T minimum) {
        this.minimum = minimum;
    }

    public void setMaximum(T maximum) {
        this.maximum = maximum;
    }

    @Override
    public String getValueAsString() {
        return getValue().doubleValue() + "";
    }

    @Override
    public T getValue() {
        return value;
    }
    
    @Override
    public void setValue(T object) {

        double number = MathUtil.round(object.doubleValue(), decimals);
        Number newNumber = number;

        if (this.getValue() instanceof Integer) super.setValue((T) Integer.valueOf(newNumber.intValue()));
        else if (this.getValue() instanceof Float) super.setValue((T) Float.valueOf(newNumber.floatValue()));
        else if (this.getValue() instanceof Long) super.setValue((T) Long.valueOf(newNumber.longValue()));
        else if (this.getValue() instanceof Byte) super.setValue((T) Byte.valueOf(newNumber.byteValue()));
        else if (this.getValue() instanceof Short) super.setValue((T) Short.valueOf(newNumber.shortValue()));
        else if (this.getValue() instanceof Double) super.setValue((T) Double.valueOf(newNumber.doubleValue()));
        if(getValue() instanceof Integer) {
            int curr = getValue().intValue();
            int min = getMinimum().intValue();
            int max = getMaximum().intValue();
            if(curr < min) {
                super.setValue(getMinimum());
            } else if(curr > max) {
                super.setValue(getMaximum());
            }
        } else if(getValue() instanceof Float) {
            float curr = getValue().floatValue();
            float min = getMinimum().floatValue();
            float max = getMaximum().floatValue();
            if(curr < min) {
                super.setValue(getMinimum());
            } else if(curr > max) {
                super.setValue(getMaximum());
            }
        } else if(getValue() instanceof Long) {
            long curr = getValue().longValue();
            long min = getMinimum().longValue();
            long max = getMaximum().longValue();
            if(curr < min) {
                super.setValue(getMinimum());
            } else if(curr > max) {
                super.setValue(getMaximum());
            }
        } else if(getValue() instanceof Short) {
            short curr = getValue().shortValue();
            short min = getMinimum().shortValue();
            short max = getMaximum().shortValue();
            if(curr < min) {
                super.setValue(getMinimum());
            } else if(curr > max) {
                super.setValue(getMaximum());
            }
        } else if(getValue() instanceof Double) {
            double curr = getValue().doubleValue();
            double min = getMinimum().doubleValue();
            double max = getMaximum().doubleValue();
            if(curr < min) {
                super.setValue(getMinimum());
            } else if(curr > max) {
                super.setValue(getMaximum());
            }
        } else if(getValue() instanceof Byte) {
            byte curr = getValue().byteValue();
            byte min = getMinimum().byteValue();
            byte max = getMaximum().byteValue();
            if(curr < min) {
                super.setValue(getMinimum());
            } else if(curr > max) {
                super.setValue(getMaximum());
            }
        }
    }

    @Override
    public void setValue(String value) {
        try {
            this.setValue(NumberParser.parse(value, (Class<T>) this.value.getClass()));
        } catch (NumberFormatException e) {}
    }

    public int getDecimals() {
        return decimals;
    }
}

