package fr.dog.property.impl;

import fr.dog.property.Property;
import lombok.Getter;
import lombok.Setter;

import java.util.function.BooleanSupplier;

@Getter
public final class NumberProperty extends Property<Float> {

    private final Float minimumValue, maximumValue;
    private final Float increment;
    @Setter
    public float[] floats = new float[1];

    private NumberProperty(final String label, final Float minimumValue, final Float value,
                           final Float maximumValue, final Float increment, final BooleanSupplier dependency) {
        super(label, value, dependency);
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
        this.increment = increment == null ? 1f : increment;
    }

    public static NumberProperty newInstance(final String label, final Float minimumValue, final Float value,
                                             final Float maximumValue, final Float increment, final BooleanSupplier dependency) {
        return new NumberProperty(label, minimumValue, value, maximumValue, increment, dependency);
    }

    public static NumberProperty newInstance(final String label, final Float minimumValue, final Float value,
                                             final Float maximumValue, final Float increment) {
        return new NumberProperty(label, minimumValue, value, maximumValue, increment, () -> true);
    }

    @Override
    public void setValue(Float value) {
        float precision = 1.0F / increment;
        super.setValue(Math.round(Math.max(minimumValue, Math.min(maximumValue, value)) * precision) / precision);
    }
}