package wtf.diablo.client.setting.impl;

import wtf.diablo.client.setting.api.AbstractSetting;

public final class NumberSetting<T extends Number> extends AbstractSetting<T> {
    private final T minimum;
    private final T maximum;
    private final T increment;

    public NumberSetting(final String name, final T value, final T minimum, final T maximum, final T increment) {
        super(name, value);
        this.minimum = minimum;
        this.maximum = maximum;
        this.increment = increment;
    }

    @Override
    public T parse(final String value) {
        if(getValue() instanceof Integer)
            return (T) Integer.valueOf(value);
        else if(getValue() instanceof Double)
            return (T) Double.valueOf(value);
        else if(getValue() instanceof Float)
            return (T) Float.valueOf(value);
        else if(getValue() instanceof Long)
            return (T) Long.valueOf(value);
        else if(getValue() instanceof Short)
            return (T) Short.valueOf(value);
        else if(getValue() instanceof Byte)
            return (T) Byte.valueOf(value);
        else
            return null;
    }

    @Override
    public void setValue(final T value) {
        final double result = (double) Math.round(value.doubleValue() / increment.doubleValue()) * increment.doubleValue();

        if(value instanceof Integer)
            super.setValue((T) Integer.valueOf((int) result));
        else if(value instanceof Double)
            super.setValue((T) Double.valueOf(result));
        else if(value instanceof Float)
            super.setValue((T) Float.valueOf((float) result));
        else if(value instanceof Long)
            super.setValue((T) Long.valueOf((long) result));
        else if(value instanceof Short)
            super.setValue((T) Short.valueOf((short) result));
        else if(value instanceof Byte)
            super.setValue((T) Byte.valueOf((byte) result));
        else
            super.setValue(value);
    }

    public T getMinimum() {
        return this.minimum;
    }

    public T getMaximum() {
        return this.maximum;
    }

    public T getIncrement() {
        return this.increment;
    }

    public boolean isInteger() {
        return this.getValue().equals(super.getValue().intValue());
    }
}
