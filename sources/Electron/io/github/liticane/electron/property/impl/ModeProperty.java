package io.github.liticane.electron.property.impl;

import io.github.liticane.electron.property.Property;
import lombok.Getter;

import java.util.List;
import java.util.function.Supplier;

@Getter
public class ModeProperty extends Property<String> {
    private final List<String> values;

    public ModeProperty(String name, String value, List<String> values, Supplier<Boolean> dependency) {
        super(name, value, dependency);
        this.values = values;
    }
}