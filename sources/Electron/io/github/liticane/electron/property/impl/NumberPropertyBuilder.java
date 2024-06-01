package io.github.liticane.electron.property.impl;

import io.github.liticane.electron.structure.Builder;

import java.util.function.Supplier;

public class NumberPropertyBuilder implements Builder<NumberProperty> {

    private String name;
    private Double minimum, value, maximum;
    private Supplier<Boolean> dependency;

    public NumberPropertyBuilder setName(final String name) {
        this.name = name;
        return this;
    }

    public NumberPropertyBuilder setMinimum(final Double minimum) {
        this.minimum = minimum;
        return this;
    }

    public NumberPropertyBuilder setValue(final Double value) {
        this.value = value;
        return this;
    }

    public NumberPropertyBuilder setMaximum(final Double maximum) {
        this.maximum = maximum;
        return this;
    }

    public NumberPropertyBuilder setDependency(final Supplier<Boolean> dependency) {
        this.dependency = dependency;
        return this;
    }

    @Override
    public NumberProperty build() {
        return new NumberProperty(this.name, this.minimum, this.value, this.maximum, this.dependency);
    }
}
