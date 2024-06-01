package io.github.liticane.electron.property.impl;

import io.github.liticane.electron.structure.Builder;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class ModePropertyBuilder implements Builder<ModeProperty> {
    private String name, value;
    private List<String> values;
    private Supplier<Boolean> dependency;

    public ModePropertyBuilder setName(final String name) {
        this.name = name;
        return this;
    }

    public ModePropertyBuilder setValue(final String value) {
        this.value = value;
        return this;
    }

    public ModePropertyBuilder setValues(final String[] values) {
        this.values = Arrays.asList(values);
        return this;
    }

    public ModePropertyBuilder setDependency(final Supplier<Boolean> dependency) {
        this.dependency = dependency;
        return this;
    }

    @Override
    public ModeProperty build() {
        return new ModeProperty(this.name, this.value, this.values, dependency);
    }
}