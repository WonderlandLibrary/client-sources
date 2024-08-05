package fr.dog.property;

import lombok.Getter;
import lombok.Setter;

import java.util.function.BooleanSupplier;

@Getter
public abstract class Property<T> {
    private final String label;
    @Setter private T value;
    private final BooleanSupplier dependency;

    public Property(String label, T value, BooleanSupplier dependency) {
        this.label = label;
        this.value = value;
        this.dependency = dependency;
    }

    public boolean isVisible() {
        return dependency == null || dependency.getAsBoolean();
    }
}