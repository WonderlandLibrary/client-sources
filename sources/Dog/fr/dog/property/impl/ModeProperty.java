package fr.dog.property.impl;

import fr.dog.property.Property;
import imgui.type.ImInt;
import lombok.Getter;

import java.util.Arrays;
import java.util.function.BooleanSupplier;

@Getter
public final class ModeProperty extends Property<String> {
    private final String[] values;

    private ModeProperty(String label, String[] possibilities, String value, BooleanSupplier dependency) {
        super(label, value, dependency);

        this.values = possibilities;
    }

    public static ModeProperty newInstance(String label, String[] possibilities, String value, BooleanSupplier dependency) {
        return new ModeProperty(label, possibilities, value, dependency);
    }

    public static ModeProperty newInstance(String label, String[] possibilities, String value) {
        return new ModeProperty(label, possibilities, value, () -> true);
    }

    public void setIndexValue(int index) {
        super.setValue(this.values[Math.max(0, Math.min(this.values.length - 1, index))]);
    }

    public int getIndex() {
        return Arrays.asList(values).indexOf(getValue());
    }

    public ImInt getImGuiIndex() {
        return new ImInt(Arrays.asList(values).indexOf(getValue()));
    }

    public boolean is(String query) {
        return this.getValue().equalsIgnoreCase(query);
    }
}