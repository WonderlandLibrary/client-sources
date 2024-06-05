package me.dinozoid.strife.property.implementations;

import me.dinozoid.strife.property.Property;

import java.util.function.Supplier;

public class DoubleProperty extends Property<Double> {

    private final double min;
    private final double max;
    private final double increment;
    private final Representation representation;

    public DoubleProperty(String label, double value, double min, double max, double increment, Representation representation, Supplier<Boolean> dependency) {
        super(label, value, dependency);
        this.min = min;
        this.max = max;
        this.increment = increment;
        this.representation = representation;
    }

    public DoubleProperty(String label, double value, double min, double max, double increment, Supplier<Boolean> dependency) {
        this(label, value, min, max, increment, Representation.DOUBLE, dependency);
    }

    public DoubleProperty(String label, double value, double min, double max, double increment, Representation representation) {
        this(label, value, min, max, increment, representation, () -> true);
    }

    public DoubleProperty(String label, double value, double min, double max, double increment) {
        this(label, value, min, max, increment, Representation.DOUBLE, () -> true);
    }

    @Override
    public void value(Double value) {
        if(this.value != null && !this.value.equals(value)) {
            if(value < min) value = min;
            if(value > max) value = max;
        }
        super.value(value);
    }

    @Override
    public Double value() {
        return super.value();
    }

    public double min() {
        return min;
    }

    public double max() {
        return max;
    }

    public double increment() {
        return increment;
    }

    public Representation representation() {
        return representation;
    }
}
