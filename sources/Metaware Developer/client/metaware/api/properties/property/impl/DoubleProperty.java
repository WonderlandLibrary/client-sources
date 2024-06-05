package client.metaware.api.properties.property.impl;


import client.metaware.api.properties.property.Property;

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
    public void setValue(Double value) {
        if(this.value != null && !this.value.equals(value)) {
            if(value < min) value = min;
            if(value > max) value = max;
        }
        super.setValue(value);
    }

    @Override
    public Double getValue() {
        return super.getValue();
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double increment() {
        return increment;
    }

    public Representation representation() {
        return representation;
    }
}
