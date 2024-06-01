package io.github.liticane.electron.property.impl;

import io.github.liticane.electron.structure.Builder;

import java.util.function.Supplier;

public class BooleanPropertyBuilder implements Builder<BooleanProperty> {
    private String name;
    private Boolean value;
    private Supplier<Boolean> dependency;

    public BooleanPropertyBuilder setName(final String name) {
        this.name = name;
        return this;
    }

    public BooleanPropertyBuilder setValue(final Boolean value) {
        this.value = value;
        return this;
    }

    public BooleanPropertyBuilder setDependency(final Supplier<Boolean> dependency) {
        this.dependency = dependency;
        return this;
    }

    @Override
    public BooleanProperty build() {
        return new BooleanProperty(this.name, this.value, this.dependency);
    }
}