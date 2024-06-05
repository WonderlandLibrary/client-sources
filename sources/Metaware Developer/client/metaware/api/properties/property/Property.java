package client.metaware.api.properties.property;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Property<T> {

    protected final Class owner;
    protected final String label;
    public float optionAnim = 0;//present
    public float optionAnimNow = 0;//present
    protected final Supplier<Boolean> dependency;
    protected T value;

    private final List<ValueChangeListener<T>> valueChangeListeners = new ArrayList<>();

    public Property(String label, T value, Supplier<Boolean> dependency) {
        this.label = label;
        this.dependency = dependency;
        this.value = value;
        owner = this.getClass();
    }

    public Property(String label, T value) {
        this(label, value, () -> true);
    }

    public enum Representation {
        INT, DOUBLE, FLOAT, PERCENTAGE, MILLISECONDS, DISTANCE
    }

    public boolean isAvailable() {
        return dependency.get();
    }

    public void addValueChange(ValueChangeListener<T> valueChangeListener) {
        valueChangeListeners.add(valueChangeListener);
    }

    public void setValue(T value) {
        T oldValue = this.value;
        this.value = value;
        if(oldValue != value) {
            for(ValueChangeListener<T> valueChangeListener : valueChangeListeners) {
                valueChangeListener.onValueChange(oldValue, value);
            }
        }
    }

    public void callOnce() {
        for (ValueChangeListener<T> valueChangeListener : valueChangeListeners)
            valueChangeListener.onValueChange(value, value);
    }

    public Class<?> type() {
        return value.getClass();
    }

    public String label() {
        return label;
    }

    public Supplier<Boolean> dependency() {
        return dependency;
    }

    public T getValue() {
        return value;
    }

    public Object getValue2() {
        return value;
    }

    public Object owner() {
        return owner;
    }

    public List<ValueChangeListener<T>> valueChangeListeners() {
        return valueChangeListeners;
    }
}
