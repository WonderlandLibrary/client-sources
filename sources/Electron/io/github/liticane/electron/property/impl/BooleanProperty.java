package io.github.liticane.electron.property.impl;

import io.github.liticane.electron.property.Property;

import java.util.function.Supplier;

public class BooleanProperty extends Property<Boolean> {
    BooleanProperty(String name, Boolean value, Supplier<Boolean> dependency) {
        super(name, value, dependency);
    }
}