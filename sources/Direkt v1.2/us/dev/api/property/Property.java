package us.dev.api.property;

import us.dev.api.interfaces.Labeled;

/**
 * Created by Foundry on 11/15/2015.
 */
public class Property<T> implements Labeled {
    private String label;
    protected T value;

    public Property(final String label, final T value) {
        this.label = label;
        this.value = value;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    public T getValue() {
        return this.value;
    }

    public Class<? extends T> getType() {
        return (Class<? extends T>) value.getClass();
    }

    public void setValue(final T value) {
        this.value = value;
    }
}
