/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.valuesystem;

import us.amerikan.valuesystem.Value;

public class NumberValue<T extends Number>
extends Value<T> {
    private final T minValue;
    private final T maxValue;

    public NumberValue(String valueName, T valueObject, T minValue, T maxValue) {
        super(valueName, valueObject);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public T getMaxValue() {
        return this.maxValue;
    }

    public T getMinValue() {
        return this.minValue;
    }
}

