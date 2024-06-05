package me.dinozoid.strife.property.implementations;

import me.dinozoid.strife.property.Property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class MultiSelectEnumProperty<T extends Enum<T>> extends Property<List<T>> {

    private final T[] values;

    public MultiSelectEnumProperty(String label, Supplier<Boolean> dependency, T... values) {
        super(label, Arrays.asList(values), dependency);
        if (values.length == 0) throw new RuntimeException("Must have at least one default value.");
        this.values = (T[]) value.get(0).getClass().getEnumConstants();
    }

    public MultiSelectEnumProperty(String label, T... values) {
        this(label, () -> true, values);
    }

    public boolean selected(T variant) {
        return value().contains(variant);
    }

    public void value(int index) {
        final List<T> copyValues = new ArrayList<>(this.value);
        final T referencedVariant = this.values[index];
        if(copyValues.contains(referencedVariant)) copyValues.remove(referencedVariant);
        else copyValues.add(referencedVariant);
        value(copyValues);
    }

    public T[] values() {
        return values;
    }
}
