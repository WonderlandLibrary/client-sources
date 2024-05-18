package us.dev.api.property;

import us.dev.api.interfaces.Bounded;

/**
 * @author Foundry
 */
public class BoundedProperty<T extends Comparable<T>> extends Property<T> implements Bounded<T>, Comparable<T> {
    private final T upperBound, lowerBound;

    public BoundedProperty(String label, T lowerBound, T value, T upperBound) {
        super(label, value);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    public void setValue(T value) {
        this.value = clamp(value);
    }

    @Override
    public T upperBound() {
        return this.upperBound;
    }

    @Override
    public T lowerBound() {
        return this.lowerBound;
    }

    @Override
    public int compareTo(T o) {
        return value.compareTo(o);
    }
}
