package dev.excellent.impl.value;

import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.client.module.api.Module;
import dev.excellent.impl.value.mode.Mode;
import lombok.Data;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

@Data
public abstract class Value<T> implements IAccess {

    private final String name;
    public BooleanSupplier hideIf;
    private T value;
    private boolean visible;
    private Module parent;

    private Consumer<T> valueChangeConsumer;
    private final T defaultValue;

    public Value(final String name, final T defaultValue) {
        this.name = name;
        this.hideIf = null;
        this.defaultValue = defaultValue;
        this.setValue(defaultValue);
    }

    public Value(final String name, final Module parent, final T defaultValue) {
        this.name = name;
        this.hideIf = null;
        this.parent = parent;
        this.defaultValue = defaultValue;
        this.setValue(defaultValue);
        parent.getValues().add(this);
    }

    public Value(final String name, final Mode<?> parent, final T defaultValue) {
        this.name = name;
        this.hideIf = null;
        this.defaultValue = defaultValue;
        this.setValue(defaultValue);
        parent.getValues().add(this);
    }

    public Value(final String name, final Module parent, final T defaultValue, final BooleanSupplier hideIf) {
        this.name = name;
        this.hideIf = hideIf;
        this.parent = parent;
        this.defaultValue = defaultValue;
        this.setValue(defaultValue);
        parent.getValues().add(this);
    }

    public Value(final String name, final Mode<?> parent, final T defaultValue, final BooleanSupplier hideIf) {
        this.name = name;
        this.hideIf = hideIf;
        this.defaultValue = defaultValue;
        this.setValue(defaultValue);
        parent.getValues().add(this);
    }

    public void setValueAsObject(final Object value) {
        if (this.valueChangeConsumer != null) this.valueChangeConsumer.accept((T) value);
        this.value = (T) value;
    }

    public void setValue(final T value) {
        if (this.valueChangeConsumer != null) this.valueChangeConsumer.accept(value);
        this.value = value;
    }

    public abstract List<Value<?>> getSubValues();
}