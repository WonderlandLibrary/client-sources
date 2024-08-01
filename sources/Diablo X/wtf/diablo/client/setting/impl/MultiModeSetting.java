package wtf.diablo.client.setting.impl;

import wtf.diablo.client.setting.api.AbstractSetting;
import wtf.diablo.client.setting.api.IMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public final class MultiModeSetting<T extends Enum<T> & IMode> extends AbstractSetting<List<T>> {
    private final T[] enumValues;

    @SafeVarargs
    public MultiModeSetting(String name, T[] enumValues, T... selected) {
        super(name, Arrays.asList(selected));
        this.enumValues = enumValues;
    }

    public void select(T value) {
        final List<T> selectedValues = new LinkedList<>(this.getValue());

        if (selectedValues.contains(value)) {
            selectedValues.remove(value);
        } else {
            selectedValues.add(value);
        }

        this.setValue(selectedValues);
    }

    @Override
    public List<T> parse(String value) {
        final String[] values = value.substring(1, value.length() - 1).split(", ");

        final List<T> selectedValues = new ArrayList<>();
        for (final String val : values) {
            for (final T constant : enumValues) {
                if (constant.getName().equalsIgnoreCase(val)) {
                    selectedValues.add(constant);
                }
            }
        }

        return selectedValues;
    }

    public void addValue(final IMode value) {
        final List<T> selectedValues = new LinkedList<>(this.getValue());
        selectedValues.add((T) value);
        this.setValue(selectedValues);
    }

    public void removeValue(final IMode value) {
        final List<T> selectedValues = new LinkedList<>(this.getValue());
        selectedValues.remove(value);
        this.setValue(selectedValues);
    }

    public boolean containsValue(final IMode value) {
        return this.getValue().contains(value);
    }

    public T[] getEnumValues() {
        return this.enumValues;
    }
}