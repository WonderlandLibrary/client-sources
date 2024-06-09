package us.dev.api.property.reactive;

import us.dev.api.property.Property;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author Foundry
 */
public class RxProperty<T> extends Property<T> {
    protected final Queue<ChangeListener<? super T>> changeListeners = new ArrayDeque<>();

    public RxProperty(final String label, final T value) {
        super(label, value);
    }

    @Override
    public void setValue(final T value) {
        final T oldValue = this.value;
        this.value = value;
        changeListeners.forEach(changeListener -> changeListener.accept(oldValue, value));
    }

    public RxProperty<T> withListener(ChangeListener<? super T> listener) {
        changeListeners.add(listener);
        return this;
    }
}
