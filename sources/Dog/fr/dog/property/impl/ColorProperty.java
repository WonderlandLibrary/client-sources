package fr.dog.property.impl;

import fr.dog.property.Property;

import java.awt.*;
import java.util.function.BooleanSupplier;

public final class ColorProperty extends Property<Color> {
    private ColorProperty(String label, Color value, BooleanSupplier dependency) {
        super(label, value, dependency);
    }

    public static ColorProperty newInstance(String label, Color value, BooleanSupplier dependency) {
        return new ColorProperty(label, value, dependency);
    }

    public static ColorProperty newInstance(String label, Color value) {
        return new ColorProperty(label, value, () -> true);
    }
}