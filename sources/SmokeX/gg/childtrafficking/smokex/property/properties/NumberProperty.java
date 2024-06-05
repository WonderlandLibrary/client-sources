// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.property.properties;

import java.util.function.Supplier;
import gg.childtrafficking.smokex.property.Property;

public final class NumberProperty<T extends Number> extends Property<T>
{
    private final T min;
    private final T max;
    private final T increment;
    
    public NumberProperty(final String identifier, final String displayName, final T value, final T min, final T max, final T increment, final Supplier<Boolean> booleanSupplier) {
        super(identifier, displayName, value, booleanSupplier);
        this.min = min;
        this.max = max;
        this.increment = increment;
    }
    
    public NumberProperty(final String identifier, final String displayName, final T value, final T min, final T max, final T increment) {
        super(identifier, displayName, value, () -> true);
        this.min = min;
        this.max = max;
        this.increment = increment;
    }
    
    public NumberProperty(final String displayName, final T value, final T min, final T max, final T increment) {
        this(displayName.replace(" ", ""), displayName, value, min, max, increment);
    }
    
    @Override
    public void setValue(T value) {
        if (this.min.doubleValue() > value.doubleValue()) {
            value = this.min;
        }
        if (this.max.doubleValue() < value.doubleValue()) {
            value = this.max;
        }
        super.setValue(value);
    }
    
    @Override
    public boolean setValueFromString(final String value) {
        try {
            this.setValue(this.parseNumber(value));
            return true;
        }
        catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public String getValueAsString() {
        return this.getValue().toString();
    }
    
    private Number parseNumber(final String input) {
        if (!this.isNumber(input)) {
            throw new IllegalArgumentException(input + " is not a valid number");
        }
        if (this.getValue().getClass().isAssignableFrom(Float.class)) {
            return Float.parseFloat(input);
        }
        if (this.getValue().getClass().isAssignableFrom(Double.class)) {
            return Double.parseDouble(input);
        }
        if (this.getValue().getClass().isAssignableFrom(Short.class)) {
            return Short.parseShort(this.truncateDots(input));
        }
        if (this.getValue().getClass().isAssignableFrom(Byte.class)) {
            return Byte.parseByte(this.truncateDots(input));
        }
        if (this.getValue().getClass().isAssignableFrom(Integer.class)) {
            return Integer.parseInt(this.truncateDots(input));
        }
        if (this.getValue().getClass().isAssignableFrom(Long.class)) {
            return Long.parseLong(this.truncateDots(input));
        }
        throw new IllegalArgumentException(this.getValue().getClass() + " is not a valid number type.");
    }
    
    private boolean isNumber(final String input) {
        final String numbers = "0123456789";
        for (final char c : input.toCharArray()) {
            if (c != '.') {
                if (!numbers.contains(String.valueOf(c))) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private String truncateDots(final String input) {
        if (!input.contains(".")) {
            return input;
        }
        return String.valueOf((int)Math.ceil(Float.parseFloat(input)));
    }
}
