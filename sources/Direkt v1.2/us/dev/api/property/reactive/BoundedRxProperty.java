package us.dev.api.property.reactive;

import us.dev.api.interfaces.Bounded;

/**
 * @author Foundry
 */
public class BoundedRxProperty<T extends Comparable<T>> extends RxProperty<T> implements Bounded<T>, Comparable<T> {
    private final T upperBound, lowerBound;

    public BoundedRxProperty(String label, T lowerBound, T value, T upperBound) {
        super(label, value);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    public void setValue(T value) {
        super.setValue(clamp(value));
    }

    @Override
    public T upperBound() {
        return this.upperBound;
    }

    @Override
    public T lowerBound() {
        return this.lowerBound;
    }

    public BoundedRxProperty<T> withListener(ChangeListener<? super T> listener) {
        changeListeners.add(listener);
        return this;
    }

    @Override
    public int compareTo(T o) {
        return value.compareTo(o);
    }
}
