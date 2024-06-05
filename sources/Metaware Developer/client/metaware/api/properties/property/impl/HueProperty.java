package client.metaware.api.properties.property.impl;

import client.metaware.api.properties.property.Property;

import java.util.function.Supplier;

public class HueProperty extends Property<Double> {

    public HueProperty(String label, Double value, Supplier<Boolean> dependency) {
        super(label, value, dependency);
    }

    public HueProperty(String label, Double value) {
        this(label, value, () -> true);
    }

}
