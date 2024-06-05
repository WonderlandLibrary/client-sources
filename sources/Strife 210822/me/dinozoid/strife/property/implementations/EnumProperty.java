package me.dinozoid.strife.property.implementations;

import me.dinozoid.strife.property.Property;

import java.util.function.Supplier;

public class EnumProperty<T extends Enum> extends Property<T> {

    private final T[] values;

    public EnumProperty(String label, T value, Supplier<Boolean> dependency) {
        super(label, value, dependency);
        this.values = (T[]) value.getClass().getEnumConstants();
    }

    public EnumProperty(String label, T value) {
        this(label, value, () -> true);
    }

    public void value(int index) {
        value(values[index]);
    }

    public T[] values() {
        return values;
    }
}
