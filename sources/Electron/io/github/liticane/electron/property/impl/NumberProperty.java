package io.github.liticane.electron.property.impl;

import io.github.liticane.electron.property.Property;
import lombok.Getter;

import java.util.function.Supplier;

@Getter
public class NumberProperty extends Property <Double> {
    private final Double minimum, maximum;

    public NumberProperty(String name, Double minimum, Double value, Double maximum, Supplier<Boolean> dependency) {
        super(name, value, dependency);
        this.minimum = minimum;
        this.maximum = maximum;
    }
}