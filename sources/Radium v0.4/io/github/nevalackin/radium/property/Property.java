package io.github.nevalackin.radium.property;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Property<T> {

    protected final String label;
    protected final Supplier<Boolean> dependency;
    private final List<ValueChangeListener<T>> valueChangeListeners = new ArrayList<>();
    protected T value;

    public Property(String label, T value, Supplier<Boolean> dependency) {
        this.label = label;
        this.value = value;
        this.dependency = dependency;
    }

    public Property(String label, T value) {
        this(label, value, () -> true);
    }

    public void addValueChangeListener(ValueChangeListener<T> valueChangeListener) {
        valueChangeListeners.add(valueChangeListener);
    }

    public boolean isAvailable() {
        return dependency.get();
    }

    public String getLabel() {
        return label;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        T oldValue = this.value;
        this.value = value;
        if (oldValue != value) {
            for (ValueChangeListener<T> valueChangeListener : valueChangeListeners)
                valueChangeListener.onValueChange(oldValue, value);
        }
    }

    public void callFirstTime() {
        for (ValueChangeListener<T> valueChangeListener : valueChangeListeners)
            valueChangeListener.onValueChange(value, value);
    }

    public Class<?> getType() {
        return value.getClass();
    }
}
