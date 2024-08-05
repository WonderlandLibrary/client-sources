package fr.dog.property.impl;

import fr.dog.property.Property;

import java.util.function.BooleanSupplier;

public final class BooleanProperty extends Property<Boolean> {
    private BooleanProperty(final String label, final Boolean value, final BooleanSupplier dependency) {
        super(label, value, dependency);
    }

    public static BooleanProperty newInstance(final String label, final Boolean value, final BooleanSupplier dependency) {
        return new BooleanProperty(label, value, dependency);
    }

    public static BooleanProperty newInstance(final String label, final Boolean value) {
        return new BooleanProperty(label, value, () -> true);
    }
}